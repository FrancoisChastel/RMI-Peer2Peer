package Tracker.NetworkInteraction;

import Tracker.Model.Peer;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Interface of interaction between Peers and tracker though network
 * @author Alan BAZARSI && François CHASTEL
 */
public interface IPeerToTrackerInteraction extends Remote
{
    /**
     * login of a peer will return all the peers register in the tracker.
     * @param identifiant   : id of login ;
     * @param password      : password associated to the login.
     * @return              : return all the peers with their id associated.
     * @throws RemoteException
     * @throws NotBoundException
     */
    public HashMap<String,Peer> login(String identifiant, String password) throws RemoteException, NotBoundException;

    /**
     * Logout of a peer.
     * @param identifiant   : id of login ;
     * @param password      : password associated to the login.
     * @return              : true if it was successfully logout ;
     *                        false if it was not logout because there is no peers with this id, or with this password
     *                        , if it is already logout.
     * @throws RemoteException
     * @throws NotBoundException
     */
    public boolean logout(String identifiant, String password) throws RemoteException;


    /**
     * Register a new peer in the tracker.
     * @param identifiant   : id of login ;
     * @param password      : password associated to the login ;
     * @param url           : url (host) were peer can be reach ;
     * @param port          : port were peer can be reach.
     * @return              : true if it was successfully register ;
     *                        false if it was not register because already exist.
     * @throws RemoteException
     */
    public boolean signup(String identifiant, String password, String url, int port) throws RemoteException;
}
