package com.javaminecraft;

class NewRoot {
    public static void main(String[] arguments) {
        int number = 100;
        if (arguments.length > 0) {
            number = Integer.parseInt(arguments[0]);
        }
        System.out.println("The square root of "
            + number
            + " is "
            + Math.sqrt(number)
        );
    }
}
