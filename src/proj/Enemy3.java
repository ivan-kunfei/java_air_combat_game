package proj;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy3 extends  Enemy implements  Shootter{

    public EnemyBullet1[] mybullets=new EnemyBullet1[1];

    public EnemyBullet1[] shoot(){
        int x1 = this.getX()+this.getWeidth()/2;
        int y = this.getY()+this.getHeight()-100;
        EnemyBullet1 enemyBullet = new EnemyBullet1(x1, y);
        mybullets[0]=(enemyBullet);
        return mybullets;
    }



    public Enemy3(int m){
        super(m);
    }


    static BufferedImage[] myImages =new BufferedImage[9];
    static {
        try {
            for(int i=0; i<8;i++) {
                myImages[i] = ImageIO.read(Enemy3.class.getResourceAsStream("/images/enemy3"+i+".png"));
            }
            myImages[8]=ImageIO.read(Enemy3.class.getResourceAsStream("/images/enemy3_hit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int dieCount=0;
    int biHitedCount=0;
    @Override
    public BufferedImage getImage() {
        if(this.getLife()>0 &&this.beHited==true){
            biHitedCount++;
            if (biHitedCount >=50){
                System.out.println(biHitedCount);
                this.beHited = false;
                biHitedCount=0;
            }
            if(biHitedCount<50) {
                return myImages[8];
            }

        }

        if(this.getLife()<=0){
            this.canShoot=false;
            dieCount++;
            if (dieCount==210){
                this.canClear=true;
                dieCount=209;
            }
            if(dieCount<=30){
                return myImages[1];
            }else if(dieCount <= 60){
                return myImages[2];
            }else if(dieCount <= 90){
                return myImages[3];
            }else if(dieCount <=120) {
                return myImages[4];
            }else if(dieCount <=150){
                return myImages[5];
            }else if(dieCount <=180){
                return myImages[6];
            }else return myImages[7];
        }else {
            return myImages[0];
        }
    }

}
