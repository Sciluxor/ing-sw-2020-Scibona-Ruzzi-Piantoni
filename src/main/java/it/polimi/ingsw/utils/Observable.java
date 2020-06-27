package it.polimi.ingsw.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * Class extended by the observable classes of the game
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class Observable<T> {

    private List<Observer<T>> observers = new ArrayList<>();

    /**
     * Function that add an observer to the list of observers of the object
     * @param observer The new observer to add
     */

    public void addObservers(Observer<T> observer){
        observers.add(observer);
    }

    /**
     * Function that remove an observer from the list of observers of the object
     * @param observer The observer to remove
     */

    public void removeObserver(Observer<T> observer){
        observers.remove(observer);
    }

    /**
     * Function that notify all the observers of the object
     * @param message The message to send to the observers
     */

    public void notify(T message){
        for(Observer<T> observer: observers){
            observer.update(message);
        }
    }

}
