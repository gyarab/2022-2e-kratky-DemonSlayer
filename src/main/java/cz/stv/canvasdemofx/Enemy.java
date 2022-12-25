package cz.stv.canvasdemofx;

import java.util.Date;

public class Enemy {
    double enemyy;
    double enemyx;
    double enemycentery;
    double enemycenterx;
    boolean range;
    double speed;


    Enemy(boolean range, int count, double speed,long time) {
        Date date = new Date();
        time = date.getTime() - time;
        this.speed = speed;
        this.range = range;

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
     * mění polohu nepřítele po
     * */
    public void moved(double slayerx, double slayery){
        if(slayerx <= enemyx && slayery <= enemyy){
            enemyx -= speed;
            enemyy -= speed;
        }else if (slayerx >= enemyx && slayery <= enemyy) {
            enemyx += speed;
            enemyy -= speed;
        }else if (slayerx <= enemyx) {
            enemyx -= speed;
            enemyy += speed;
        }else{
            enemyx += speed;
            enemyy += speed;
        }

        enemycenterx = enemyx + 5;
        enemycentery = enemyy + 5;
    }
}
