package boom.gameobject;

import boom.effect.Animation;
import boom.effect.Data;
import boom.effect.FrameImage;

import java.awt.*;
import java.util.ArrayList;

public class Plunk extends Bomber{
    private ArrayList<String> plunk;

    public Plunk(float posX, float posY, float width, float height, GameWorld gameWorld) {
        super(posX, posY, width, height, gameWorld);
        this.size = 2;
        this.quantity = 1;
        this.speed = 3;
        this.heart = 3;
        this.score = 0;
        this.ALIVE = true;

        plunk = new ArrayList<>();
        plunk.add("plunkup");
        plunk.add("plunkdown");
        plunk.add("plunkright");
        plunk.add("plunkright");
        plunk.add("plunk13");
        plunk.add("plunk23");
        plunk.add("plunk33");
        plunk.add("plunk43");


        animation1 = new Animation(Data.getInstance().getAnimation(plunk.get(0)));
        animation2 = new Animation(Data.getInstance().getAnimation(plunk.get(1)));
        animation3 = new Animation(Data.getInstance().getAnimation(plunk.get(2)));
        animation4 = new Animation(Data.getInstance().getAnimation(plunk.get(3)));
        animation4.flipAllImage();

        animation1.updateFrame(350*1000000);
        animation2.updateFrame(350*1000000);
        animation3.updateFrame(350*1000000);
        animation4.updateFrame(350*1000000);

        frameImage1 = new FrameImage(Data.getInstance().getFrameImage(plunk.get(4)));
        frameImage2 = new FrameImage(Data.getInstance().getFrameImage(plunk.get(5)));
        frameImage3 = new FrameImage(Data.getInstance().getFrameImage(plunk.get(6)));
        frameImage4 = new FrameImage(Data.getInstance().getFrameImage(plunk.get(7)));
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
    @Override
    public void reset(float xNew, float yNew){
        if (heart > 0) {
            this.size = 2;
            this.quantity = 1;
            this.speed = 3;
            this.posX = xNew;
            this.posY = yNew;
            ALIVE = true;
        } else {
            ALIVE = false;
        }
    }


}
