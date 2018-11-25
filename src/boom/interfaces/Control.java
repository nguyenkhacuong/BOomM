package boom.interfaces;

import boom.sound.GameSound;

import javax.swing.*;
import java.awt.*;

public class Control extends JPanel {
    private static final String TAG_MENU = "tag_menu";
    private static final String TAG_PLAYGAME = "tag_playgame";
    private static final String TAG_GUIDE = "tag_Guide";
    private static final String TAG_HIGHTSCORE = "tag_hightscore";
    private static final String TAG_ACTOR = "tag_actor";
    // Manage components in a manner that at one time only displays one component
    private CardLayout cardLayout;
    
    private GameFrame gameFrame;
    private Menu menu;
    private GamePanel gamepanel;
    private Guide guide;
    private HightScorePanel hightScorePanel;
    private ChooseActor chooseActor;

    public Control(GameFrame gameFrames){
        this.gameFrame = gameFrames;
        setBackground(Color.WHITE);
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        menu = new Menu(this);
        add(menu,TAG_MENU);
        gamepanel = new GamePanel(this);
        add(gamepanel, TAG_PLAYGAME);
        guide = new Guide(this);
        add(guide, TAG_GUIDE);
        hightScorePanel = new HightScorePanel(this);
        add(hightScorePanel, TAG_HIGHTSCORE);
        chooseActor = new ChooseActor(this);
        add(chooseActor, TAG_ACTOR);
        showMenu();

    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }
    public void showMenu(){
        cardLayout.show(Control.this, TAG_MENU);
        menu.requestFocus();
    }

    public void showPanel(){
        cardLayout.show(Control.this, TAG_PLAYGAME);
        gamepanel.requestFocus();
        GameSound.getIstance().getAudio(GameSound.MENU).stop();
        GameSound.getIstance().getAudio(GameSound.PLAYGAME).loop();
    }

    public void showPanel(int type){
        gamepanel.chooseActor(type);
        cardLayout.show(Control.this, TAG_PLAYGAME);
        gamepanel.requestFocus();
        GameSound.getIstance().getAudio(GameSound.MENU).stop();
        GameSound.getIstance().getAudio(GameSound.PLAYGAME).loop();
    }

    public void showGuide(){
        cardLayout.show(Control.this, TAG_GUIDE);
        guide.requestFocus();
    }

    public void showHightScore(){
        cardLayout.show(Control.this, TAG_HIGHTSCORE);
        hightScorePanel.requestFocus();
    }

    public void showActor(){
        cardLayout.show(Control.this, TAG_ACTOR);
        chooseActor.requestFocus();
    }

}
