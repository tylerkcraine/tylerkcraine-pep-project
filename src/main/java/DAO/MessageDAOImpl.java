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

    @Override
    public ArrayList<Message> findAllMessages() {
        // Connection con = Util.ConnectionUtil.getConnection();
        // String sql = "SELECT * FROM message";
        
        // try {
        //     PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //     ps.setInt(1, message.getPosted_by());
        // }
        return null;
    }

    @Override
    public ArrayList<Message> findAllMessagesFromAccountId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllMessagesFromAccountId'");
    }

    @Override
    public Message findMessageWithId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findMessageWithId'");
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
