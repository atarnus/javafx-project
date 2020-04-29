package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private Stage window;
    TextArea textArea = new TextArea("");

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
    public void start(Stage window) throws FileNotFoundException {
        window.setTitle("Text Editor");                             // Window title and style
        window.initStyle(StageStyle.UNIFIED);
        window.centerOnScreen();

        BorderPane pane = new BorderPane();                         // Setting up BorderPane and Bars
        Scene scene = new Scene(pane, 640, 480);
        MenuBar menuBar = new MenuBar();
        ToolBar toolBar = new ToolBar();
        VBox paneTop = new VBox(menuBar, toolBar);
        pane.setTop(paneTop);
        pane.setCenter(textArea);

        Image imgSave = new Image("images/iconsave.png");           // Toolbar icons
        Image imgOpen = new Image("images/iconopen.png");
        Image imgCut = new Image("images/iconcut.png");
        Image imgCopy = new Image("images/iconcopy.png");
        Image imgPaste = new Image("images/iconpaste.png");

        Button clear = new Button("Clear Text");                    // Toolbar buttons
        Button open = new Button();
        Button save = new Button();
        Button cut = new Button();
        Button copy = new Button();
        Button paste = new Button();
        clear.setPrefSize(75, 35);
        open.setPrefSize(40, 35);
        save.setPrefSize(40, 35);
        cut.setPrefSize(40, 35);
        copy.setPrefSize(40, 35);
        paste.setPrefSize(40, 35);

        open.setGraphic(new ImageView(imgOpen));                        // Setting icons to buttons
        save.setGraphic(new ImageView(imgSave));
        cut.setGraphic(new ImageView(imgCut));
        copy.setGraphic(new ImageView(imgCopy));
        paste.setGraphic(new ImageView(imgPaste));

        open.setTooltip(new Tooltip("Open File"));                  // Button tooltips
        save.setTooltip(new Tooltip("Save File"));
        cut.setTooltip(new Tooltip("Cut Text"));
        copy.setTooltip(new Tooltip("Copy Text"));
        paste.setTooltip(new Tooltip("Paste Text"));

        toolBar.getItems().addAll(save, open, new Separator(), cut, copy, paste, new Separator(), clear);

        clear.setOnAction(ActionEvent -> textArea.setText(""));     // Button Actions
        open.setOnAction(this::open);
        save.setOnAction(this::save);
        cut.setOnAction(actionEvent -> textArea.cut());
        copy.setOnAction(actionEvent -> textArea.copy());
        paste.setOnAction(actionEvent -> textArea.paste());

        textArea.setOnKeyPressed(keyEvent -> {                      // On Text Area: Tab -> 4 Spaces
            if (keyEvent.getCode() == KeyCode.TAB) {
                int x = textArea.getCaretPosition();
                textArea.replaceText(x - 1, x, "    ");
            }
        });

        Menu file = new Menu("File");                           // Menus
        Menu edit = new Menu("Edit");
        Menu about = new Menu("About");
        menuBar.getMenus().addAll(file, edit, about);

        MenuItem menuOpen = new MenuItem("Open..");             // Menu Items
        MenuItem menuSave = new MenuItem("Save..");
        MenuItem menuExit = new MenuItem("Exit");
        MenuItem menuCut = new MenuItem("Cut");
        MenuItem menuCopy = new MenuItem("Copy");
        MenuItem menuPaste = new MenuItem("Paste");
        MenuItem menuAbout = new MenuItem("About App");
        file.getItems().addAll(menuOpen, menuSave, new SeparatorMenuItem(), menuExit);
        edit.getItems().addAll(menuCut, menuCopy, menuPaste);
        about.getItems().add(menuAbout);

        // Menu Shortcut Keys
        menuOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        menuSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        menuCut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        menuCopy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        menuPaste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));

        menuExit.setOnAction(actionEvent -> System.exit(0));    // Menu Actions
        menuCut.setOnAction(actionEvent -> textArea.cut());
        menuCopy.setOnAction(actionEvent -> textArea.copy());
        menuPaste.setOnAction(actionEvent -> textArea.paste());
        menuOpen.setOnAction(this::open);
        menuSave.setOnAction(this::save);

        window.setScene(scene);
        window.show();
    }

    public void open(ActionEvent event) {                           // Open File Method
        FileChooser fc = new FileChooser();
        File openFile = fc.showOpenDialog(window);
        if (openFile != null) {
            Path openPath = openFile.toPath();
            try {
                String content = Files.readString(openPath);
                textArea.setText(content);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void save(ActionEvent event) {                           // Save File Method
        FileChooser fc = new FileChooser();
        File saveFile = fc.showSaveDialog(window);
        if (saveFile != null) {
            Path savePath = saveFile.toPath();
            try {
                String saveContent = textArea.getText();
                Files.writeString(savePath, saveContent);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        launch(args);
    }
}