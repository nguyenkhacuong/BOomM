package boom.gameobject;

import boom.sound.GameSound;
import boom.effect.Animation;
import boom.effect.Data;
import boom.effect.FrameImage;
import boom.interfaces.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.*;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public abstract class Bomber {
    // Variable position X
    protected float posX;
    // Variable position Y
    protected float posY;
    // Variable bomber's width
    protected float width;
    // Variable bomber's height
    protected float height;
    // Variable speed X
    protected float speedX;
    // Variable speed Y
    protected float speedY;
    // Variable bomber's direction
    protected int direction = 0;
    // Variable heart and score
    protected int heart, score;

    // Variable max size of bomber
    public static final int MAX_SIZE = 8;
    // Variable max quantity boom of bomber
    public static final int MAX_QUANTITY = 8;
    // Variable max speed of bomber
    public static final int MAX_SPEED = 6;

    // Variable present size
    protected int size;
    // Variable present quantity
    protected int quantity;
    // Variable present speed
    protected int speed;
    // Variable booms can put
    protected int count = 0;

    public final int DIR_DOWN = 1;
    public final int DIR_UP = 2;
    public final int DIR_RIGHT = 3;
    public final int DIR_LEFT = 4;

    // Variable bomber's status
    public static boolean ALIVE = true;

    GameWorld gameWorld;

    private ArrayList<String> name;

    Animation animation1, animation2, animation3, animation4;
    FrameImage frameImage1, frameImage2, frameImage3, frameImage4;

    private BufferedImage dead;


    private boolean isStop = false;
    protected int code;

    // Variable past position
    protected int tempX, tempY;
    public boolean check = false;


    // Constructor
    public Bomber(float posX, float posY, float width, float height, GameWorld gameWorld) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.gameWorld = gameWorld;

        try {
            dead = ImageIO.read(new File("./Character/bebong_dead.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // Update new bomber's position
    public void update(){
        setPosX(getPosX() + speedX);
        setPosY(getPosY() + speedY);


    };

    // Draw bomber with direction
    public void checkDraw(Graphics2D g2){
        // Srt if bomber is moving
        if(code == KeyEvent.VK_DOWN && speedY != 0) {
            animation2.draw((int) (posX), (int) (posY), g2);
            setDirection(DIR_DOWN);
        }
        if(code == KeyEvent.VK_UP && speedY != 0) {
            animation1.draw((int) (posX), (int) (posY), g2);
            setDirection(DIR_UP);
        }
        if(code == KeyEvent.VK_RIGHT && speedX != 0) {
            animation3.draw((int) (posX), (int) (posY), g2);
            setDirection(DIR_RIGHT);
        }

        if(code == KeyEvent.VK_LEFT && speedX != 0) {
            animation4.draw((int) (posX), (int) (posY), g2);
            setDirection(DIR_LEFT);
        }
        // If bomber stop
        if(direction == 0) frameImage1.draw(g2, (int) posX, (int) posY);
        if(speedY == 0 && direction == DIR_DOWN) frameImage1.draw(g2, (int) posX, (int) posY);
        if(speedY == 0 && direction == DIR_UP) frameImage4.draw(g2, (int) posX, (int) posY);
        if(speedX == 0 && direction == DIR_RIGHT) frameImage3.draw(g2, (int) posX, (int) posY);
        if(speedX == 0 && direction == DIR_LEFT) frameImage2.draw(g2, (int) posX, (int) posY);
    }

    // Set time to draw next frame
    public void setTime(){
        animation1.updateFrame(System.nanoTime());
        animation2.updateFrame(System.nanoTime());
        animation3.updateFrame(System.nanoTime());
        animation4.updateFrame(System.nanoTime());
    }

    // Inogre frame when moving
    public void inogre(){
        animation1.setInogreFrame(0);
        animation1.setInogreFrame(1);
        animation1.setInogreFrame(2);
        animation2.setInogreFrame(0);
        animation2.setInogreFrame(1);
        animation2.setInogreFrame(2);
        animation3.setInogreFrame(0);
        animation3.setInogreFrame(1);
        animation3.setInogreFrame(2);
        animation4.setInogreFrame(0);
        animation4.setInogreFrame(1);
        animation4.setInogreFrame(2);
    }

    // Draw bomber
    public void draw(Graphics2D g2){
        if (ALIVE = true) {
            // Check is bomber impact wall
            if (bomberImpactWall() == false) {
                setPosX(getPosX() - speedX);
                setPosY(getPosY() - speedY);
                setSpeed(0);
            }
            // Check is bomber impact item
            bomberImpactItem();
            // Check is bomber impact monster
            bomberImpactMonster();
            // Put boom
            if (check == true && (PhysicalMap.map[(int)posY/50][(int)posX/50] == 0) && count < quantity){
                PhysicalMap.booms[(int)posY/50][(int)posX/50] = new Boom((int)posX/50, (int)posY/50, size, quantity, gameWorld);
                PhysicalMap.booms[(int)posY/50][(int)posX/50].setPut(true);
                // Assign value present position to tempX, tempY
                tempX = (int)posX/50;
                tempY = (int)posY/50;
                // Assign value to map
                PhysicalMap.map[tempY][tempX] = -11;
                count++;

                check = false;
            } else {
                check = false;
            }

            // Reset map in position if boom is explosive
            for (int i = 0; i < PhysicalMap.booms.length; i++) {
                for (int j = 0; j < PhysicalMap.booms[0].length; j++) {
                    if (PhysicalMap.booms[i][j].isExplosive == true) {
                        if (count > 0) {
                            count--;
                        }
                        PhysicalMap.map[i][j] = 0;
                        check = false;
                    }
                }
            }
            checkDraw(g2);
            setTime();
            inogre();
        }

    }

    public void drawdead(Graphics2D g2){
        g2.drawImage(dead, (int) posX-25, (int) posY-25, null);
    }

    public boolean bomberImpactWall(){
            for (int i = 0; i < PhysicalMap.map.length; i++) {
                for (int j = 0; j < PhysicalMap.map[0].length; j++) {
                    if (PhysicalMap.map[i][j] > 0 || PhysicalMap.map[i][j] == -10) {
                        if (direction == DIR_LEFT) {
                            Rectangle rectangle1 = new Rectangle(j * 50, i * 50, 50, 50);
                            Rectangle rectangle2 = new Rectangle((int) (posX - width), (int) (posY - height / 2), (int) width, (int) height);
                            if (rectangle1.intersects(rectangle2)) return false;
                        } else if (direction == DIR_RIGHT) {
                            Rectangle rectangle1 = new Rectangle(j * 50, i * 50, 50, 50);
                            Rectangle rectangle2 = new Rectangle((int) (posX), (int) (posY - height / 2), (int) width, (int) height);
                            if (rectangle1.intersects(rectangle2)) return false;
                        } else if (direction == DIR_DOWN) {
                            Rectangle rectangle1 = new Rectangle(j * 50, i * 50, 50, 50);
                            Rectangle rectangle2 = new Rectangle((int) (posX - width / 2), (int) ((posY+12) - (height-12) / 25), (int) width, (int) height-12);
                            if (rectangle1.intersects(rectangle2)) return false;
                        } else if (direction == DIR_UP) {
                            Rectangle rectangle1 = new Rectangle(j * 50, i * 50, 50, 50);
                            Rectangle rectangle2 = new Rectangle((int) (posX - width / 2), (int) (posY - height), (int) width, (int) height);
                            if (rectangle1.intersects(rectangle2)) return false;
                        }
                    }
                }
            }

        return true;
    }

    // Set new position
    public void setNew(float xNew, float yNew){
        this.posX = xNew;
        this.posY = yNew;
        this.ALIVE = true;
    }

    public abstract void reset(float xNew, float yNew);


    public void bomberImpactItem(){
            if (PhysicalMap.map[(int) posY / 50][(int) posX / 50] == -1) {
                if (quantity < MAX_QUANTITY) quantity++;
                PhysicalMap.map[(int) posY / 50][(int) posX / 50] = 0;
                GameSound.instance.getAudio(GameSound.ITEM).play();
            }
            if (PhysicalMap.map[(int) posY / 50][(int) posX / 50] == -2) {
                if (size < MAX_SIZE) size++;
                PhysicalMap.map[(int) posY / 50][(int) posX / 50] = 0;
                GameSound.instance.getAudio(GameSound.ITEM).play();
            }
            if (PhysicalMap.map[(int) posY / 50][(int) posX / 50] == -3) {
                if (speed < MAX_SPEED) speed++;
                PhysicalMap.map[(int) posY / 50][(int) posX / 50] = 0;
                GameSound.instance.getAudio(GameSound.ITEM).play();
            }
    }

    public void bomberImpactMonster(){
        if (GameWorld.level == 1) {
            for (Moster moster : gameWorld.mosters) {
                if ((int) posX / 50 == (int) (moster.posX) / 50 && (int) posY / 50 == (int) moster.posY / 50) {
                    if (heart > 0) {
                        heart--;
                    }
                    GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
                    ALIVE = false;
                    reset(150, 625);
                }
            }
        }
        if (GameWorld.level == 2) {
            for (Haitacto haitacto : gameWorld.haitactos) {
                if ((int) posX / 50 == (int) (haitacto.posX) / 50 && (int) posY / 50 == (int) haitacto.posY / 50) {
                    if (heart > 0) {
                        heart--;
                    }
                    GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
                    ALIVE = false;
                    reset(1120, 625);
                }
            }
        }
        if (GameWorld.level == 3) {
            for (KhungLongXanh khungLongXanh : gameWorld.khungLongXanhs) {
                if ((int) posX / 50 == (int) (khungLongXanh.posX) / 50 && (int) posY / 50 == (int) khungLongXanh.posY / 50) {
                    if (heart > 0) {
                        heart--;
                    }
                    GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
                    ALIVE = false;
                    reset(510, 625);
                }
            }
        }

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (0 < size && size <= MAX_SIZE) {
            this.size = size;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (0 < quantity && quantity <= MAX_QUANTITY) {
            this.quantity = quantity;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (0 < speed && speed <= MAX_SPEED) {
            this.speed = speed;
        }

    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
