package boom.effect;

import boom.gameobject.GameWorld;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

public class Data {
    private static Data instance = null;
    private String framefile = "Frame.txt";
    private String animationfile = "Animation.txt";
    private String physicalmap = "Map.txt";
    private String physicalmap2 = "Map2.txt";
    private String physicalmap3 = "Map3.txt";
    private String name;

    private Hashtable<String, FrameImage> frameImages;
    private Hashtable<String, Animation> animations;

    private int[][] map;

    // Disable constructor
    private Data(){
        switch (GameWorld.level){
            case 1:
                name = physicalmap;
                break;
            case 2:
                name = physicalmap2;
                break;
            case 3:
                name = physicalmap3;
                break;
        }
    }

    public static Data getInstance(){
        if(instance == null)
            instance = new Data();
        return instance;
    }

    //Load frame from file
    public void loadFrame() throws IOException {
        frameImages = new Hashtable<String, FrameImage>();

        FileReader fr = new FileReader(framefile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if (br.readLine() == null){
            throw new IOException();
        } else {
            fr = new FileReader(framefile);
            br = new BufferedReader(fr);

            // Ignore line empty
            while((line = br.readLine()).equals(""));
            // Number of frames need to read
            int n = Integer.parseInt(line);

            for (int i = 0; i < n; i ++){
                FrameImage frame = new FrameImage();
                while((line = br.readLine()).equals(""));
                // Set frame name
                frame.setName(line);

                while((line = br.readLine()).equals(""));
                String path = line;

                while((line = br.readLine()).equals(""));
                // Read posX
                int x = Integer.parseInt(line);
                while((line = br.readLine()).equals(""));
                // Read posY
                int y = Integer.parseInt(line);
                while((line = br.readLine()).equals(""));
                // Read frame width
                int w = Integer.parseInt(line);
                while((line = br.readLine()).equals(""));
                // Read frame height
                int h = Integer.parseInt(line);

                // New image from data
                BufferedImage imageData = ImageIO.read(new File(path));
                // Cut image
                BufferedImage subImage = imageData.getSubimage(x, y, w, h);
                // Set image to frame
                frame.setImage(subImage);
                // Put all on hashtable
                instance.frameImages.put(frame.getName(), frame);
            }
        }
        br.close();
    }

    // Load animation from file
    public void loadAnimation() throws IOException {
        animations = new Hashtable<String, Animation>();

        FileReader fr = new FileReader(animationfile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if (br.readLine() == null){
            throw new IOException();
        } else {
            fr = new FileReader(animationfile);
            br = new BufferedReader(fr);

            while((line = br.readLine()).equals(""));
            int n = Integer.parseInt(line);

            for (int i = 0; i < n; i ++){
                Animation animation = new Animation();

                while((line = br.readLine()).equals(""));
                animation.setName(line);

                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");

                for (int j = 0; j < str.length; j += 2)
                    animation.addFrame(getFrameImage(str[j]), Double.parseDouble(str[j + 1]));

                instance.animations.put(animation.getName(), animation);
            }
        }
        br.close();
    }

    // Get frame
    public FrameImage getFrameImage(String name){
        FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
        return frameImage;
    }

    // Get animation
    public Animation getAnimation(String name){
        Animation animation = new Animation(instance.animations.get(name));
        return animation;
    }

    public void loadData() throws IOException {
        loadFrame();
        loadAnimation();
        loadMap("Map.txt");
    }

    public int[][] getMap(){
        return instance.map;
    }

    public void loadMap(String names) throws IOException {
        FileReader fr = new FileReader(names);
        BufferedReader br = new BufferedReader(fr);

        String line = null;
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);

        instance.map = new int[numberOfRows][numberOfColumns];

        for (int i = 0; i < numberOfRows; i ++){
            line = br.readLine();
            String[] str = line.split(" ");
            for (int j = 0; j < numberOfColumns; j ++){
                instance.map[i][j] = Integer.parseInt(str[j]);
            }
        }
        br.close();
    }

}

