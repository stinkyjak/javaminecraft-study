package com.javaminecraft;

public class ModemTester {
    public static void main(String[] arguments) {
        CableModem alpha = new CableModem();
        DslModem beta = new DslModem();
        alpha.speed = 3.77;
        beta.speed = 5.25;
        System.out.println("\nTrying the cable modem:");
        alpha.displaySpeed();
        alpha.connect();
        System.out.println("\nTrying the DSL modem:");
        beta.displaySpeed();
        beta.connect();
    }
}
