package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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
        window.centerOnScreen();

        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 640, 480);
        MenuBar menuBar = new MenuBar();
        TextArea textArea = new TextArea("");
        pane.setTop(menuBar);
        pane.setCenter(textArea);

        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu run = new Menu("Run");
        Menu about = new Menu("About");

        MenuItem menuNew = new MenuItem("New");
        menuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        MenuItem menuOpen = new MenuItem("Open..");
        menuOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        MenuItem menuSave = new MenuItem("Save..");
        menuSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        MenuItem menuExit = new MenuItem("Exit");
        menuExit.setOnAction(actionEvent -> System.exit(0));
        menuExit.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        MenuItem menuCut = new MenuItem("Cut");
        menuCut.setOnAction(actionEvent -> textArea.cut());
        menuCut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        MenuItem menuCopy = new MenuItem("Copy");
        menuCopy.setOnAction(actionEvent -> textArea.copy());
        menuCopy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        MenuItem menuPaste = new MenuItem("Paste");
        menuPaste.setOnAction(actionEvent -> textArea.paste());
        menuPaste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        MenuItem menuRun = new MenuItem("Compile and Run..");
        menuRun.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        MenuItem menuAbout = new MenuItem("About App");
        SeparatorMenuItem separator = new SeparatorMenuItem();

        file.getItems().add(menuNew);
        file.getItems().add(menuOpen);
        file.getItems().add(menuSave);
        file.getItems().add(separator);
        file.getItems().add(menuExit);
        edit.getItems().add(menuCut);
        edit.getItems().add(menuCopy);
        edit.getItems().add(menuPaste);
        run.getItems().add(menuRun);
        about.getItems().add(menuAbout);

        menuBar.getMenus().add(file);
        menuBar.getMenus().add(edit);
        menuBar.getMenus().add(run);
        menuBar.getMenus().add(about);


        window.setScene(scene);

        /*
        Button clear = new Button("Clear");
        clear.setOnAction(actionEvent -> textArea.setText(""));
        */


        window.show();
        System.out.println("start");


    }

    public static void main(String args[]) {
        launch(args);
    }
}