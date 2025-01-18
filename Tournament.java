package cwk4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tournament implements CARE {
    private final String name;
    private final List<Champion> championsReserve;
    private final List<Champion> championsTeam;
    private final List<Challenge> challenges;
    private int treasury;

    public Tournament(String name) {
        this.name = name;
        this.championsReserve = new ArrayList<>();
        this.championsTeam = new ArrayList<>();
        this.challenges = new ArrayList<>();
        this.treasury = 1000; // Set initial treasury value

    }



    public void addChampionToTeam(String championName) {
        for (Champion champion : championsReserve) {
            if (champion.name.equals(championName)) {
                if (champion.state.equals("waiting") && treasury >= champion.entryFee) {
                    treasury -= champion.entryFee;
                    champion.state = "entered";
                    championsTeam.add(champion);
                    System.out.println(champion.name + " has been added to your team.");
                } else if (champion.state.equals("entered")) {
                    System.out.println(champion.name + " is already in your team.");
                } else {
                    System.out.println("Not enough funds to add " + champion.name + " to your team.");
                }
                return;
            }
        }
        System.out.println("Champion not found or unavailable.");
    }

    public void retireChampion(String championName) {
        for (Champion champion : championsTeam) {
            if (champion.name.equals(championName)) {
                if (champion.state.equals("entered")) {
                    champion.state = "waiting";
                    treasury += champion.entryFee / 2;
                    championsTeam.remove(champion);
                    System.out.println(champion.name + " has been retired from your team.");
                } else {
                    System.out.println(champion.name + " cannot be retired.");
                }
                return;
            }
        }
        System.out.println("Champion not found in your team.");
    }

    public void meetChallenge(int challengeNumber) {
        boolean challengeExists = false;
        for (Challenge challenge : challenges) {
            if (challenge.challengeNumber == challengeNumber) {
                challengeExists = true;
                for (Champion champion : championsTeam) {
                    if (canChallenge(champion, challengeNumber)) {
                        // Simulating a random enemy with random skill level and reward
                        int enemySkillLevel = new Random().nextInt(10) + 1;
                        int reward = new Random().nextInt(401) + 100;
                        if (champion.skillLevel >= enemySkillLevel) {
                            treasury += reward;
                            System.out.println("Challenge won by " + champion.name + ". Reward: " + reward + " gulden");
                        } else {
                            if (treasury >= reward) {
                                treasury -= reward;
                                champion.state = "disqualified";
                                System.out.println("Challenge lost on skill level. Deducted " + reward + " gulden from treasury.");
                            } else {
                                treasury -= reward;
                                System.out.println("Challenge is lost and Rare Earth completely defeated.");
                                return;
                            }
                        }
                        return;
                    }
                }
                System.out.println("No suitable champion available.");
                break;
            }
        }
        if (!challengeExists) {
            System.out.println("No such challenge.");
        }
    }

    public void displayState() {
        System.out.println("Vizier: " + name);
        System.out.println("Treasury: " + treasury + " gulden");
        System.out.println("Champions in Reserve:");
        for (Champion champion : championsReserve) {
            System.out.println(champion.name + " - Skill Level: " + champion.skillLevel + ", Entry Fee: " +
                    champion.entryFee + " gulden, State: " + champion.state);
        }
        System.out.println("Champions in Team:");
        for (Champion champion : championsTeam) {
            System.out.println(champion.name + " - Skill Level: " + champion.skillLevel + ", Entry Fee: " +
                    champion.entryFee + " gulden, State: " + champion.state);
        }
        System.out.println("Available Challenges:");
        for (Challenge challenge : challenges) {
            System.out.println("Challenge " + challenge.challengeNumber + ": " + challenge.challengeType +
                    " challenge against " + challenge.enemy + " - Skill Required: " + challenge.skillRequired +
                    ", Reward: " + challenge.reward + " gulden");
        }
    }



    public void loadSampleData() {
        championsReserve.add(new Champion("Ganfrank", "Wizard", 7, 400));
        championsReserve.add(new Champion("Rudolf", "Wizard", 6, 400));
        championsReserve.add(new Champion("Elblond", "Warrior", 1, 150));
        championsReserve.add(new Champion("Flimsi", "Warrior", 2, 200));
        championsReserve.add(new Champion("Drabina", "Dragon", 7, 500));
        championsReserve.add(new Champion("Golum", "Dragon", 7, 500));
        championsReserve.add(new Champion("Argon", "Warrior", 9, 900));
        championsReserve.add(new Champion("Neon", "Wizard", 2, 300));
        championsReserve.add(new Champion("Xenon", "Dragon", 7, 500));
        championsReserve.add(new Champion("Atlanta", "Warrior", 5, 500));
        championsReserve.add(new Champion("Krypton", "Wizard", 8, 300));
        championsReserve.add(new Champion("Hedwig", "Wizard", 1, 400));

        // Sample data for challenges
        challenges.add(new Challenge(1, "Magic", "Borg", 3, 100));
        challenges.add(new Challenge(2, "Fight", "Huns", 3, 120));
        challenges.add(new Challenge(3, "Mystery", "Ferengi", 3, 150));
        challenges.add(new Challenge(4, "Magic", "Vandal", 9, 200));
        challenges.add(new Challenge(5, "Mystery", "Borg", 7, 90));
        challenges.add(new Challenge(6, "Fight", "Goth", 8, 45));
        challenges.add(new Challenge(7, "Magic", "Frank", 10, 200));
        challenges.add(new Challenge(8, "Fight", "Sith", 10, 170));
        challenges.add(new Challenge(9, "Mystery", "Cardashian", 9, 300));
        challenges.add(new Challenge(10, "Fight", "Jute", 2, 300));
        challenges.add(new Challenge(11, "Magic", "Celt", 2, 250));
        challenges.add(new Challenge(12, "Mystery", "Celt", 1, 250));
        challenges.add(new Challenge(13, "Magic", "Spuds", 1, 100));

    }

    public boolean canChallenge(Champion champion, int challengeNumber) {
        for (Challenge challenge : challenges) {
            if (challenge.challengeNumber == challengeNumber) {
                if (challenge.challengeType.equals("Magic") && champion.type.equals("Wizard")) {
                    return true;
                } else if (challenge.challengeType.equals("Fight") && (champion.type.equals("Warrior") || champion.type.equals("Dragon"))) {
                    return true;
                } else if (challenge.challengeType.equals("Mystery") && (champion.type.equals("Wizard") || champion.type.equals("Dragon"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

}
