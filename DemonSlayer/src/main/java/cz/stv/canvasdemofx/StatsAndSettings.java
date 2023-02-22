package cz.stv.canvasdemofx;

import javafx.scene.input.KeyCode;

import java.io.FileOutputStream;

public class StatsAndSettings {
    int currentlevel = 1;
    int level = 1;
    int scrab = 0;
    KeyCode up = KeyCode.W;
    KeyCode down = KeyCode.S;
    KeyCode left = KeyCode.A;
    KeyCode right = KeyCode.D;
    int difficulty = 2;
    boolean hardcore = false;
    KeyCode escape = KeyCode.ESCAPE;
    KeyCode granate = KeyCode.Q;
    int scone = 0;
    int sctwo = 0;
    int scthree = 0;
    int scfour = 0;
    int scfive = 0;
    int scsix = 0;
    int scseven = 0;

    public StatsAndSettings(){}

    public void scrabCounter(int lvl, int hp){
        if(lvl == 1 && scone < hp){
            scone = hp;
            scrab += hp - scone;
        } else if(lvl == 2 && sctwo < hp){
            sctwo = hp;
            scrab += hp - sctwo;
        } else if(lvl == 3 && scthree < hp){
            scthree = hp;
            scrab += hp - scthree;
        } else if(lvl == 4 && scfour < hp){
            scfour = hp;
            scrab += hp - scfour;
        } else if(lvl == 5 && scfive < hp){
            scfive = hp;
            scrab += hp - scfive;
        } else if(lvl == 6 && scsix < hp){
            scsix = hp;
            scrab += hp - scsix;
        } else if(lvl == 7 && scseven < hp){
            scseven = hp;
            scrab += hp - scseven;
        }
    }
}
