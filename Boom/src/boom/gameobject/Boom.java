package boom.gameobject;

import boom.sound.GameSound;
import boom.effect.Data;
import boom.interfaces.GamePanel;

import javax.imageio.ImageIO;
import javax.management.monitor.GaugeMonitor;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Boom {

    // Variable position boom
    private int boomX, boomY;
    protected int x0, y0;

    // Variable explosive up
    boolean explodedUP = true;
    // Variable explosive down
    boolean explodedDOWN = true;
    // Variable explosive left
    boolean explodedLEFT = true;
    // Variable explosive right
    boolean explodedRIGHT = true;

    // Variable time put, deadline boom, time start if boom exploded, deadline exploded
    private long beginTimes, timeline,  beginTime, timeExploded;
    //private float size, bombsize = 8;
    //private int quantity, max_quantyity;
    private float width, height;
    private int size, quantity;
    private GameWorld gameWorld;

    public boolean put = false;

    private Image boom1;
    private BufferedImage bangleft, bangright, bangup, bangdown, bangmid;
    private BufferedImage outleft, outright, outup, outdown;

    private Random random;


    public boolean isPut = false, exploded = false, isExplosive = false;

    public Boom(int boomX, int boomY, int size, int quantity, GameWorld gameWorld) {
        this.boomX = boomX;
        this.boomY = boomY;
        this.gameWorld = gameWorld;
        boom1 = new ImageIcon("./Character/lua.gif").getImage();
        this.size = size;
        this.quantity = quantity;
        random = new Random();
        try {
            bangleft = ImageIO.read(new File("./Character/bangleft.png"));
            bangright = ImageIO.read(new File("./Character/bangright.png"));
            bangup = ImageIO.read(new File("./Character/bangup.png"));
            bangdown = ImageIO.read(new File("./Character/bangdown.png"));
            bangmid = ImageIO.read(new File("./Character/bangmid.png"));
            outleft = ImageIO.read(new File("./Character/outleft.png"));
            outright = ImageIO.read(new File("./Character/outright.png"));
            outup = ImageIO.read(new File("./Character/outup.png"));
            outdown = ImageIO.read(new File("./Character/outdown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.width = boom1.getWidth(null);
        this.height = boom1.getHeight(null);
    }

    public Boom(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        boom1 = new ImageIcon("./Character/lua.gif").getImage();
        random = new Random();
        try {
            bangleft = ImageIO.read(new File("./Character/bangleft.png"));
            bangright = ImageIO.read(new File("./Character/bangright.png"));
            bangup = ImageIO.read(new File("./Character/bangup.png"));
            bangdown = ImageIO.read(new File("./Character/bangdown.png"));
            bangmid = ImageIO.read(new File("./Character/bangmid.png"));
            outleft = ImageIO.read(new File("./Character/outleft.png"));
            outright = ImageIO.read(new File("./Character/outright.png"));
            outup = ImageIO.read(new File("./Character/outup.png"));
            outdown = ImageIO.read(new File("./Character/outdown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.width = boom1.getWidth(null);
        this.height = boom1.getHeight(null);
    }

    public Boom(){
        boom1 = new ImageIcon("./Character/lua.gif").getImage();
        random = new Random();
        try {
            bangleft = ImageIO.read(new File("./Character/bangleft.png"));
            bangright = ImageIO.read(new File("./Character/bangright.png"));
            bangup = ImageIO.read(new File("./Character/bangup.png"));
            bangdown = ImageIO.read(new File("./Character/bangdown.png"));
            bangmid = ImageIO.read(new File("./Character/bangmid.png"));
            outleft = ImageIO.read(new File("./Character/outleft.png"));
            outright = ImageIO.read(new File("./Character/outright.png"));
            outup = ImageIO.read(new File("./Character/outup.png"));
            outdown = ImageIO.read(new File("./Character/outdown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        if (put){
            // Check boom is put
            if (isPut == false) {
                x0 = boomX;
                y0 = boomY;
                isPut = true;
                beginTimes = System.nanoTime();

            }
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
            put = false;
        }
        // Start time while boom isn't explode
        if(exploded == false) {
            timeline = (System.nanoTime() - beginTimes) / 1000000;
        }

        // Dead line boom if time > 3 seconds
        if (timeline >= 3000 && isPut == true){
            exploded = true;
            isPut = false;
        }

        // Start time when boom was exploded
        if (exploded == true){
            beginTime = System.nanoTime();
            isPut = false;
            exploded = false;
            isExplosive = true;
            GameSound.getIstance().getAudio(GameSound.BONG_BANG).play();
        }

        timeExploded = (System.nanoTime() - beginTime)/1000000;

        // Draw boom when it has put
        if(isPut) {
            g2.drawImage(boom1, x0 * 50 - 5, y0 * 50 - 20, null);
        }
        // Check when boom is explosiving
        if(isExplosive){
            // Draw boom bang when is exploded
                if (timeExploded <= 500) {
                    g2.drawImage(bangmid, x0 * 50, y0 * 50, null);
                    // Draw boom bang down
                    for (int i = 1; i <= size; i++) {
                        // Exploded other boom if bang impact 
                         if (PhysicalMap.map[y0 + i][x0] == -10 || PhysicalMap.map[y0 + i][x0] == -11){
                            if (PhysicalMap.booms[y0 + i][x0].isPut == true) {

                                PhysicalMap.booms[y0 + i][x0].exploded = true;
                                explodedDOWN = false;
                                PhysicalMap.booms[y0 + i][x0].explodedDOWN = true;
                                PhysicalMap.booms[y0 + i][x0].explodedUP = false;
                                break;

                            }
                        }
                        // Draw boom bang if not impact anything
                        else if ((PhysicalMap.map[y0 + i][x0] <= 0) && i != size) {
                            g2.drawImage(bangdown, x0 * 50, (y0 + i) * 50, null);
                        } else if (PhysicalMap.map[y0 + i][x0] <= 0) {
                            g2.drawImage(outdown, x0 * 50, (y0 + i) * 50, null);
                        } else
                        // Check boom bang impact wall
                        if (PhysicalMap.map[y0 + i][x0] > 0){
                            break;
                        }
                         // Kill monster if boom bang impact
                        for (int k = 0; k < gameWorld.mosters.size(); k++) {
                            if ((y0 + i) == (int) (gameWorld.mosters.get(k).posY) / 50 && x0 == (int) (gameWorld.mosters.get(k).posX) / 50) {
                                //gameWorld.mosters.get(k).LIVE = false;
                                gameWorld.mosters.remove(k);
                                gameWorld.count_monster--;
                                gameWorld.bomber.setScore(gameWorld.bomber.getScore()+100);
                                GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                            }
                        }
                        if (GameWorld.level == 2) {
                            for (int k = 0; k < gameWorld.haitactos.size(); k++) {
                                if ((y0 + i) == (int) (gameWorld.haitactos.get(k).posY) / 50 && x0 == (int) (gameWorld.haitactos.get(k).posX) / 50) {
                                    // gameWorld.mosters.get(k).LIVE = false;
                                    gameWorld.haitactos.remove(k);
                                    gameWorld.count_monster2--;
                                    gameWorld.bomber.setScore(gameWorld.bomber.getScore() + 150);
                                    GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                                }
                            }
                        }
                        if (GameWorld.level == 3) {
                            for (int k = 0; k < gameWorld.khungLongXanhs.size(); k++) {
                                if ((y0 + i) == (int) (gameWorld.khungLongXanhs.get(k).posY) / 50 && x0 == (int) (gameWorld.khungLongXanhs.get(k).posX) / 50) {
                                    // gameWorld.mosters.get(k).LIVE = false;
                                    gameWorld.khungLongXanhs.remove(k);
                                    gameWorld.count_monster3--;
                                    gameWorld.bomber.setScore(gameWorld.bomber.getScore() + 400);
                                    GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                                }
                            }
                        }

                    }

                    // Draw boom bang up
                    for (int i = 1; i <= size; i++) {
                        if (PhysicalMap.map[y0 - i][x0] > 0) break;
                        else if (PhysicalMap.map[y0 - i][x0] == -10 || PhysicalMap.map[y0 - i][x0] == -11){
                            if (PhysicalMap.booms[y0 - i][x0].isPut == true) {

                                PhysicalMap.booms[y0 - i][x0].exploded = true;
                                explodedUP = false;
                                PhysicalMap.booms[y0 - i][x0].explodedUP = true;
                                PhysicalMap.booms[y0 - i][x0].explodedDOWN = false;
                                break;

                            }
                        } else
                        if (PhysicalMap.map[y0 - i][x0] <= 0 && i != size) {
                            g2.drawImage(bangup, x0 * 50, (y0 - i) * 50, null);
                        } else if (PhysicalMap.map[y0 - i][x0] <= 0) {
                            g2.drawImage(outup, x0 * 50, (y0 - i) * 50, null);
                        }
                        for (int k = 0; k < gameWorld.mosters.size(); k++) {
                            if ((y0 - i) == (int) (gameWorld.mosters.get(k).posY) / 50 && x0 == (int) (gameWorld.mosters.get(k).posX) / 50) {
                                // gameWorld.mosters.get(k).LIVE = false;
                                gameWorld.mosters.remove(k);
                                gameWorld.count_monster--;
                                gameWorld.bomber.setScore(gameWorld.bomber.getScore()+100);
                                GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                            }
                        }
                        if (GameWorld.level == 2) {
                            for (int k = 0; k < gameWorld.haitactos.size(); k++) {
                                if ((y0 - i) == (int) (gameWorld.haitactos.get(k).posY) / 50 && x0 == (int) (gameWorld.haitactos.get(k).posX) / 50) {
                                    // gameWorld.mosters.get(k).LIVE = false;
                                    gameWorld.haitactos.remove(k);
                                    gameWorld.count_monster2--;
                                    gameWorld.bomber.setScore(gameWorld.bomber.getScore() + 150);
                                    GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                                }
                            }
                        }
                        if (GameWorld.level == 3) {
                            for (int k = 0; k < gameWorld.khungLongXanhs.size(); k++) {
                                if ((y0 - i) == (int) (gameWorld.khungLongXanhs.get(k).posY) / 50 && x0 == (int) (gameWorld.khungLongXanhs.get(k).posX) / 50) {
                                    // gameWorld.mosters.get(k).LIVE = false;
                                    gameWorld.khungLongXanhs.remove(k);
                                    gameWorld.count_monster3--;
                                    gameWorld.bomber.setScore(gameWorld.bomber.getScore() + 400);
                                    GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                                }
                            }
                        }
                    }
                     // Draw boom bang right
                    for (int i = 1; i <= size; i++) {
                        if (PhysicalMap.map[y0][x0 + i] > 0) break;
                        else if (PhysicalMap.map[y0][x0+i] == -10 || PhysicalMap.map[y0][x0+i] == -11){
                            if (PhysicalMap.booms[y0][x0+i].isPut == true) {

                                PhysicalMap.booms[y0][x0+i].exploded = true;
                                explodedRIGHT = false;
                                PhysicalMap.booms[y0][x0+i].explodedRIGHT = true;
                                PhysicalMap.booms[y0][x0+i].explodedLEFT = false;
                                break;

                            }
                        } else
                        if (PhysicalMap.map[y0][x0 + i] <= 0 && i != size) {
                            g2.drawImage(bangright, (x0 + i) * 50, y0 * 50, null);
                        } else if (PhysicalMap.map[y0][x0 + i] <= 0) {
                            g2.drawImage(outright, (x0 + i) * 50, y0 * 50, null);
                        }
                        for (int k = 0; k < gameWorld.mosters.size(); k++) {
                            if (y0 == (int) (gameWorld.mosters.get(k).posY) / 50 && (x0 + i) == (int) (gameWorld.mosters.get(k).posX) / 50) {
                                //gameWorld.mosters.get(k).LIVE = false;
                                gameWorld.mosters.remove(k);
                                gameWorld.count_monster--;
                                gameWorld.bomber.setScore(gameWorld.bomber.getScore()+100);
                                GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                            }
                        }
                        if (GameWorld.level == 2) {
                            for (int k = 0; k < gameWorld.haitactos.size(); k++) {
                                if (y0 == (int) (gameWorld.haitactos.get(k).posY) / 50 && (x0 + i) == (int) (gameWorld.haitactos.get(k).posX) / 50) {
                                    // gameWorld.mosters.get(k).LIVE = false;
                                    gameWorld.haitactos.remove(k);
                                    gameWorld.count_monster2--;
                                    gameWorld.bomber.setScore(gameWorld.bomber.getScore() + 150);
                                    GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                                }
                            }
                        }
                        if (GameWorld.level == 3) {
                            for (int k = 0; k < gameWorld.khungLongXanhs.size(); k++) {
                                if (y0 == (int) (gameWorld.khungLongXanhs.get(k).posY) / 50 && (x0 + i) == (int) (gameWorld.khungLongXanhs.get(k).posX) / 50) {
                                    // gameWorld.mosters.get(k).LIVE = false;
                                    gameWorld.khungLongXanhs.remove(k);
                                    gameWorld.count_monster3--;
                                    gameWorld.bomber.setScore(gameWorld.bomber.getScore() + 400);
                                    GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                                }
                            }
                        }
                    }
                     // Draw boom bang left
                    for (int i = 1; i <= size; i++) {
                        if (PhysicalMap.map[y0][x0 - i] > 0) break;
                        else if (PhysicalMap.map[y0][x0-i] == -10 || PhysicalMap.map[y0][x0-i] == -11){
                            if (PhysicalMap.booms[y0][x0-i].isPut == true) {

                                PhysicalMap.booms[y0][x0-i].exploded = true;
                                explodedLEFT = false;
                                PhysicalMap.booms[y0][x0-i].explodedLEFT = true;
                                PhysicalMap.booms[y0][x0-i].explodedRIGHT = false;
                                break;

                            }
                        } else
                        if (PhysicalMap.map[y0][x0 - i] <= 0 && i != size) {
                            g2.drawImage(bangleft, (x0 - i) * 50, y0 * 50, null);
                        } else if (PhysicalMap.map[y0][x0 - i] <= 0) {
                            g2.drawImage(outleft, (x0 - i) * 50, y0 * 50, null);
                        }
                        for (int k = 0; k < gameWorld.mosters.size(); k++) {
                            if (y0 == (int) (gameWorld.mosters.get(k).posY) / 50 && (x0 - i) == (int) (gameWorld.mosters.get(k).posX) / 50) {
                                // gameWorld.mosters.get(k).LIVE = false;
                                gameWorld.mosters.remove(k);
                                gameWorld.count_monster--;
                                gameWorld.bomber.setScore(gameWorld.bomber.getScore()+100);
                                GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                            }
                        }

                        if (GameWorld.level == 2) {
                            for (int k = 0; k < gameWorld.haitactos.size(); k++) {
                                if (y0 == (int) (gameWorld.haitactos.get(k).posY) / 50 && (x0 - i) == (int) (gameWorld.haitactos.get(k).posX) / 50) {
                                    // gameWorld.mosters.get(k).LIVE = false;
                                    gameWorld.haitactos.remove(k);
                                    gameWorld.count_monster2--;
                                    gameWorld.bomber.setScore(gameWorld.bomber.getScore() + 150);
                                    GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                                }
                            }
                        }

                        if (GameWorld.level == 3) {
                            for (int k = 0; k < gameWorld.khungLongXanhs.size(); k++) {
                                if (y0 == (int) (gameWorld.khungLongXanhs.get(k).posY) / 50 && (x0 - i) == (int) (gameWorld.khungLongXanhs.get(k).posX) / 50) {
                                    // gameWorld.mosters.get(k).LIVE = false;
                                    gameWorld.khungLongXanhs.remove(k);
                                    gameWorld.count_monster3--;
                                    gameWorld.bomber.setScore(gameWorld.bomber.getScore() + 400);
                                    GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                                }
                            }
                        }
                    }

                }

                // Do something when boom is finish exploded
                if (timeExploded > 500) {
                    //System.out.println(System.nanoTime() - beginTime);
                    isPut = false;
                    exploded = false;
                    put = false;

                    // Check up
                    for (int i = 1; i <= size; i++) {
                        if (explodedDOWN == true) {
                            // Is bomber impact bombang
                            if ((y0) == (int)gameWorld.bomber.getPosY()/50 && x0 == (int)gameWorld.bomber.getPosX()/50){
                                if (gameWorld.bomber.heart > 0) {
                                    gameWorld.bomber.heart --;
                                }
                                GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
                                Bomber.ALIVE = false;
                                if (GameWorld.level == 1) {
                                    gameWorld.bomber.reset(150, 625);
                                } else if (GameWorld.level == 2){
                                    gameWorld.bomber.reset(1120, 625);
                                } else if (GameWorld.level == 3){
                                    gameWorld.bomber.reset(510, 625);
                                }
                            }
                            if ((y0+i) == (int)gameWorld.bomber.getPosY()/50 && x0 == (int)gameWorld.bomber.getPosX()/50){
                                if (gameWorld.bomber.heart > 0) {
                                    gameWorld.bomber.heart --;
                                }
                                GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
                                Bomber.ALIVE = false;
                                if (GameWorld.level == 1) {
                                    gameWorld.bomber.reset(150, 625);
                                } else if (GameWorld.level == 2){
                                    gameWorld.bomber.reset(1120, 625);
                                } else if (GameWorld.level == 3){
                                    gameWorld.bomber.reset(510, 625);
                                }
                            }

                            if (PhysicalMap.map[y0 + i][x0] == -1 || PhysicalMap.map[y0 + i][x0] == -2
                                    || PhysicalMap.map[y0 + i][x0] == -3) {
                                PhysicalMap.map[y0 + i][x0] = 0;
                            }
                            if (PhysicalMap.map[y0 + i][x0] == 7 || PhysicalMap.map[y0 + i][x0] == 20 ||
                                    PhysicalMap.map[y0 + i][x0] == 23 || PhysicalMap.map[y0 + i][x0] == 24) {
                                PhysicalMap.map[y0 + i][x0] = -3 + random.nextInt(4);
                                break;
                            }
                            if (PhysicalMap.map[y0 + i][x0] > 0) break;

                        }
                    }
                    // Check down
                    for (int i = 1; i <= size; i++) {
                        if (explodedUP == true) {
                            if ((y0-i) == (int)gameWorld.bomber.getPosY()/50 && x0 == (int)gameWorld.bomber.getPosX()/50){
                                if (gameWorld.bomber.heart > 0) {
                                    gameWorld.bomber.heart --;
                                }
                                GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
                                Bomber.ALIVE = false;
                                if (GameWorld.level == 1) {
                                    gameWorld.bomber.reset(150, 625);
                                } else if (GameWorld.level == 2){
                                    gameWorld.bomber.reset(1120, 625);
                                } else if (GameWorld.level == 3){
                                    gameWorld.bomber.reset(510, 625);
                                }
                            }

                            if (PhysicalMap.map[y0 - i][x0] == -1 || PhysicalMap.map[y0 - i][x0] == -2
                                    || PhysicalMap.map[y0 - i][x0] == -3) {
                                PhysicalMap.map[y0 - i][x0] = 0;
                            }
                            if (PhysicalMap.map[y0 - i][x0] == 7 || PhysicalMap.map[y0 - i][x0] == 20 ||
                                    PhysicalMap.map[y0 - i][x0] == 23 || PhysicalMap.map[y0 - i][x0] == 24) {
                                PhysicalMap.map[y0 - i][x0] = -3 + random.nextInt(4);
                                break;
                            }
                            if (PhysicalMap.map[y0 - i][x0] > 0) break;
                        }
                    }

                    // Check right
                    for (int i = 1; i <= size; i++) {
                        if (explodedRIGHT == true) {
                            if (y0 == (int)gameWorld.bomber.getPosY()/50 && (x0+i) == (int)gameWorld.bomber.getPosX()/50){
                                if (gameWorld.bomber.heart > 0) {
                                    gameWorld.bomber.heart --;
                                }
                                GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
                                Bomber.ALIVE = false;
                                if (GameWorld.level == 1) {
                                    gameWorld.bomber.reset(150, 625);
                                } else if (GameWorld.level == 2){
                                    gameWorld.bomber.reset(1120, 625);
                                } else if (GameWorld.level == 3){
                                    gameWorld.bomber.reset(510, 625);
                                }
                            }

                            if (PhysicalMap.map[y0][x0 + i] == -1 || PhysicalMap.map[y0][x0 + i] == -2
                                    || PhysicalMap.map[y0][x0 + i] == -3) {
                                PhysicalMap.map[y0][x0 + i] = 0;
                            }
                            if (PhysicalMap.map[y0][x0 + i] == 7 || PhysicalMap.map[y0][x0 + i] == 20 ||
                                    PhysicalMap.map[y0][x0 + i] == 23 || PhysicalMap.map[y0][x0 + i] == 24) {
                                PhysicalMap.map[y0][x0 + i] = -3 + random.nextInt(4);
                                ;
                                break;
                            }
                            if (PhysicalMap.map[y0][x0 + i] > 0) break;
                        }
                    }

                    // Check left
                    for (int i = 1; i <= size; i++) {
                        if (explodedLEFT == true) {
                            if (y0 == (int)gameWorld.bomber.getPosY()/50 && (x0-i) == (int)gameWorld.bomber.getPosX()/50){
                                if (gameWorld.bomber.heart > 0) {
                                    gameWorld.bomber.heart --;
                                }
                                GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
                                Bomber.ALIVE = false;
                                if (GameWorld.level == 1) {
                                    gameWorld.bomber.reset(150, 625);
                                } else if (GameWorld.level == 2){
                                    gameWorld.bomber.reset(1120, 625);
                                } else if (GameWorld.level == 3){
                                    gameWorld.bomber.reset(510, 625);
                                }
                            }

                            if (PhysicalMap.map[y0][x0 - i] == -1 || PhysicalMap.map[y0][x0 - i] == -2
                                    || PhysicalMap.map[y0][x0 - i] == -3) {
                                PhysicalMap.map[y0][x0 - i] = 0;
                            }
                            if (PhysicalMap.map[y0][x0 - i] == 7 || PhysicalMap.map[y0][x0 - i] == 20 ||
                                    PhysicalMap.map[y0][x0 - i] == 23 || PhysicalMap.map[y0][x0 - i] == 24) {
                                PhysicalMap.map[y0][x0 - i] = -3 + random.nextInt(4);

                                break;
                            }
                            if (PhysicalMap.map[y0][x0 - i] > 0) break;
                        }
                    }
                    if (GameWorld.level == 1) {
                        if (gameWorld.count_monster == 0) {
                            System.out.println("Pass level 1");
                            GameWorld.level = 2;
                            gameWorld.bomber.reset(1125, 625);
                            try {
                                Data.getInstance().loadMap("Map2.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            PhysicalMap.map = Data.getInstance().getMap();
                            GameSound.getIstance().getAudio(GameSound.PLAYGAME).stop();
                            GameSound.getIstance().getAudio(GameSound.PLAYGAME2).loop();
                        }
                    } else if (GameWorld.level == 2){
                        if (gameWorld.count_monster2 == 0) {
                            System.out.println("Pass level 2");
                            GameWorld.level = 3;
                            gameWorld.bomber.reset(510, 625);
                            try {
                                Data.getInstance().loadMap("Map3.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            PhysicalMap.map = Data.getInstance().getMap();
                            GameSound.getIstance().getAudio(GameSound.PLAYGAME2).stop();
                            GameSound.getIstance().getAudio(GameSound.PLAYGAME3).loop();
                        }
                    }
                    PhysicalMap.map[y0][x0] = 0;
                    isExplosive = false;
                }
            }

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTimeline() {
        return timeline;
    }

    public void setTimeline(long timeline) {
        this.timeline = timeline;
    }


    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public float getBoomX() {
        return boomX;
    }

    public void setBoomX(int boomX) {
        this.boomX = boomX;
    }

    public int getBoomY() {
        return boomY;
    }

    public void setBoomY(int boomY) {
        this.boomY = boomY;
    }

    public Image getBoom1() {
        return boom1;
    }

    public void setBoom1(Image boom1) {
        this.boom1 = boom1;
    }


    public long getBeginTimes() {
        return beginTimes;
    }

    public void setBeginTimes(long beginTimes) {
        this.beginTimes = beginTimes;
    }

    public boolean isPut() {
        return put;
    }

    public void setPut(boolean put) {
        this.put = put;
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public int getY0() {
        return y0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public BufferedImage getBangleft() {
        return bangleft;
    }

    public void setBangleft(BufferedImage bangleft) {
        this.bangleft = bangleft;
    }

    public BufferedImage getBangright() {
        return bangright;
    }

    public void setBangright(BufferedImage bangright) {
        this.bangright = bangright;
    }

    public BufferedImage getBangup() {
        return bangup;
    }

    public void setBangup(BufferedImage bangup) {
        this.bangup = bangup;
    }

    public BufferedImage getBangdown() {
        return bangdown;
    }

    public void setBangdown(BufferedImage bangdown) {
        this.bangdown = bangdown;
    }

    public BufferedImage getBangmid() {
        return bangmid;
    }

    public void setBangmid(BufferedImage bangmid) {
        this.bangmid = bangmid;
    }

    public BufferedImage getOutleft() {
        return outleft;
    }

    public void setOutleft(BufferedImage outleft) {
        this.outleft = outleft;
    }

    public BufferedImage getOutright() {
        return outright;
    }

    public void setOutright(BufferedImage outright) {
        this.outright = outright;
    }

    public BufferedImage getOutup() {
        return outup;
    }

    public void setOutup(BufferedImage outup) {
        this.outup = outup;
    }

    public BufferedImage getOutdown() {
        return outdown;
    }

    public void setOutdown(BufferedImage outdown) {
        this.outdown = outdown;
    }
/*
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > max_quantyity) return;
        else
            this.quantity = quantity;
    }

    public int getMax_quantyity() {
        return max_quantyity;
    }

    public void setMax_quantyity(int max_quantyity) {
        this.max_quantyity = max_quantyity;
    }*/
}
