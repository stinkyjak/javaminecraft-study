package com.javaminecraft;

import java.net.*;

public class PageCatalog {
    public static void main(String[] arguments) {
        HomePage[] catalog = new HomePage[5];
        try {
            catalog[0] = new HomePage("Minecraft",
                "http://www.minecraft.net", "software");
            catalog[1] = new HomePage("Wiki",
                "http://minecraft.gamepedia.com", "reference");
            catalog[2] = new HomePage("Facebook",
                "https://www.facebook.com/minecraft", "social");
            catalog[3] = new HomePage("Programming",
                "http://www.javaminecraft.com", "reference");
            catalog[4] = new HomePage("Company",
                "mojang.com");
            for (int i = 0; i < catalog.length; i++) {
                System.out.println(catalog[i].owner + ": " +
                    catalog[i].address + " -- " +
                    catalog[i].category);
            }
        } catch (MalformedURLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
