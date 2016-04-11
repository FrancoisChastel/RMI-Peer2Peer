package Peer.NetworkControler;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Objective    : Interaction between tracker and peer.
 * @author Alan BAZARSI && François CHASTEL
 */
public interface ITrackerToPeerInteraction extends Remote
{
    /**
     * Add peer in the list of peers of the peer
     * @param identifiant   : Id of the peer.
     * @param url           : URL to join this peer ;
     * @param port          : port to join this peer ;
     * @return              : true if peer were successfully add ;
     *                        false if peer were not add.
     * @throws RemoteException
     * @throws NotBoundException
     */
    public boolean addPeers(String identifiant, String url, int port) throws RemoteException, NotBoundException;


    /**
     * Delete peer in the list of peers of the peer
     * @param identifiant   : Id of the peer.
     * @return              : true if peer were successfully deleted ;
     *                        false if peer were not deleted.
     * @throws RemoteException
     * @throws NotBoundException
     */
    public boolean deletePeers(String identifiant) throws RemoteException;
}
