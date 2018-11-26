package boom.gameobject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Moster extends Bomber{
    private int speeds;
    private int moves;
    private BufferedImage imageup, imagedown, imageright, imageleft;
    public boolean LIVE = true;
    private Random random;

    public Moster(float posX, float posY, float width, float height,int direction, int speeds, GameWorld gameWorld){
        super(posX, posY, width, height, gameWorld);
        random = new Random();
        this.direction = direction;
        this.speeds = speeds;

        try {
            imageup = ImageIO.read(new File("./Character/haitacnholen.png"));
            imagedown = ImageIO.read(new File("./Character/haitacnhoxuong.png"));
            imageright = ImageIO.read(new File("./Character/haitacnhophai.png"));
            imageleft = ImageIO.read(new File("./Character/haitacnhotrai.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        if (direction == DIR_DOWN) {
            if (mostervsWall() == false) {
                setSpeedY(0);
                int move = 1 + random.nextInt(3);
                switch (move) {
                    case 1:
                        setDirection(DIR_RIGHT);
                        g2.drawImage(imageright, (int) posX - (imageright.getWidth()) / 2, (int) posY - (imageright.getHeight()) / 2, null);
                        break;
                    case 2:
                        setDirection(DIR_LEFT);
                        g2.drawImage(imageleft, (int) posX - (imageleft.getWidth()) / 2, (int) posY - (imageleft.getHeight()) / 2, null);
                        break;
                    case 3:
                        setDirection(DIR_UP);
                        g2.drawImage(imageup, (int) posX - (imageup.getWidth()) / 2, (int) posY - (imageup.getHeight()) / 2, null);
                        break;
                    default:
                        break;
                }

            } else {
                setSpeedX(0);
                setSpeedY(speeds);
                g2.drawImage(imagedown, (int) posX - (imagedown.getWidth()) / 2, (int) posY - imagedown.getHeight() / 2, null);
            }
        } else if (direction == DIR_UP) {
            if (mostervsWall() == false) {
                setSpeedY(0);
                int move = 1 + random.nextInt(3);
                switch (move) {
                    case 1:
                        setDirection(DIR_LEFT);
                        g2.drawImage(imageleft, (int) posX - (imageleft.getWidth()) / 2, (int) posY - (imageleft.getHeight()) / 2, null);
                        break;
                    case 2:
                        setDirection(DIR_RIGHT);
                        g2.drawImage(imageright, (int) posX - (imageright.getWidth()) / 2, (int) posY - (imageright.getHeight()) / 2, null);
                        break;
                    case 3:
                        setDirection(DIR_DOWN);
                        g2.drawImage(imagedown, (int) posX - (imagedown.getWidth()) / 2, (int) posY - imagedown.getHeight() / 2, null);
                        break;
                    default:
                        break;
                }
            } else {
                setSpeedX(0);
                setSpeedY(speeds * -1);
                g2.drawImage(imageup, (int) posX - (imageup.getWidth()) / 2, (int) posY - (imageup.getHeight()) / 2, null);
            }
        } else if (direction == DIR_RIGHT) {
            if (mostervsWall() == false) {
                setSpeedX(0);
                int move = 1 + random.nextInt(3);
                switch (move) {
                    case 1:
                        setDirection(DIR_UP);
                        g2.drawImage(imageup, (int) posX - (imageup.getWidth()) / 2, (int) posY - (imageup.getHeight()) / 2, null);
                        break;
                    case 2:
                        setDirection(DIR_DOWN);
                        g2.drawImage(imagedown, (int) posX - (imagedown.getWidth()) / 2, (int) posY - imagedown.getHeight() / 2, null);
                        break;
                    case 3:
                        setDirection(DIR_LEFT);
                        g2.drawImage(imageleft, (int) posX - (imageleft.getWidth()) / 2, (int) posY - (imageleft.getHeight()) / 2, null);
                        break;
                    default:
                        break;
                }
            } else {
                setSpeedY(0);
                setSpeedX(speeds);
            }
            g2.drawImage(imageright, (int) posX - (imageright.getWidth()) / 2, (int) posY - (imageright.getHeight()) / 2, null);
        } else if (direction == DIR_LEFT) {
            if (mostervsWall() == false) {
                setSpeedX(0);
                int move = 1 + random.nextInt(3);
                switch (move) {
                    case 1:
                        setDirection(DIR_DOWN);
                        g2.drawImage(imagedown, (int) posX - (imagedown.getWidth()) / 2, (int) posY - imagedown.getHeight() / 2, null);
                        break;
                    case 2:
                        setDirection(DIR_UP);
                        g2.drawImage(imageup, (int) posX - (imageup.getWidth()) / 2, (int) posY - (imageup.getHeight()) / 2, null);
                        break;
                    case 3:
                        setDirection(DIR_RIGHT);
                        g2.drawImage(imageright, (int) posX - (imageright.getWidth()) / 2, (int) posY - (imageright.getHeight()) / 2, null);
                        break;
                    default:
                        break;
                }
            } else {
                setSpeedY(0);
                setSpeedX(speeds * -1);
                g2.drawImage(imageleft, (int) posX - (imageleft.getWidth()) / 2, (int) posY - (imageleft.getHeight()) / 2, null);
            }
        }


    }

    @Override
    public void reset(float xNew, float yNew) {

    }

    @Override
    public void update() {
        setPosX(getPosX() + speedX);
        setPosY(getPosY() + speedY);
    }

    public boolean mostervsWall(){
            for (int i = 0; i < gameWorld.maps.map.length; i++) {
                for (int j = 0; j < gameWorld.maps.map[0].length; j++) {
                    if (gameWorld.maps.map[i][j] > 0 || PhysicalMap.map[i][j] == -10 || PhysicalMap.map[i][j] == -11) {
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
                            Rectangle rectangle2 = new Rectangle((int) (posX - width / 2), (int) (posY - height / 25), (int) width, (int) height);
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

    public int getSpeeds() {
        return speeds;
    }

    public void setSpeeds(int speeds) {
        this.speeds = speeds;
    }

}
