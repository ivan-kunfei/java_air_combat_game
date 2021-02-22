package proj;

import javax.imageio.ImageIO;
import java.io.IOException;

public class EnemyBullet2 extends EnemyBullet {


    public EnemyBullet2(int x, int y){
        super(x,y);
    }

    {
        try{
            bulletImage = ImageIO.read(EnemyBullet2.class.getResourceAsStream("/images/bullet1.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
