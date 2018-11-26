/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boom.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.HashMap;

public class GameSound {
    public static GameSound instance;

    public static final String MENU = "menu.wav";
    public static final String PLAYGAME = "playgame.wav";
    public static final String PLAYGAME2 = "audiomap2.wav";
    public static final String PLAYGAME3 = "audiomap3.wav";
    public static final String BOMB = "newbomb.wav";
    public static final String BOMBER_DIE = "bomber_die.wav";
    public static final String BOMBER_DieDRINK = "bomDrink.wav";
    public static final String MONSTER_DIE = "monster_die.wav";
    public static final String BONG_BANG = "bomb_bang.wav";
    public static final String ITEM = "item.wav";
    public static final String WIN = "win.wav";
    public static final String LOSE = "lose.mid";
    public static final String FOOT = "foot.wav";
    private HashMap<String, AudioClip> audioMap;

    public GameSound() {
        audioMap = new HashMap<>();
        loadAllAudio();
    }

    public static GameSound getIstance() {
        if (instance == null) {
            instance = new GameSound();
        }

        return instance;
    }

    public void loadAllAudio() {
        putAudio(MENU);
        putAudio(PLAYGAME);
        putAudio(PLAYGAME2);
        putAudio(PLAYGAME3);
        putAudio(BOMB);
        putAudio(MONSTER_DIE);
        putAudio(BOMBER_DieDRINK);
        putAudio(BOMBER_DIE);
        putAudio(BONG_BANG);
        putAudio(ITEM);
        putAudio(WIN);
        putAudio(LOSE);
        putAudio(FOOT);
        
    }

    public void stop() {
        getAudio(MENU).stop();
        getAudio(PLAYGAME).stop();
        getAudio(PLAYGAME2).stop();
        getAudio(PLAYGAME3).stop();
        getAudio(BOMB).stop();
        getAudio(MONSTER_DIE).stop();
        getAudio(BOMBER_DieDRINK).stop();
        getAudio(BOMBER_DIE).stop();
        getAudio(BONG_BANG).stop();
        getAudio(ITEM).stop();
        getAudio(WIN).stop();
        getAudio(LOSE).stop();
    }

    public void putAudio(String name) {
        AudioClip auClip = Applet.newAudioClip(GameSound.class.getResource(name));
        audioMap.put(name, auClip);
    }

    public AudioClip getAudio(String name) {
        return audioMap.get(name);
    }
}
