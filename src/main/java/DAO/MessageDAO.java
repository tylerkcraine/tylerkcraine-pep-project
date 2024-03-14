package DAO;

import java.util.ArrayList;

import Model.Message;

public interface MessageDAO {
    public Message insertMessage(Message message);
    public ArrayList<Message> findAllMessages();
    public ArrayList<Message> findAllMessagesFromAccountId(int id);
    public Message findMessageWithId(int id);
    public boolean deleteMessageWithId(int id);
    public boolean updateMessageWithId(int id, String message_text);
}
