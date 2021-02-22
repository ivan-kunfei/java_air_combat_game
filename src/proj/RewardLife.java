package proj;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RewardLife extends Reward{

    public RewardLife(int x, int y){
        super(x, y);
    }
    static BufferedImage images;

    static{
        try{
            images = ImageIO.read(RewardLife.class.getResourceAsStream("/images/bee.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(){
        return images;
    }

}
