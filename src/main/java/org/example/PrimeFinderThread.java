package org.example;

import java.util.Scanner;
import java.util.concurrent.*;

public class PrimeFinderThread {
    record PrimeResult(int input, long primeNumber) {
    }

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static long findNthPrime(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        int count = 0;
        long number = 1;
        while (count < n) {
            number++;
            if (isPrime(number)) {
                count++;
            }
        }
        return number;
    }

    public static boolean isPrime(long number) {
        return Main.isPrime(number);
    }

    public void start() {

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Enter a number or 'exit' to quit: ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                try {
                    int n = Integer.parseInt(input);
                    Future<PrimeResult> future = executor.submit(() -> new PrimeResult(n, findNthPrime(n)));
                    executor.execute(new ResultPrinter(future));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input type");
                }
            }
        } finally {
            executor.shutdown();
        }
    }

    public static void main(String[] args) {
        PrimeFinderThread primeFinderThread = new PrimeFinderThread();
        primeFinderThread.start();
    }

    static class ResultPrinter implements Runnable {
        private final Future<PrimeResult> future;

        public ResultPrinter(Future<PrimeResult> future) {
            this.future = future;
        }

        @Override
        public void run() {
            try {
                PrimeResult result = future.get();
                System.out.println("Prime number for " + result.input() + ": " + result.primeNumber());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

