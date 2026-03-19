package com.agredo.escriturarapida.model;

import java.util.Random;

public class Words implements IWords {

    private final Random random = new Random();

    private final String[] easyWords = {
            "Ash", "Rune", "Fog", "Soul", "Thorn",
            "Flame", "Night", "Dusk", "Crypt", "Blade"
    };

    private final String[] mediumWords = {
            "Abyss", "Cinder", "Hollow", "Wraith", "Relic",
            "Phantom", "Omen", "Shrine", "Ember", "Eclipse"
    };

    private final String[] hardWords = {
            "Catacomb", "Revenant", "Obsidian", "Sacrifice", "Labyrinth",
            "Harbinger", "Moonshade", "Bloodmoon", "Evernight", "Voidwalker"
    };

    @Override
    public String getRandomWord() {
        return easyWords[random.nextInt(easyWords.length)];
    }

    @Override
    public String getWordByLevel(int level) {
        if (level <= 5) {
            return easyWords[random.nextInt(easyWords.length)];
        } else if (level <= 15) {
            return mediumWords[random.nextInt(mediumWords.length)];
        } else {
            return hardWords[random.nextInt(hardWords.length)];
        }
    }
}
