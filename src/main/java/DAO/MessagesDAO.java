package DAO;
import Util.ConnectionUtil;
import Model.Message;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
public class MessagesDAO {
    
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages  = new ArrayList();

        try{
            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message m = new Message(rs.getInt("id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted"));
                messages.add(m);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message insertMessage(Message m){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql  = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, m.getPosted_by());
            preparedStatement.setString(2, m.getMessage_text());
            preparedStatement.setLong(3, m.getTime_posted_epoch());
        
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, m.getPosted_by(), m.getMessage_text(), m.getTime_posted_epoch());
            }
        }catch(SQLException e) {System.out.println(e.getMessage());}
           
        return null;
    }

    public Message getMessageByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql  = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"), 
                rs.getInt("posted_by"), 
                rs.getString("message_text"), 
                rs.getLong("time_posted_epoch"));
                return m;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    
    public Message deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = getMessageByID(id);
        try{
            String sql  = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int deletedRows = preparedStatement.executeUpdate();
            if(deletedRows > 0 ) return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateMessage(int id, String message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql  = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message);
            ps.setInt(2, id);

            int updated = ps.executeUpdate();
            return updated > 0;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Message> getAllMessagesForID(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages  = new ArrayList();

        try{
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message m = new Message(rs.getInt("id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted"));
                messages.add(m);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public boolean checkID(int id){
        
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){return true;}
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    
}
}
