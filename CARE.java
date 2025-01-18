package cwk4;


/**
 * The CARE interface specifies the methods required to interact with the player (Vizier).
 * It defines the functional requirements of the system without concern for implementation details.
 */
interface CARE {

    /**
     * Loads sample data for champions and challenges.
     * This method initializes the game with predefined sample data for champions and challenges.
     */
    void loadSampleData();

    /**
     * Adds a champion to the player's team.
     *
     * @param championName The name of the champion to add.
     *                     This method adds the specified champion to the player's team.
     */
    void addChampionToTeam(String championName);

    /**
     * Retires a champion from the player's team.
     *
     * @param championName The name of the champion to retire.
     *                     This method removes the specified champion from the player's team.
     */
    void retireChampion(String championName);

    /**
     * Meets a challenge with the player's team.
     *
     * @param challengeNumber The number of the challenge to meet.
     *                        This method attempts to meet the specified challenge with the player's team.
     */
    void meetChallenge(int challengeNumber);

    /**
     * Displays the current state of the game.
     * This method presents the current state of the game including the Vizier's name, treasury, champions in reserve,
     * champions in the team, and available challenges.
     */
    void displayState();
}
