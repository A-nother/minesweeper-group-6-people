package com.miniproject;

import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;

public class Minesweeper {
    static char SAFE_CELL = '.';
    static char MINE_CELL = 'X' ;
    static int IS_SAFE = 0;
    static int IS_MINE = 1;
    int fieldX, fieldY;
    int[][] cells;
    String fieldFileName;

    public Minesweeper(String fieldFile) {
        this.fieldFileName = fieldFile;
        initFromFile(fieldFileName);
    }

    public Minesweeper(int fieldX, int fieldY) {
        this.fieldX = fieldX;
        this.fieldY = fieldY;
        this.cells = new int[fieldX][fieldY];
        for (int i=0; i<fieldX; i++) {
            for (int j=0; j<fieldY; j++) {
                cells[i][j] = IS_SAFE;
            }
        }
    }

    void displayField() {
        // Task 1: Display the mine field to terminal
        for(int i=0; i<fieldX; i++){
            for(int j=0; j <fieldY; j++){
                if ((j+1)%9!=0) {
                    if (cells[i][j] == 0) {
                        System.out.print(SAFE_CELL);
                    } else {
                        System.out.print(MINE_CELL);
                    }
                } else {
                    if (cells[i][j] == 0) {
                        System.out.println(SAFE_CELL);
                    } else {
                        System.out.println(MINE_CELL);
                    }
                }
            }
        }
    }
    void setMineCell(int x, int y) {
        cells[x][y] = IS_MINE;
    }

    void initFromFile(String mineFieldFile) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(mineFieldFile);
        
        // Task 2: Using `java.util.Scanner` to load mine field from the input stream named, `is`
        Scanner sc = new Scanner(is);
        fieldX = sc.nextInt();
        fieldY = sc.nextInt();
        sc.nextLine();
        cells = new int[fieldX][fieldY];
        int i = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            int j = 0;
            for (char cell : line.toCharArray()) {
                if (cell == '.') {
                    cells[i][j] = IS_SAFE;
                } else {
                    cells[i][j] = IS_MINE;
                }
                j++;
            }
            i++;
        }
    }
}