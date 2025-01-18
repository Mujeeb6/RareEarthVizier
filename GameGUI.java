package cwk4;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame implements ActionListener {
    private RareEarthVizier vizier;

    private final JTextField vizierNameField; // New text field for vizier name input

    private final JTextField championNameField;
    private final JTextField challengeNumberField;
    private final JTextArea gameStateTextArea;

    public GameGUI() {
        super("Rare Earth Vizier Game");

        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel vizierNameLabel = new JLabel("Vizier Name:");
        vizierNameLabel.setBounds(20, 20, 120, 25);
        mainPanel.add(vizierNameLabel);

        vizierNameField = new JTextField();
        vizierNameField.setBounds(150, 20, 200, 25);
        mainPanel.add(vizierNameField);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setBounds(370, 20, 200, 25);
        startGameButton.addActionListener(this);
        mainPanel.add(startGameButton);

        JLabel championLabel = new JLabel("Champion Name:");
        championLabel.setBounds(20, 60, 120, 25);
        mainPanel.add(championLabel);

        championNameField = new JTextField();
        championNameField.setBounds(150, 60, 200, 25);
        mainPanel.add(championNameField);

        JButton addChampionButton = new JButton("Add Champion to Team");
        addChampionButton.setBounds(370, 60, 200, 25);
        addChampionButton.addActionListener(this);
        mainPanel.add(addChampionButton);

        JLabel challengeLabel = new JLabel("Challenge Number:");
        challengeLabel.setBounds(20, 100, 120, 25);
        mainPanel.add(challengeLabel);

        challengeNumberField = new JTextField();
        challengeNumberField.setBounds(150, 100, 200, 25);
        mainPanel.add(challengeNumberField);

        JButton meetChallengeButton = new JButton("Meet Challenge");
        meetChallengeButton.setBounds(370, 100, 200, 25);
        meetChallengeButton.addActionListener(this);
        mainPanel.add(meetChallengeButton);

        gameStateTextArea = new JTextArea();
        gameStateTextArea.setEditable(false);
        gameStateTextArea.setBounds(20, 140, 550, 500);
        mainPanel.add(gameStateTextArea);

        JButton displayStateButton = new JButton("Display Game State");
        displayStateButton.setBounds(200, 650, 200, 25);
        displayStateButton.addActionListener(this);
        mainPanel.add(displayStateButton);

        JButton retireChampionButton = new JButton("Retire Champion");
        retireChampionButton.setBounds(20, 650, 150, 25);
        retireChampionButton.addActionListener(this);
        mainPanel.add(retireChampionButton);

        JButton quitButton = new JButton("Quit Game");
        quitButton.setBounds(420, 650, 150, 25);
        quitButton.addActionListener(this);
        mainPanel.add(quitButton);

        add(mainPanel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start Game")) {
            String vizierName = vizierNameField.getText().trim();
            if (!vizierName.isEmpty()) {
                vizier = new RareEarthVizier(vizierName);
                vizier.loadSampleData();
                updateGameState();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a name for the Vizier.");
            }
        } else if (e.getActionCommand().equals("Add Champion to Team")) {
            String championName = championNameField.getText().trim();
            if (!championName.isEmpty()) {
                vizier.addChampionToTeam(championName);
                updateGameState();
            }
        } else if (e.getActionCommand().equals("Meet Challenge")) {
            String challengeNumberStr = challengeNumberField.getText().trim();
            if (!challengeNumberStr.isEmpty()) {
                int challengeNumber = Integer.parseInt(challengeNumberStr);
                vizier.meetChallenge(challengeNumber);
                updateGameState();
            }
        } else if (e.getActionCommand().equals("Display Game State")) {
            updateGameState();
        } else if (e.getActionCommand().equals("Retire Champion")) {
            String championName = championNameField.getText().trim();
            if (!championName.isEmpty()) {
                vizier.retireChampion(championName);
                updateGameState();
            }
        } else if (e.getActionCommand().equals("Quit Game")) {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the game?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void updateGameState() {
        gameStateTextArea.setText("");
        gameStateTextArea.append("Vizier: " + vizier.name + "\n");
        gameStateTextArea.append("Treasury: " + vizier.treasury + " gulden\n");
        gameStateTextArea.append("Champions in Reserve:\n");
        for (Champion champion : vizier.championsReserve) {
            gameStateTextArea.append(champion.name + " - Skill Level: " + champion.skillLevel + ", Entry Fee: " + champion.entryFee +
                    " gulden, State: " + champion.state + "\n");
        }
        gameStateTextArea.append("Champions in Team:\n");
        for (Champion champion : vizier.championsTeam) {
            gameStateTextArea.append(champion.name + " - Skill Level: " + champion.skillLevel + ", Entry Fee: " + champion.entryFee +
                    " gulden, State: " + champion.state + "\n");
        }
        gameStateTextArea.append("Available Challenges:\n");
        for (Challenge challenge : vizier.challenges) {
            gameStateTextArea.append("Challenge " + challenge.challengeNumber + ": " + challenge.challengeType + " challenge against " +
                    challenge.enemy + " - Skill Required: " + challenge.skillRequired + ", Reward: " + challenge.reward + " gulden\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUI gameGUI = new GameGUI();
            gameGUI.setVisible(true);
        });
    }
}
