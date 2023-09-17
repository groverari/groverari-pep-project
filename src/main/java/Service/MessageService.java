package Service;

import Model.Message;
import DAO.MessagesDAO;
import java.util.List;

public class MessageService {
    MessagesDAO mDAO ;
    

    public MessageService(){
        this.mDAO = new MessagesDAO();
    }

    public Message addMessage(Message m){
        System.out.println("Made it here");
        if(m.getMessage_text().length() > 0 &&
         m.getMessage_text().length() < 255 && 
         mDAO.checkID(m.getPosted_by())){
            return mDAO.insertMessage(m);
        }
        return null;
    }

    public Message getMessagebyID(int id){
        return mDAO.getMessageByID(id);
    }

    public List<Message> getAllMessages(){
        return mDAO.getAllMessages();
    }

    public List<Message> getAllMessagesByID(int id){
        return mDAO.getAllMessagesForID(id);
    }

    public Message deleteMessage(int m){
        return mDAO.deleteMessage(m);
    }
    public Message updateMessage(int id, String message){
        if(message.length() > 0 && message.length() < 255){
            mDAO.updateMessage(id, message);
            return getMessagebyID(id);
        }
        return null;
    }



}
