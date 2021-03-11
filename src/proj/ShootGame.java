package proj;

import com.sun.deploy.panel.JavaPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class ShootGame extends JavaPanel {

    public static void main(String[]args){

        //Set frame
        JFrame frame= new JFrame("Flight Game");
        ShootGame game= new ShootGame();
        frame.add(game);
        frame.setSize(533,820);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //Find a icon
        /*frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // */

        // Execute GAME
        game.action();

    }
    // boos的初始生命价
    private int bossLife=180;
    // 一号敌机初始生命值
    private int ememy1Life=2;
    // 二号敌机初始生命值
    private int enemy2Life=4;
    // 三号敌机初始生命值
    private int enemy3Life=5;
    // 当前界面有没有boss存在
    private boolean hasBoss=false;
    // 游戏状态 ：start  sunning  pause  gameover
    private int state;
    private static final int START = 0;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int GAME_OVER = 3;
    private int score = 0; // 得分
    private Timer timer; // 定时器
    private int intervel = 500 / 100; // 计时器刷新的时间间隔(10毫秒)
    // 英雄机
    private Hero hero = new Hero();

    // 不同游戏状态的图片 还有背景图片
    public static BufferedImage start;
    public static BufferedImage pause;
    public static BufferedImage gameover;
    private Background background=new Background();
    public static BufferedImage background1;
    public static BufferedImage background2;


    // 子弹列表  敌机列表  补给列表
    private List<Bullet> Bullets = new ArrayList(); // 子弹数组
    private List<Enemy> ememies= new ArrayList<>();
    private List<Reward> rewards = new ArrayList<>();

    static { // 静态代码块，初始化图片资源： 背景  开始  暂停 结束
        try {
            background1 = ImageIO.read(ShootGame.class.getResource("/images/background.png"));
            background2 = ImageIO.read(ShootGame.class.getResource("/images/background.png"));
            start = ImageIO.read(ShootGame.class.getResourceAsStream("/images/bee.png"));
            pause = ImageIO.read(ShootGame.class.getResourceAsStream("/images/pause.png"));
            gameover = ImageIO
                    .read(ShootGame.class.getResourceAsStream("/images/gameover.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void action(){
        // 鼠标监听事件
        MouseAdapter l = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) { // 鼠标移动
                if (state == RUNNING) { // 运行状态下移动英雄机--随鼠标位置
                    int x = e.getX();
                    int y = e.getY();
                    hero.moveTo(x,y);
                }
            }


            @Override
            public void mouseClicked(MouseEvent e) { // 鼠标点击
                switch (state) {
                    case START:
                        state = RUNNING; // 启动状态下运行
                        break;
                    case RUNNING:
                        state = PAUSE;
                        break;
                    case PAUSE:
                        state = RUNNING;
                        break;
                    case GAME_OVER: // 游戏结束，清理现场
                        ememy1Life=2;
                        enemy2Life=3;
                        enemy3Life=5;
                        bossLife=180;
                        ememies = new ArrayList<>(); // 清空飞行物
                        Bullets = new ArrayList<>(); // 清空子弹
                        hero = new Hero(); // 重新创建英雄机
                        score = 0; // 清空成绩
                        state = START; // 状态设置为启动
                        hasBoss=false; // boss状态设置为没有boss
                        bossCount=300;  // boss计数重新设置为300
                        break;
                }
            }

        };
        this.addMouseListener(l);
        this.addMouseMotionListener(l);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state ==RUNNING){
                    background.move();
                    shootAction();
                    allStep();
                    reduceLife();
                    clear();
                    generateRewards();
                    enterAction();
                    checkGameOver();
                }
                repaint();
            }
        }, intervel, intervel);
    }

    /**
     *  如果英雄机生命小于等于0  游戏结束
     */
    public void checkGameOver(){
        if(hero.getLife()<=0){
            state=GAME_OVER;
        }
    }

    /**
     * 随机生成飞行物
     *
     * 分数大于300的时候且场景中不存在boss的时候, 生成一个boss
     * 之后每当分数增加700多且场景中不存在boss的时候  生成一个boss
     *
     * 有boss的时候 有0.2的几率生成敌机2
     * 没有boss的时候  以 2：3：2的概率生成三种敌机
     */
    int bossCount = 300;
    public Enemy nextOne() {
        if(score>=bossCount  && hasBoss==false){
            Boss b=new Boss(bossLife);
            bossCount+=700;
            hasBoss=true;
            return b;
        }
        if(hasBoss) {
            double dd=Math.random();
            if(dd<=0.8) {
                return null;
            }else{
                return new Enemy2(enemy2Life);
            }
        }else{
            Random random = new Random();
            int type = random.nextInt(70); // [0,70)
            if (type <= 20) {
                return new Enemy1(ememy1Life);
            } else if (type <= 50) {
                return new Enemy2(enemy2Life);
            } else {
                return new Enemy3(enemy3Life);
            }
        }
    }

    /** 飞行物入场 */
    int flyEnteredIndex = 0; // 飞行物入场计数
    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 120 == 0) { // 400毫秒生成一个飞行物--10*40
            Enemy enemy=nextOne();
            if(enemy != null) {
                ememies.add(enemy);
            }
        }
    }

    /**
     * 画分数和生命值的方法
     */

    public void paintScore(Graphics g){
        int x=10;
        int y=25;
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 17); // 字体
        g.setColor(new Color(0xEE9223));
        g.setFont(font); // 设置字体
        g.drawString("SCORE: " + score, x, y); // 画分数
        y += 20; // y坐标增20
        g.drawString("LIFE: " + hero.getLife(), x, y); // 画命
    }

    /**
     * 画补给的方法
     */
    public void paintReward(Graphics g){
        for (Reward r:rewards){
            g.drawImage(r.getImage(), r.getX(), r.getY(),null);
        }
    }


    /** 画敌机 */
    public void paintEnemy(Graphics g){
        for(Enemy e :ememies){
            g.drawImage(e.getImage(),e.getX(),e.getY(),null);
        }
    }

    /**
     *  画子弹
     */
    public void paintBullet(Graphics g){
        for (Bullet b: Bullets){
            g.drawImage(b.getImage(),b.getX(), b.getY(),null);
        }
    }

    /**
     * 所有敌机,子弹，补给物 移动一步
     */
    public void allStep(){
        for (Enemy e:ememies){
            e.step();
        }

        for (Reward r:rewards){
            r.step();
        }

        for (Bullet b: Bullets){
            b.step();
        }
    }


    /**
     * 射击
     */
    int shootIndex = 0; // 射击计数

    public void shootAction() {
        shootIndex++;
        // 每140毫秒 英雄机发射一次子弹
        if (shootIndex % 12 == 0) {
            for(Bullet b:hero.shoot()){
                Bullets.add(b);
            }
        }

        // 每2700毫秒 敌机发射一次子弹
        if(shootIndex % 270 == 0 ) {
            for (Enemy e : ememies) {
                if (e instanceof  Shootter && e.getLife()>0){
                    for (Bullet b: ((Shootter) e).shoot()){
                        Bullets.add(b);
                    }
                }

            }
        }
    }


    /** 画英雄机 */
    public void paintHero(Graphics g) {
        g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
    }

    /** 画游戏状态 */
    public void paintState(Graphics g) {
        switch (state) {
            case START: // 启动状态
                g.drawImage(start, -100, 0, null);
                break;
            case PAUSE: // 暂停状态
                g.drawImage(pause, 0, 0, null);
                break;
            case GAME_OVER: // 游戏终止状态
                g.drawImage(gameover, 0, 0, null);
                break;
        }
    }

    /**
     * 绘制所有图像
     */
    @Override
    public void paint(Graphics g) {
        background.paint(g);
        paintState(g);
        paintEnemy(g);
        paintHero(g);
        paintReward(g);
        paintBullet(g);
        paintScore(g);
    }

    /**
     * 判断子弹和飞机是否相撞
     */
    public boolean isCrash(Bullet b, Plane p){
        if(b.getX()+b.getWeidth()>p.getX() &&
            b.getX()<p.getX()+p.getWeidth()&&
            b.getY()+b.getHeight()>p.getY()&&
            b.getY()<p.getY()+p.getHeight()
        ){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除越界子弹和越界补给
     * 如果英雄机和敌机碰撞  分情况讨论
     * 如果英雄机和补给碰撞  补给消失
     * 如果子弹和飞机碰撞 飞机减命  删除子弹
     * 飞机被击中状态为true 激发被击中动画效果
     *
     * 因为列表不能一边遍历一边删除元素  所以复制一个新列表
     * 对新列表进行删改  最后把新列表赋值给旧列表
     */
    public void reduceLife(){
        List<Reward> lr=new ArrayList<>();
        lr.addAll(rewards);
        List<Bullet> lb=new ArrayList<>();
        lb.addAll(Bullets);

        // 删除越界子弹
        for(Bullet b:Bullets){
            if( b.getY()>800 || b.getY()<3){
                lb.remove(b);
            }
        }

        // 删除越界补给
        // 如果英雄机和补给碰撞 删除补给
        for (Reward r:rewards) {
            if (r.getY() > 800) {
                lr.remove(r);
            }
            if (hero.isGetReward(r)) {
                lr.remove(r);
                if(r instanceof RewardLife){
                    hero.setLife(hero.getLife()+1);
                }else{
                    hero.isdouble = true;
                }
            }
        }

        // 如果英雄机和普通敌机碰撞 英雄机减命 敌机生命值设为0
        // 如果英雄机和Boss相撞 同归于尽 游戏结束
        for (Enemy e: ememies){
            if ( hero.isBoom(e)) {
                if(e instanceof  Boss){
                    e.setLife(0);
                    hero.setLife(0);
                }else {
                    e.setLife(0);
                    hero.jianming();
                    hero.isdouble = false;
                }
            }
        }

        // 子弹射到飞机的情况
        for(Bullet b:Bullets){
            if(b instanceof  HeroBullet){
                for(Enemy e:ememies){
                    if (isCrash(b,e) && e.getLife()>=0){
                        lb.remove(b);
                        e.jianming();
                        e.beHited=true;
                    }
                }
            }
            // 你可以考虑增加一下英雄机被击中的动画效果
            if(b instanceof  EnemyBullet && isCrash(b,hero)){
                lb.remove(b);
                hero.jianming();
                hero.isdouble=false;
                System.out.println("英雄当前生命为：" + hero.getLife() );
            }
        }
        Bullets=lb;
        rewards=lr;
    }

    /**
     * 如果敌机 canClear  也就是死亡动画效果播放完毕
     * 则删除敌机  分数 += 30；
     * 如果boss被删除 游戏难度增加 下一个boss生命值加50  其他敌机生命值+1  有boss的状态为false
     */
    public void clear () {
        List<Enemy> ne = new ArrayList<>();
        ne.addAll(ememies);
        for (Enemy e : ememies) {

            if (e.canClear()) {
                if(e instanceof Boss){
                    bossLife+=50;
                    ememy1Life+=1;
                    enemy2Life+=1;
                    enemy3Life+=1;
                    hasBoss=false;
                }
                ne.remove(e);
                score += 30;
            }
        }
        ememies = ne;
    }


    /**
     * 掉落补给
     * 敌机一和敌机二 有0.5的概率掉落补给
     * boos死亡后掉落四个补给
    */
    public void generateRewards() {
        for (Enemy e : ememies) {
            if (e instanceof Enemy3 && e.getLife() <= 0) {
                if(e.canReward ){
                    double d=Math.random();
                    if(d<0.5) {
                        int x = e.getX();
                        int y = e.getY();
                        RewardLife r = new RewardLife(x, y);
                        rewards.add(r);
                    }
                    e.canReward = false;
                }
            }
            if (e instanceof Enemy2 && e.getLife() <= 0) {
                if(e.canReward ){
                    double d=Math.random();
                    if(d<0.5) {
                        int x = e.getX();
                        int y = e.getY();
                        Reward r = new Reward(x, y);
                        rewards.add(r);
                    }
                    e.canReward = false;
                }
            }
            if (e instanceof Boss && e.getLife() <= 0) {
                if(((Boss) e).canReward ){
                        int x = e.getX();
                        int y = e.getY();
                        Reward r1 = new Reward(x, y);
                        RewardLife r2=new RewardLife(x+e.getWeidth()/4,y);
                    RewardLife r3=new RewardLife(x+e.getWeidth()/2,y);
                    RewardLife r4=new RewardLife(x+e.getWeidth()*3/4,y);
                        rewards.add(r1); rewards.add(r2);rewards.add(r3);rewards.add(r4);
                    }
                    ((Boss) e).canReward = false;
                }
        }
    }


}


