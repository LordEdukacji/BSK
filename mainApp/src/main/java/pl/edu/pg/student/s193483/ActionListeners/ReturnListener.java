package pl.edu.pg.student.s193483.ActionListeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class ReturnListener
 * @brief Listener for the return button
 */
public class ReturnListener implements ActionListener {
    private CardLayout cardLayout;  //!< Card layout of the app, for changing cards
    private JPanel cards;           //!< Cards for the layout

    /**
     * @brief Default constructor
     * @param cardLayout Card layout of the app
     * @param cards Cards for the layout
     */
    public ReturnListener(CardLayout cardLayout, JPanel cards) {
        this.cardLayout = cardLayout;
        this.cards = cards;
    }

    /**
     * @brief Actions to be performed upon clicking the return button
     * @details Goes to the main menu
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        cardLayout.show(cards, "MAIN");
    }
}
