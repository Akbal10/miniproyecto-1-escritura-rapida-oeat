package com.agredo.escriturarapida.model;

import java.util.Random;

/**
 * Implementation of the {@link IWords} interface.
 *
 * <p>This class provides random words based on the current game level,
 * dividing them into three difficulty tiers:</p>
 *
 * <ul>
 *     <li><b>Easy (Levels 1–5):</b> Short and simple words</li>
 *     <li><b>Medium (Levels 6–15):</b> Moderate-length fantasy terms</li>
 *     <li><b>Hard (Level 16+):</b> Complex and longer words</li>
 * </ul>
 *
 * <p>The vocabulary is themed around dark fantasy to match the
 * "Abyss Trial" aesthetic of the game.</p>
 *
 * @author Omar Agredo
 * @version 1.0
 */
public class Words implements IWords {

    /** Random generator used to select words from arrays. */
    private final Random random = new Random();

    /** Easy difficulty words (levels 1–5). */
    private final String[] easyWords = {
            "Ash", "Rune", "Fog", "Soul", "Thorn",
            "Flame", "Night", "Dusk", "Crypt", "Blade"
    };

    /** Medium difficulty words (levels 6–15). */
    private final String[] mediumWords = {
            "Abyss", "Cinder", "Hollow", "Wraith", "Relic",
            "Phantom", "Omen", "Shrine", "Ember", "Eclipse"
    };

    /** Hard difficulty words (level 16 and above). */
    private final String[] hardWords = {
            "Catacomb", "Revenant", "Obsidian", "Sacrifice", "Labyrinth",
            "Harbinger", "Moonshade", "Bloodmoon", "Evernight", "Voidwalker"
    };

    /**
     * Returns a random word from the easy difficulty pool.
     *
     * <p>This method satisfies the interface contract and is used as a default
     * fallback when no level is specified.</p>
     *
     * @return a random word from the easyWords array
     */
    @Override
    public String getRandomWord() {
        return easyWords[random.nextInt(easyWords.length)];
    }

    /**
     * Returns a random word based on the current game level.
     *
     * <p>The difficulty scales as follows:</p>
     * <ul>
     *     <li>Level ≤ 5 → Easy words</li>
     *     <li>Level ≤ 15 → Medium words</li>
     *     <li>Level > 15 → Hard words</li>
     * </ul>
     *
     * @param level the current game level
     * @return a randomly selected word from the corresponding difficulty group
     */
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
