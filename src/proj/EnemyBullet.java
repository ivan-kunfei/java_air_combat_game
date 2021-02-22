package proj;

import java.awt.image.BufferedImage;

public abstract class EnemyBullet extends  Bullet{

    public BufferedImage image;

    public EnemyBullet(int x, int y){
        super(x,y);
        this.speed=3;
    }



    @Override
    public BufferedImage getImage() {
        return this.bulletImage;
    }

}
