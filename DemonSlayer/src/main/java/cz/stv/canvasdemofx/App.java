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
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

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
    private ChoiceBox<String> leveldifficulty;

    @FXML
    private Label scrapCount;

    @FXML
    private Button BB;

    @FXML
    private Button PB;

    @FXML
    private Button FB;

    @FXML
    private Button FMG;

    @FXML
    private Button MG;

    @FXML
    private Button G;

    @FXML
    private Button SG;

    @FXML
    private Button AS;

    @FXML
    private Button FW;

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

    static boolean ingame = false;
    static Stage stage = null;

    /**
     * zapne hru
     * */
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
    public void exitOptions(ActionEvent e) throws IOException {
        exitWindow((Stage)((Node) e.getSource()).getScene().getWindow());
    }

    /**
     * vypne okno
     * */
    public static void exitWindow(Stage s) throws IOException {
        if(ingame){
            MainForm.newgame = false;
            scene = new Scene(loadFXML("MainForm"));
            stage.setScene(scene);
            stage.show();
            ingame = false;
        }
        s.close();
    }

    /**
     * vypne upgrades
     * */
    public void exitUpgrades(ActionEvent event) throws IOException {exitWindow((Stage)((Node) event.getSource()).getScene().getWindow());}

    /**
     * detekuje a přenastavuje klávesy v hlavním menu
     */
    @FXML
    public void keyMMPressed(KeyEvent event) throws IOException {

        KeyCode key = event.getCode();

        StatsAndSettings s = getAll();

        if (key == s.escape) {
            settings(false, (Stage)((Node) event.getSource()).getScene().getWindow());
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
    public void onUpPressed(KeyEvent event) throws IOException {
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
    public void changeUpgrade() {changeupgrade = true;}

    /**
     * zapne nové okno s nastaveními
     */
    public static void settings(boolean a, Stage s) throws IOException {
        stage = s;
        ingame = a;
        Stage options = new Stage();
        scene = new Scene(loadFXML("Options"));
        options.setScene(scene);
        options.show();
    }

    /**
     * zapne nové okno s upgradama
     * */
    private void upgrade() throws IOException {
        Stage options = new Stage();
        scene = new Scene(loadFXML("Upgrades"));
        options.setScene(scene);
        options.show();
    }

    @FXML
    public void renameAllButtons(){
        StatsAndSettings s = getAll();

        if(!s.hardcore){
            if (Objects.equals(leveldifficulty.getValue(), "Easy")){
                s.difficulty = 1;
            } else if (Objects.equals(leveldifficulty.getValue(), "Normal")){
                s.difficulty = 2;
            } else if(Objects.equals(leveldifficulty.getValue(), "Hard")){
                s.difficulty = 3;
            }
        }

        uppressed.setText(s.up.getName());
        downpressed.setText(s.down.getName());
        leftpressed.setText(s.left.getName());
        rightpressed.setText(s.right.getName());
        granatepressed.setText(s.granate.getName());
        upgrades.setText(s.upgrade.getName());
        escape.setText(s.escape.getName());
        hardcoremode.setSelected(s.hardcore);
        if(s.difficulty == 1){
            leveldifficulty.setValue("Easy");
        }else if (s.difficulty == 2){
            leveldifficulty.setValue("Normal");
        }else if (s.difficulty == 3){
            leveldifficulty.setValue("Hard");
        }
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
        } catch (IOException ignored) {}
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
     * detekuje klávesy stlačené při upgrades
     * */
    @FXML
    public void resetCharacer(ActionEvent event) throws IOException {
        WarningWindow.openWarning(4, event);
    }

    /**
     * otevře settings
     * */
    public void openSettings(ActionEvent actionEvent) throws IOException {
        settings(false, (Stage)((Node) actionEvent.getSource()).getScene().getWindow());
    }

    /**
     * upgrades settings
     * */
    public void openUpgrades() throws IOException {
        upgrade();
    }

    /**
     * nastavuje hardcore mod
     * */
    @FXML
    public void setHardcore() {
        StatsAndSettings s = getAll();
        s.hardcore = hardcoremode.isSelected();
        s.difficulty = 3;
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

    /**
     * obnový upgrades
     * */
    @FXML
    public void reset() {
        StatsAndSettings s = getAll();

        if(s.scrap >= 6){
            setStyleLG(SG);
            setStyleLG(FMG);
        } else {
            setStyleG(SG);
            setStyleG(FMG);
        }

        if(s.scrap >= 4){
            setStyleLG(AS);
            setStyleLG(FW);
            setStyleLG(MG);
        } else {
            setStyleG(AS);
            setStyleG(FW);
            setStyleG(MG);
        }

        if(s.scrap >= 3){
            setStyleLG(PB);
            setStyleLG(BB);
            setStyleLG(G);
        } else {
            setStyleG(PB);
            setStyleG(BB);
            setStyleG(G);
        }

        if(s.scrap >= 2){
            setStyleLG(FB);
        } else {
            setStyleG(FB);
        }

        if(s.BB){setStyleDG(BB);}
        if(s.G){setStyleDG(G);}
        if(s.AS){setStyleDG(AS);}
        if(s.FW){setStyleDG(FW);}
        if(s.FMG){setStyleDG(FMG);}
        if(s.MG){setStyleDG(MG);}
        if(s.PB){setStyleDG(PB);}
        if(s.FB){setStyleDG(FB);}
        if(s.SG){setStyleDG(SG);}

        scrapCount.setText("Scrap: " + s.scrap);
    }

    /**
     * Nastavý pozadí tlačítka na tmavě šedou (z nějakého důvodu je šedá tmavší než tmavě šeda)
     * */
    public void setStyleDG(Button b){
        b.setStyle("-fx-background-color: darkgray; -fx-border-color: BLACK;");
    }

    /**
     * Nastavý pozadí tlačítka na světle šedou
     * */
    public void setStyleLG(Button b){
        b.setStyle("-fx-background-color: lightgray; -fx-border-color: BLACK;");
    }

    /**
     * Nastavý pozadí tlačítka na šedou
     * */
    public void setStyleG(Button b){
        b.setStyle("-fx-background-color: gray; -fx-border-color: BLACK;");
    }

    /**
     * koupí Faster Machine Gun
     * */
    @FXML
    public void buyFMG() {
        StatsAndSettings s = getAll();

        if(s.scrap >= 6 && !s.FMG){
            s.scrap -= 6;
            s.FMG = true;
            saveAll(s);
        }
    }

    /**
     * koupí Shotgun
     * */
    @FXML
    public void buySG() {
        StatsAndSettings s = getAll();

        if(s.scrap >= 6 && !s.SG){
            s.scrap -= 6;
            s.SG = true;
            saveAll(s);
        }
    }

    /**
     * koupí Machine Gun
     * */
    @FXML
    public void buyMG() {
        StatsAndSettings s = getAll();

        if(s.scrap >= 4 && !s.MG){
            s.scrap -= 4;
            s.MG = true;
            saveAll(s);
        }
    }

    /**
     * koupí Auto Shooting
     * */
    @FXML
    public void buyAS() {
        StatsAndSettings s = getAll();

        if(s.scrap >= 4 && !s.AS){
            s.scrap -= 4;
            s.AS = true;
            saveAll(s);
        }
    }

    /**
     * koupí Faster Walking
     * */
    @FXML
    public void buyFW() {
        StatsAndSettings s = getAll();

        if(s.scrap >= 4 && !s.FW){
            s.scrap -= 4;
            s.FW = true;
            saveAll(s);
        }
    }

    /**
     * koupí Faster Machine Gun
     * */
    @FXML
    public void buyPB() {
        StatsAndSettings s = getAll();

        if(s.scrap >= 3 && !s.PB){
            s.scrap -= 3;
            s.PB = true;
            saveAll(s);
        }
    }

    /**
     * koupí Faster Machine Gun
     * */
    @FXML
    public void buyBB() {
        StatsAndSettings s = getAll();

        if(s.scrap >= 3 && !s.BB){
            s.scrap -= 3;
            s.BB = true;
            saveAll(s);
        }
    }

    /**
     * koupí Faster Machine Gun
     * */
    @FXML
    public void buyG() {
        StatsAndSettings s = getAll();

        if(s.scrap >= 3 && !s.G){
            s.scrap -= 3;
            s.G = true;
            saveAll(s);
        }
    }

    /**
     * koupí Faster Machine Gun
     * */
    @FXML
    public void buyFB() {
        StatsAndSettings s = getAll();

        if(s.scrap >= 2 && !s.FB){
            s.scrap -= 2;
            s.FB = true;
            saveAll(s);
        }
    }
}