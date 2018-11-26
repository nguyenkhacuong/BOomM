    package boom.interfaces;

import boom.sound.GameSound;
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.MouseEvent;
    import java.awt.event.MouseListener;

    public class Guide extends JPanel implements MouseListener {
        private Control control;
        private JLabel lbbackground;
        private ImageIcon backgroundIcon, cancel;
        private JLabel lbOK;

        public Guide(Control control) {
            this.control = control;
            setBackground(Color.YELLOW);
            setLayout(null);
            initLabel();
        }

        public void initLabel(){
            lbbackground = new JLabel();
            lbbackground.setBounds(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
            lbbackground.setBackground(Color.BLACK);
            backgroundIcon = new ImageIcon("./Character/background_option.png");
            lbbackground.setIcon(backgroundIcon);

            lbOK = new JLabel();
            cancel = new ImageIcon("./Character/cancel1.png");
            lbOK.setBounds(1110, 625, cancel.getIconWidth(), cancel.getIconHeight());
            lbOK.addMouseListener(this);
            lbOK.setIcon(cancel);

            add(lbOK);
            add(lbbackground);


        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getSource()==lbOK){
                control.showMenu();
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
           
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource()==lbOK){
                lbOK.setIcon(cancel);
            }
        }
    }