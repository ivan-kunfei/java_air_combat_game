package proj;


public abstract class Plane extends  FlyingObject{


    private int life=3;
    public boolean canShoot = false;


    public Plane(){
        super();
    }


    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }



}
