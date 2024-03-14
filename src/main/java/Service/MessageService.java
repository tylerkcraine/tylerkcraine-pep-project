package Service;

import java.util.ArrayList;

import DAO.AccountDAOImpl;
import DAO.MessageDAOImpl;
import Model.Message;

public class MessageService {
    AccountDAOImpl accountDAO;
    MessageDAOImpl messageDAO;

    public MessageService() {
        this.accountDAO = new AccountDAOImpl();
        this.messageDAO = new MessageDAOImpl();
    }

    /**
     * service to insert messages in the database
     * @param message Message object representing new message (message_id not current initilized)
     * @return returns message with message id or null if insert checks don't pass or insert fails
     */
    public Message createMessage(Message message) {
        if (this.accountDAO.findAccountByUserId(message.getPosted_by()) == null ||
                message.message_text.isBlank()  ||
                message.getMessage_text().length() >= 255) {
            return null;
        }

        return this.messageDAO.insertMessage(message);
    }

    /**
     * simple service to return list of messages in the database to handler
     * @return ArrayList containing all messages in database
     */
    public ArrayList<Message> allMessages() {
        return this.messageDAO.findAllMessages();
    }

    /**
     * simple service to return list of messages with a certain poster in the database to handler
     * @param id the Account id to use to query
     * @return ArrayList containing all messages in database
     */
    public ArrayList<Message> allMessagesWithAccountId(int id) {
        return this.messageDAO.findAllMessagesFromAccountId(id);
    }

    /**
     * simple service to return a message with the provided message id
     * @param id of the message to be found
     * @return Message object representing the fetched message, null if it doesn't exist
     */
    public Message findMessageWithId(int id) {
        return this.messageDAO.findMessageWithId(id);
    }

    /**
     * simple service to delete a message from the database
     * checks to see if message exists and returns null if it doesn't
     * @param id of the message to be delete
     * @return Message object representing the deleted message, null if it doesn't exist
     */
    public Message deleteMessageWithId(int id) {
        Message message = this.messageDAO.findMessageWithId(id);
        if (message != null) {
            this.messageDAO.deleteMessageWithId(id);
            return message;
        }
        return null;
    }

    /**
     * service to update a message in the database based on a new message object
     * @param message representing the new message values (message_id must not be null)
     * @return message representing the new message, null if update failed or doesn't exist
     */
    public Message updateMessage(int id, String message_text) {
        if (this.messageDAO.findMessageWithId(id) == null ||
                message_text.isBlank()  ||
                message_text.length() >= 255) {
            return null;
        }
        boolean updated = this.messageDAO.updateMessageWithId(id, message_text);
        
        if (updated) {
            return this.messageDAO.findMessageWithId(id);
        } else {
            return null;
        }
    }
}