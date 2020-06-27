package it.polimi.ingsw.utils;

/**
 * Interface for the observers
 */

public interface Observer<T> {

    /**
     * Function to do an update when they receive a notification from the object they are observing
     * @param message Message received from the object they are observing
     */

    void update(T message);

}
