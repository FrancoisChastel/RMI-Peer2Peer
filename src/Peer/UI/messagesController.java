package Peer.UI;

import Peer.staticPeer;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *  @author Alan BAZARSI && François CHASTEL
 */
public class messagesController {

    @FXML private TextField messageContent;

    @FXML private TextField destinataire;

    /**
     * Back to the login page
     * @param event     : on click of a button.
     */
    @FXML
    void retourAccueil(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.LOGIN);
    }

    /**
     * Load all the cotnent of the text field concern by message and send them though network.
     * @param event     : on click of a button.
     */
    @FXML
    void sendMessage(ActionEvent event) {
        try {
            staticPeer.networkControler.sendMessage(destinataire.getText(), messageContent.getText());
            messageContent.setText("");
            destinataire.setText("");
            refreshMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refresh message by launching a thread in order to refresh the list of message displayed.
     * @param event     : on click of a button.
     */
    @FXML
    void refreshMessage(ActionEvent event) {

        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                while (true) {
                    refreshMessage();
                    Thread.sleep(1000);
                }
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    /**
     * Clear history of message by emptying all the message of chat history.
     * @param event     : on click of a button.
     */
    @FXML
    void clearHistory(ActionEvent event) {
        ObservableList<String> messages = listeMessages.getItems();


        while (messages.size()>0)
        {   for (int cursor=0; cursor<messages.size(); cursor++) {   messages.remove(cursor);
        }
        }
    }

    /**
     * Log out client by calling network controller
     * @param event     : on click of a button.
     * @throws RemoteException
     * @throws NotBoundException
     */
    @FXML
    void logout(ActionEvent event) throws RemoteException, NotBoundException {
        staticPeer.networkControler.logout();
        clearHistory(event);
        VistaNavigator.loadVista(VistaNavigator.LOGIN);
    }

    /**
     * Save history by calling network controller
     * @param event     : on click of a button.
     */
    @FXML
    void saveHistory(ActionEvent event)
    {
        try {
            staticPeer.networkControler.saveChatHistory();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load history by calling network controller.
     * @param event     : on click of a button.
     */
    @FXML
    void loadHistory(ActionEvent event)
    {
        try {
            staticPeer.networkControler.loadChatHistory();
            clearHistory(event);
            refreshMessage(event);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recover history by calling network controller
     * @param event     : on click of a button.
     */
    @FXML
    void recoverHistory(ActionEvent event)
    {
        try {
            try {
                staticPeer.networkControler.recoverMessage();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            clearHistory(event);
            refreshMessage(event);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public ListView<String> listeMessages;


    /**
     * refreshMessage display by launching a thread of listenning.
     */
    public void refreshMessage(){
        ObservableList<String> messages;

        int differences;
        do
        {
            differences = 0;
            messages = listeMessages.getItems();

            ArrayList<String> tmpMessages = staticPeer.networkControler.getStringChat();

            for (String cursor : tmpMessages)
            {   if (!messages.contains(cursor))
            {   messages.add(cursor);
                differences++;
            }
            }
        }
        while (differences > 0);

        if (differences > 0)
        {   listeMessages.setItems(null);
            listeMessages.setItems(messages);
        }
    }
}
