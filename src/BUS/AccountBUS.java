package BUS;

import DAO.AccountDAO;
import DTO.AccountDTO;
import java.util.List;

public class AccountBUS {

    private AccountDAO accountDAO;

    public AccountBUS() {
        accountDAO = new AccountDAO();
    }

    public boolean addAccount(AccountDTO account) {
        return accountDAO.addAccount(account);
    }

    public boolean updateAccount(AccountDTO account) {
        return accountDAO.updateAccount(account);
    }

    public boolean deleteAccount(int maTK) {
        return accountDAO.deleteAccount(maTK);
    }

    public AccountDTO getAccountbyId(int maTK) {
        return accountDAO.getAccountbyId(maTK);
    }

    public List<AccountDTO> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
}
