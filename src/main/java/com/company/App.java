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
import java.util.Optional;


public class App extends Application {

    private Stage window;
    TextArea textArea = new TextArea("");

    @Override
    public void start(Stage window) {
        window.setTitle("Text Editor");                                 // Window title and style
        window.initStyle(StageStyle.UNIFIED);
        window.centerOnScreen();

        BorderPane pane = new BorderPane();                             // Setting up BorderPane and Bars
        Scene scene = new Scene(pane, 640, 480);
        MenuBar menuBar = new MenuBar();
        ToolBar toolBar = new ToolBar();
        VBox paneTop = new VBox(menuBar, toolBar);
        pane.setTop(paneTop);
        pane.setCenter(textArea);

        textArea.setOnKeyPressed(keyEvent -> {                      // On Text Area: Tab -> 4 Spaces
            if (keyEvent.getCode() == KeyCode.TAB) {
                int x = textArea.getCaretPosition();
                textArea.replaceText(x - 1, x, "    ");
            }
        });

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
        toolBar.getItems().addAll(save, open, new Separator(), cut, copy, paste, new Separator(), clear);

        clear.setPrefSize(75, 35);                              // Button sizes
        open.setPrefSize(40, 35);
        save.setPrefSize(40, 35);
        cut.setPrefSize(40, 35);
        copy.setPrefSize(40, 35);
        paste.setPrefSize(40, 35);

        open.setGraphic(new ImageView(imgOpen));                     // Setting icons to buttons
        save.setGraphic(new ImageView(imgSave));
        cut.setGraphic(new ImageView(imgCut));
        copy.setGraphic(new ImageView(imgCopy));
        paste.setGraphic(new ImageView(imgPaste));

        open.setTooltip(new Tooltip("Open File"));                  // Button tooltips
        save.setTooltip(new Tooltip("Save File"));
        cut.setTooltip(new Tooltip("Cut Text"));
        copy.setTooltip(new Tooltip("Copy Text"));
        paste.setTooltip(new Tooltip("Paste Text"));

        clear.setOnAction(this::clear);                             // Button Actions
        open.setOnAction(this::open);
        save.setOnAction(this::save);
        cut.setOnAction(actionEvent -> textArea.cut());
        copy.setOnAction(actionEvent -> textArea.copy());
        paste.setOnAction(actionEvent -> textArea.paste());

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

        menuCut.setOnAction(actionEvent -> textArea.cut());         // Menu Actions
        menuCopy.setOnAction(actionEvent -> textArea.copy());
        menuPaste.setOnAction(actionEvent -> textArea.paste());
        menuOpen.setOnAction(this::open);
        menuSave.setOnAction(this::save);
        menuExit.setOnAction(this::exit);
        menuAbout.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText(null);
            alert.setContentText("This is a small project done as a part of Code Training Camp course. \n\nButton icons from http://icons8.com.");
            alert.showAndWait();
        });

        window.setScene(scene);
        window.show();
    }

    private void clear(ActionEvent actionEvent) {                   // Clear Text Method with Confirmation Window
        Alert clearAlert = new Alert(Alert.AlertType.CONFIRMATION);
        clearAlert.setTitle("Clear Text");
        clearAlert.setHeaderText(null);
        clearAlert.setContentText("Are you sure want to clear all text?");
        Optional<ButtonType> result = clearAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            textArea.setText("");
        }
    }

    private void exit(ActionEvent event) {                          // Exit Method with Confirmation Window
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit");
        exitAlert.setHeaderText(null);
        exitAlert.setContentText("Are you sure want to exit? Any unsaved information will be lost.");
        Optional<ButtonType> result = exitAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    public void open(ActionEvent event) {                           // Open File Method
        FileChooser fc = new FileChooser();                         // File Chooser with Extension Filter
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Java Files", "*.java"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File openFile = fc.showOpenDialog(window);
        if (openFile != null) {                                     // If file is chosen, set its content to Text Area
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
        FileChooser fc = new FileChooser();                         // File Chooser with Extension Filter
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Java Files", "*.java"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File saveFile = fc.showSaveDialog(window);
        if (saveFile != null) {                                     // If file is chosen, save Text Area content to it
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