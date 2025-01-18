package cwk4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Entity {
    String name;
    int skillLevel;
    int entryFee;

    public Entity(String name, int skillLevel, int entryFee) {
        this.name = name;
        this.skillLevel = skillLevel;
        this.entryFee = entryFee;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", skillLevel=" + skillLevel +
                ", entryFee=" + entryFee +
                '}';
    }
}

class Champion extends Entity {
    String type;
    String state;

    public Champion(String name, String type, int skillLevel, int entryFee) {
        super(name, skillLevel, entryFee);
        this.type = type;
        this.state = "waiting";
    }

    @Override
    public String toString() {
        return "Champion{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", skillLevel=" + skillLevel +
                ", entryFee=" + entryFee +
                ", state='" + state + '\'' +
                '}';
    }
}

class Challenge {
    int challengeNumber;
    String challengeType;
    String enemy;
    int skillRequired;
    int reward;

    public Challenge(int challengeNumber, String challengeType, String enemy, int skillRequired, int reward) {
        this.challengeNumber = challengeNumber;
        this.challengeType = challengeType;
        this.enemy = enemy;
        this.skillRequired = skillRequired;
        this.reward = reward;
    }
    public String toString() {
        return "Challenge{" +
                "challengeNumber=" + challengeNumber +
                ", challengeType='" + challengeType + '\'' +
                ", enemy='" + enemy + '\'' +
                ", skillRequired=" + skillRequired +
                ", reward=" + reward +
                '}';
    }
}


class RareEarthVizier implements CARE {
    String name;
    int treasury;
    List<Champion> championsReserve;
    List<Champion> championsTeam;
    List<Challenge> challenges;

    public RareEarthVizier(String name) {
        this.name = name;
        this.treasury = 1000;
        this.championsReserve = new ArrayList<>();
        this.championsTeam = new ArrayList<>();
        this.challenges = new ArrayList<>();
    }

    public String toString() {
        return "RareEarthVizier{" +
                "name='" + name + '\'' +
                ", treasury=" + treasury +
                ", championsReserve=" + championsReserve +
                ", championsTeam=" + championsTeam +
                ", challenges=" + challenges +
                '}';
    }

    public void loadSampleData() {
        // Sample data for champions
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

    public void displayState() {
        System.out.println("Vizier: " + name);
        System.out.println("Treasury: " + treasury + " gulden");
        System.out.println("Champions in Reserve:");
        for (Champion champion : championsReserve) {
            System.out.println(champion.name + " - Skill Level: " + champion.skillLevel + ", Entry Fee: " + champion.entryFee +
                    " gulden, State: " + champion.state);
        }
        System.out.println("Champions in Team:");
        for (Champion champion : championsTeam) {
            System.out.println(champion.name + " - Skill Level: " + champion.skillLevel + ", Entry Fee: " + champion.entryFee +
                    " gulden, State: " + champion.state);
        }
        System.out.println("Available Challenges:");
        for (Challenge challenge : challenges) {
            System.out.println("Challenge " + challenge.challengeNumber + ": " + challenge.challengeType + " challenge against " +
                    challenge.enemy + " - Skill Required: " + challenge.skillRequired + ", Reward: " + challenge.reward + " gulden");
        }
    }

}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name as Vizier: ");
        String vizierName = scanner.nextLine();

        CARE vizier =  new RareEarthVizier(vizierName);
        vizier.loadSampleData();

        boolean gameRunning = true;
        while (gameRunning) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add champion to team");
            System.out.println("2. Retire champion");
            System.out.println("3. Meet challenge");
            System.out.println("4. Display game state");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the name of the champion to add to your team: ");
                    String championToAdd = scanner.nextLine();
                    vizier.addChampionToTeam(championToAdd);
                    break;
                case 2:
                    System.out.print("Enter the name of the champion to retire: ");
                    String championToRetire = scanner.nextLine();
                    vizier.retireChampion(championToRetire);
                    break;
                case 3:
                    System.out.print("Enter the challenge number: ");
                    int challengeNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    vizier.meetChallenge(challengeNumber);
                    break;
                case 4:
                    vizier.displayState();
                    break;
                case 5:
                    gameRunning = false;
                    System.out.println("Exiting the game. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
        scanner.close();
    }
}