package Tracker.Model;

import Peer.NetworkControler.ITrackerToPeerInteraction;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Data structure in order to store peer.
 * @author Alan BAZARSI && François CHASTEL
 */
public class Peers
{   HashMap<String,Peer> peers;

    public Peers() {
        this.peers = new HashMap<String, Peer>();
    }

    public HashMap<String, Peer> getPeers() {
        return peers;
    }

    public void setPeers(HashMap<String, Peer> peers) {
        this.peers = peers;
    }

    public HashMap<String,Peer> login(String identifiant, String password) throws RemoteException, NotBoundException {
        System.out.println("["+(new Date()).toString()+"]" +identifiant+ " is attempting a connexion");
        if (!isMatching(identifiant, password))
        {   System.out.println("["+(new Date()).toString()+"]Connexion from "+identifiant+ " refused");
            return new HashMap<String, Peer>();
        }

        if (peers.get(identifiant).isConnected())
        {   System.out.println("["+(new Date()).toString()+"]"+identifiant+ " is already online");
            return new HashMap<String, Peer>();
        }

        HashMap<String,Peer> tmpPeers = new HashMap<String, Peer>();

        peers.get(identifiant).setConnected(true);
        Registry registry = LocateRegistry.getRegistry(peers.get(identifiant).getPort());
        ITrackerToPeerInteraction stub = (ITrackerToPeerInteraction) registry.lookup(identifiant);
        peers.get(identifiant).setStub(stub);

        System.out.println("["+(new Date()).toString()+"]creation stub " +peers.get(identifiant).getPort()+ " "+identifiant);


        Set cles = peers.keySet();
        for (Object cle : cles) {
            if (peers.get(cle).isConnected() && !cle.toString().equals(identifiant))
            {
                tmpPeers.put(cle.toString(), peers.get(cle));
                System.out.println("[" + (new Date()).toString() + "]" + "-- sending " + identifiant + " at " + peers.get(identifiant).getPort() + "- to "
                        + cle.toString() + " at " + peers.get(cle).getPort());
                peers.get(cle).getStub().addPeers(identifiant,
                        peers.get(identifiant).getUrl(),
                        peers.get(identifiant).getPort());
            }
        }

        System.out.println("["+(new Date()).toString()+"]"+identifiant+ " just connect and peers just sent --");
        return  tmpPeers;
    }

    /**
     * Set isConnected to false if id and password are coherent in our structure.
     * @param identifiant   : id of the peer that want to log out
     * @param password      : password associated to the id that want to log out
     * @return              : true if he was successfully logout;
     *                        false if he was not logout.
     * @throws RemoteException
     */
    public boolean logout(String identifiant, String password) throws RemoteException
    {
        if (!isMatching(identifiant,password))
        {   return false;
        }

        peers.get(identifiant).setConnected(false);
        peers.get(identifiant).setStub(null);

        Set cles = peers.keySet();
        for (Object cle : cles) {
            if (peers.get(cle).isConnected() && !cle.toString().equals(peers.get(identifiant)))
            {   peers.get(cle).getStub().deletePeers(identifiant);
            }
        }

        System.out.println("["+(new Date()).toString()+"]"+identifiant+ " just logout --");
        return true;
    }

    /**
     * Register a new peer if it is not already connected.
     * @param identifiant   : id associated to the peer ;
     * @param password      : password associated to the peer ;
     * @param url           : url (host) were you can reach the peer ;
     * @param port          : port were you can reach the peer.
     * @return
     */
    public boolean signup(String identifiant, String password, String url, int port)
    {   if (peers.containsKey(identifiant) && !isMatching(identifiant,password))
        {   System.out.println("["+(new Date()).toString()+"]"+identifiant+ " already signed -- "+url+ ":"+port);
            return false;
        }

        if (peers.containsKey(identifiant))
        {   System.out.println("["+(new Date()).toString()+"]"+identifiant+ " url and port were updated -- "+url+ ":"+port);
            peers.get(identifiant).setUrl(url);
            peers.get(identifiant).setPort(port);

            return true;
        }

        peers.put(identifiant,new Peer(password,url,port));

        System.out.println("["+(new Date()).toString()+"]"+identifiant+ " just signup --");

        return true;
    }

    /**
     * Check if password match to the id in our peers.
     * @param identifiant   : id of the peer ;
     * @param password      : password associated to the peer.
     * @return
     */
    public boolean isMatching(String identifiant,String password)
    {   if (!peers.containsKey(identifiant))
        {   return false;
        }
        if (!peers.get(identifiant).isMatching(password))
        {   return false;
        }

        return true;
    }

}
