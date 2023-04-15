package cz.stv.canvasdemofx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static cz.stv.canvasdemofx.MainMenu.loadFXML;

public class WarningWindow {

    static int warningoption = 0;

    private static Scene scene;

    static ActionEvent event;

    /**
     * vatvoří nové okno s upozorněním
     * */
    public static void openWarning(int i, ActionEvent a) throws IOException {
        event = a;
        warningoption = i;
        Stage options = new Stage();
        scene = new Scene(loadFXML("WarningWindow"));
        options.setScene(scene);
        options.show();
    }


    /**
     * potvrdí daný příklaz, kvůli kterému se otevřelo příkazové okno
     * */
    public void Accept(ActionEvent e) throws IOException {
        App.exitWindow((Stage)((Node) e.getSource()).getScene().getWindow());
        if(warningoption == 1){
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(loadFXML("MainForm"));
            stage.setScene(scene);
            stage.show();

        } else if(warningoption == 2){
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(loadFXML("MainMenu"));
            stage.setScene(scene);
            stage.show();
        } else if(warningoption == 3){
            Platform.exit();
        } else if(warningoption == 4){
            StatsAndSettings s = App.getAll();
            s.resetCharacter();
            App.saveAll(s);
        }
        warningoption = 0;
    }

    /**
     * zruší daný příklaz, kvůli kterému se otevřelo příkazové okno
     * */
    public void BackToGame(ActionEvent event) throws IOException {
        App.exitWindow((Stage)((Node) event.getSource()).getScene().getWindow());
        warningoption = 0;
    }
}
