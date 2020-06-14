package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that removes Listeners from the provided JButton
 * @author Scilux
 * @version 1.0
 * @since 2020/06/13
 */

public class EliminateListeners {
    /**
     * Private class builder
     */

    private EliminateListeners() {
        throw new IllegalStateException("EliminateListeners class cannot be instantiated");
    }

    /**
     * Method that remove the ActionListener selected from the supplied JButton
     * @param button JButton to delete the ActionListener
     * @param clas Class of the ActionListener to be deleted
     */

    public static void eliminateActionClass(JButton button, Class clas){
        List<ActionListener> toRemove = new ArrayList<>();
        for (int x = 0; x < button.getActionListeners().length; x++){
            if (button.getActionListeners()[x].getClass().equals(clas))
                toRemove.add(button.getActionListeners()[x]);
        }
        for (ActionListener action : toRemove){
            button.removeActionListener(action);
        }
    }

    /**
     * Method that remove the MouseListener selected from the supplied JButton
     * @param button JButton to delete the MouseListener
     * @param clas Class of the MouseListener to be deleted
     */

    public static void eliminateMouseClass(JButton button, Class clas){
        List<MouseListener> toRemove = new ArrayList<>();
        for (int x = 0; x < button.getMouseListeners().length; x++){
            if (button.getMouseListeners()[x].getClass().equals(clas))
                toRemove.add(button.getMouseListeners()[x]);
        }
        for (MouseListener mouse : toRemove){
            button.removeMouseListener(mouse);
        }

    }

    /**
     * Method that remove ALL the ActionListener from the supplied JButton
     * @param button JButton to delete the ActionListener
     */

    public static void eliminateAllActionClass(JButton button){
        List<ActionListener> toRemove = new ArrayList<>();
        for (int x = 0; x < button.getActionListeners().length; x++){
            toRemove.add(button.getActionListeners()[x]);
        }
        for (ActionListener action : toRemove){
            button.removeActionListener(action);
        }
    }

    /**
     * Method that remove ALL the MouseListener from the supplied JButton
     * @param button JButton to delete the MouseListener
     */

    public static void eliminateAllMouseClass(JButton button){
        List<MouseListener> toRemove = new ArrayList<>();
        for (int x = 0; x < button.getMouseListeners().length; x++){
            toRemove.add(button.getMouseListeners()[x]);
        }
        for (MouseListener mouse : toRemove){
            button.removeMouseListener(mouse);
        }
    }
}
