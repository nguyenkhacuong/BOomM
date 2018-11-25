package boom.interfaces;

import boom.gameobject.HightScore;
import boom.sound.GameSound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HightScorePanel extends JPanel implements MouseListener {
    private Control control;
    private JLabel lbOK, lbRefresh;
    private ArrayList<HightScore> arrHightScore;
    private ImageIcon cancel, fresh;

    public HightScorePanel(Control control) {
        this.control = control;
        setBackground(Color.YELLOW);
        setLayout(null);
        initLabel();
    }

    public void initLabel(){
        ReadFileHightScore();

        lbOK = new JLabel();
        cancel = new ImageIcon("./Character/cancel1.png");
        lbOK.setBounds(1120, 625, cancel.getIconWidth(), cancel.getIconHeight());
        lbOK.addMouseListener(this);
        lbOK.setIcon(cancel);
        add(lbOK);
        
        lbRefresh = new JLabel();
        fresh = new  ImageIcon("./Character/refresh.png");
        lbRefresh.setBounds(200, 625, fresh.getIconWidth(), fresh.getIconHeight());
        lbRefresh.addMouseListener(this);
        lbRefresh.setIcon(fresh);
        add(lbRefresh);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D )g;
        drawImage(g2d);
        drawHightSore(g2d);
    }

    // Draw Background in label
    public void drawImage(Graphics2D g2d){
        Image background = new ImageIcon("./Character/background_hightscore.png").getImage();
        g2d.drawImage(background, 0, 0, null);
    }

    // Draw Hight score in label
    public void drawHightSore(Graphics2D g2d){
        g2d.setStroke(new java.awt.BasicStroke(2));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.setColor(Color.RED);
        g2d.setColor(Color.YELLOW);
        int y=150;
        for(int i=0;i<arrHightScore.size();i++){
            g2d.drawString(""+(i+1), 420, y);
            g2d.drawString(""+arrHightScore.get(i).getName(), 520, y);
            g2d.drawString(""+arrHightScore.get(i).getScore(), 850, y);
            y=y+40;
        }

    }

    public void ReadFileHightScore(){
        arrHightScore = new ArrayList<HightScore>();
        try {
            FileReader file = new FileReader("HightScore.txt");
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


    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource()==lbOK){
            control.showMenu();
        }
        if(e.getSource()==lbRefresh){
            ReadFileHightScore();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==lbOK){
            lbOK.setIcon(new ImageIcon("./Character/cancel2.png"));
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
        }
        if(e.getSource()==lbRefresh){
            lbRefresh.setIcon(new ImageIcon("./Character/refresh2.png"));
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==lbOK){
            lbOK.setIcon(cancel);
        }
        if(e.getSource()==lbRefresh){
            lbRefresh.setIcon(fresh);
        }
    }
}
