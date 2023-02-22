package cz.stv.canvasdemofx;

import java.util.Date;

public class Enemy {
    double enemyy;
    double enemyx;
    double enemycentery;
    double enemycenterx;
    boolean range;
    boolean faster;
    double speed = 0.9;


    Enemy(int count, long time,boolean range, boolean faster) {
        Date date = new Date();
        time = date.getTime() - time;
        this.range = range;
        this.faster = faster;
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
    }

    /**
     * mění polohu nepřítele
     * */
    public void moved(double slayerx, double slayery, boolean player){

        double x = slayerx - enemyx;
        double y = slayery - enemyy;
        double c = speed/Math.sqrt(x*x + y*y);
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
    }
}
