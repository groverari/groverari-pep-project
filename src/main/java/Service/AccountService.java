package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account m){
        if(m.getUsername().length() != 0 && !accountDAO.checkUsername(m.getUsername()) && m.getPassword().length() >= 4){
            return accountDAO.register(m);
        }
        return null;
    }

    public Account login(Account acc){
        if(!accountDAO.checkUsername(acc.getUsername())){
            return null;
        }
        Account a =  accountDAO.login(acc);
        if(a.getPassword() == acc.getPassword()){
            return a;
        }
        return null;
    
    }
}
