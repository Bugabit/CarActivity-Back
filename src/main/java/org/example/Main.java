package org.example;

import org.example.service.CarService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CarService service = new CarService();
        boolean playing = true;

        System.out.println("Bienvenido al juego del coche");
        System.out.println("Selecciona la dificultad:");
        System.out.println("1. Muy fácil (mapa visible y definido)");
        System.out.println("2. Fácil (mapa visible y aleatorio)");
        System.out.println("3. Media (mapa invisible y definido)");
        System.out.println("4. Difícil (mapa invisible y aleatorio)");
        service.setDifficulty(sc.next());

        if (!service.generateMap()) {
            System.out.println("Opción incorrecta");
            return;
        }
        service.print();

        while (playing) {
            System.out.println("Selecciona una dirección para avanzar (wasd):");
            playing = service.move(sc.next());
        }
    }

}