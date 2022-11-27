package org.example;

import org.example.service.CarService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CarService service = new CarService();
        boolean playing = true;

        System.out.println("Bienvenido al juego del coche");

        /* TODO: 2 Player

        System.out.println("Selecciona el número de jugadores:");
        System.out.println("1.- Un jugador");
        System.out.println("2.- Dos jugadores");
        int players = sc.nextInt();
        if (players == 2) {
            service.addPlayer();
            service.randomize();
        }

         */

//        service.printMap();
        service.printCloser();

        while (playing) {
            System.out.println("Selecciona una dirección para avanzar (wasd):");
            playing = service.move(sc.next());
        }
    }

}