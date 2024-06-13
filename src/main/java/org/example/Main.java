package org.example;

import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a number or 'exit' to quit: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int n = Integer.parseInt(input);
                    findNthPrime(n);
                }
            catch (NumberFormatException e) {
                System.out.println("Invalid input type");
            }
            Thread.sleep(1000);
        }
        scanner.close();
    }
    //    private static long findNthPrime(int n) {
//        int count, i;
//        long num = 1;
//        count = 0;
//
//        while (count < n) {
//            num = num + 1;
//            for (i = 2; i <= num; i++) {
//                if (num % i == 0) {
//                    break;
//                }
//            }
//            if (i == num) {
//                count = count + 1;
//            }
//        }
//        return num;
//    }

    public static boolean isPrime(long number) {
        if (number < 2) return false;
        for (long i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public static void findNthPrime(int n) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        if (executor.getActiveCount() <= executor.getMaximumPoolSize()) {
            Callable<Void> callable = () -> {
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
                System.out.println( n+ "th prime number: " + number);
                return null;
            };
            executor.submit(callable);
        }

        else{
            System.out.println("No free threads. Please try again later.");
        }
        executor.shutdown();
    }
}