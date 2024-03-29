package cz.stv.canvasdemofx;

import java.util.Date;

public class Enemy {
    double enemyy;
    double enemyx;
    double enemycentery;
    double enemycenterx;
    boolean range;
    boolean faster;
    boolean tank;
    int hp = 3;
    boolean boss;
    double speed = 0.60;


    Enemy(int count, long time, boolean range, boolean faster, boolean tank, boolean boss) {
        Date date = new Date();
        time = date.getTime() - time;
        this.boss = boss;
        this.tank = tank;
        this.range = range;
        this.faster = faster;


        if(tank){hp*=2;}
        if(faster){
            speed += speed * 0.25;
        }

        if(count == 1){
            enemyx = time%126*10;
            enemyy = -10;
        }else if(count == 2){
            enemyx = -10;
            enemyy = time%130 * 5;
        }else if(count == 3){
            enemyx = time%63 * 20;
            enemyy = 650;
        }else{
            enemyx = 1260;
            enemyy = time%50 * 13;
        }

        enemycenterx = enemyx + 5;
        enemycentery = enemyy + 5;

        if(boss){
            this.tank = false;
            this.range = false;
            this.faster = false;
            hp = 2000;
            speed /= 2;
            enemycenterx = enemyx + 10;
            enemycentery = enemyy + 10;
        }
    }

    /**
     * mění polohu nepřítele
     * */
    public void moved(double slayerx, double slayery, boolean player){

        double x = slayerx - enemyx;
        double y = slayery - enemyy;
        double rangeatac = 1;

        if(player && range){
            if(x <= 50 && x >= -50 && y <= 50 && y >= -50){
                rangeatac = 2;
            }else if(x <= 100 && x >= -100 && y <= 100 && y >= -100){
                rangeatac = 1.5;
            }
        }

        double c = rangeatac*speed/Math.sqrt(x*x + y*y);
        x*=c;
        y*=c;

        if(!player){
            x*=-1;
            y*=-1;
        }
        enemyx += x;
        enemyy += y;

        enemycenterx = enemyx + 5;
        enemycentery = enemyy + 5;
        if(boss){
            enemycenterx = enemyx + 10;
            enemycentery = enemyy + 10;
        }
    }
}
