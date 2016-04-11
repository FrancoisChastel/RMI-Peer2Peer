package Peer.IOPersistance;

import Peer.Model.IMessage;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Alan BAZARSI && François CHASTEL
 */
public class IOPersistance
{
    /**
     * Save a list of iMessage in a specify file "fileName"
     * @param fileName  : File's name where messages will be store
     * @param toSave    : List of messages that you want to store in the file
     * @throws IOException
     */
    public static  void save(String fileName, ArrayList<IMessage> toSave) throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
        oos.writeInt(toSave.size());
        for (IMessage e : toSave)
            oos.writeObject(e);
        oos.close();
    }

    /**
     * Load a list of iMessage in a specify file "fileName"
     * @param fileName  : File's name where you want to read the messages
     * @return          : list of messages stored in the file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<IMessage> load(String fileName) throws IOException, ClassNotFoundException
    {
        ArrayList<IMessage> list = new ArrayList<IMessage>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fileName)));
        int n=ois.readInt();
        for (int i=0; i<n; i++)
            list.add((IMessage) ois.readObject());
        ois.close();
        return list;
    }
}
