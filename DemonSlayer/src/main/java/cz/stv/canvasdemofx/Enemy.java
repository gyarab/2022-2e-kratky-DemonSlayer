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
    double speed = 0.60;


    Enemy(int count, long time, boolean range, boolean faster, boolean tank) {
        Date date = new Date();
        time = date.getTime() - time;
        this.tank = tank;
        if(tank){hp*=2;}
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
        double rangeatac = 1;

        if(player && range){
            if(x <= 10 && x >= -10 && y <= 10 && y >= -10){
                rangeatac = 3;
            }else if(x <= 20 && x >= -20 && y <= 20 && y >= -20){
                rangeatac = 2;
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
    }
}
