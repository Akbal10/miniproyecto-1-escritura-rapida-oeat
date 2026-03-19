package com.agredo.escriturarapida.model;

/**
 * Defines the contract for word providers used in the game.
 *
 * <p>Implementations of this interface are responsible for supplying
 * words that the player must type during the game.</p>
 *
 * <p>It supports both random word generation and difficulty-based
 * word selection depending on the player's level.</p>
 *
 * @author Omar Agredo
 * @version 1.0
 */
public interface IWords {

    /**
     * Returns a random word from the default pool.
     *
     * <p>This method is typically used for initial states
     * or simple word generation.</p>
     *
     * @return a randomly selected word
     */
    String getRandomWord();

    /**
     * Returns a word based on the current game level.
     *
     * <p>The difficulty of the word should increase as the level increases.</p>
     *
     * @param level the current level of the player
     * @return a word appropriate for the given difficulty level
     */
    String getWordByLevel(int level);
}