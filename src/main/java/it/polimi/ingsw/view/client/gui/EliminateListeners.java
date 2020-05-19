package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class EliminateListeners {

    private EliminateListeners() {
        throw new IllegalStateException("BackgroundButton class cannot be instantiated");
    }

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

    public static void eliminateAllActionClass(JButton button){
        List<ActionListener> toRemove = new ArrayList<>();
        for (int x = 0; x < button.getActionListeners().length; x++){
            toRemove.add(button.getActionListeners()[x]);
        }
        for (ActionListener action : toRemove){
            button.removeActionListener(action);
        }
    }

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
