package Peer.NetworkControler;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Objective    : Interaction available between two peers.
 * @author Alan BAZARSI && François CHASTEL
 */
public interface IPeerToPeerInteraction extends Remote
{
    /**
     * Add message on a peer, it can be an Object
     * @param message       : message you want to add to the peer concerned
     * @return              : true if message were successfully add;
     *                        false if message were not add.
     * @throws RemoteException
     */
    public boolean addMessage(Object message) throws RemoteException;

    /**
     * Delete message on a peer
     * @param message       :
     * @return              : true if message were successfully deleted ;
     *                        false if message were not deleted.
     * @throws RemoteException
     */
    public boolean deleteMessage(Object message) throws RemoteException;

    /**
     * Send a request to access to the chat history of the exchange.
     * @return              : messages peer have.
     * @throws RemoteException
     */
    public Object recoverMessages() throws RemoteException;
}
