package Peer.Model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Objective    : specify IMessage that will composed chat history.
 * @author Alan BAZARSI && François CHASTEL
 */
public interface IMessage extends Serializable, Comparator<IMessage>
{
    /**
     * return the content of the message
     * @return      : Object corresponding to the content.
     */
    public Object getMessage();

    /**
     * return when the message were created
     * @return      : Date of creation of the message.
     */
    public Date getDateOfCreation();

    /**
     * return psuedo of the sender, or creator of the message.
     * @return      : creator of the message
     */
    public String getPsuedo();

    /**
     * return reciever of the message
     * @return      : return reciever.
     */
    public String getDestinataire();
}
