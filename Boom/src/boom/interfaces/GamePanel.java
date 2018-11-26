package boom.interfaces;

import boom.sound.GameSound;
import boom.effect.Data;
import boom.gameobject.GameWorld;
import boom.gameobject.HightScore;
import boom.gameobject.PhysicalMap;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {

    Thread thread;
    public static boolean isRunning = false;
    private Manager manager;
    public GameWorld gameWorld;
    private Control control;
    private JLabel lbMenu;
    private ImageIcon cancel;
    private ArrayList<HightScore> arrHightScore;

    public GamePanel(Control control){
        gameWorld = new GameWorld(1);
        manager = new Manager(gameWorld);
        this.control = control;
        setBackground(Color.WHITE);
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);
        startGame();
        initLabel();
        arrHightScore = new ArrayList<>();
        innitArrHightScore("HightScore.txt");


    }

    public void chooseActor(int type) {
        gameWorld = new GameWorld(type);
        manager = new Manager(gameWorld);
    }

    // Add return label
    private void initLabel(){
        lbMenu = new JLabel();
        cancel = new ImageIcon("./Character/cancel1.png");
        lbMenu.setIcon(cancel);
        lbMenu.setBounds(1250, 600, cancel.getIconWidth(), cancel.getIconHeight());
        lbMenu.addMouseListener(this);
        add(lbMenu);
    }

    // Save score when game finish
    public void saveScore() {
        // Check score can save or not
        if (arrHightScore.size() >= 10) {
            if (gameWorld.bomber.getScore() > arrHightScore.get(arrHightScore.size() - 1).getScore()) {
                String name = JOptionPane.showInputDialog("Mời nhập tên của bạn");
                HightScore newScore = new HightScore(name, gameWorld.bomber.getScore());
                arrHightScore.add(newScore);
            }
        } else {
            String name = JOptionPane.showInputDialog("Mời nhập tên của bạn");
            HightScore newScore = new HightScore(name, gameWorld.bomber.getScore());
            arrHightScore.add(newScore);
        }
        // Sort hight score array
        Collections.sort(arrHightScore, new Comparator<HightScore>() {

            @Override
            public int compare(HightScore score1, HightScore score2) {
                if (score1.getScore() < score2.getScore()) {
                    return 1;
                } else {
                    if (score1.getScore() == score2.getScore()) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            }
        });

        // Remove the last element if array have more than 10 element
        if(arrHightScore.size() > 10){
            arrHightScore.remove(arrHightScore.size()-1);
        }

        // Write score to file
        try {
            FileOutputStream fileOutput = new FileOutputStream("HightScore.txt");
            for(int i = 0; i < arrHightScore.size(); i ++){
                String content = arrHightScore.get(i).getName()+":"+arrHightScore.get(i).getScore()+"\n";
                fileOutput.write(content.getBytes());
            }
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }


    // Read score from file
    public void innitArrHightScore(String path){
        try {
            FileReader file = new FileReader(path);
            BufferedReader input = new BufferedReader(file);
            String line;
            // Read file, read one line
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


    // Check game win or lose
    public void checkWinOrLose(){

        if (gameWorld.count_monster3 == 0){
            GameWorld.level = 1;
            GameSound.getIstance().stop();
            GameSound.getIstance().getAudio(GameSound.WIN).play();               
            saveScore();
            innitArrHightScore("HightScore.txt");
            resetGame();
            control.showMenu();
            GameSound.getIstance().getAudio(GameSound.MENU).play();

        }
        if (gameWorld.bomber.getHeart() == 0){
            GameWorld.level = 1;
            GameSound.getIstance().stop();
            GameSound.getIstance().getAudio(GameSound.LOSE).play();         
            saveScore();
            innitArrHightScore("HightScore.txt");
            resetGame();
            control.showMenu();
            GameSound.getIstance().getAudio(GameSound.MENU).play();
        }
    }

    @Override
    public void paint(Graphics g){
        if (GameWorld.level == 1) {
            gameWorld.Render((Graphics2D) g);
        } else if (GameWorld.level == 2) {
            gameWorld.RenderMap2((Graphics2D) g);
        } else if (GameWorld.level == 3) {
            gameWorld.RenderMap3((Graphics2D) g);
        }


        //g.drawImage(bufImage, 0, 0, this);
    }

    // Update new position
    public void updateGame(){
        gameWorld.Update();
    }

    // Reset game
    public void resetGame(){
        try {
            Data.getInstance().loadData();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        gameWorld = new GameWorld(gameWorld.getType());
        arrHightScore = new ArrayList<>();
        innitArrHightScore("HightScore.txt");
        gameWorld.level = 1;
        GameSound.getIstance().getAudio(GameSound.PLAYGAME2).stop();
        GameSound.getIstance().getAudio(GameSound.PLAYGAME3).stop();
        addKeyListener(this);
        startGame();
    }

    public void startGame(){
        // Initialization thread if it null
        if(thread == null){
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {

        // Variable FPS
        long FPS = 60;
        // Variable period
        long period = 1000000*1000/FPS;
        // Time start and sleep
        long beginTime, sleepTime;

        // Set up beginTime
        beginTime = System.nanoTime();

        // Game running when isRunning = true
        while(isRunning){

            updateGame();
            repaint();
            // Variable execution time
            long deltaTime = System.nanoTime() - beginTime;
            sleepTime = period - deltaTime;
            try {
                if(sleepTime > 0)
                    Thread.sleep(sleepTime/1000000);
                else
                    Thread.sleep(period/2000000);
            } catch (InterruptedException e){}

            // Re-set beginTime
            beginTime = System.nanoTime();
            checkWinOrLose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        manager.inputKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        manager.inputKeyReleased(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource()==lbMenu){
            resetGame();
            GameSound.getIstance().getAudio(GameSound.PLAYGAME).stop();
            GameSound.getIstance().getAudio(GameSound.MENU).play();
            control.showMenu();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==lbMenu){
            lbMenu.setIcon(new ImageIcon("./Character/cancel2.png"));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==lbMenu){
            lbMenu.setIcon(cancel);
        }
    }
}