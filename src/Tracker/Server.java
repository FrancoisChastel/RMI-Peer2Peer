package Tracker;

import Tracker.NetworkControler.TrackerNetworkControler;
import Tracker.NetworkInteraction.IPeerToTrackerInteraction;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

/**
 * Server example called ServeurAuthentification on port 10000
 * @author Alan BAZARSI && François CHASTEL
 */
public class Server {
    static int SERVEUR_PORT = 10000;
    static String RMI_SERVER_NAME = "ServeurAuthentification";

    public static void main(String[] argv) {
        try {
            System.out.println("["+(new Date()).toString()+"]"+"Launching server.... at "+(InetAddress.getLocalHost()).getCanonicalHostName()+ " on "+SERVEUR_PORT);
            IPeerToTrackerInteraction skeleton = (IPeerToTrackerInteraction) UnicastRemoteObject.exportObject(new TrackerNetworkControler(), SERVEUR_PORT);
            Registry registry = LocateRegistry.createRegistry(SERVEUR_PORT);
            registry.rebind(RMI_SERVER_NAME, skeleton);
            System.out.println("["+(new Date()).toString()+"]"+"Server ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
