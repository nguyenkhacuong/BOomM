package boom.interfaces;

import boom.sound.GameSound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JPanel{
        private GameFrame gameFrames;
        private Control control;
        private JLabel lbbackground;
        private JLabel lbPlayGame;
        private JLabel lbGuide;
        private JLabel lbHigthScore;
        private JLabel lbExit;
        private ImageIcon backgroundIcon;

        public Menu(Control control){
            this.control = control;
            this.gameFrames = control.getGameFrame();
            setBackground(Color.YELLOW);
            setLayout(null);
            initLabel(gameFrames);
            initbackground();
        }

        public void initLabel(GameFrame gameFrames){
            lbPlayGame = setLabel((gameFrames.getWidth()-200)/9, gameFrames.getHeight()-100, "./Character/Play.png");
            lbGuide = setLabel(lbPlayGame.getX() + 300,lbPlayGame.getY(), "./Character/Option.png");
            lbHigthScore = setLabel(lbGuide.getX() + 300,lbGuide.getY(), "./Character/HightScore.png");
            lbExit = setLabel(lbHigthScore.getX() + 300,lbHigthScore.getY(), "./Character/Exit.png");

            lbPlayGame.addMouseListener(mouseAdapter);
            lbGuide.addMouseListener(mouseAdapter);
            lbHigthScore.addMouseListener(mouseAdapter);
            lbExit.addMouseListener(mouseAdapter);

            add(lbPlayGame);
            add(lbGuide);
            add(lbHigthScore);
            add(lbExit);

        }

        public void initbackground(){
            lbbackground = new JLabel();
            lbbackground.setBounds(0, 0, gameFrames.getWidth(), gameFrames.getHeight());
            lbbackground.setBackground(Color.BLACK);
            backgroundIcon = new ImageIcon("./Character/BGStart.png");
            lbbackground.setIcon(backgroundIcon);
            add(lbbackground);
        }

        public JLabel setLabel(int x, int y, String ImageIcon){
            JLabel label = new JLabel();
            ImageIcon Icon = new ImageIcon(ImageIcon);
            label.setBounds(x, y, Icon.getIconWidth(), Icon.getIconHeight());
            label.setIcon(Icon);
            return label;
        }

        private MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(e.getSource()==lbPlayGame){
                    ImageIcon playIcon = new ImageIcon("./Character/Play2.png");
                    lbPlayGame.setIcon(playIcon);
                    GameSound.getIstance().getAudio(GameSound.BOMB).play();
                }
                if(e.getSource()==lbGuide){
                    ImageIcon GuideIcon = new ImageIcon("./Character/Option2.png");
                    lbGuide.setIcon(GuideIcon);
                    GameSound.getIstance().getAudio(GameSound.BOMB).play();
                }
                if(e.getSource()==lbHigthScore){
                    ImageIcon hightScoreIcon = new ImageIcon("./Character/HightScore2.png");
                    lbHigthScore.setIcon(hightScoreIcon);
                    GameSound.getIstance().getAudio(GameSound.BOMB).play();
                }
                if(e.getSource()==lbExit){
                    ImageIcon exitIcon = new ImageIcon("./Character/Exit2.png");
                    lbExit.setIcon(exitIcon);
                    GameSound.getIstance().getAudio(GameSound.BOMB).play();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(e.getSource()==lbPlayGame){
                    ImageIcon playIcon = new ImageIcon("./Character/Play.png");
                    lbPlayGame.setIcon(playIcon);
                }
                if(e.getSource()==lbGuide){
                    ImageIcon GuideIcon = new ImageIcon("./Character/Option.png");
                    lbGuide.setIcon(GuideIcon);
                }
                if(e.getSource()==lbHigthScore){
                    ImageIcon hightScoreIcon = new ImageIcon("./Character/HightScore.png");
                    lbHigthScore.setIcon(hightScoreIcon);
                }
                if(e.getSource()==lbExit){
                    ImageIcon exitIcon = new ImageIcon("./Character/Exit.png");
                    lbExit.setIcon(exitIcon);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getSource()==lbExit){
                    GameSound.getIstance().getAudio(GameSound.MENU).stop();
                    System.exit(0);

                }
                if(e.getSource()==lbPlayGame){
                    control.showActor();

                }
                if(e.getSource()==lbGuide){
                    control.showGuide();
                }
                if(e.getSource()==lbHigthScore){
                    control.showHightScore();
                }
            }
        };


}
