package proj;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy2 extends  Enemy implements  Shootter{

    public Enemy2(int m){
        super(m);
    }

    public EnemyBullet[] mybullets=new EnemyBullet[1];

    public EnemyBullet[] shoot(){

        int x1 = this.getX()+this.getWeidth()/2;
        int y = this.getY()+this.getHeight()-50;
        EnemyBullet enemyBullet2 = new EnemyBullet2(x1, y);
        mybullets[0]=enemyBullet2;
        return mybullets;
    }

    static BufferedImage [] myImages =new BufferedImage[6];
    static {
        try {
            myImages[0]=ImageIO.read(Enemy2.class.getResourceAsStream("/images/emeny21.png"));
            myImages[1]=ImageIO.read(Enemy2.class.getResourceAsStream("/images/ememy22.png"));
            myImages[2]=ImageIO.read(Enemy2.class.getResourceAsStream("/images/enemy23.png"));
            myImages[3]=ImageIO.read(Enemy2.class.getResourceAsStream("/images/enemy24.png"));
            myImages[4]=ImageIO.read(Enemy2.class.getResourceAsStream("/images/enemy25.png"));
            myImages[5]=ImageIO.read(Enemy2.class.getResourceAsStream("/images/enemy2_hit.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int dieCount=0;
    int hitedCount=0;
    @Override
    public BufferedImage getImage() {
        if(this.getLife()>0 && this.beHited){
            hitedCount++;
            if(hitedCount>=50){
                System.out.println(hitedCount);
                hitedCount=0;
                beHited=false;
            }
            if(hitedCount<50){
                return myImages[5];
            }

        }


        if(this.getLife()<=0){
            dieCount++;
            if (dieCount==200){
                this.canClear=true;
                dieCount=199;
                return myImages[4];
            }
            if(dieCount<=50){
                return myImages[1];
            }else if(dieCount <= 100){
                return myImages[2];
            }else if(dieCount <= 150){
                return myImages[3];
            }else{
                return myImages[4];
            }
        }else{
            return myImages[0];
        }
    }
}
