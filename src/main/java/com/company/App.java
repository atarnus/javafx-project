package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class App extends Application {

    private TextArea textArea;
    private Stage window;

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
        FileChooser fc = new FileChooser();

        window.setTitle("JavaFX Code Editor");
        window.initStyle(StageStyle.UNIFIED);
        window.centerOnScreen();

        BorderPane pane = new BorderPane();

        Scene scene = new Scene(pane, 640, 480);
        MenuBar menuBar = new MenuBar();
        ToolBar toolBar = new ToolBar();
        TextArea textArea = new TextArea("");

        VBox paneTop = new VBox(menuBar, toolBar);
        pane.setTop(paneTop);
        pane.setCenter(textArea);

        // Toolbar buttons
        ColorPicker colorPicker = new ColorPicker();
        Button clear = new Button("Clear Text");
        Button button2 = new Button();
        toolBar.getItems().addAll(clear);
        clear.setOnAction(actionEvent -> textArea.setText(""));

        // On Text Area: Tab -> 4 spaces
        textArea.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.TAB) {
                    int x = textArea.getCaretPosition();
                    textArea.replaceText(x-1, x, "    ");
                }
        });

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

        file.getItems().addAll(menuNew, menuOpen, menuSave, separator, menuExit);
        edit.getItems().addAll(menuCut, menuCopy, menuPaste);
        run.getItems().add(menuRun);
        about.getItems().add(menuAbout);

        menuBar.getMenus().addAll(file, edit, run, about);

        // Menu Open File
        menuOpen.setOnAction(e -> {
            File openFile = fc.showOpenDialog(window);
            Path openpath = openFile.toPath();
            if (openFile != null) {
                try {
                    String content = Files.readString(openpath);
                    textArea.setText(content);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        // Menu Save File
        menuSave.setOnAction(e -> {
            File saveFile = fc.showSaveDialog(window);
            Path savePath = saveFile.toPath();
            if (saveFile != null) {
                try {
                    String savecontent = textArea.getText();
                    Files.writeString(savePath, savecontent);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        window.setScene(scene);
        window.show();
        System.out.println("start");
    }

    public static void main(String args[]) {
        launch(args);
    }
}