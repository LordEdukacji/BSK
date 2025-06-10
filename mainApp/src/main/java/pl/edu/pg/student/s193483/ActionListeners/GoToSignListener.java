package pl.edu.pg.student.s193483.ActionListeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoToSignListener implements ActionListener {
    private CardLayout cardLayout;
    private JPanel cards;

    public GoToSignListener(CardLayout cardLayout, JPanel cards) {
        this.cardLayout = cardLayout;
        this.cards = cards;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cardLayout.show(cards, "SIGN");
    }
}
