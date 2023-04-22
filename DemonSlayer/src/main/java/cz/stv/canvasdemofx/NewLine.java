package cz.stv.canvasdemofx;

public class NewLine extends Thread {

    /**
     * spustí nové vlákno
     * */
    @Override
    public void run() {
        MainForm.collisions();
    }
}


