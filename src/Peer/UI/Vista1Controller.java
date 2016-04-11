package Peer.UI;

import Peer.staticPeer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.script.ScriptException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Controller de la Vista1
 * @author Alan BAZARSI && François CHASTEL
 */
public class Vista1Controller {
    @FXML private TextField login;

    @FXML private TextField password;

    @FXML private TextField port;

    @FXML private TextField serverPort;

    @FXML private TextField serverName;

    @FXML private TextField serverIp;

    /**
     * Load messenger on click of a button.
     *
     * @param event event that launching messenger.
     */
    @FXML
    void loginAction(ActionEvent event) throws RemoteException, NotBoundException {
        VistaNavigator.loadVista(VistaNavigator.MESSENGER);
    }

    /**
     * Sign up and start peer by calling network controler
     *
     * @param event event that launching messenger.
     */
    @FXML
    void signUpAction(ActionEvent event) throws RemoteException, NotBoundException, AlreadyBoundException, UnknownHostException {
        staticPeer.networkControler.setIdentifiant(login.getText());
        staticPeer.networkControler.setMotDePasse(password.getText());
        staticPeer.networkControler.startPeer(Integer.parseInt(port.getText()));
        staticPeer.networkControler.login(Integer.parseInt(port.getText()), serverName.getText(), serverIp.getText(), Integer.parseInt(serverPort.getText()));
    }


    public void initialize() throws IOException, ScriptException, NoSuchMethodException, InterruptedException
    {
    }

}
