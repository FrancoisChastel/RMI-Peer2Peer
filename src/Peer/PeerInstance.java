package Peer;

import Peer.NetworkControler.NetworkControler;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Date;

/**
 * Example of ChatServiceBackup on localHost
 * @author Alan BAZARSI && François CHASTEL
 */
public class PeerInstance {
    public static void main(String [ ] args) throws IOException, ClassNotFoundException, NotBoundException, AlreadyBoundException {
        int serveurPort = 10000;
        int clientPort = 10002;
        String serveurName = "ServeurAuthentification";
        String ipServeur = "localhost";

        NetworkControler networkControler = new NetworkControler("ChatServicesBackup", "sudo");
        networkControler.startPeer(clientPort);

        System.out.println("[" + (new Date()).toString() + "]" + "connecting to the server ....");
        networkControler.login(clientPort, serveurName, ipServeur, serveurPort);

        networkControler.launchingPeer(clientPort);

    }
}