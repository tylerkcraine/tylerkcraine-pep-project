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
     * @param message
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
     * @return ArrayList containing all messages in database
     */
    public ArrayList<Message> allMessagesWithAccountId(int id) {
        return this.messageDAO.findAllMessagesFromAccountId(id);
    }

    /**
     * simple service to return a message with the provided message id
     * @return Message object representing the fetched message
     */
    public Message findMessageWithId(int id) {
        return this.messageDAO.findMessageWithId(id);
    }
}