package proj;


public abstract class Enemy extends  Plane {
    public boolean canClear=false;
    public boolean beHited= false;
    public boolean canReward=true;
    private int speed =2;
    public Enemy(int life){
        super();
        this.setX( (int)(Math.random()*  (512-this.getImage().getWidth())));
        this.setY(-100);
        this.setLife(life);
    }

    public void jianming(){
        this.setLife(this.getLife()-1);
    }

    public void step(){
        int y=this.getY();
        this.setY(y+speed);
    }

    public boolean canClear(){
        if( this.getY()>=700){
            return true;
        }else{
            return this.canClear;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


}
