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
        if (!accountDAO.ktNvTonTai(account.getMaNV())) {
            return false; // Trả về false nếu nhân viên không tồn tại
        }
        return accountDAO.addAccount(account);
    }

    public boolean updateAccount(AccountDTO account) {
        return accountDAO.updateAccount(account);
    }

    public boolean deleteAccount(int maTK) {
        return accountDAO.deleteAccount(maTK);
    }

    public boolean ktTaiKhoanTonTai(int maNV) {
        return accountDAO.ktTaiKhoanTonTai(maNV);
    };

    public boolean ktNvTonTai(int maNV) {
        return accountDAO.ktNvTonTai(maNV);
    }

    public List<AccountDTO> getAllAccounts() {
        return accountDAO.getAllAccounts();
    };
}
