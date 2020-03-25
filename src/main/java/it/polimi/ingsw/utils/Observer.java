package it.polimi.ingsw.utils;

public interface Observer<T> {

    void update(T message);

}
