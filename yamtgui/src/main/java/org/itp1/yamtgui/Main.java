package org.itp1.yamtgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Yamt.fxml")); // DON'T DELETE THIS SLASH
        stage.setTitle("YAMT");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    public static void main(String[] args) {
        // Yamt gui
        launch(args);
    }
}