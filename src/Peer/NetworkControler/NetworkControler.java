package Peer.NetworkControler;

import Peer.IOPersistance.IOPersistance;
import Peer.Model.ChatHistory;
import Peer.Model.IMessage;
import Peer.Model.TextualMessage;
import Tracker.Model.Peer;
import Tracker.NetworkInteraction.IPeerToTrackerInteraction;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Alan BAZARSI && François CHASTEL
 */
public class NetworkControler implements IPeerToPeerInteraction, ITrackerToPeerInteraction{
    String identifiant;
    String motDePasse;
    ChatHistory chat;
    HashMap<String,IPeerToPeerInteraction> peers;
    IPeerToTrackerInteraction tracker;

    /** Constructor **/
    public NetworkControler(String identifiant,String motDePasse)
    {
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
        this.chat = new ChatHistory();
        this.peers = new HashMap<String,IPeerToPeerInteraction>();
    }

    /** Getters and setters **/
    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public ChatHistory getChat() {
        return chat;
    }

    public void setChat(ChatHistory chat) {
        this.chat = chat;
    }

    public HashMap<String,IPeerToPeerInteraction> getPeers() {
        return peers;
    }

    public void setPeers(HashMap<String,IPeerToPeerInteraction> peers) {
        this.peers = peers;
    }

    public IPeerToTrackerInteraction getTracker() {
        return tracker;
    }

    public void setTracker(IPeerToTrackerInteraction tracker) {
        this.tracker = tracker;
    }

    public Registry startPeer(int clientPort) throws RemoteException, AlreadyBoundException
    {
        System.out.println("["+(new Date()).toString()+"]"+"-- Launching Peer : " +this.getIdentifiant()+ " at "+clientPort);

        ITrackerToPeerInteraction skeleton = (ITrackerToPeerInteraction) UnicastRemoteObject.exportObject(this, clientPort);
        Registry registry = LocateRegistry.createRegistry(clientPort);
        registry.bind(this.getIdentifiant(), skeleton);

        System.out.println("["+(new Date()).toString()+"]"+"Peer successfully launched --");
        return registry;
    }

    public Registry launchingPeer(int clientPort) throws RemoteException
    {
        /**System.out.println("["+(new Date()).toString()+"]"+"-- Launching Peer messenger : " +this.getIdentifiant());
        IPeerToPeerInteraction skeleton = (IPeerToPeerInteraction) UnicastRemoteObject.exportObject(this, clientPort);
        Registry registry = LocateRegistry.createRegistry(clientPort);
        registry.rebind(this.getIdentifiant()+"_Peers", skeleton);

        System.out.println("["+(new Date()).toString()+"]"+"Peer messenger successfully launched --");**/
        return null;
    }

    /**
     * Log Peer to a specific Tracker, he will use registry to check for IPeerToTrackerInteraction and set it to a
     * tracker. He will track the state of the connexion (in progress, succeed, peers recieved, fail)
     * @param port          : port of the peer ;
     * @param serveurName   : name of the specific server ;
     * @param serveurIP     : IP of the specific server ;
     * @param serveurPort   : port of the specific server ;
     * @return              : true if connexion succeed ;
     *                        false if connexion failed.
     * @throws RemoteException
     * @throws NotBoundException
     * @throws UnknownHostException
     */
    public boolean login(int port, String serveurName, String serveurIP , int serveurPort) throws RemoteException, NotBoundException, UnknownHostException {   System.out.println("["+(new Date()).toString()+"]"+"--Login to "+serveurName+" at "+serveurPort);
        Registry registryServeur = LocateRegistry.getRegistry(serveurIP,serveurPort);
        this.setTracker((IPeerToTrackerInteraction) registryServeur.lookup(serveurName));

        HashMap<String,Peer> tmp;

        if (this.tracker.signup(identifiant, motDePasse, (InetAddress.getLocalHost()).getCanonicalHostName(),port))
        {   System.out.println("["+(new Date()).toString()+"]"+"Connexion succeed to "+serveurName);
            tmp = this.tracker.login(identifiant, motDePasse);

            Set cles = tmp.keySet();
            for (Object cle : cles)
            {
                System.out.println("["+(new Date()).toString()+"]"+"Add " +cle.toString()+ " port "+tmp.get(cle).getPort());
                addPeers(cle.toString(),tmp.get(cle).getUrl(),tmp.get(cle).getPort());
            }

            System.out.println("["+(new Date()).toString()+"]"+tmp.size()+ " peers are in your node");

            return true;
        }
        System.out.println("["+(new Date()).toString()+"]"+"Connexion failed to "+serveurName+" --");
        return false;
    }

    /**
     * Send an event to the tracking for logging out.
     * @return              : true if log out succeed ;
     *                        false if connexion failed.
     * @throws RemoteException
     * @throws NotBoundException
     */
    public boolean logout() throws RemoteException, NotBoundException
    {
        if (this.getTracker() == null)
        {
            System.out.println("["+(new Date()).toString()+"]"+"-- You are not yet connected to any tracker --");
            return false;
        }

        return this.getTracker().logout(this.getIdentifiant(),this.getMotDePasse());
    }


    /**
     * Add message to the chat history, it can be called by another tracker in order to add a message.
     * @param message       : message you want to add to the peer concerned
     * @return              : true if message were successfully add ;
     *                        false if message were not add.
     */
    @Override
    public boolean addMessage(Object message)
    {   System.out.println("["+(new Date()).toString()+"]"+" "+((IMessage)message).getPsuedo()+" said : "+message.toString());
        return chat.addMessage((IMessage)message);
    }

    /**
     * Add message to the chat history, it can be called by another tracker in order to add a message.
     * @param message       : message you want to add to the peer concerned
     * @return              : true of message were successfully add ;
     *                        false if message were not add.
     */
    @Override
    public boolean deleteMessage(Object message) {
        if (message.getClass().getName() != IMessage.class.getClass().getName())
        {   return false;
        }
        return chat.removeMessage((IMessage) message);
    }

    /**
     * Send chat history to the peer that call this method.
     * @return              : chat history.
     */
    @Override
    public Object recoverMessages() {
        return this.chat;
    }


    /**
     * add peer to the list of peers available, can be called though network by IPeerToPeerInteraction
     * @param identifiant   : Id of the peer.
     * @param url           : URL to join this peer ;
     * @param port          : port to join this peer ;
     * @return              : true if peer were add ;
     *                        false if peer were not add because already exist.
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Override
    public boolean addPeers(String identifiant, String url, int port) throws RemoteException, NotBoundException {
        if(this.peers.containsKey(identifiant) || this.getIdentifiant().equals(identifiant))
        {   System.out.println("["+(new Date()).toString()+"]"+"Peer already exist "+identifiant+" --");
            return false;
        }

        System.out.println("["+(new Date()).toString()+"]"+"Adding "+identifiant+" at " +port+ " --");
        Registry registry = LocateRegistry.getRegistry(url,port);
        IPeerToPeerInteraction stub = (IPeerToPeerInteraction) registry.lookup(identifiant);
        this.peers.put(identifiant, stub);
        return true;
    }

    /**
     * delete peer of the list of peers available, can be called though network by IPeerToPeerInteraction
     * @param identifiant   : Id of the peer.
     * @return              : true if peer were deleted ;
     *                        false if peer were not deleted because does not exist.
     */
    @Override
    public boolean deletePeers(String identifiant) {
        if(!this.peers.containsKey(identifiant))
        {   return false;
        }
        this.peers.remove(identifiant);

        return true;
    }


    /**
     * Save chat history by calling IOPersistance, chat history will be write on a file called "chathistory.data"
     * @throws IOException
     */
    public void saveChatHistory() throws IOException
    {   IOPersistance.save("chathistory.data",chat.getChatHistory());
    }

    public ArrayList<String> getStringChat()
    {   return chat.getStringChat();
    }

    /**
     * Load chat history by calling IOPersistance, chat history will be load on a file called "chathistory.data"
     * @throws IOException
     */
    public void loadChatHistory()throws IOException, ClassNotFoundException
    {   chat.setChatHistory(IOPersistance.load("chathistory.data"));
    }

    /**
     * Send message to all the peers available
     * @param destinataire      : destinataire of the message ;
     * @param message           : content of the message.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendMessage(String destinataire,String message)throws IOException, ClassNotFoundException
    {   IMessage tmpMessage = new TextualMessage(message,new Date(), identifiant,destinataire);
        chat.addMessage(tmpMessage);

        Set cles = peers.keySet();
        for (Object cle : cles) {
            peers.get(cle).addMessage(tmpMessage);
        }
    }

    /**
     * Ask to all peers to send their history in order to recreate a coherent chat history.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void recoverMessage()throws IOException, ClassNotFoundException
    {
        Set cles = peers.keySet();
        for (Object cle : cles) {
            chat.mergeMessages(((ChatHistory)peers.get(cle).recoverMessages()).getChatHistory());
        }
    }
}
