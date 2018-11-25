/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boom.effect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FrameImage {
    private String name;
    private BufferedImage image;

    public FrameImage(String name, BufferedImage image){
        this.name = name;
        this.image = image;
    }

    public FrameImage(FrameImage frameImage){
        image = new BufferedImage(frameImage.getImageWidth(), frameImage.getImageHeight()
                , frameImage.getImage().getType());
        Graphics g = image.getGraphics();
        g.drawImage(frameImage.getImage(), 0, 0, null);
    }

    public FrameImage(){
        image = null;
        name = null;
    }

    public int getImageWidth(){
       // Get image width
        return image.getWidth();
    }

    public int getImageHeight(){
        // Get image height
        return image.getHeight();
    }

    public void draw(Graphics2D g2, int x, int y){
        g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}

