package com.javaminecraft;
 
public class PrimeFinder implements Runnable {
    Thread go;
    StringBuilder primes;
    boolean done = false;
 
    public PrimeFinder() {
        primes = new StringBuilder();
    }
 
    public void start() {
        if (go == null) {
            go = new Thread(this);
            go.start();
        }
    }

    @Override
    public void run() {
        int quantity = 500000;
        int numPrimes = 0;
        // candidate: the number that might be prime
        int candidate = 2;
        primes.append("\nFirst ");
        primes.append(quantity);
        primes.append(" primes: ");
        while (numPrimes < quantity) {
            if (isPrime(candidate)) {
                primes.append(candidate);
                primes.append(" ");
                numPrimes++;
            }
            candidate++;
        }
        done = true;
    }
 
    public static boolean isPrime(int checkNumber) {
        double root = Math.sqrt(checkNumber);
        for (int i = 2; i <= root; i++) {
            if (checkNumber % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] arguments) {
        PrimeFinder app = new PrimeFinder();
        app.start();
        int count = 1;
        while (!app.done) {
            System.out.print(count + " ");
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                // do nothing
            }
        }
        System.out.println(app.primes.toString());
    }
}
