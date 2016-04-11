package Peer.Model;

import java.util.Date;

/**
 * Objective    : Specify textual message.
 * @author Alan BAZARSI && François CHASTEL
 */
public class TextualMessage implements IMessage
{
    String message;
    Date dateOfCreation;
    String psuedo;
    String destinataire;

    /**
     * Create a textual message which the content is a string.
     * @param message           : content of the message ;
     * @param dateOfCreation    : date of creation of the message ;
     * @param psuedo            : psuedo of the creator ;
     * @param destinataire      : reciever of the message, by default it is all.
     */
    public TextualMessage(String message, Date dateOfCreation, String psuedo, String destinataire) {
        this.message = message;
        this.dateOfCreation = dateOfCreation;
        this.psuedo = psuedo;
        if (destinataire == "")
        {   this.destinataire = "all";
        }
        else
        {   this.destinataire = destinataire;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getPsuedo() {
        return psuedo;
    }

    @Override
    public String getDestinataire() {
        return destinataire;
    }

    public void setPsuedo(String psuedo) {
        this.psuedo = psuedo;
    }

    @Override
    public int compare(IMessage o1, IMessage o2)
    {   return ((TextualMessage)o1).dateOfCreation.compareTo(((TextualMessage)o2).getDateOfCreation());
    }

    /**
     * Equals if it is a Textual Message and the messages have same values for each parameters.
     * @param o Object
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextualMessage that = (TextualMessage) o;

        if (!dateOfCreation.equals(that.dateOfCreation)) return false;
        if (!message.equals(that.message)) return false;
        if (!psuedo.equals(that.psuedo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + dateOfCreation.hashCode();
        result = 31 * result + psuedo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "["+dateOfCreation+"]" + " " +psuedo+ " > "+destinataire+" : " +message;
    }
}
