package proj;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background {
    // 背景图图像
    private static BufferedImage bgImg1;
    private static BufferedImage bgImg2;

    //背景图坐标
    //背景图滚动速度
    private int height;
    private int speed;
    private int y1;
    private int y2;


    static {
        try{
            bgImg1 = ImageIO.read(Background.class.getResource("/images/background.png"));
            bgImg2 = ImageIO.read(Background.class.getResource("/images/background.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Background(){
        height = 768;
        speed =3;
        y1=0;
        y2=-height;
    }

    // 绘画游戏背景图像
    public void paint(Graphics g){
        g.drawImage(bgImg1,0,y1,null);
        g.drawImage(bgImg2,0, y2,null);
    }

    // 背景图滚动
    public void move(){
        y1 += speed;
        y2 += speed;
        if( y1>=height ){
            y1 = -height;
        }
        if(y2 >= height){
            y2= -height;
        }
    }
}
