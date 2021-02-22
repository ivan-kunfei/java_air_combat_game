package proj;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Reward extends  FlyingObject{

    static BufferedImage image;
    private int xpeed=2;
    private int yspeed=1;

    static{
        try{
            image = ImageIO.read(Reward.class.getResourceAsStream("/images/bullet_supply.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public Reward(int x, int y){
        this.setX(x);
        this.setY(y);
    }


    public void step(){
        if(this.getX()+this.getWeidth() >= 480 || this.getX()<=0 ){
            this.xpeed= - this.xpeed;
        }
        int x=this.getX();
        int y=this.getY();
        this.setX(x+xpeed);
        this.setY(y+yspeed);
    }


    @Override
    public BufferedImage getImage() {
        return image;
    }
}
