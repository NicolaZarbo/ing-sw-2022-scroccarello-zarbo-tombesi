package it.polimi.ingsw.view.Gui.Scenes;


import it.polimi.ingsw.view.Gui.GUI;

public abstract class ObservableByGUI {
        protected GUI observer;

        public void addObserver(GUI observer){
            this.observer = observer;
        }

       /* public void sendMessage(InGameMessage messageToSend){
            observer.getMessageManager().update(messageToSend);
        }

        public void sendMessage(PreGameMessage messageToSend){
            observer.getMessageManager().update(messageToSend);
        }

        public void connect(String ip, String port, String username){
            observer.getMessageManager().connect(ip, port, username);
        } */
    }

