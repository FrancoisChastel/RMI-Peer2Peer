package Peer.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Objective    : Manage history of chat, this is a list of IMessage
 * @author Alan BAZARSI && François CHASTEL
 */
public class ChatHistory implements Serializable
{
    ArrayList<IMessage> chatHistory;

    /**
     * Constructor      : initialize a list of IMessage
     */
    public ChatHistory()
    {
        this.chatHistory = new ArrayList<IMessage>();
    }

    public ArrayList<IMessage> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(Object chatHistory)
    {   this.chatHistory = (ArrayList<IMessage>)chatHistory;
    }

    /**
     * Add message in chat history
     * @param message   : Message that you want to add in chat history
     * @return          : - true if the message were add
     *                    - false if the message is null and were not add
     */
    public boolean addMessage(IMessage message)
    {
        if (message == null)
        {   return false;
        }

        this.chatHistory.add(message);

        sortChatHistory();

        return true;
    }

    /**
     * Remove a message based on this .equals
     * @param message   : Message you want to remove in the chat history
     * @return          : - true if the message were successfully removed ;
     *                    - false if the message can not be remove.
     */
    public boolean removeMessage(IMessage message)
    {
        if (message == null)
        {   return false;
        }
        if (!chatHistory.contains(message))
        {   return false;
        }

        for (int cursor = 0; cursor < chatHistory.size(); cursor++)
        {   if (chatHistory.get(cursor).equals(message))
            {   chatHistory.remove(cursor);
            }
        }
        return true;
    }

    /**
     * Merge a list of message, if you have two history of message for the same context.
     * You can recreate a chat history based on another chat history and have a complete history.
     * @param messages  : Messages you want to merge with chat history.
     * @return          : number of differences between messages gived in parameters and chat history.
     */
    public int mergeMessages(ArrayList<IMessage> messages)
    {   int differences = 0;
        for (IMessage cursor : messages)
        {   if (!chatHistory.contains(cursor))
            {   chatHistory.add(cursor);
                differences++;
            }
        }

        sortChatHistory();

        return differences;
    }

    /**
     * Give you chat history in a list of string based on IMessage.toString().
     * @return          : List of string.
     */
    public ArrayList<String> getStringChat()
    {   ArrayList<String> tmp = new ArrayList<String>();

        for (IMessage message : chatHistory)
        {   tmp.add(message.toString());
        }
        return tmp;
    }

    /**
     * sort chat history based on compare method of each IMessages.
     * @return          : sorted chat history.
     */
    public ArrayList<IMessage> sortChatHistory()
    {   Collections.sort(chatHistory, new Comparator<IMessage>() {
            public int compare(IMessage result1, IMessage result2) {
                return result1.compare(result1,result2);
            }
        });

        return chatHistory;
    }

    /**
     * Same usage than equals but if you give a chat history in parameter every messages of both chat history will be
     * compared to know if there is the same content.
     * @param o         : object you want to compare to chat history
     * @return          : true if there is no difference ;
     *                    false if there is difference or if o is not a chat history object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatHistory that = (ChatHistory) o;

        if (chatHistory != null)
        {
            if (chatHistory.size() != that.getChatHistory().size())
            {
                return false;
            }

            for (int cursorMessage = 0; cursorMessage < chatHistory.size(); cursorMessage++)
            {
                if (!chatHistory.get(cursorMessage).equals(that.getChatHistory().get(cursorMessage)))
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @return          : Normalize String version of chat history.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("ChatHistory {");
        stringBuilder.append("/n");

        for (IMessage message : chatHistory)
        {   stringBuilder.append(message.toString());
        }
        stringBuilder.append("/n");
        stringBuilder.append("}");

        String finalString = stringBuilder.toString();

        return finalString;
    }
}
