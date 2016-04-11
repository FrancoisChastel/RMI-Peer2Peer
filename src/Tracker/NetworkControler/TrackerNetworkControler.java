package Tracker.NetworkControler;

import Tracker.Model.Peer;
import Tracker.Model.Peers;
import Tracker.NetworkInteraction.IPeerToTrackerInteraction;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Tracker's network controller.
 * @author Alan BAZARSI && François CHASTEL
 */
public class TrackerNetworkControler implements IPeerToTrackerInteraction
{   Peers peers;

    public TrackerNetworkControler()
    {
        this.peers = new Peers();
    }

    public Peers getPeers() {
        return peers;
    }

    public void setPeers(Peers peers) {
        this.peers = peers;
    }

    @Override
    public HashMap<String,Peer> login(String identifiant, String password) throws RemoteException, NotBoundException {
        return peers.login(identifiant,password);
    }

    @Override
    public boolean logout(String identifiant, String password) throws RemoteException {
        return peers.logout(identifiant,password);
    }

    @Override
    public boolean signup(String identifiant, String password, String url, int port) throws RemoteException {
        return peers.signup(identifiant,password,url,port);
    }
}
