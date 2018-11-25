package boom.gameobject;

import boom.effect.Data;
import boom.interfaces.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PhysicalMap{
    public static int[][] map, map2, map3;
    int titleSize;
    BufferedImage image, image2, image3, image4, image3_1;
    BufferedImage da, go, out12, out17, out10, out14, out13, out16, out11, out15, namdo, namxanh, buicay, go2;
    Image image5, image6, image7;
    private float posX, posY;
    private GameWorld gameWorld;
    BufferedImage imageBG, imageBG2, imageBG3;

    public static Boom[][] booms;

    public PhysicalMap(GameWorld gameWorld){
        booms = new Boom[14][24];
                try {
                    imageBG = ImageIO.read(new File("./Character/BG1.jpg"));
                    imageBG2 = ImageIO.read(new File("./Character/BGmap2.png"));
                    imageBG3 = ImageIO.read(new File("./Character/BGmap3.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }



        this.titleSize = 50;
        this.gameWorld = gameWorld;
        map = Data.getInstance().getMap();
        try {
            image = ImageIO.read(new File("./Character/box1.png"));
            image2 = ImageIO.read(new File("./Character/wood.png"));
            image3 = ImageIO.read(new File("./Character/cay.png"));
            image3_1 = ImageIO.read(new File("./Character/ngon.png"));
            image4 = ImageIO.read(new File("./Character/buico.png"));
            image5 = new ImageIcon("./Character/itembomb.gif").getImage();
            image6 = new ImageIcon("./Character/itemsize.gif").getImage();
            image7 = new ImageIcon("./Character/itemshoe.gif").getImage();

            out10 = ImageIO.read(new File("./Character/10.png"));
            out11 = ImageIO.read(new File("./Character/11.png"));
            out12 = ImageIO.read(new File("./Character/12.png"));
            out13 = ImageIO.read(new File("./Character/13.png"));
            out14 = ImageIO.read(new File("./Character/14.png"));
            out15 = ImageIO.read(new File("./Character/15.png"));
            out16 = ImageIO.read(new File("./Character/16.png"));
            out17 = ImageIO.read(new File("./Character/17.png"));
            da = ImageIO.read(new File("./Character/da.png"));
            go = ImageIO.read(new File("./Character/go.png"));
            go2 = ImageIO.read(new File("./Character/go2.png"));
            namdo = ImageIO.read(new File("./Character/namdo.png"));
            namxanh = ImageIO.read(new File("./Character/namxanh.png"));
            buicay = ImageIO.read(new File("./Character/buicay.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getTitleSize(){
        return titleSize;
    }

    public void resetMap(){
        map = Data.getInstance().getMap();
    }

    public void draw(Graphics2D g2) {
        if (GameWorld.level == 1) {
            g2.drawImage(imageBG, 0, 0, imageBG.getWidth(), imageBG.getHeight(), null);
        } else if (GameWorld.level == 2){
            g2.drawImage(imageBG2, 0, 0, imageBG.getWidth(), imageBG.getHeight(), null);
        } else if (GameWorld.level == 3){
            g2.drawImage(imageBG3, 0, 0, imageBG.getWidth(), imageBG.getHeight(), null);
        }
            g2.setColor(Color.PINK);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 1) {
                    g2.drawImage(image, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 7) {
                    g2.drawImage(image2, j * titleSize, i * titleSize, null);
                }  else if (map[i][j] == 3) {
                    g2.drawImage(image3, j * titleSize, i * titleSize - 30, null);
                }else if (map[i][j] == 4) {
                    g2.drawImage(image4, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == -3) {
                    g2.drawImage(image7, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == -1) {
                    g2.drawImage(image5, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == -2) {
                    g2.drawImage(image6, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 10) {
                    g2.drawImage(out10, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 11) {
                    g2.drawImage(out11, j * titleSize, i * titleSize, null);
                }  else if (map[i][j] == 12) {
                    g2.drawImage(out12, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 13) {
                    g2.drawImage(out13, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 14) {
                    g2.drawImage(out14, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 15) {
                    g2.drawImage(out15, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 16) {
                    g2.drawImage(out16, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 17) {
                    g2.drawImage(out17, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 20) {
                    g2.drawImage(buicay, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 24) {
                    g2.drawImage(namxanh, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 21) {
                    g2.drawImage(da, j * titleSize, i * titleSize, null);
                } else if (map[i][j] == 22) {
                    g2.drawImage(go, j * titleSize, i * titleSize - 21, null);
                } else if (map[i][j] == 23) {
                    g2.drawImage(namdo, j * titleSize-1, i * titleSize-1, null);
                }
            }
        }
    }


    public void draw2(Graphics2D g2){
        g2.setColor(Color.PINK);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 3)
                    g2.drawImage(image3_1, j * titleSize, i * titleSize - 30, null);
                else if (map[i][j] == 22)
                    g2.drawImage(go2, j * titleSize, i * titleSize - 21, null);

            }
        }
    }

    public void drawInfo(Graphics2D g2d) {
        Image imgInfor = new ImageIcon("./Character/gach.png").getImage();
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.RED);
        g2d.drawImage(imgInfor, 1200, 0, null);
        Image heart = new ImageIcon(
                "./Character/heart_1.png").getImage();
        Image cancel = new ImageIcon("./Character/cancel1.png").getImage();
        if (gameWorld.bomber.getHeart() == 3) {
            g2d.drawImage(heart, 1250, 120, null);
            g2d.drawImage(heart, 1275, 120, null);
            g2d.drawImage(heart, 1300, 120, null);
        }
        if (gameWorld.bomber.getHeart() == 2) {
            g2d.drawImage(heart, 1250, 120, null);
            g2d.drawImage(heart, 1275, 120, null);
        }
        if (gameWorld.bomber.getHeart() == 1) {
            g2d.drawImage(heart, 1250, 120, null);
        }
        g2d.drawRect(1210, 175, 150, 50);
        g2d.drawString(Integer.toString(gameWorld.bomber.getScore()), imgInfor.getWidth(null)/2-15+1200, 205);
        g2d.drawImage(cancel, 1250, 600, null);
    }
}
