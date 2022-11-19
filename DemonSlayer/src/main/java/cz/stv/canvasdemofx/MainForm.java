package cz.stv.canvasdemofx;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MainForm implements Initializable
{
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

  boolean paused = false;



  /*
   * poloha středu a levého dolního okraje postavy hráče
   * */
  double slayerx = 500.0;
  double slayery = 500.0;
  double slayercenterx = slayerx + 7.5;
  double slayercentery = slayery + 7.5;

  /*
  * zmáčknuté klávesy
  * */
  boolean wpressed = false;
  boolean apressed = false;
  boolean spressed = false;
  boolean dpressed = false;

  double xmouse;
  double ymouse;

  double cyrcr = 20.0;


  double gunx = slayerx + 4;
  double guny = slayery;

  ArrayList<Bullet> projecriles = new ArrayList<Bullet>();  //pole střel ve hře

  /**
   * Volá funkce co kreslí a rozhoduje o souřadnicích hráče
   * */
  @FXML
  private void draw() {

    GraphicsContext gc = canvas.getGraphicsContext2D();

    if (wpressed == true && slayery > 25){
      slayery = slayery - 1;
      slayercentery = slayercentery - 1;
    }

    if (apressed == true && slayerx > 25){
      slayerx = slayerx - 1;
      slayercenterx = slayercenterx - 1;
    }

    if (spressed == true && slayery < 560){
      slayery = slayery + 1;
      slayercentery = slayercentery + 1;
    }

    if (dpressed == true && slayerx < 1160){
      slayerx = slayerx + 1;
      slayercenterx = slayercenterx + 1;
    }

    drawDelate();
    int b = projecriles.toArray().length;
    for (int a = 0; a < b;a++) {
      projecriles.get(a).moved();
      drawBullet(projecriles.get(a).positionx, projecriles.get(a).positiony);
      if(projecriles.get(a).positiony > 600 || projecriles.get(a).positionx > 1200 || projecriles.get(a).positiony < 0 || projecriles.get(a).positionx < 0){
        projecriles.remove(a);
        a--;
        b--;
      }
    }


    drawGun(slayercenterx, slayercentery, xmouse, ymouse);
    drawSlayer(slayerx, slayery);
  }

  /**
   * Překreslí vše na bálou plochu
   * */
  private void drawDelate() {
    
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    gc.setFill(Color.SADDLEBROWN);
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
    System.out.println(bulletpositionx + " " + bulletpositiony);
  }

  /**
   * Nakreslí zbraň v závislosti miši a panáčka. Délka zbraně je 20 měnitelná pomocí lenghtofstroke. Počítá ve 4 kvadrantech.
   *
   * dodělat - textura
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
   * Zjišťuje zda hráč pohl miší
   * */
  @FXML
  private void onMouseMoved(MouseEvent event) {

    xmouse = event.getX();
    ymouse = event.getY();
  }

  /**
   * Zjišťuje zda hráč pohl miší a zároveň držel nějaké tlačítko na miši
   *
   * Dodělat - funkčtnost na pravé a levé
   * */
  @FXML
  private void onMouseDragged(MouseEvent event) {

    xmouse = event.getX();
    ymouse = event.getY();
  }

  /**
   * Vytvoří novou střelu
   * */
  @FXML
  private void onMousePressed(MouseEvent event) {
    projecriles.add(new Bullet(slayercenterx,slayercentery,xmouse,ymouse));
  }

  /**
   * Dodělat - nic nedělá
   * */
  @FXML
  private void onMouseReleased(MouseEvent event) {
    //System.out.println("onMouseReleased: " + event.getX() + " : " + event.getY());
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

    }else if (key == KeyCode.ESCAPE) {
      pauseTheGame();
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
}
