package com.javaminecraft;

class Credits {
    public static void main(String[] args) {
        // set up game information
        String title = "Minecraft";
        int year = 2009;
        String creator = "Markus 'Notch' Persson";
        String developer = " Jens 'Jeb' Bergensten";
        String music = " Daniel 'C418' Rosenfeld";
        String art = "Kristoffer Zetterstrand";
        // display information
        System.out.println(title + " (" + year + ")\n" +
            "A " + creator + " game.\n\n" +
            "Developer\t" + developer + "\n" +
            "Music\t" + music + "\n" +
            "Art\t" + art);
    }
}
