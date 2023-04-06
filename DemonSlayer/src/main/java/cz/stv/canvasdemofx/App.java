package cz.stv.canvasdemofx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

/**
 * Třída má za úkol spouštět levely
 * */
public class App extends Application implements Serializable {

    private static Scene scene;

    @FXML
    private Button granatepressed;

    @FXML
    private Button escape;

    @FXML
    private Button leveltwobutton;

    @FXML
    private Button levelthreebutton;

    @FXML
    private Button levelfourbutton;

    @FXML
    private Button levelfivebutton;

    @FXML
    private Button levelsixbutton;

    @FXML
    private Button levelsevenbutton;

    @FXML
    private Button uppressed;

    @FXML
    private Button downpressed;

    @FXML
    private Button leftpressed;

    @FXML
    private Button rightpressed;

    @FXML
    private Button upgrades;

    @FXML
    private CheckBox hardcoremode;

    @FXML
    private ChoiceBox leveldifficulty;

    /*
    * Zmáčknuté klávesy
    * */
    boolean changeup = false;
    boolean changedown = false;
    boolean changeleft = false;
    boolean changeright = false;
    boolean changegranate = false;
    boolean changeescape = false;
    boolean changeupgrade = false;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainForm"));
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * zapne 1. level
     *
     * https://stackoverflow.com/questions/32922424/how-to-get-the-current-opened-stage-in-javafx - nová scéna
     * */
    @FXML
    private void startLevelOne(ActionEvent event) throws IOException {
        StatsAndSettings s = getAll();
        s.currentlevel = 1;
        saveAll(s);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        start(stage);
    }

    /**
     * zapne 2. level
     * */
    @FXML
    private void startLevelTwo(ActionEvent event) throws IOException {
        StatsAndSettings s = getAll();
        if(s.level >= 2){
            s.currentlevel = 2;
            saveAll(s);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            start(stage);
        }else{
            changeCollor(leveltwobutton);
        }
    }

    /**
     * zapne 3. level
     * */
    @FXML
    private void startLevelThree(ActionEvent event) throws IOException {
        StatsAndSettings s = getAll();
        if(s.level >= 3){
            getAll();
            s.currentlevel = 3;
            saveAll(s);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            start(stage);
        }else{
            changeCollor(levelthreebutton);
        }
    }

    /**
     * zapne 4. level
     * */
    @FXML
    private void startLevelFour(ActionEvent event) throws IOException {
        StatsAndSettings s = getAll();
        if(s.level >= 4){
            getAll();
            s.currentlevel = 4;
            saveAll(s);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            start(stage);
        }else{
            changeCollor(levelfourbutton);
        }
    }

    /**
     * zapne 5. level
     * */
    @FXML
    private void startLevelFive(ActionEvent event) throws IOException {
        StatsAndSettings s = getAll();
        if(s.level >= 5){
            getAll();
            s.currentlevel = 5;
            saveAll(s);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            start(stage);
        }else{
            changeCollor(levelfivebutton);
        }
    }

    /**
     * zapne 6. level
     * */
    @FXML
    private void startLevelSix(ActionEvent event) throws IOException {
        StatsAndSettings s = getAll();
        if(s.level >= 6){
            getAll();
            s.currentlevel = 6;
            saveAll(s);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            start(stage);
        }else{
            changeCollor(levelsixbutton);
        }
    }

    /**
     * zapne 7. level
     * */
    @FXML
    private void startLevelSeven(ActionEvent event) throws IOException {
        StatsAndSettings s = getAll();
        if(s.level >= 7){
            getAll();
            s.currentlevel = 7;
            saveAll(s);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            start(stage);
        }else{
            changeCollor(levelsevenbutton);
        }
    }

    /**
     * Vypne program
     * */
    @FXML
    public void exit(){
        Platform.exit();
    }

    /**
     * Vypne options
     * */
    @FXML
    public void exitOptions(ActionEvent event){
        exitWindow((Stage)((Node) event.getSource()).getScene().getWindow());
    }
    public void exitWindow(Stage stage){
        stage.close();
    }

    /**
     * detekuje a přenastavuje klávesy v hlavním menu
     */
    @FXML
    public void keyMMPressed(KeyEvent event) throws IOException {

        KeyCode key = event.getCode();

        StatsAndSettings s = getAll();

        if (key == s.escape) {
            settings();
        }else if(key == s.upgrade){
            upgrade();
        }
    }

    /**
     * detekuje a přenastavuje klávesy c options
     */
    @FXML
    public void keyOpPressed(KeyEvent event) throws IOException {

        KeyCode key = event.getCode();

        StatsAndSettings s = getAll();

        if(changeup){
            changeup = false;
            s.up = key;
            saveAll(s);
        }else if(changedown){
            changedown = false;
            s.down = key;
            saveAll(s);
        }else if(changeleft){
            changeleft = false;
            s.left = key;
            saveAll(s);
        }else if(changeright){
            changeright = false;
            s.right = key;
            saveAll(s);
        }else if(changeescape){
            changeescape = false;
            s.escape = key;
            saveAll(s);
        }else if(changegranate){
            changegranate = false;
            s.granate = key;
            saveAll(s);
        }else if(changeupgrade){
            changeupgrade = false;
            s.upgrade = key;
            saveAll(s);
        }else if (key == s.escape) {
            exitWindow((Stage)((Node) event.getSource()).getScene().getWindow());
        }
    }


    /**
     * detekuje klávesy stlačené při upgrades
     * */
    @FXML
    public void onUpPressed(KeyEvent event) {
        KeyCode key = event.getCode();
        StatsAndSettings s = getAll();

        if(s.upgrade == key){
            exitWindow((Stage)((Node) event.getSource()).getScene().getWindow());
        }
    }

    /**
     * detekuje klávesu na změnu pohynu nahoru
     * */
    @FXML
    public void changeUp() {
        changeup = true;
    }

    /**
     * detekuje klávesu na změnu pohynu dolu
     * */
    @FXML
    public void changeDown() {
        changedown = true;
    }

    /**
     * detekuje klávesu na změnu pohynu doleva
     * */
    @FXML
    public void changeLeft() {
        changeleft = true;
    }

    /**
     * detekuje klávesu na změnu pohynu doprava
     * */
    @FXML
    public void changeRight() {
        changeright = true;
    }

    /**
     * detekuje klávesu na escape
     * */
    @FXML
    public void changeEscape() {
        changeescape = true;
    }

    /**
     * detekuje klávesu na granátu
     * */
    @FXML
    public void changeGranate() {
        changegranate = true;
    }

    /**
     * detekuje klávesu na upgradu
     * */
    public void changeUpgrade(ActionEvent actionEvent) {changeupgrade = true;}

    /**
     * zapne nové okno s nastaveními
     */
    public void settings() throws IOException {
        Stage options = new Stage();
        scene = new Scene(loadFXML("Options"));
        options.setScene(scene);
        options.show();
    }

    private void upgrade() throws IOException {
        Stage options = new Stage();
        scene = new Scene(loadFXML("Upgrades"));
        options.setScene(scene);
        options.show();
    }

    @FXML
    public void renameAllButtons(){
        StatsAndSettings s = getAll();
        uppressed.setText(s.up.getChar());
        downpressed.setText(s.down.getChar());
        leftpressed.setText(s.left.getChar());
        rightpressed.setText(s.right.getChar());
        granatepressed.setText(s.granate.getChar());
        upgrades.setText(s.upgrade.getChar());
        escape.setText(s.escape.getChar());
        hardcoremode.setSelected(s.hardcore);
        saveAll(s);
    }


    /**
     * uloží StatsAndSettings do souboru
     */
    public static void saveAll(StatsAndSettings s){
        try{
            FileOutputStream f = new FileOutputStream("soubor.dat");
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(s);
            out.close();
            f.close();
        } catch (IOException e) {

        }
    }

    /**
     * dostane data o StatsAndSettings ze souboru
     */
    public static StatsAndSettings getAll(){
        try{
            FileInputStream f = new FileInputStream("soubor.dat");
            ObjectInputStream in = new ObjectInputStream(f);
            StatsAndSettings s = (StatsAndSettings) in.readObject();
            in.close();
            f.close();
            return s;
        } catch (IOException e) {
            return new StatsAndSettings();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new StatsAndSettings();
        }
    }

    /**
     * změní barvu písma na tlačítku
     * */
    public void changeCollor(Button b){
        b.setTextFill(Color.RED);
    }


    /**
     * obnový obrazovku
     * */
    @FXML
    public void reset() {

    }

    /**
     * detekuje klávesy stlačené při upgrades
     * */
    @FXML
    public void resetCharacer() {
        StatsAndSettings s = getAll();
        s.resetCharacter();
        saveAll(s);
    }

    /**
     * otevře settings
     * */
    public void openSettings(ActionEvent actionEvent) throws IOException {
        settings();
    }

    /**
     * upgrades settings
     * */
    public void openUpgrades(ActionEvent actionEvent) throws IOException {
        upgrade();
    }

    /**
     * nastavuje hardcore mod
     * */
    @FXML
    public void setHardcore() {
        StatsAndSettings s = getAll();
        s.hardcore = hardcoremode.isSelected();
        System.out.println(hardcoremode.isCache());
        saveAll(s);
    }

    /**
     * resetuje nastavení
     * */
    @FXML
    public void resBinds() {
        StatsAndSettings s = getAll();
        s.resetBinds();
        saveAll(s);
    }
}