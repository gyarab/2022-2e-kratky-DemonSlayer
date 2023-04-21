package cz.stv.canvasdemofx;

public class NewLine extends Thread {

    @Override
    public void run() {
        MainForm.collisions();
    }
}


