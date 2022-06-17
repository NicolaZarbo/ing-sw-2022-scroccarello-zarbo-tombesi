package it.polimi.ingsw.observer;

import java.util.ArrayList;

public class Observable<T> {

    private final ArrayList<Observer<T>>  observers = new ArrayList<>();

    /**It adds an observer to the observable.
     * @param obs the observer added to the observable*/
    public void addObserver(Observer<T> obs){
        synchronized (observers) {
            observers.add(obs);
        }
    }
    /**It removes an observer from the observable.
     * @param obs the observer to remove from the observable*/
    public void removeObserver(Observer<T> obs){
        synchronized (observers) {
            observers.remove(obs);
        }
    }

    /**It notifies all the observers attached at the observable.
     * @param message message sent as notify*/
    protected void notify(T message){
        synchronized (observers) {
            for(Observer<T> observer : observers){
                observer.update(message);
            }
        }
    }

}
