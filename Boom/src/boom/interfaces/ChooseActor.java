package boom.interfaces;

import boom.sound.GameSound;
import boom.gameobject.Bomber;
import boom.gameobject.GameWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChooseActor extends JPanel implements MouseListener {
    private Control control;
    private JLabel lbBoz;
    private JLabel lbIke;
    private JLabel lbPlunk;
    private JLabel lbOption;
    private JLabel lbChoose;
    private ImageIcon backgrLbChoose;
    private JLabel lbCancel;
    private GameWorld gameWorld;

    public ChooseActor(Control control) {
        this.control = control;
        setBackground(Color.YELLOW);
        setLayout(null);
        initLabel();
        initbackground();
    }

    public ChooseActor(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

    // Add label 
    public void initLabel() {
        lbCancel = setLabel(1135, 625, "./Character/cancel1.png");
        lbBoz = setLabel(290, 387, "./Character/khoKho1.png");
        lbIke = setLabel(565, 387, "./Character/beBong1.png");
        lbPlunk = setLabel(840, 387, "./Character/tiBanh1.png");

        lbOption = setLabel(488, 102, "./Character/opkhokho.png");

        add(lbCancel);
        add(lbBoz);
        add(lbIke);
        add(lbPlunk);
        add(lbOption);

        lbCancel.addMouseListener(this);
        lbOption.addMouseListener(this);
        lbBoz.addMouseListener(this);
        lbIke.addMouseListener(this);
        lbPlunk.addMouseListener(this);

        gameWorld = new GameWorld();
    }

    // Add background
    public void initbackground() {

        lbChoose = new JLabel();
        lbChoose.setBounds(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
        lbChoose.setBackground(Color.BLACK);
        backgrLbChoose = new ImageIcon("./Character/background_Actor.png");
        lbChoose.setIcon(backgrLbChoose);

        add(lbChoose);
    }

    // Setup label
    public JLabel setLabel(int x, int y, String ImageIcon) {
        JLabel label = new JLabel();
        ImageIcon Icon = new ImageIcon(ImageIcon);
        label.setBounds(x, y, Icon.getIconWidth(), Icon.getIconHeight());
        label.setIcon(Icon);
        return label;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == lbCancel) {
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
            control.showMenu();
        }
        if (e.getSource() == lbBoz) {
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
            control.showPanel(2);
            Bomber.ALIVE = true;
            GamePanel.isRunning = true;
        }
        if (e.getSource() == lbIke) {
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
            control.showPanel(1);
            Bomber.ALIVE = true;
            GamePanel.isRunning = true;
        }
        if (e.getSource() == lbPlunk) {
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
            control.showPanel(3);
            Bomber.ALIVE = true;
            GamePanel.isRunning = true;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource() == lbCancel) {
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
            ImageIcon cancelIcon = new ImageIcon("./Character/cancel2.png");
            lbCancel.setIcon(cancelIcon);
        }
        if (e.getSource() == lbBoz) {
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
            ImageIcon khoKhoIcon = new ImageIcon("./Character/khoKho2.png");
            ImageIcon khoKho = new ImageIcon("./Character/opkhokho.png");
            lbBoz.setIcon(khoKhoIcon);
            lbOption.setIcon(khoKho);
            //gameWorld.bomber.setChoosen(2);
        }
        if (e.getSource() == lbIke) {
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
            ImageIcon beBongIcon = new ImageIcon("./Character/beBong2.png");
            ImageIcon beBong = new ImageIcon("./Character/opbebong.png");
            lbIke.setIcon(beBongIcon);
            lbOption.setIcon(beBong);
        }
        if (e.getSource() == lbPlunk) {
            GameSound.getIstance().getAudio(GameSound.BOMB).play();
            ImageIcon tiBanhIcon = new ImageIcon("./Character/tiBanh2.png");
            ImageIcon tiBanh = new ImageIcon("./Character/optibanh.png");
            lbPlunk.setIcon(tiBanhIcon);
            lbOption.setIcon(tiBanh);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource() == lbCancel) {
            lbCancel.setIcon(new ImageIcon("./Character/cancel1.png"));
        }
        if (e.getSource() == lbBoz) {
            lbBoz.setIcon(new ImageIcon("./Character/khoKho1.png"));
        }
        if (e.getSource() == lbIke) {
            lbIke.setIcon(new ImageIcon("./Character/beBong1.png"));
        }
        if (e.getSource() == lbPlunk) {
            lbPlunk.setIcon(new ImageIcon("./Character/tiBanh1.png"));
        }
    }
}
