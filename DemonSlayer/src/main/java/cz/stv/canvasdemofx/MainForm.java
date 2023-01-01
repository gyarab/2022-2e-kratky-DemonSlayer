package cz.stv.canvasdemofx;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MainForm implements Initializable{
  private EventHandler<ActionEvent> timingHandler = new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event)
    {
      if(paused == false) {
        draw();
      }
    }
  };

  private Timeline timer;

  private static Scene scene;

  @FXML
  private Canvas canvas;

  @FXML
  private Button escape;
  
  private boolean isMousePressed = false;

  /**
   * Nastavuje fps
   * */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

    Duration duration = Duration.millis(5);
    KeyFrame keyFrame = new KeyFrame (duration, timingHandler);
    
    timer = new Timeline(keyFrame);
    timer.setCycleCount(Timeline.INDEFINITE);
    timer.play();
  }

  boolean paused = false;// zastavování hry

  /*
   * poloha středu a levého dolního okraje postavy hráče
   * */
  double slayerx = 500.0;
  double slayery = 500.0;
  double slayercenterx = slayerx + 7.5;
  double slayercentery = slayery + 7.5;

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

  int count = 0;

  int hp = 5;

  ArrayList<Bullet> projecriles = new ArrayList<Bullet>();  //pole střel ve hře
  ArrayList<Enemy> enemys = new ArrayList<Enemy>();  //pole nepřátel ve hře

  /**
   * Volá funkce co kreslí a rozhoduje o souřadnicích hráče
   * */
  @FXML
  private void draw() {

    GraphicsContext gc = canvas.getGraphicsContext2D();

    if(enemysummoncaountdown == 0){
      summonEnemy();
      enemysummoncaountdown = 1000;
    }
    if(fastwalkingmode){
      step = 1.25;
    }else{
      step = 1;
    }

    if (wpressed == true && slayery > 25){
      slayery = slayery - step;
      slayercentery = slayercentery - step;
    }

    if (apressed == true && slayerx > 25){
      slayerx = slayerx - step;
      slayercenterx = slayercenterx - step;
    }

    if (spressed == true && slayery < 560){
      slayery = slayery + step;
      slayercentery = slayercentery + step;
    }

    if (dpressed == true && slayerx < 1160){
      slayerx = slayerx + step;
      slayercenterx = slayercenterx + step;
    }

    drawDelate();

    int b = projecriles.toArray().length;
    for (int a = 0; a < b; a++) {
      projecriles.get(a).moved();
      drawBullet(projecriles.get(a).positionx, projecriles.get(a).positiony);
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

    b = projecriles.toArray().length;
    int d = enemys.toArray().length;
    for(int c = 0; c < d;c++){
      enemys.get(c).moved(slayercenterx, slayercentery);
      drawEnemy(enemys.get(c).enemyx, enemys.get(c).enemyy);
      if(15 > enemys.get(c).enemycenterx - slayercenterx && enemys.get(c).enemycenterx - slayercenterx > -15 && 15 > enemys.get(c).enemycentery - slayercentery && enemys.get(c).enemycentery - slayercentery > -15) {
        enemys.remove(c);
        d--;
        c--;
        hp--;
      }
      for(int f = 0; f < b; f++){
        if(8 > enemys.get(c).enemycenterx - projecriles.get(f).positionx && enemys.get(c).enemycenterx - projecriles.get(f).positionx > -8 && 8 > enemys.get(c).enemycentery - projecriles.get(f).positiony && enemys.get(c).enemycentery - projecriles.get(f).positiony > -8) {
          enemys.remove(c);
          projecriles.remove(f);
          d--;
          c--;
          b--;
          f--;
        }
      }
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
      pauseTheGame();
      System.out.println("Game over");
    }
  }

  /**
   * Překreslí vše na bálou plochu
   * */
  private void drawDelate() {
    
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    gc.setFill(Color.LIGHTGREEN);
    gc.fillRect(0, 0, 1280, 680);
    
  }

  /**
   * Nakreslí postavu hráče
   *
   * dodělat - textura
   * */
  private void drawSlayer(double hunterx, double huntery) {
    
    GraphicsContext gc = canvas.getGraphicsContext2D();

    gc.setFill(Color.GRAY);
    gc.fillRect(hunterx, huntery, 15, 15);
  }

  /**
   * Nakreslí střelu hráče
   *
   * dodělat - textura
   * */
  private void drawBullet(double bulletpositionx, double bulletpositiony) {

    GraphicsContext gc = canvas.getGraphicsContext2D();

    gc.setFill(Color.BLACK);
    gc.fillRect(bulletpositionx, bulletpositiony, 3, 3);
  }


  /**
   * Nakreslí nepřítele
   * */
  public void drawEnemy(double enemypx, double enemypy){

    GraphicsContext gc = canvas.getGraphicsContext2D();

    gc.setFill(Color.RED);
    gc.fillRect(enemypx, enemypy, 10, 10);

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
    if(automaticshooting == false) {
      shootnow = false;
    }

    if (caountdown == 0) {
      projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse, ymouse, fastmode, bouncemode));

      caountdown += 100;

      if(mashinegunmode){
        caountdown /= 2;
      }

      if(ultramashinegunmode){
        caountdown /= 3;
      }

      if (shotgunmode) {
        if (xmouse > slayercenterx) {
          projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse + 10, ymouse, fastmode, bouncemode));
          if (ymouse > slayercentery) {
            projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse, ymouse + 10, fastmode, bouncemode));
          } else {
            projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse, ymouse - 10, fastmode, bouncemode));
          }
        } else {
          projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse - 10, ymouse, fastmode, bouncemode));
          if (ymouse > slayercentery) {
            projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse, ymouse + 10, fastmode, bouncemode));
          } else {
            projecriles.add(new Bullet(slayercenterx, slayercentery, xmouse, ymouse - 10, fastmode, bouncemode));
          }
        }
      }
    }
  }

  /**
   * Přidá nového nepřítele do hry
   * */
  public void summonEnemy() {
    enemys.add(new Enemy(false,1,1.0, time));
    count++;
    enemys.add(new Enemy(false,2,1.0, time));
    count++;
    enemys.add(new Enemy(false,3,1.0, time));
    count++;
    enemys.add(new Enemy(false,4,1.0, time));
    count++;
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
  private void onMousePressed(MouseEvent event) {
    shootnow = true;
  }

  /**
   * Vypne střelbu
   * */
  @FXML
  private void onMouseReleased(MouseEvent event) {
    shootnow = false;
  }

  /**
   * Po ztlačení klávesy zapne spuštění klávesy
   * */
  @FXML
  public void onKeyPressed(KeyEvent event) {

    KeyCode key = event.getCode();

    if (key == KeyCode.W) {
      wpressed = true;
    } else if (key == KeyCode.A) {
      apressed = true;
    } else if (key == KeyCode.S) {
      spressed = true;
    } else if (key == KeyCode.D) {
      dpressed = true;
    } else if (key == KeyCode.ESCAPE) {
      pauseTheGame();
    } else if (key == KeyCode.P) {
      shotgunMode();
    } else if (key == KeyCode.O) {
      mashinegunMode();
    } else if (key == KeyCode.I) {
      fastBulletsMode();
    } else if (key == KeyCode.U) {
      fastWalkingMode();
    } else if (key == KeyCode.Z) {
      bounceMode();
    } else if (key == KeyCode.T) {
      granateMode();
    } else if (key == KeyCode.X) {
      ultraMashinegunMode();
    } else if (key == KeyCode.C) {
      autoShootingMode();
    }
  }

  /**
   * Po uvolnění klávesy vypne spuštění klávesy
   * */
  @FXML
  public void onKeyReleased(KeyEvent event) {

    KeyCode key = event.getCode();

    if (key == KeyCode.W) {
      wpressed = false;

    } else if (key == KeyCode.A) {
      apressed = false;

    } else if (key == KeyCode.S) {
      spressed = false;

    } else if (key == KeyCode.D) {
      dpressed = false;

    }
  }

  /**
   * Nastaví jestlimá hra běžet či nikoliv
   * */
  public void pauseTheGame(){
    if (paused == false){
      paused = true;
    } else if (paused == true){
      paused = false;
    }
  }

  /**
   * Aktivuje mod brokovnice
   * */
  public void shotgunMode(){
    if (shotgunmode == false){
      shotgunmode = true;
    } else if (shotgunmode == true){
      shotgunmode = false;
    }
  }

  /**
   * Aktivuje mod rychlostřelby
   * */
  public void mashinegunMode(){
    if (mashinegunmode == false){
      mashinegunmode = true;
    } else if (mashinegunmode == true){
      mashinegunmode = false;
    }
  }

  /**
   * Aktivuje mod ještě rychlejší střelby
   * */
  public void ultraMashinegunMode(){
    if (ultramashinegunmode == false){
      ultramashinegunmode = true;
    } else if (ultramashinegunmode == true){
      ultramashinegunmode = false;
    }
  }

  /**
   * Ahtivuje mod rychle kulky
   * */
  public void fastBulletsMode(){
    if (fastmode == false){
      fastmode = true;
    } else if (fastmode == true){
      fastmode = false;
    }
  }

  /**
   * Ahtivuje mod rychle chozeni
   * */
  public void fastWalkingMode(){
    if (fastwalkingmode == false){
      fastwalkingmode = true;
    } else if (fastwalkingmode == true){
      fastwalkingmode = false;
    }
  }

  /**
   * Ahtivuje mod odražení kulek
   * */
  public void bounceMode(){
    if (bouncemode == false){
      bouncemode = true;
    } else if (bouncemode == true){
      bouncemode = false;
    }
  }

  /**
   * Ahtivuje mod granát - nedoděláno
   * */
  public void granateMode(){
    if (granatemode == false){
      granatemode = true;
    } else if (granatemode == true){
      granatemode = false;
    }
  }

  /**
   * Ahtivuje mod průstřel nepřátel - nedoděláno
   * */
  public void piercingMode(){
    if (piercingmode == false){
      piercingmode = true;
    } else if (piercingmode == true){
      piercingmode = false;
    }
  }


  /**
   * Ahtivuje mod autostřelby
   * */
  public void autoShootingMode() {
    if (autoshootingmode == false) {
      autoshootingmode = true;
    } else if (autoshootingmode == true) {
      autoshootingmode = false;
    }
  }
}