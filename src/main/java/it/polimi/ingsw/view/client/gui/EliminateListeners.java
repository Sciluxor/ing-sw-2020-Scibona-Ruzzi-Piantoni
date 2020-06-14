package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che elimina i Listeners dal JButton fornito
 * @author Scilux
 * @version 1.0
 * @since 2020/06/13
 */

public class EliminateListeners {
    /**
     * Costruttore privato della classe
     */

    private EliminateListeners() {
        throw new IllegalStateException("EliminateListeners class cannot be instantiated");
    }

    /**
     * Metodo che elimina l'ActionListener selezionato dal JButton fornito
     * @param button JButton a cui eliminare l'ActionListener
     * @param clas Classe dell'ActionListener da eliminare
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
     * Metodo che elimina il MouseListener selezionato dal JButton fornito
     * @param button JButton a cui eliminare il MouseListener
     * @param clas Classe del MouseListener da eliminare
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
     * Metodo che elimina TUTTI gli ActionListener del JButton fornito
     * @param button JButton a cui eliminare gli ActionListener
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
     * Metodo che elimina TUTTI i MouseListener del JButton fornito
     * @param button JButton a cui eliminare i MouseListener
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
