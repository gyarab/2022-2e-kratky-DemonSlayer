package cz.stv.canvasdemofx;

import javafx.scene.input.KeyCode;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.security.Key;

public class StatsAndSettings implements Serializable {
    int currentlevel = 1;
    int level = 1;
    int scrab = 0;
    KeyCode up = KeyCode.W;
    KeyCode down = KeyCode.S;
    KeyCode left = KeyCode.A;
    KeyCode right = KeyCode.D;
    KeyCode upgrade = KeyCode.U;
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
    boolean BB = false;
    boolean SG = false;
    boolean AS = false;
    boolean PB = false;
    boolean FMG = false;
    boolean MG = false;
    boolean FB = false;
    boolean FW = false;
    boolean G = false;

    public StatsAndSettings(){}

    public void scrabCounter(int lvl, int hp){
        if(lvl == 1 && scone < hp){
            scrab += hp - scone;
            scone = hp;
        } else if(lvl == 2 && sctwo < hp){
            scrab += hp - sctwo;
            sctwo = hp;
        } else if(lvl == 3 && scthree < hp){
            scrab += hp - scthree;
            scthree = hp;
        } else if(lvl == 4 && scfour < hp){
            scrab += hp - scfour;
            scfour = hp;
        } else if(lvl == 5 && scfive < hp){
            scrab += hp - scfive;
            scfive = hp;
        } else if(lvl == 6 && scsix < hp){
            scrab += hp - scsix;
            scsix = hp;
        } else if(lvl == 7 && scseven < hp){
            scrab += hp - scseven;
            scseven = hp;
        }
    }

    public void resetBinds(){
        up = KeyCode.W;
        down = KeyCode.S;
        left = KeyCode.A;
        right = KeyCode.D;
        difficulty = 2;
        hardcore = false;
        escape = KeyCode.ESCAPE;
        granate = KeyCode.Q;
        upgrade = KeyCode.U;
    }

    public void resetCharacter(){
        scone = 0;
        sctwo = 0;
        scthree = 0;
        scfour = 0;
        scfive = 0;
        scsix = 0;
        scseven = 0;
        currentlevel = 1;
        level = 1;
        scrab = 0;
        BB = false;
        SG = false;
        AS = false;
        PB = false;
        FMG = false;
        MG = false;
        FB = false;
        FW = false;
        G = false;
    }
}
