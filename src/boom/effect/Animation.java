/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boom.effect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    private String name;
    // Check frame
    private boolean repeated;
    // Array contain frame
    private ArrayList<FrameImage> frameImages;
    // Present frame
    private int currentFrame;
    // Array contain delay time
    private ArrayList<Double> delayFrames;
    // Array contain inogre frame
    private ArrayList<Boolean> inogreFrames;

    private long beginTime;
    private boolean drawRectFrame;



    public Animation(){
        delayFrames = new ArrayList<Double>();
        beginTime = 0;
        currentFrame = 0;
        frameImages = new ArrayList<FrameImage>();
        inogreFrames = new ArrayList<Boolean>();
        drawRectFrame = false;
        repeated = true;
    }

    public Animation(Animation animation){
        beginTime = animation.beginTime;
        currentFrame = animation.currentFrame;
        drawRectFrame = animation.drawRectFrame;
        repeated = animation.repeated;

        delayFrames = new ArrayList<Double>();
        for(double dFrames : animation.delayFrames){
            delayFrames.add(dFrames);
        }

        frameImages = new ArrayList<FrameImage>();
        for(FrameImage FrameImg : animation.frameImages){
            frameImages.add(new FrameImage(FrameImg));
        }

        inogreFrames = new ArrayList<Boolean>();
        for(Boolean b : animation.inogreFrames){
            inogreFrames.add(b);
        }
    }

    public ArrayList<Boolean> getInogreFrames() {
        return inogreFrames;
    }

    public void setInogreFrames(ArrayList<Boolean> inogreFrames) {
        this.inogreFrames = inogreFrames;
    }

    public Boolean isInogreFrame(int id){
        return inogreFrames.get(id);
    }

    public void setInogreFrame(int id){
        if(id >= 0 && id < frameImages.size()){
            inogreFrames.set(id, true);
        }
    }

    public void unInogreFrame(int id){
        if(id >= 0 && id < frameImages.size()){
            inogreFrames.set(id, false);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public ArrayList<FrameImage> getFrameImages() {
        return frameImages;
    }

    public void setFrameImages(ArrayList<FrameImage> frameImages) {
        this.frameImages = frameImages;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        if(currentFrame >= 0 && currentFrame < frameImages.size())
            this.currentFrame = currentFrame;
        else
            this.currentFrame = 0;
    }

    public ArrayList<Double> getDelayFrames() {
        return delayFrames;
    }

    public void setDelayFrames(ArrayList<Double> delayFrames) {
        this.delayFrames = delayFrames;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public boolean isDrawRectFrame() {
        return drawRectFrame;
    }

    public void setDrawRectFrame(boolean drawRectFrame) {
        this.drawRectFrame = drawRectFrame;
    }

    public void reset(){
        beginTime = 0;
        currentFrame = 0;

        for(int i = 0; i < inogreFrames.size(); i ++){
            inogreFrames.set(i, false);
        }
    }

    // Add frame
    public void addFrame(FrameImage frameImage, double timeNextFrame){
        inogreFrames.add(false);
        frameImages.add(frameImage);
        delayFrames.add(new Double(timeNextFrame));
    }

    public BufferedImage getCurrentImage(){
        return frameImages.get(currentFrame).getImage();
    }

    // Update and repaint next frame in animation
    public void updateFrame(long currentTime){
        if(beginTime == 0)
            beginTime = currentTime;
        else {
            if(currentTime - beginTime > delayFrames.get(currentFrame)){
                nextFrame();
                beginTime = currentTime;
            }
        }
    }

    // Set next frame
    public void nextFrame(){
        if(currentFrame >= frameImages.size() - 1) {
            if (repeated)
                currentFrame = 0;
        } else {
            currentFrame++;
        }
        if(inogreFrames.get(currentFrame)) nextFrame();
    }

    // Check last frame
    public boolean isLastFrame(){
        if(currentFrame == frameImages.size() - 1)
            return true;
        else return false;
    }

    // Flip frame
    public void flipAllImage(){
        for (int i = 0; i < frameImages.size(); i ++){
            BufferedImage image = frameImages.get(i).getImage();
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(), 0);

            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            image = op.filter(image, null);

            frameImages.get(i).setImage(image);
        }
    }

    public void draw(int x, int y, Graphics2D g2){
        BufferedImage image = getCurrentImage();

        g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
        if(drawRectFrame)
            g2.drawRect(x - image.getWidth()/2, x - image.getWidth()/2, image.getWidth(), image.getHeight());
    }
}
