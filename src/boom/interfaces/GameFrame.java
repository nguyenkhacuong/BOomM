/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boom.interfaces;

import boom.sound.GameSound;
import boom.effect.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class GameFrame extends JFrame {

    public static final int SCREEN_WIDTH = 1366;
    public static final int SCREEN_HEIGHT = 700;

    private JLabel lbBackGround;
    private JLabel lbPlay;
    private JLabel lbGuide, lbGuideBG;
    private ImageIcon backGround, play, play2, guide, guide2;
    private Control mContainer;
    // private MyContainer mContainer;

    GamePanel gamePanel;

    public GameFrame() {
        // Lấy size màn hình máy tính hiện tại
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - (SCREEN_HEIGHT + 10)) / 2, SCREEN_WIDTH, (SCREEN_HEIGHT - 10));

        try {
            Data.getInstance().loadData();
            GameSound.getIstance().getAudio(GameSound.MENU).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mContainer = new Control(this);
        add(mContainer);
        addWindowListener(mwindow);
        
    }
    private WindowAdapter mwindow = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            GameSound.getIstance().stop();
            gamePanel.isRunning = false;
        }
    };

    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.setTitle("Crazy Arcade");
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setExtendedState(gameFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        gameFrame.setVisible(true);

    }
}
