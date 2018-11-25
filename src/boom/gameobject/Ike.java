package boom.gameobject;

import boom.effect.Animation;
import boom.effect.Data;
import boom.effect.FrameImage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Ike extends Bomber {
    private ArrayList<String> ike;

    public Ike(float posX, float posY, float width, float height, GameWorld gameWorld) {
        super(posX, posY, width, height, gameWorld);
        this.size = 2;
        this.quantity = 2;
        this.speed = 2;
        this.heart = 3;
        this.score = 0;
        this.ALIVE = true;
        ike = new ArrayList<>();
        ike.add("ikeup");
        ike.add("ikedown");
        ike.add("ikeright");
        ike.add("ikeright");
        ike.add("ike13");
        ike.add("ike23");
        ike.add("ike33");
        ike.add("ike43");

        animation1 = new Animation(Data.getInstance().getAnimation(ike.get(0)));
        animation2 = new Animation(Data.getInstance().getAnimation(ike.get(1)));
        animation3 = new Animation(Data.getInstance().getAnimation(ike.get(2)));
        animation4 = new Animation(Data.getInstance().getAnimation(ike.get(3)));
        animation4.flipAllImage();

        animation1.updateFrame(350*1000000);
        animation2.updateFrame(350*1000000);
        animation3.updateFrame(350*1000000);
        animation4.updateFrame(350*1000000);

        frameImage1 = new FrameImage(Data.getInstance().getFrameImage(ike.get(4)));
        frameImage2 = new FrameImage(Data.getInstance().getFrameImage(ike.get(5)));
        frameImage3 = new FrameImage(Data.getInstance().getFrameImage(ike.get(6)));
        frameImage4 = new FrameImage(Data.getInstance().getFrameImage(ike.get(7)));
    }

    public void checkDraw(Graphics2D g2){
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
        if(direction == 0) frameImage1.draw(g2, (int) posX, (int) posY);
        if(speedY == 0 && direction == DIR_DOWN) frameImage1.draw(g2, (int) posX, (int) posY);
        if(speedY == 0 && direction == DIR_UP) frameImage4.draw(g2, (int) posX, (int) posY);
        if(speedX == 0 && direction == DIR_RIGHT) frameImage3.draw(g2, (int) posX, (int) posY);
        if(speedX == 0 && direction == DIR_LEFT) frameImage2.draw(g2, (int) posX, (int) posY);
    }

    public void setTime(){
        animation1.updateFrame(System.nanoTime());
        animation2.updateFrame(System.nanoTime());
        animation3.updateFrame(System.nanoTime());
        animation4.updateFrame(System.nanoTime());
    }

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

    public void draw(Graphics2D g2){
        if (ALIVE = true) {
            if (bomberImpactWall() == false) {
                setPosX(getPosX() - speedX);
                setPosY(getPosY() - speedY);
                setSpeed(0);
            }
            bomberImpactItem();
            bomberImpactMonster();


            if (check == true && (PhysicalMap.map[(int)posY/50][(int)posX/50] == 0) && count < quantity){
                if (PhysicalMap.map[tempY][tempX] == -11) PhysicalMap.map[tempY][tempX] = -10;
                PhysicalMap.booms[(int)posY/50][(int)posX/50] = new Boom((int)posX/50, (int)posY/50, size, quantity, gameWorld);
                PhysicalMap.booms[(int)posY/50][(int)posX/50].setPut(true);
                PhysicalMap.map[(int)posY/50][(int)posX/50] = -11;
                tempX = (int)posX/50;
                tempY = (int)posY/50;

                count++;

                check = false;
            } else {
                check = false;
            }

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
    @Override
    public void update() {
        setPosX(getPosX() + speedX);
        setPosY(getPosY() + speedY);

        if (PhysicalMap.map[tempY][tempX] == -11 && PhysicalMap.map[tempY][tempX] != PhysicalMap.map[(int)getPosY()/50][(int)getPosX()/50]){
            PhysicalMap.map[tempY][tempX] = -10;

        }
    }

    public void reset(float xNew, float yNew){
        if (heart > 0) {
            this.size = 2;
            this.quantity = 2;
            this.speed = 2;
            this.posX = xNew;
            this.posY = yNew;
            ALIVE = true;
        } else {
            ALIVE = false;
        }
    }
}
