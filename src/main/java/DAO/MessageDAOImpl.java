package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Message;

public class MessageDAOImpl implements MessageDAO {

    /**
     * attempts to insert a message object into the database
     * @param message Object representing the new message
     * @result a Message object representing the message in the database, null if insert failed
     */
    @Override
    public Message insertMessage(Message message) {
        Connection con = Util.ConnectionUtil.getConnection();
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()) {
                int id = rs.getInt("message_id");
                return new Message(id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * a simple method to return all the messages in the message table
     * @return ArrayList containing all the messages in the message table.
     */
    @Override
    public ArrayList<Message> findAllMessages() {
        Connection con = Util.ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message";
        ArrayList<Message> messageList = new ArrayList<Message>();
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("message_id");
                int accountId = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long dateEpoch = rs.getLong("time_posted_epoch");
                messageList.add(new Message(id, accountId, messageText, dateEpoch));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messageList;
    }


    /**
     * a method to return all the messages in the message table that belong to an account id
     * @param id Account id to be searched for
     * @return ArrayList containing all the messages in the message table that belong to the mentioned account_id. list is empty if none found
     */ 
    @Override
    public ArrayList<Message> findAllMessagesFromAccountId(int id) {
        Connection con = Util.ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE posted_by = ?";
        ArrayList<Message> messageList = new ArrayList<Message>();
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int result_id = rs.getInt("message_id");
                int accountId = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long dateEpoch = rs.getLong("time_posted_epoch");
                messageList.add(new Message(result_id, accountId, messageText, dateEpoch));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messageList;
    }


    /**
     * method to return a message with a message id
     * @param id message id to be queried
     * @return message object representing the fetch message, null if not found
     */
    @Override
    public Message findMessageWithId(int id) {
        Connection con = Util.ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE message_id = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int result_id = rs.getInt("message_id");
                int accountId = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long dateEpoch = rs.getLong("time_posted_epoch");
                return new Message(result_id, accountId, messageText, dateEpoch);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Message deleteMessageWithId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMessageWithId'");
    }

    @Override
    public Message updateMessageWithId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMessageWithId'");
    }
    
}
