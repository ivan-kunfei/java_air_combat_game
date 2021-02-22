package proj;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HeroBullet extends Bullet {
    private BufferedImage bulletImage;
    static BufferedImage images[]=new BufferedImage[2];

    static{
        try{
            images[1] = ImageIO.read(HeroBullet.class.getResourceAsStream("/images/bullet.png"));
            images[0] = ImageIO.read(HeroBullet.class.getResourceAsStream("/images/bulletSingle.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public HeroBullet(int x, int y,int z){
        super(x,y);
        this.speed=-5;
        if(z==1){
            this.bulletImage= images[0];
        }
        if(z==2){
            this.bulletImage=images[1];
        }
    }



    @Override
    public BufferedImage getImage() {
        return this.bulletImage;
    }

}
