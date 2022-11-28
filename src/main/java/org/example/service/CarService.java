package org.example.service;

import org.apache.commons.lang3.StringUtils;
import org.example.entity.Car;
import org.example.entity.Map;

import java.util.Random;

public class CarService {

    // COLORS
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    // DIRECTIONS
    final String UP = "w";
    final String DOWN = "s";
    final String LEFT = "a";
    final String RIGHT = "d";

    Map map;
    Car car;
    private String difficulty;

    public CarService() {
        map = new Map();
        car = new Car(1);

        // INITIALIZE
        car.setPosX(map.getMatrix()[0].length / 2);
        car.setPosY(map.getMatrix().length - 1);
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public boolean move(String direction) {
        if (!movePosition(direction)) {
            return false;
        }

        print();
        return checkPosition();
    }

    // Move the car to the selected square
    private boolean movePosition(String direction) {
        switch (direction) {
            case UP:
                if (car.getPosY() - 1 < 0) {
                    car.setPosY(map.getMatrix().length);
                }
                car.setPosY(car.getPosY() - 1);
                break;
            case DOWN:
                if (car.getPosY() + 1 >= map.getMatrix().length) {
                    car.setPosY(-1);
                }
                car.setPosY(car.getPosY() + 1);
                break;
            case LEFT:
                if (car.getPosX() - 1 < 0) {
                    car.setPosX(map.getMatrix()[0].length);
                }
                car.setPosX(car.getPosX() - 1);
                break;
            case RIGHT:
                if (car.getPosX() + 1 >= map.getMatrix()[0].length) {
                    car.setPosX(-1);
                }
                car.setPosX(car.getPosX() + 1);
                break;
            default:
                System.out.println(ANSI_RED + "Te has equivocado cruck. A empezar de nuevo por listo");
                return false;
        }
        return true;
    }

    // Check the current box and perform the corresponding action
    public boolean checkPosition() {
        int position = map.getMatrix()[car.getPosY()][car.getPosX()];
        car.setHearts(car.getHearts() + position);

        if (car.getHearts() > 10) {
            System.out.println("ENHORABUENA CHAMPION");
            return false;
        } else if (car.getHearts() <= 0) {
            System.out.println(ANSI_RED + "CAGASTE");
            return false;
        } else {
            System.out.println("Vidas restantes: " + car.getHearts());
        }

        return true;
    }

    public boolean generateMap() {
        switch (difficulty) {
            case "1":
            case "3":
                map.defineMap();
                return true;
            case "2":
            case "4":
                randomize();
                return true;
        }
        return false;
    }

    public void print() {
        switch (difficulty) {
            case "1":
            case "2":
                printMap();
                break;
            case "3":
            case "4":
                printCloser();
                break;
        }
    }

    // Shows the whole map
    public void printMap() {
        for (int i = 0; i < map.getMatrix().length; i++) {
            System.out.print("[");
            for (int j = 0; j < map.getMatrix()[0].length; j++) {
                if (car.getPosY() == i && car.getPosX() == j) {
                    System.out.print(ANSI_RED);
                }

                String position = StringUtils.rightPad(String.valueOf(map.getMatrix()[i][j]), 3);
                System.out.print(" " + position + " ");
                System.out.print(ANSI_RESET);
            }
            System.out.println("]");
        }
    }

    // Show only the squares close to the car
    public void printCloser() {
        movePosition(UP);
        System.out.println("   [" + map.getMatrix()[car.getPosY()][car.getPosX()] + "]   ");
        movePosition(DOWN);
        movePosition(LEFT);
        System.out.print("[" + map.getMatrix()[car.getPosY()][car.getPosX()] + "] ");
        movePosition(RIGHT);
        System.out.print(ANSI_RED + map.getMatrix()[car.getPosY()][car.getPosX()] + ANSI_RESET);
        movePosition(RIGHT);
        System.out.println(" [" + map.getMatrix()[car.getPosY()][car.getPosX()] + "]");
        movePosition(LEFT);
        movePosition(DOWN);
        System.out.println("   [" + map.getMatrix()[car.getPosY()][car.getPosX()] + "]   ");
        movePosition(UP);
    }

    // Creates new random map
    public void randomize() {
        initObstacles(Map.SPIKE, 7);
        initObstacles(Map.RAVINE, 5);
        initObstacles(Map.GOAL, 1);
    }

    // Initialize obstacles randomly
    private void initObstacles(int obstacle, int limit) {
        Random r = new Random();
        while (limit > 0) {
            int x = r.nextInt(11);
            int y = r.nextInt(9);
            if (map.getMatrix()[y][x] != 0
                    || (x == car.getPosX() && y == car.getPosY())) {
                continue;
            }
            map.getMatrix()[y][x] = obstacle;
            limit--;
        }
    }
}