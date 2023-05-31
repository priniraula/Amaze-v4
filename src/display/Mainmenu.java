package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import generator.Mazegen;

public class Mainmenu implements ActionListener {
    public JFrame mainMenuFrame;
    public JButton playGameButton, instructionsButton, exitButton;
    public JLabel titleLabel, versionLabel, creatorLabel;
    public JPanel buttonPanel, titlePanel, footerPanel;

    public Mainmenu () {
        drawMainMenu();
    }

    public void drawMainMenu () {
        // draw basic frame window
        mainMenuFrame = new JFrame("Amaze version 4");
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setBackground(Color.white);
        mainMenuFrame.setPreferredSize(new Dimension(600, 600));
        mainMenuFrame.setLayout(new BorderLayout());

        // draw basic JPanel window
        buttonPanel = new JPanel(new GridLayout(3, 1));
        titlePanel = new JPanel();
        footerPanel = new JPanel();

        // draw the game buttons
        playGameButton = new JButton("Play");
        instructionsButton = new JButton("Instructions");
        exitButton = new JButton("Exit");

        //add action listeners to the buttons
        playGameButton.addActionListener(this);
        instructionsButton.addActionListener(this);
        exitButton.addActionListener(this);

        // add buttons to the buttonPanel
        buttonPanel.add(playGameButton);
        buttonPanel.add(instructionsButton);
        buttonPanel.add(exitButton);

        // draw titleLabel
        titleLabel = new JLabel("Welcome to Amaze!");

        // draw footerLabel
        versionLabel = new JLabel("Amaze version 4");

        // add titleLabel to titlePanel
        titlePanel.add(titleLabel);

        // add version to footerPanel
        footerPanel.add(versionLabel);

        // add panels to the frame
        mainMenuFrame.add(buttonPanel, BorderLayout.CENTER);
        mainMenuFrame.add(titlePanel, BorderLayout.NORTH);
        mainMenuFrame.add(footerPanel, BorderLayout.SOUTH);

        // Finished adding everything to frame, now we pack it and display
        mainMenuFrame.pack();
        mainMenuFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playGameButton) {
            System.out.println("play button pressed");
            
            Game game = new Game("Game", 800, 800);
            Mazegen.run();
            game.start();
        }
        else if (e.getSource() == instructionsButton) {
            System.out.println("instructions button pressed");
        }
        else if (e.getSource() == exitButton) {
            System.out.println("exiting game");
            System.exit(0);
        }
    }
}
