package Tracker.Model;

import Peer.NetworkControler.ITrackerToPeerInteraction;

import java.io.Serializable;

/**
 * Peer representation for the server.
 * @author Alan BAZARSI && François CHASTEL
 */
public class Peer implements Serializable {
    String password;
    String url;
    int port;
    boolean connected;
    ITrackerToPeerInteraction stub;

    public Peer(String password, String url, int port) {
        this.password = password;
        this.url = url;
        this.port = port;
        this.connected = false;
        this.stub = null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public ITrackerToPeerInteraction getStub() {
        return stub;
    }

    public void setStub(ITrackerToPeerInteraction stub) {
        this.stub = stub;
    }

    /**
     * Check if password is the good one compare to the given one.
     * @param password      : password of login.
     * @return              : true if password is equal ;
     *                        false if password is not equal.
     */
    public boolean isMatching(String password)
    {   if (password.equals(this.password))
        {   return true;
        }

        return false;
    }
}
