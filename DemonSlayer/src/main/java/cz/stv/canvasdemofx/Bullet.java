package cz.stv.canvasdemofx;

public class Bullet {

    double xmove; // pohyb
    double ymove;
    double positionx;  // pozice
    double positiony;
    double speed;  //rychlost

    /**
     * Vypočítá všechny výchozí hodnoty střely
     * */
    Bullet(double xplayer, double yplayer, double xmouse, double ymouse) {
        speed = 5; //rychlost střely

        positionx = xplayer; //počátek střely v bodě x (poloha hráče při střelbě na ose x)
        positiony = yplayer; //počátek střely v bodě y (poloha hráče při střelbě na ose y)
        double z;
        xmouse = xmouse-xplayer;
        ymouse = ymouse-yplayer;

        if (0 < xmouse) {

            if (0 < ymouse){ // když je x > 0 y > 0

                z = Math.sqrt(Math.pow(xmouse, 2) + Math.pow(ymouse, 2));

                double sinb = xmouse / z;
                double sina = ymouse / z;

                xmove = sinb * speed;
                ymove = sina * speed;
            }

            if (0 > ymouse){ // když je x > 0 y < 0

                ymouse = -ymouse;

                z = Math.sqrt(Math.pow(xmouse, 2) + Math.pow(ymouse, 2));

                double sinb = xmouse / z;
                double sina = ymouse / z;

                xmove = sinb * speed;
                ymove = -sina * speed;
            }

        } else if (0 > xmouse) {

            xmouse = -xmouse;

            if (0 < ymouse){ // když je x < 0 y > 0

                z = Math.sqrt(Math.pow(xmouse, 2) + Math.pow(ymouse, 2));

                double sinb = xmouse / z;
                double sina = ymouse / z;

                xmove = -sinb * speed;
                ymove = sina * speed;
            }

            if (0 > ymouse){ // když je x < 0 y < 0

                ymouse = -ymouse;

                z = Math.sqrt(Math.pow(xmouse, 2) + Math.pow(ymouse, 2));

                double sinb = xmouse / z;
                double sina = ymouse / z;

                xmove = -sinb * speed;
                ymove = -sina * speed;
            }
        }
    }


    public double getXMove(){
        return xmove;
    }

    /**
     * Posune střelu
     * */
    public void moved(){
        positionx = xmove + positionx;
        positiony = ymove + positiony;
    }

    public double getYMove(){
        return ymove;
    }
}
