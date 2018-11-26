package boom.gameobject;

import boom.effect.Data;
import boom.interfaces.ChooseActor;
import boom.interfaces.GameFrame;
import boom.interfaces.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GameWorld {
    public Bomber bomber, bomber2;
    public PhysicalMap maps;
    public ArrayList<Moster> mosters, mosters2;
    public ArrayList<Haitacto> haitactos;
    public ArrayList<KhungLongXanh> khungLongXanhs;
    private Moster moster, moster2, moster3, moster4, moster5, moster6, moster7;
    private Haitacto haitacto, haitacto2, haitacto3, haitacto4, haitacto5, haitacto6;
    private KhungLongXanh khungLongXanh, khungLongXanh2, khungLongXanh3, khungLongXanh4, khungLongXanh5, khungLongXanh6, khungLongXanh7, khungLongXanh8;
    private int type;

    private ArrayList<HightScore> arrHightScore;


    public static int level = 1;
    public int count_monster, count_monster2, count_monster3;
    public Boom boom;

    public GameWorld(int type){
        this.type = type;
        setBomber();
        maps = new PhysicalMap(this);
        boom = new Boom(this);
        for (int i = 0; i < PhysicalMap.booms.length; i++) {
            for (int j = 0; j < PhysicalMap.booms[0].length; j++) {
                PhysicalMap.booms[i][j] = new Boom();
            }
        }

        moster = new Moster(500, 625, 25, 25, 1,3, this);
        moster2 = new Moster(175, 175, 25, 25,2, 3, this);
        moster3 = new Moster(100, 75, 25, 25,3, 3, this);
        moster4 = new Moster(750, 325, 25, 25,3, 3, this);
        moster5 = new Moster(400, 100, 25, 25,3, 3, this);
        moster6 = new Moster(650, 250, 25, 25,3, 3, this);
        moster7 = new Moster(1000, 200, 25, 25,3, 3, this);

        haitacto = new Haitacto(250, 625, 25, 25, 1, 4,  this);
        haitacto2 = new Haitacto(500, 125, 25, 25, 1, 4,  this);
        haitacto3 = new Haitacto(350, 625, 25, 25, 1, 4,  this);
        haitacto4 = new Haitacto(650, 350, 25, 25, 1,4, this);
        haitacto5 = new Haitacto(950, 125, 25, 25, 1,4, this);
        haitacto6 = new Haitacto(100, 625, 25, 25, 1,4, this);

        haitactos = new ArrayList<>();
        haitactos.add(haitacto);
        haitactos.add(haitacto2);
        haitactos.add(haitacto3);
        haitactos.add(haitacto4);
        haitactos.add(haitacto5);
        haitactos.add(haitacto6);
        count_monster2 = haitactos.size();


        mosters = new ArrayList<>();
        mosters.add(moster);
        mosters.add(moster2);
        mosters.add(moster3);
        mosters.add(moster4);
        mosters.add(moster5);
        mosters.add(moster6);
        mosters.add(moster7);

        count_monster = mosters.size();

        mosters2 = new ArrayList<>();
        mosters2.add(moster);
        mosters2.add(moster2);
        mosters2.add(moster3);
        mosters2.add(moster4);

        khungLongXanh = new KhungLongXanh(75, 625, 25, 30, 1, 8,  this);
        khungLongXanh2 = new KhungLongXanh(150, 625, 25, 30, 1, 8,  this);
        khungLongXanh3 = new KhungLongXanh(1025, 625, 25, 25, 1, 8,  this);
        khungLongXanh4 = new KhungLongXanh(75, 75, 25, 25, 1, 8,  this);
        khungLongXanh5 = new KhungLongXanh(1025, 375, 25, 25, 1, 8,  this);
        khungLongXanh6 = new KhungLongXanh(600, 100, 25, 25, 1, 8,  this);
        khungLongXanh7 = new KhungLongXanh(350, 585, 25, 25, 1, 8,  this);
        khungLongXanh8 = new KhungLongXanh(175, 625, 25, 25, 1, 8,  this);

        khungLongXanhs = new ArrayList<>();
        khungLongXanhs.add(khungLongXanh);
        khungLongXanhs.add(khungLongXanh2);
        khungLongXanhs.add(khungLongXanh3);
        khungLongXanhs.add(khungLongXanh4);
        khungLongXanhs.add(khungLongXanh5);
        khungLongXanhs.add(khungLongXanh6);
        khungLongXanhs.add(khungLongXanh7);
        khungLongXanhs.add(khungLongXanh8);
        count_monster3 = khungLongXanhs.size();

        arrHightScore = new ArrayList<>();
        innitArrHightScore("HightScore.txt");

    }

    public GameWorld(){
        boom = new Boom(this);
        for (int i = 0; i < PhysicalMap.booms.length; i++) {
            for (int j = 0; j < PhysicalMap.booms[0].length; j++) {
                PhysicalMap.booms[i][j] = new Boom();
            }
        }

        moster = new Moster(500, 625, 25, 25, 1,3, this);
        moster2 = new Moster(175, 175, 25, 25,2, 3, this);
        moster3 = new Moster(100, 75, 25, 25,3, 3, this);
        moster4 = new Moster(750, 325, 25, 25,3, 3, this);
        moster5 = new Moster(400, 100, 25, 25,3, 3, this);
        moster6 = new Moster(650, 250, 25, 25,3, 3, this);
        moster7 = new Moster(1000, 200, 25, 25,3, 3, this);

        haitacto = new Haitacto(250, 625, 25, 25, 1, 4,  this);
        haitacto2 = new Haitacto(500, 125, 25, 25, 1, 4,  this);
        haitacto3 = new Haitacto(350, 625, 25, 25, 1, 4,  this);
        haitacto4 = new Haitacto(650, 350, 25, 25, 1,4, this);
        haitacto5 = new Haitacto(950, 125, 25, 25, 1,4, this);
        haitacto6 = new Haitacto(100, 625, 25, 25, 1,4, this);

        haitactos = new ArrayList<>();
        haitactos.add(haitacto);
        haitactos.add(haitacto2);
        haitactos.add(haitacto3);
        haitactos.add(haitacto4);
        haitactos.add(haitacto5);
        haitactos.add(haitacto6);
        count_monster2 = haitactos.size();


        mosters = new ArrayList<>();
        mosters.add(moster);
        mosters.add(moster2);
        mosters.add(moster3);
        mosters.add(moster4);
        mosters.add(moster5);
        mosters.add(moster6);
        mosters.add(moster7);
        count_monster = mosters.size();

        mosters2 = new ArrayList<>();
        mosters2.add(moster);
        mosters2.add(moster2);
        mosters2.add(moster3);
        mosters2.add(moster4);

        khungLongXanh = new KhungLongXanh(75, 625, 25, 30, 1, 8,  this);
        khungLongXanh2 = new KhungLongXanh(150, 625, 25, 30, 1, 8,  this);
        khungLongXanh3 = new KhungLongXanh(1025, 625, 25, 25, 1, 8,  this);
        khungLongXanh4 = new KhungLongXanh(75, 75, 25, 25, 1, 8,  this);
        khungLongXanh5 = new KhungLongXanh(1025, 375, 25, 25, 1, 8,  this);
        khungLongXanh6 = new KhungLongXanh(600, 100, 25, 25, 1, 8,  this);
        khungLongXanh7 = new KhungLongXanh(350, 585, 25, 25, 1, 8,  this);
        khungLongXanh8 = new KhungLongXanh(175, 625, 25, 25, 1, 8,  this);

        khungLongXanhs = new ArrayList<>();
        khungLongXanhs.add(khungLongXanh);
        khungLongXanhs.add(khungLongXanh2);
        khungLongXanhs.add(khungLongXanh3);
        khungLongXanhs.add(khungLongXanh4);
        khungLongXanhs.add(khungLongXanh5);
        khungLongXanhs.add(khungLongXanh6);
        khungLongXanhs.add(khungLongXanh7);
        khungLongXanhs.add(khungLongXanh8);
        count_monster3 = khungLongXanhs.size();

        arrHightScore = new ArrayList<>();
        innitArrHightScore("HightScore.txt");


    }

    public void setBomber(){
        if (type == 1) {
            bomber = new Ike(150, 625, 25, 25, this);
        } else if (type == 2) {
            bomber = new Boz(150, 625, 25, 25, this);
        } else if (type == 3) {
            bomber = new Plunk(150, 625, 25, 25, this);
        } else {
            bomber = new Ike(150, 625, 25, 25,  this);
        }
    }

    public void innitArrHightScore(String path){
        try {
            FileReader file = new FileReader(path);
            BufferedReader input = new BufferedReader(file);
            String line;
            while ((line = input.readLine()) != null) {
                String str[] = line.split(":");
                String name = str[0];
                int score = Integer.parseInt(str[1]);
                HightScore hightScore = new HightScore(name, score);
                arrHightScore.add(hightScore);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void Update(){
        if (bomber != null) {
            bomber.update();
        }
            for (Moster mosterx : mosters) {
                mosterx.update();
            }

            for (Haitacto haitactox : haitactos) {
                haitactox.update();
            }

            for (KhungLongXanh khungLongXanhx : khungLongXanhs){
                khungLongXanhx.update();
            }
    }

    public void drawBoom(Graphics2D g2){
        for (int i = 0; i < PhysicalMap.booms.length; i++) {
            for (int j = 0; j < PhysicalMap.booms[0].length; j++) {
                PhysicalMap.booms[i][j].draw(g2);
            }
        }
    }

    public void Render(Graphics2D g2){
        maps.draw(g2);

        for (Moster mosterx : mosters) {
            mosterx.draw(g2);
        }
        drawBoom(g2);
        bomber.draw(g2);
        maps.draw2(g2);

        maps.drawInfo(g2);


    }

    public void RenderMap2(Graphics2D g2){
        maps.draw(g2);

        for (Haitacto haitactox : haitactos){
            haitactox.draw(g2);

        }
        drawBoom(g2);
        bomber.draw(g2);
        maps.draw2(g2);

        maps.drawInfo(g2);


    }

    public void RenderMap3(Graphics2D g2){
        maps.draw(g2);

        for (KhungLongXanh haitactox : khungLongXanhs){
            haitactox.draw(g2);

        }
        drawBoom(g2);
        bomber.draw(g2);
        maps.draw2(g2);

        maps.drawInfo(g2);
    }

    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return type;
    }
}
