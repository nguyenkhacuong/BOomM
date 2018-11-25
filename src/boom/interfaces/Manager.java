package boom.interfaces;

import boom.gameobject.GameWorld;
import java.awt.event.KeyEvent;

public class Manager {

    private GameWorld gameWorld;

    // Constructor with variable gameworld
    public Manager(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

    public void inputKeyPressed(int keyCode){
        switch (keyCode)
        {
            case KeyEvent.VK_UP:

                gameWorld.bomber.setStop(false);
                gameWorld.bomber.setCode(KeyEvent.VK_UP);
                gameWorld.bomber.setSpeedY(gameWorld.bomber.getSpeed()*-1);
                gameWorld.bomber.setSpeedX(0);
                break;

            case KeyEvent.VK_DOWN:
                gameWorld.bomber.setStop(false);
                gameWorld.bomber.setCode(KeyEvent.VK_DOWN);
                gameWorld.bomber.setSpeedY(gameWorld.bomber.getSpeed());
                gameWorld.bomber.setSpeedX(0);
                break;

            case KeyEvent.VK_LEFT:
                gameWorld.bomber.setStop(false);
                gameWorld.bomber.setCode(KeyEvent.VK_LEFT);
                gameWorld.bomber.setSpeedX(gameWorld.bomber.getSpeed()*-1);
                gameWorld.bomber.setSpeedY(0);
                break;

            case KeyEvent.VK_RIGHT:

                gameWorld.bomber.setStop(false);
                gameWorld.bomber.setCode(KeyEvent.VK_RIGHT);
                gameWorld.bomber.setSpeedX(gameWorld.bomber.getSpeed());
               // System.out.println(gameWorld.bomber.getPosX() + "--" + gameWorld.bomber.getSpeedX());
                gameWorld.bomber.setSpeedY(0);
                break;

            case KeyEvent.VK_SPACE:
                gameWorld.bomber.setCheck(true);
                gameWorld.boom.put = true;
                break;

            case KeyEvent.VK_1:

                break;

            case KeyEvent.VK_2:

                break;

            case KeyEvent.VK_3:

                break;
        }
    }

    public void inputKeyReleased(int keyCode){
        switch (keyCode)
        {
            case KeyEvent.VK_UP:
                gameWorld.bomber.setStop(true);
                gameWorld.bomber.setSpeedY(0);
                break;

            case KeyEvent.VK_DOWN:
                gameWorld.bomber.setStop(true);
                gameWorld.bomber.setSpeedY(0);
                break;

            case KeyEvent.VK_LEFT:
                gameWorld.bomber.setStop(true);
                gameWorld.bomber.setSpeedX(0);
                break;

            case KeyEvent.VK_RIGHT:
                gameWorld.bomber.setStop(true);
                gameWorld.bomber.setSpeedX(0);
                break;

            case KeyEvent.VK_SPACE:

                break;

            case KeyEvent.VK_1:

                break;

            case KeyEvent.VK_2:

                break;

            case KeyEvent.VK_3:

                break;
        }
    }
}

