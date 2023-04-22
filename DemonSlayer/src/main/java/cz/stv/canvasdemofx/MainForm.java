package cz.stv.canvasdemofx;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainForm extends Application implements Initializable {
  private EventHandler<ActionEvent> timingHandler = new EventHandler<ActionEvent>() {


    /**
     * slouží k spouštění kolizí pohybu a malování
     * */
    @Override
    public void handle(ActionEvent event)
    {
      try {
        if(numberofinteruction == 5){
          numberofinteruction = 0;
          NewLine t = new NewLine();
          t.start();
        }
        if(hp != hpold){
          drawHpBar();
          hpold = hp;
        }
        draw();
        numberofinteruction++;
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    }
  };

  int numberofinteruction = 0;

  private Timeline timer;

  private static Scene scene;

  @FXML
  private Canvas canvas;

  @FXML
  private Canvas hpbar;

  @FXML
  private Button escape;

  @FXML
  private Label scrapcaunt;

  /**
   * Nastavuje fps a nastavení hry
   * */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    StatsAndSettings s = App.getAll();

    bouncemode = s.BB;
    piercingmode = s.PB;
    granatemode = s.G;
    fastmode = s.FB;
    mashinegunmode = s.MG;
    ultramashinegunmode = s.FMG;
    shotgunmode = s.SG;
    autoshootingmode = s.AS;
    fastwalkingmode = s.FW;

    level = s.currentlevel;
    difficulty = s.difficulty;

    if (difficulty == 1) {
      spawnrate += 500;
    } else if (difficulty == 3) {
      spawnrate -= 200;
    }

    if (s.level > 3) {
      spawnrate = spawnrate / 2;
    }

    if(newgame) {
      scrapcaunt.setText("Number of scrap gotten from this level: " + s.getScrapInLevel());

      slayerx = 500.0;
      slayery = 500.0;
      slayercenterx = slayerx + 7.5;
      slayercentery = slayery + 7.5;
      gunx = slayerx + 4;
      guny = slayery;

      for (int a = 0; a < enemys.size(); ) {
        enemys.remove(a);
      }
      for (int a = 0; a < projecriles.size(); ) {
        projecriles.remove(a);
      }

      hpold = 5;
      hp = 5;

      if (s.hardcore) {
        hpold = 1;
        hp = 1;
        difficulty = 3;
      }
    } else {
      newgame = true;
    }

    Duration duration = Duration.millis(3);
    KeyFrame keyFrame = new KeyFrame (duration, timingHandler);
    
    timer = new Timeline(keyFrame);
    timer.setCycleCount(Timeline.INDEFINITE);
    timer.play();
    drawHpBar();
  }
  
  /*
   * poloha středu a levého dolního okraje postavy hráče
   * */
  static double slayerx = 500.0;
  static double slayery = 500.0;
  static double slayercenterx = slayerx + 7.5;
  static double slayercentery = slayery + 7.5;

  /*
   * zapnuté mody
   * */
  boolean fastmode = false;
  boolean shotgunmode = false;
  boolean mashinegunmode = false;
  boolean fastwalkingmode = false;
  boolean bouncemode = false;
  boolean granatemode = false;
  boolean piercingmode = false;
  boolean autoshootingmode = false;
  boolean ultramashinegunmode = false;

  /*
  * zmáčknuté klávesy
  * */
  boolean wpressed = false;
  boolean apressed = false;
  boolean spressed = false;
  boolean dpressed = false;
  boolean shootnow = false;

  /*
  * momentální čas v millisekundách
  * */
  Date date = new Date();
  long time = date.getTime();
  /*
   * souřadnice miši
   * */
  double xmouse;
  double ymouse;

  double gunx = slayerx + 4;
  double guny = slayery;

  double step = 1; //Délka pohybu

  int caountdown = 0;
  int enemysummoncaountdown = 0;

  int count = 1;

  /*
  * základní parametry levelu
  * */
  int level = 1;
  int hpold = 5;
  static int hp = 5;
  int difficulty = 2;
  int spawnrate = 1500;
  int granattimer = 0;

  static boolean newgame = true;

  static ArrayList<Bullet> projecriles = new ArrayList<Bullet>();  //pole střel ve hře
  static ArrayList<Enemy> enemys = new ArrayList<Enemy>();  //pole nepřátel ve hře

  /**
   * vyřeší včechny kolize
   * */
  static void collisions(){
    int b = projecriles.toArray().length;
    for (int a = 0; a < b; a++) {
      if(projecriles.get(a).positiony > 600){
        if(projecriles.get(a).bounce){
          projecriles.get(a).bounce = false;
          projecriles.get(a).ymove *= -1;
        }else{
          projecriles.remove(a);
          a--;
          b--;
        }
      }else if(projecriles.get(a).positionx > 1200){
        if(projecriles.get(a).bounce){
          projecriles.get(a).bounce = false;
          projecriles.get(a).xmove *= -1;
        }else{
          projecriles.remove(a);
          a--;
          b--;
        }
      }else if(projecriles.get(a).positiony < 0){
        if(projecriles.get(a).bounce){
          projecriles.get(a).bounce = false;
          projecriles.get(a).ymove *= -1;
        }else{
          projecriles.remove(a);
          a--;
          b--;
        }
      }else if(projecriles.get(a).positionx < 0){
        if(projecriles.get(a).bounce){
          projecriles.get(a).bounce = false;
          projecriles.get(a).xmove *= -1;
        }else{
          projecriles.remove(a);
          a--;
          b--;
        }
      }
    }

    for(int a = 0; a < enemys.size();a++){
      for(int c = a + 1; c < enemys.size();c++){
        if(10 > enemys.get(a).enemycenterx - enemys.get(c).enemycenterx && enemys.get(a).enemycenterx - enemys.get(c).enemycenterx > -10 && 10 > enemys.get(a).enemycentery - enemys.get(c).enemycentery && enemys.get(a).enemycentery - enemys.get(c).enemycentery > -10) {
          MainForm.enemys.get(a).moved(enemys.get(c).enemyx, enemys.get(c).enemyy, false);
          MainForm.enemys.get(c).moved(enemys.get(a).enemyx, enemys.get(a).enemyy, false);
        }
      }
    }

    int d = enemys.toArray().length;
    for(int c = 0; c < d;c++){
      //Ubírá životy hráče
      if(!enemys.get(c).boss){
        if(15 > enemys.get(c).enemycenterx - slayercenterx && enemys.get(c).enemycenterx - slayercenterx > -15 && 15 > enemys.get(c).enemycentery - slayercentery && enemys.get(c).enemycentery - slayercentery > -15) {
          enemys.remove(c);
          d--;
          c--;
          hp--;

        }
        //Zabíjí jednotky a ubírá jim životy
        for(int f = 0; f < b; f++){
          try{
            if(8 > enemys.get(c).enemycenterx - projecriles.get(f).positionx && enemys.get(c).enemycenterx - projecriles.get(f).positionx > -8 && 8 > enemys.get(c).enemycentery - projecriles.get(f).positiony && enemys.get(c).enemycentery - projecriles.get(f).positiony > -8) {
              enemys.get(c).hp--;
              if(enemys.get(c).hp < 1){
                enemys.remove(c);
                d--;
                c--;
              }
              if(projecriles.get(f).piercing){
                projecriles.get(f).piercing = false;
              }else{
                projecriles.remove(f);
                b--;
                f--;
              }
            }
          } catch(IndexOutOfBoundsException ignore){}
        }
      }else{
        if(20 > enemys.get(c).enemycenterx - slayercenterx && enemys.get(c).enemycenterx - slayercenterx > -20 && 20 > enemys.get(c).enemycentery - slayercentery && enemys.get(c).enemycentery - slayercentery > -20) {
          enemys.remove(c);
          d--;
          c--;
          hp = 0;
        }
        //Zabíjí jednotky a ubírá jim životy
        for(int f = 0; f < b; f++){
          try {
            if(13 > enemys.get(c).enemycenterx - projecriles.get(f).positionx && enemys.get(c).enemycenterx - projecriles.get(f).positionx > -13 && 13 > enemys.get(c).enemycentery - projecriles.get(f).positiony && enemys.get(c).enemycentery - projecriles.get(f).positiony > -13) {
              enemys.get(c).hp--;
              if(enemys.get(c).hp < 1){
                enemys.remove(c);
                d--;
                c--;
              }
              if(projecriles.get(f).piercing){
                projecriles.get(f).piercing = false;
              }else{
                projecriles.remove(f);
                b--;
                f--;
              }
            }
          }catch(IndexOutOfBoundsException ignore){}
        }
      }
    }
  }

  /**
   * Volá funkce co kreslí a posouvá objekty (hráč, jednotky, střely,... )
   * */
  @FXML
  public void draw() throws IOException, InterruptedException {

    if(enemysummoncaountdown == 0){
      if(enemys.size() <= 8){
        summonEnemy();
      }
      if(granattimer > 0){
        granattimer--;
      }else{
        if(granatemode){
          drawHpBar();
        }
      }
      enemysummoncaountdown = spawnrate;
    }

    if(fastwalkingmode){
      step = 1.1;
    }else{
      step = 0.7;
    }

    if (wpressed && slayery > 25){
      slayery = slayery - step;
      slayercentery = slayercentery - step;
    }

    if (apressed && slayerx > 25){
      slayerx = slayerx - step;
      slayercenterx = slayercenterx - step;
    }

    if (spressed && slayery < 560){
      slayery = slayery + step;
      slayercentery = slayercentery + step;
    }

    if (dpressed && slayerx < 1160){
      slayerx = slayerx + step;
      slayercenterx = slayercenterx + step;
    }

    drawDelate();

    /*
     * kreslí střely
     * */
    for (int a = 0; a < projecriles.toArray().length; a++) {
      projecriles.get(a).moved();
      drawBullet(projecriles.get(a).positionx, projecriles.get(a).positiony);
    }

    /*
     * Kreslí jednotky
     * */
    for(int c = 0; c < enemys.toArray().length;c++){
      enemys.get(c).moved(slayercenterx, slayercentery, true);
      drawEnemy(enemys.get(c).enemyx, enemys.get(c).enemyy, enemys.get(c).range,enemys.get(c).faster, enemys.get(c).tank, enemys.get(c).boss);
    }

    drawGun(slayercenterx, slayercentery, xmouse, ymouse);
    drawSlayer(slayerx, slayery);

    if(shootnow){
      shoot(autoshootingmode);
    }

    if(caountdown != 0){
      caountdown--;
    }
    enemysummoncaountdown--;

    if(hp < 1){
      gameOver(false);
    }
  }

  /**
   * Překreslí vše na ploše
   * */
  private void drawDelate() {
    
    GraphicsContext gc = canvas.getGraphicsContext2D();
    switch(level){
      case(1):
        gc.setFill(Color.GREEN);
        break;
      case(2):
        gc.setFill(Color.LIGHTGREEN);
        break;
      case(3):
        gc.setFill(Color.LIGHTSEAGREEN);
        break;
      case(4):
        gc.setFill(Color.SADDLEBROWN);
        break;
      case(5):
        gc.setFill(Color.BROWN);
        break;
      case(6):
        gc.setFill(Color.FIREBRICK);
        break;
      case(7):
        gc.setFill(Color.INDIANRED);
        break;
    }
    gc.fillRect(0, 0, 1280, 680);
  }

  /**
   * Nakreslí postavu hráče
   * */
  private void drawSlayer(double hunterx, double huntery) {
    
    GraphicsContext gc = canvas.getGraphicsContext2D();

    gc.setFill(Color.GRAY);
    gc.fillRect(hunterx, huntery, 15, 15);
  }

  /**
   * Nakreslí střelu hráče
   * */
  private void drawBullet(double bulletpositionx, double bulletpositiony) {

    GraphicsContext gc = canvas.getGraphicsContext2D();

    gc.setFill(Color.BLACK);
    gc.fillRect(bulletpositionx, bulletpositiony, 5, 5);
  }


  /**
   * Nakreslí nepřítele
   * */
  public void drawEnemy(double enemypx, double enemypy, boolean range, boolean faster, boolean tank, boolean boss){

    GraphicsContext gc = canvas.getGraphicsContext2D();

    int size = 10;
    if(boss){
      gc.setFill(Color.PURPLE);
      size *= 2;
    }else if(faster && tank){
      gc.setFill(Color.ORANGERED);
    }else if(range && tank){
      gc.setFill(Color.BLUE);
    }else if(range && faster){
      gc.setFill(Color.LIGHTCYAN);
    }else if(tank){
      gc.setFill(Color.BISQUE);
    }else if(faster){
      gc.setFill(Color.YELLOW);
    }else if(range){
      gc.setFill(Color.LIGHTBLUE);
    }else{
      gc.setFill(Color.ORANGE);
    }
    gc.fillRect(enemypx, enemypy, size, size);

  }

  /**
   * Nakreslí zbraň v závislosti miši a panáčka. Délka zbraně je 20 měnitelná pomocí lenghtofstroke. Počítá ve 4 kvadrantech.
   * */
  private void drawGun(double centerx, double centery, double mousex, double mousey) {

    GraphicsContext gc = canvas.getGraphicsContext2D();

    double x = mousex - centerx;
    double y = mousey - centery;

    double z = 0;

    double lenghtofstroke = 20;


    if (0 < x) {
      if (0 < y){ // když je x > 0 y > 0

        z = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        double sinb = x / z;
        double sina = y / z;

        gunx = sinb * lenghtofstroke;
        guny = sina * lenghtofstroke;
      }

      if (0 > y){ // když je x > 0 y < 0

        y = -y;

        z = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        double sinb = x / z;
        double sina = y / z;

        gunx = sinb * lenghtofstroke;
        guny = -sina * lenghtofstroke;
      }

    } else if (0 > x) {

      x = -x;

      if (0 < y){ // když je x < 0 y > 0

        z = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        double sinb = x / z;
        double sina = y / z;

        gunx = -sinb * lenghtofstroke;
        guny = sina * lenghtofstroke;
      }

      if (0 > y){ // když je x < 0 y < 0

        y = -y;

        z = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        double sinb = x / z;
        double sina = y / z;

        gunx = -sinb * lenghtofstroke;
        guny = -sina * lenghtofstroke;
      }
    }

    gunx = centerx + gunx;
    guny = centery + guny;

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(6);
    gc.strokeLine( centerx, centery, gunx, guny);
  }

  /**
   * Vytvoří novou střelu
   * */
  public void shoot(boolean automaticshooting){
    if(!automaticshooting) {
      shootnow = false;
    }

    if (caountdown == 0 && projecriles.size() <= 20) {
      projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse, ymouse, fastmode, bouncemode,piercingmode));

      caountdown += 100;

      if(mashinegunmode){
        caountdown /= 4;
        caountdown *= 3;
      }

      if(ultramashinegunmode){
        caountdown /= 2;
      }

      if (shotgunmode) {
        if (xmouse > slayercenterx) {
          projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse + 10, ymouse, fastmode, bouncemode,piercingmode));
        } else {
          projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse - 10, ymouse, fastmode, bouncemode,piercingmode));
        }
        if (ymouse > slayercentery) {
          projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse, ymouse + 10, fastmode, bouncemode,piercingmode));
        } else {
          projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse, ymouse - 10, fastmode, bouncemode,piercingmode));
        }
      }
    }
  }

  /**
   * Přidá nového nepřítele do hry podle toho jaká je vlna, jaký je level a jahá je obtížnost
   * */
  public void summonEnemy() throws IOException {
    if(level == 1){
      if(count < 5 && count%2 ==1){
        summonPack(count, false, false, false);
      }else if(count < 7){
        summonPack(count, false, false, false);
      }else if(count == 8){
        summonPack(count, false, false, false);
        summonPack(count*3, false, false, false);
        summonPack(count*2, false, false, false);
      }

      if(count > 8 && enemys.size() == 0){
        gameOver(true);
      }
      count++;
    }else if(level == 2){
      if(count < 5){
        summonPack(count, false, false, false);
      }else if(count < 10){
        summonPack(count, false, false, false);
        summonPack(count*3, false, false, false);
      }else if(count == 10){
        summonPack(count*3, false, false, false);
        summonPack(count*2, false, true, false);
        summonPack(count*5, false, false, false);
      }

      if(count > 10 && enemys.size() == 0){
        gameOver(true);
      }
      count++;
    }else if(level == 3){
      if(count < 3){
        summonPack(count, false, true, false);
      }else if(count < 7){
        summonPack(count, false, true, false);
        if(count%2 == 1){
          summonPack(count*2, false, false, false);
        }
      }else if(count < 11){
        summonPack(count*3, false, true, false);
        summonPack(count*2, false, true, false);
      }else if(count < 13){
        summonPack(count*3, false, false, true);
        summonPack(count*2, false, true, false);
      }else if(count == 14){
        summonPack(count*5, false, true, false);
        summonPack(count*7, false, false, true);
        summonPack(count*11, false, true, false);
      }

      if(count > 16 && enemys.size() == 0){
        gameOver(true);
      }
      count++;
    }else if(level == 4){
      if(count < 3){
        summonPack(count*5, false, true, false);
      }else if(count < 7){
        summonPack(count*5, false, true, false);
        summonPack(count*2, false, true, false);
      }else if(count < 10){
        summonPack(count*2, false, true, false);
        summonPack(count*5, false, false, true);
      }else if(count < 15){
        summonPack(count, false, true, false);
        summonPack(count*2, false, true, false);
      }else if(count == 16){
        summonPack(count*3, true, false, false);
        summonPack(count*2, false, true, false);
        summonPack(count*11, false, true, true);
      }

      if(count > 20 && enemys.size() == 0){
        gameOver(true);
      }
      count++;
    }else if(level == 5){
      if(count < 3){
        summonPack(count, false, true, true);
      }else if(count < 7){
        summonPack(count, false, true, true);
        summonPack(count*5, false, true, true);
      }else if(count < 12){
        summonPack(count*3, true, true, false);
        summonPack(count*5, false, true, true);
      }else if(count < 15){
        summonPack(count*11, false, true, true);
        summonPack(count*3, true, true, false);
        summonPack(count*7, false, true, true);
      }else if(count == 18){
        summonPack(count*3, true, false, true);
        summonPack(count*5, false, false, true);
        summonPack(count*2, false, true, false);
        summonPack(count*11, true, true, false);
      }

      if(count > 16 && enemys.size() == 0){
        gameOver(true);
      }
      count++;
    }else if(level == 6){
      if(count < 3){
        summonPack(count, true, false, true);
        summonPack(count*2, true, false, false);
      }else if(count < 9){
        summonPack(count, true, false, false);
        summonPack(count*2, true, true, false);
      }else if(count < 12){
        summonPack(count*3, true, false, true);
        summonPack(count*5, false, true, true);
      }else if(count < 18){
        summonPack(count*3, true, true, false);
        summonPack(count*5, false, true, true);
      }else if(count == 19){
        summonPack(count*3, true, false, true);
        summonPack(count*5, false, true, true);
        summonPack(count*7, true, false, true);
        summonPack(count*2, false, true, true);
        summonPack(count*11, true, true, false);
      }

      if(count > 27 && enemys.size() == 0){
        gameOver(true);
      }
      count++;
    }else if(level == 7){
      if(count == 1){
        enemys.add(new Enemy(1, time, false, false, false, true));
      }

      if(enemys.get(0).hp >= 1800){
        summonPack(count, false, false, false);
      }
      if(enemys.get(0).hp < 1800 && enemys.get(0).hp >= 1500){
        summonPack(count, false, true, false);
      }
      if(enemys.get(0).hp < 1500 && enemys.get(0).hp >= 1200){
        summonPack(count, true, false, false);
      }
      if(enemys.get(0).hp < 1200 && enemys.get(0).hp >= 1000){
        summonPack(count, false, false, true);
      }
      if(enemys.get(0).hp < 1000 && enemys.get(0).hp >= 700){
        summonPack(count, true, true, false);
      }
      if(enemys.get(0).hp < 700 && enemys.get(0).hp >= 400){
        summonPack(count, true, false, true);
      }
      if(enemys.get(0).hp < 400 && enemys.get(0).hp > 0){
        summonPack(count, false, true, true);
      }

      if(count > 2 && !enemys.get(0).boss){
        for(int a = 0; a < enemys.size(); ){
          enemys.remove(a);
        }

        gameOver(true);
      }
      count++;
    }
  }

  /**
   * přidá do hry 4 jednotky
   * */
  public void summonPack(int generator, boolean range, boolean faster, boolean tank) {
    enemys.add(new Enemy(1, time*generator, range, faster, tank, false));
    enemys.add(new Enemy(2, time*generator, range, faster, tank, false));
    enemys.add(new Enemy(3, time*generator, range, faster, tank, false));
    enemys.add(new Enemy(4, time*generator, range, faster, tank, false));
  }

  /**
   * Zjišťuje zda hráč pohl miší
   * */
  @FXML
  private void onMouseMoved(MouseEvent event) {

    xmouse = event.getX();
    ymouse = event.getY();
  }

  /**
   * Zjišťuje zda hráč pohl miší a zároveň držel nějaké tlačítko na miši
   * */
  @FXML
  private void onMouseDragged(MouseEvent event) {

    xmouse = event.getX();
    ymouse = event.getY();
  }

  /**
   * Zapne střepbu
   * */
  @FXML
  private void onMousePressed() {
    shootnow = true;
  }

  /**
   * Vypne střelbu
   * */
  @FXML
  private void onMouseReleased() {
    shootnow = false;
  }

  /**
   * Po ztlačení klávesy zapne spuštění klávesy
   * */
  @FXML
  public void onKeyPressed(KeyEvent event) throws IOException {

    KeyCode key = event.getCode();

    StatsAndSettings s = App.getAll();

    if (key == s.up) {
      wpressed = true;
    } else if (key == s.left) {
      apressed = true;
    } else if (key == s.down) {
      spressed = true;
    } else if (key == s.right) {
      dpressed = true;
    } else if (key == s.escape) {
      pauseTheGame((Stage)((Node) event.getSource()).getScene().getWindow());
    } else if(key == s.granate) {
      if(granatemode && granattimer == 0){
        shootGranate();
      }
    }
  }

  /**
   * Po uvolnění klávesy vypne spuštění klávesy
   * */
  @FXML
  public void onKeyReleased(KeyEvent event) {

    KeyCode key = event.getCode();

    StatsAndSettings s = App.getAll();

    if (key == s.up) {
      wpressed = false;
    } else if (key == s.left) {
      apressed = false;
    } else if (key == s.down) {
      spressed = false;
    } else if (key == s.right) {
      dpressed = false;
    }
  }

  /**
   * Nastaví jestlimá hra běžet či nikoliv
   * */
  public void pauseTheGame(Stage s) throws IOException {
    timer.pause();
    App.settings(true, s);
  }

  /**
   * Vrátí hlavní panel s výbětel levelů
   * */
  public void gameOver(boolean win) throws IOException {
    timer.stop();
    if (win){
      StatsAndSettings s = App.getAll();
      if(s.hardcore){hp += 5;}
      if(level == s.level){
        s.level++;
      }
      s.scrabCounter(level,hp);
      App.saveAll(s);
    }

    Stage stage = (Stage) escape.getScene().getWindow();
    start(stage);
  }

  /**
   * přepne scénu
   * */
  @Override
  public void start(Stage stage) throws IOException {
    scene = new Scene(loadFXML("MainMenu"));
    stage.setScene(scene);
    stage.show();
  }

  /**
   * nastaví FXML
   * */
  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  /**
   * vystřelý granát
   * */
  public void shootGranate() throws IOException {
    granattimer = 3;
    for(int i = 0; i < enemys.size(); i++){
      if(20 > enemys.get(i).enemycenterx - xmouse && enemys.get(i).enemycenterx - xmouse > -20 && 20 > enemys.get(i).enemycentery - ymouse && enemys.get(i).enemycentery - ymouse > -20) {
        enemys.get(i).hp -= 5;
        if(enemys.get(i).hp <= 0){
          enemys.remove(i);
          i--;
        }
      }
    }
    drawHpBar();
  }


  /**
   * resetuje level
   * */
  public void resetTheLevel(ActionEvent event) throws IOException {
    WarningWindow.openWarning(1, event);
    timer.pause();
  }

  /**
   * přejde do mainmenu
   * */
  public void backToMenu(ActionEvent event) throws IOException {
    WarningWindow.openWarning(2, event);
    timer.pause();
  }

  /**
   * vypne aplikaci
   * */
  public void exitTheApp(ActionEvent event) throws IOException {
    WarningWindow.openWarning(3, event);
    timer.pause();
  }
  /**
   * Kreslí bar s životy a ukazuje zda je možné aktivovat granát
   * */
  @FXML
  public void drawHpBar(){
    GraphicsContext gc = hpbar.getGraphicsContext2D();
    gc.setFill(Color.GRAY);
    gc.fillRect(0, 0, 675, 138);
    int a = hp;
    for(int b = 1; b <= 5;b++){
      if(a > 0){
      gc.setFill(Color.RED);
      } else {
        gc.setFill(Color.BLACK);
      }
      gc.fillRect(10*b*4 - 30, 50, 30, 78);
      a--;
    }

    gc.setFill(Color.BLACK);

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(3);
    gc.strokeLine( 25, 10, 10, 40);
    gc.strokeLine( 45, 10, 30, 40);
    gc.strokeLine( 55, 10, 40, 40);
    gc.strokeLine( 20, 25, 37, 25);
    gc.strokeLine( 55, 10, 72, 10);
    gc.strokeLine( 50, 25, 65, 25);
    gc.strokeLine( 72, 10, 65, 25);

    StatsAndSettings s = App.getAll();
    if(s.G){
      if(granattimer == 0){
        gc.setFill(Color.GREEN);
      }else{
        gc.setFill(Color.RED);
      }
    }
    gc.fillRect(250, 60, 50, 50);

    gc.strokeLine( 285, 25,290, 20);
    gc.setStroke(Color.RED);
    gc.setLineWidth(15);
    gc.strokeLine( 280, 30,270, 40);
  }

  /**
   * po stisknutí tlačítka zavolá pause the game
   * */
  public void pauseGameAction(ActionEvent actionEvent) throws IOException {
    pauseTheGame((Stage)((Node) actionEvent.getSource()).getScene().getWindow());
  }
}
