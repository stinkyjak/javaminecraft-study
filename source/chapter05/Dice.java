package com.javaminecraft;

import java.util.*;

class Dice {
    public static void main(String[] arguments) {
        double roll = Math.random() * 20;
        roll = Math.floor(roll);
        System.out.println("The random number is " + roll);
    }
}
