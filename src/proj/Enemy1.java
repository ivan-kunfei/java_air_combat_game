package proj;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy1 extends Enemy{


    public Enemy1(int m){
        super(m);
    }

    static BufferedImage[] myImages = new BufferedImage[5];

    static{
        try{
            for(int i=0; i<myImages.length;i++ ){
                myImages[i]=ImageIO.read(Enemy1.class.getResourceAsStream("/images/enemy1"+(i+1)+".png"));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    int dieCount=0;
    @Override
    public BufferedImage getImage() {
        if(this.getLife()<=0){
            dieCount++;
            if(dieCount==200){
                this.canClear=true;
                dieCount=199;
            }
            if(dieCount<=50){
                return myImages[1];
            }else if(dieCount<=100){
                return myImages[2];
            }else if(dieCount<=150){
                return myImages[3];
            }else {
                return myImages[4];
            }
        }else{
            return myImages[0];
        }
    }
}
