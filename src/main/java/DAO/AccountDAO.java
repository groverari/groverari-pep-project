package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;


public class AccountDAO {
    
    public Account register(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO Account(username, password) VALUES(?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.username);
            ps.setString(2, account.password);
            
            ps.executeUpdate();

            ResultSet pkeyResultSet = ps.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_author_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_author_id, account.getUsername(),  account.getPassword());
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;    
    }

    public Account login (Account acc){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM  Account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, acc.username);
            
            ps.executeUpdate();

            ResultSet pkeyResultSet = ps.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_author_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_author_id, acc.getUsername(),  acc.getPassword());
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;    
    }

    public boolean checkUsername(String s){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){return true;}
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
}

