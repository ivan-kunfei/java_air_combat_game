package proj;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {

    private int x;
    private int y;

    public FlyingObject(){

    }


    public abstract BufferedImage getImage();

    public int getWeidth(){
        return this.getImage().getWidth();
    }

    public int getHeight(){
        return this.getImage().getHeight();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
