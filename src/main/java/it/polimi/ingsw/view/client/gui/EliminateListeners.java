package it.polimi.ingsw.view.client.gui;

import javax.swing.*;

public class EliminateListeners {

    public static void eliminateActionClass(JButton button, Class clas){
        for (int x = 0; x < button.getActionListeners().length; x++){
            if (button.getActionListeners()[x].getClass().equals(clas))
                button.removeActionListener(button.getActionListeners()[x]);
        }
    }

    public static void eliminateMouseClass(JButton button, Class clas){
        for (int x = 0; x < button.getMouseListeners().length; x++){
            if (button.getMouseListeners()[x].getClass().equals(clas))
                button.removeMouseListener(button.getMouseListeners()[x]);
        }

    }

    public static void eliminateAllActionClass(JButton button){
        for (int x = 0; x < button.getActionListeners().length; x++){
            button.removeActionListener(button.getActionListeners()[x]);
        }
    }

    public static void eliminateAllMouseClass(JButton button){
        for (int x = 0; x < button.getMouseListeners().length; x++){
            button.removeMouseListener(button.getMouseListeners()[x]);
        }

    }
}
