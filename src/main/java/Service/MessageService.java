package Service;

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
}