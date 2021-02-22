package proj;

import java.awt.image.BufferedImage;

public abstract class Bullet extends  FlyingObject{

    public BufferedImage bulletImage;
    public int speed;

    public Bullet(int x, int y){
        this.setX(x);
        this.setY(y);
    }


    @Override
    public BufferedImage getImage() {
        return this.bulletImage;
    }


    public void step(){
        this.setY(this.getY() + speed);
    }



}
