package org.example;
import java.util.concurrent.*;
import java.util.Scanner;

public class ParallelPrimeFinder {

//    static class PrimeResult {
//        private final int input;
//        private final long primeNumber;
//
//        public PrimeResult(int input, long primeNumber) {
//            this.input = input;
//            this.primeNumber = primeNumber;
//        }
//
//        public int getInput() {
//            return input;
//        }
//
//        public long getPrimeNumber() {
//            return primeNumber;
//        }
//    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
//        CompletionService<PrimeResult> completionService = new ExecutorCompletionService<>(executor);
        CompletionService<Void> completionService = new ExecutorCompletionService<>(executor);
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("Enter a number or 'exit' to quit: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                executor.shutdown();
                break;
            }

            // Check if there are available threads in the thread pool
            if (executor.getActiveCount() >= executor.getMaximumPoolSize()) {
                System.out.println("No free threads. Please try again later.");
                continue; // Skip submitting the task
            }

            try {
                int n = Integer.parseInt(input);
                completionService.submit(() -> {
                    long prime = findNthPrime(n);
//                    return new PrimeResult(n, prime);
                    return null;
                });
            } catch (NumberFormatException e) {
                System.out.println("Invalid input type");
            }

            // Check for completed tasks and print results
//            Future<PrimeResult> future = completionService.poll();
//            if (future != null){
//            try {
//                PrimeResult result = future.get();
//                System.out.println("Prime number for " + result.getInput() + ": " + result.getPrimeNumber());
//            } catch (InterruptedException | ExecutionException e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//            }
        }
        scanner.close();
    }

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
        System.out.println(n + "th prime number: " + number);
        return number;
    }

    private static boolean isPrime(long number) {
        return Main.isPrime(number);
    }
}

