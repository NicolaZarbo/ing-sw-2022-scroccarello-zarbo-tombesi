package it.polimi.ingsw.observer;

/**The interface implemented by observable objects.
 * @see Observable*/
public interface Observer<T> {
       /**It updates all the observers' status attached to the observable.
        * @param message the message to send as notify*/
        void update(T message);
}
