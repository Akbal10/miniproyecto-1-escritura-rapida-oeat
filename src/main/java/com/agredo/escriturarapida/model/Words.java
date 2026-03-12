package com.agredo.escriturarapida.model;

import java.util.Random;

/**
 * Implementation of the IWords interface with Cyberpunk/Gamer vocabulary.
 * Manages three difficulty levels to increase game challenge.
 *
 * @author Tu Nombre
 * @version 1.1
 */
public class Words implements IWords {
    private final Random random = new Random();

    // Nivel 1-5: Palabras cortas y directas
    private final String[] easyWords = {
            "RAM", "CPU", "Ping", "Link", "Data", "Byte", "Core", "Grid", "Neon", "Code"
    };

    // Nivel 6-15: Términos técnicos y de hardware
    private final String[] mediumWords = {
            "Hacker", "Kernel", "Script", "Buffer", "Binary", "Pixel", "Neural", "Router", "Matrix", "Sensor"
    };

    // Nivel 16+: Conceptos complejos y "Cyber-slang"
    private final String[] hardWords = {
            "Cybernetics", "Mainframe", "Encryption", "Overclock", "Bandwidth", "Interface", "Algorithm", "Bitstream", "Virtualization", "Database"
    };

    /**
     * Overloaded method to get a word based on the current level.
     * @param level The current game level.
     * @return A word selected from the appropriate difficulty pool.
     */
    public String getWordByLevel(int level) {
        if (level <= 5) {
            return easyWords[random.nextInt(easyWords.length)];
        } else if (level <= 15) {
            return mediumWords[random.nextInt(mediumWords.length)];
        } else {
            return hardWords[random.nextInt(hardWords.length)];
        }
    }

    /**
     * Default implementation for IWords interface.
     * @return A random word from the easy pool.
     */
    @Override
    public String getRandomWord() {
        return getWordByLevel(1);
    }
}
