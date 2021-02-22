package proj;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hero extends  Plane implements Shootter{

    public boolean isdouble = false;
    public HeroBullet[] mybullets;

    public Hero(){
        super();
        this.setLife(30);
        this.setX(200);
        this.setY(400);
    }

    public void jianming(){
        this.setLife(this.getLife()-1);
    }

    static BufferedImage[] myImages = new BufferedImage[2];

    static {
        try {
            myImages[0] = ImageIO.read(Hero.class.getResourceAsStream("/images/hero0.png"));
            myImages[1] = ImageIO.read(Hero.class.getResourceAsStream("/images/hero1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void moveTo(int x, int y){
        this.setX(x-this.getWeidth()/2);
        this.setY(y-this.getHeight()/2);
    }

    int imagesCount = 0;
    @Override
    public BufferedImage getImage() {
        imagesCount++;
        if (imagesCount==40){
            imagesCount=1;
        }
        if (imagesCount <= 20) {
            return myImages[0];
        } else {
            return myImages[1];
        }
    }



    public HeroBullet[] shoot(){
        int x= this.getX()+this.getWeidth()/2;
        int x1 = this.getX()+this.getWeidth()/4;
        int x2 = this.getX()+this.getWeidth()*3/4;
        int y = this.getY()-5;
        HeroBullet heroBullet = new HeroBullet(x,y,1);
        HeroBullet heroBullet1 = new HeroBullet(x1, y,2);
        HeroBullet heroBullet2 = new HeroBullet(x2,y,2);
        if(this.isdouble){
            this.mybullets=new HeroBullet[2];
            this.mybullets[0]=(heroBullet1); this.mybullets[1]=heroBullet2;
            return mybullets;
        }else{
            this.mybullets=new HeroBullet[1];
            this.mybullets[0]=heroBullet;
            return  mybullets;
        }
    }


    /**
     * 判断与敌机相撞
     */
    public boolean isBoom(Enemy e){
        if(e.getLife()>0 && e.getX()>this.getX()-e.getWeidth() &&
                e.getX()<this.getX()+this.getWeidth() &&
                e.getY()>this.getY()-e.getHeight()&&
                e.getY()<this.getY()+this.getHeight()
        ){
            return true;
        }else{
            return false;
        }
    }

    /**
     *判断与补给相撞
     */
    public boolean isGetReward(Reward e){
        if(e.getX()>this.getX()-e.getWeidth() &&
                e.getX()<this.getX()+this.getWeidth() &&
                e.getY()>this.getY()-e.getHeight()&&
                e.getY()<this.getY()+this.getHeight()
        ){
            return true;
        }else{
            return false;
        }
    }

}
