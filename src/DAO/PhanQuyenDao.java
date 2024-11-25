package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.PhanQuyenDTO;
import DTO.TaiKhoan_DTO;

public class PhanQuyenDao {
    private Connection con;

    public boolean openConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=SieuThiMini;encrypt=false;trustServerCertificate = true";
            con = DriverManager.getConnection(dbUrl, "sa", "123456789");
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public ArrayList<PhanQuyenDTO> getALLQuyen() {
        ArrayList<PhanQuyenDTO> dsQuyen = new ArrayList<PhanQuyenDTO>();

        try {
            if (openConnection()) {
                String sql = "SELECT * FROM PhanQuyen";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String tenQuyen = rs.getString("TenQuyen");
                    // Kiểm tra nếu TenQuyen không phải là null và không phải chuỗi rỗng
                    if (tenQuyen != null && !tenQuyen.isEmpty()) {
                        PhanQuyenDTO pq = new PhanQuyenDTO();
                        pq.setMaQuyen(rs.getInt("MaQuyen"));
                        pq.setTenQuyen(tenQuyen);
                        pq.setMoTa(rs.getString("MoTa"));
                        dsQuyen.add(pq);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }

        return dsQuyen;
    }

    public ArrayList<TaiKhoan_DTO> getTaiKhoanByQuyen(int maQuyen) {
        ArrayList<TaiKhoan_DTO> dsTaiKhoan = new ArrayList<>();
        try {
            if (openConnection()) {
                String sql = "Select * from TaiKhoan Where MaQuyen = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, maQuyen);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    TaiKhoan_DTO tk = new TaiKhoan_DTO();
                    tk.setMaTK(rs.getInt("MaTK"));
                    tk.setTenTK(rs.getString("TenTK"));
                    tk.setMaQuyen(rs.getInt("MaQuyen"));
                    dsTaiKhoan.add(tk);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }
        return dsTaiKhoan;
    }

}
