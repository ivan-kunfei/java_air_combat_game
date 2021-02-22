package proj;

import javax.imageio.ImageIO;
import java.io.IOException;

public class EnemyBullet1 extends EnemyBullet{



    public EnemyBullet1(int x, int y){
        super(x,y);
    }

    {
        try{
            this.bulletImage = ImageIO.read(HeroBullet.class.getResourceAsStream("/images/bomb.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }







}
