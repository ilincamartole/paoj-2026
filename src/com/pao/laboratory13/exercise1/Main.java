package com.pao.laboratory13.exercise1;



import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProtocolEngine engine = new ProtocolEngine();

        int q = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < q; i++) {
            String line = scanner.nextLine();

            String result = engine.processCommand(line);

            if (!result.isEmpty()) {
                System.out.println(result);
            }
        }

        scanner.close();
    }
}