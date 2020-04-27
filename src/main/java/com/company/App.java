package com.company;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.*;

public class App extends Application {

    public App() {
        System.out.println("constructor");
    }

    @Override
    public void init() {
        System.out.println("init");
    }

    @Override
    public void stop() {
        System.out.println("stop");
    }

    @Override
    public void start(Stage window) {
        window.setTitle("JavaFX Code Editor");
        window.initStyle(StageStyle.UNIFIED);
        window.setWidth(640);
        window.setHeight(480);
        window.centerOnScreen();

        Scene scene = new Scene(new Button("Click!"));
        window.setScene(scene);

        window.show();
        System.out.println("start");
    }

    public static void main(String args[]) {
        launch(args);
    }
}