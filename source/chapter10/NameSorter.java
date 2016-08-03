package com.javaminecraft;

import java.util.*;

class NameSorter {
    public static void main(String[] arguments) {
        String names[] = { "Glimmer", "Marvel", "Rue", "Clove",
            "Thresh", "Foxface", "Cato", "Peeta", "Katniss" };
        System.out.println("The original order:");
        for (int i = 0; i < names.length; i++) {
            System.out.println(i + ": " + names[i]);
        }
        System.out.println();
        Arrays.sort(names);
        System.out.println("The new order:");
        for (int i = 0; i < names.length; i++) {
            System.out.println(i + ": " + names[i]);
        }
        System.out.println();
    }
}
