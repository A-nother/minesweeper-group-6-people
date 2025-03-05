package com.miniproject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;;

public class App extends Application {
    static Minesweeper initMineField() {
        Minesweeper game = new Minesweeper(9, 9);
        game.setMineCell(0, 1);
        game.setMineCell(1, 5);
        game.setMineCell(1, 8);
        game.setMineCell(2, 4);
        game.setMineCell(3, 6);        
        game.setMineCell(4, 2);
        game.setMineCell(5, 4);
        game.setMineCell(6, 2);
        game.setMineCell(7, 2);
        game.setMineCell(8, 6);
        return game;
    }
    static Minesweeper initMineFieldFromFile(String minefieldFile) {
        return new Minesweeper(minefieldFile);
    }
    public static void main(String[] args) {
        // Task 3: Implement a menu to select the mine field template
        // Design the menu by yourself.
        //ทำอะไรเพิ่มเติม 
        launch(args);
    } 
    
    @Override
    public void start(Stage stage) {
        Label Welcome = new Label("Welcome to Minesweeper!");

        Button StartGame = new Button("Start Game");
        Button LoadStage = new Button("Load Stage");
        Button Exit = new Button("Exit");

        StartGame.setOnAction(evt -> {
            Minesweeper game = initMineField();
            System.out.println("Game Start!");
            game.displayField();
            System.out.println();
        });
        LoadStage.setOnAction(evt -> {
            String filename = "minefield/minefield01.txt";
            Minesweeper game = initMineFieldFromFile(filename);
            System.out.println("Load Stage form: " + filename);
            game.displayField();
            System.out.println();
        });
        Exit.setOnAction(evt -> Platform.exit());

        VBox content = new VBox(Welcome, StartGame, LoadStage, Exit);
        content.setSpacing(20);
        content.setAlignment(Pos.CENTER);

        Scene scene = new Scene(content, 500, 600);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }
}
