package com.miniproject;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;
import java.util.stream.IntStream;

public class MinesweeperApp extends Application {
    private static final int GRID_SIZE = 6; // ขนาดกระดาน (ปรับได้)
    private static final int CELL_SIZE = 50;
    private static final int MINE_COUNT = 8; // จำนวนระเบิด (ปรับได้)

    private Cell[][] grid = new Cell[GRID_SIZE][GRID_SIZE];
    private Pane root;

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(createContent()));
        stage.setTitle("Minesweeper");
        stage.show();
    }

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);

        // สร้างตาราง
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                Cell cell = new Cell(x, y);
                grid[x][y] = cell;
                root.getChildren().add(cell);
            }
        }

        placeMines();
        calculateNumbers();
        return root;
    }

    private void placeMines() {
        Random rand = new Random();
        int placedMines = 0;

        while (placedMines < MINE_COUNT) {
            int x = rand.nextInt(GRID_SIZE);
            int y = rand.nextInt(GRID_SIZE);

            if (!grid[x][y].hasMine) {
                grid[x][y].hasMine = true;
                placedMines++;
            }
        }
    }

    private void calculateNumbers() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (!grid[x][y].hasMine) {
                    int count = 0;

                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            int newX = x + dx;
                            int newY = y + dy;

                            if (isValid(newX, newY) && grid[newX][newY].hasMine) {
                                count++;
                            }
                        }
                    }

                    grid[x][y].neighborMines = count;
                }
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE;
    }

    private class Cell extends StackPane {
        private int x, y;
        private boolean hasMine = false;
        private boolean isOpened = false;
        private int neighborMines = 0;

        private Rectangle bg;
        private Text text = new Text();

        Cell(int x, int y) {
            this.x = x;
            this.y = y;

            bg = new Rectangle(CELL_SIZE, CELL_SIZE);
            bg.setFill(Color.LIGHTGRAY);
            bg.setStroke(Color.BLACK);

            text.setFont(Font.font(20));
            text.setVisible(false);

            setTranslateX(x * CELL_SIZE);
            setTranslateY(y * CELL_SIZE);
            getChildren().addAll(bg, text);

            setOnMouseClicked(e -> open());
        }

        void open() {
            if (isOpened)
                return;

            isOpened = true;
            bg.setFill(Color.WHITE);

            if (hasMine) {
                bg.setFill(Color.RED);
                showGameOver();
                return;
            }

            if (neighborMines > 0) {
                text.setText(String.valueOf(neighborMines));
                text.setVisible(true);
            } else {
                IntStream.rangeClosed(-1, 1)
                        .forEach(dx -> IntStream.rangeClosed(-1, 1)
                                .forEach(dy -> {
                                    if (isValid(x + dx, y + dy))
                                        grid[x + dx][y + dy].open();
                                }));
            }

            checkWin();
        }
    }

    private void showGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("คุณแพ้! เจอระเบิดแล้ว!");
        alert.showAndWait();
        resetGame();
    }

    private void checkWin() {
        boolean won = true;
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (!grid[x][y].hasMine && !grid[x][y].isOpened) {
                    won = false;
                    break;
                }
            }
        }

        if (won) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You Win!");
            alert.setHeaderText(null);
            alert.setContentText("คุณชนะ! เปิดทุกช่องที่ไม่มีระเบิดแล้ว!");
            alert.showAndWait();
            resetGame();
        }
    }

    private void resetGame() {
        root.getChildren().clear();
        grid = new Cell[GRID_SIZE][GRID_SIZE];

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                Cell cell = new Cell(x, y);
                grid[x][y] = cell;
                root.getChildren().add(cell);
            }
        }
        placeMines();
        calculateNumbers();
    }

    public static void main(String[] args) {
        launch(args);
    }
}