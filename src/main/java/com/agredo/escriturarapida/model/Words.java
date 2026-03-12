package com.agredo.escriturarapida.model;

import java.util.Random;

public class Words implements IWords {

    private final String[] words = {
            "controlador",
            "evento",
            "interfaz",
            "java",
            "modelo",
            "pantalla",
            "teclado",
            "vista"
    };

    private final Random random = new Random();

    @Override
    public String getRandomWord() {
        int index = random.nextInt(words.length);
        return words[index];
    }
}
