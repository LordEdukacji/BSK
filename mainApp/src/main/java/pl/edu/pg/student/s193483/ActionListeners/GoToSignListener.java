package pl.edu.pg.student.s193483.ActionListeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class GoToSignListener
 * @brief Listener for the button which goes to the sign screen
 */
public class GoToSignListener implements ActionListener {
    private CardLayout cardLayout;  //!< Card layout of the app, for changing cards
    private JPanel cards;           //!< Cards for the layout

    /**
     * @brief Default constructor
     * @param cardLayout Card layout of the app
     * @param cards Cards for the layout
     */
    public GoToSignListener(CardLayout cardLayout, JPanel cards) {
        this.cardLayout = cardLayout;
        this.cards = cards;
    }

    /**
     * @brief Actions to be performed upon clicking the button which goes to the sign screen
     * @details Goes to the sign screen
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        cardLayout.show(cards, "SIGN");
    }
}
