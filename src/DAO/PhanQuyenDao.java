package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import DTO.CTPhanQuyenDTO;
import DTO.ChucNangDTO;
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
        ArrayList<PhanQuyenDTO> dsQuyen = new ArrayList<>();
        try {
            if (openConnection()) {
                String sql = "SELECT * FROM PhanQuyen";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    PhanQuyenDTO pq = new PhanQuyenDTO();
                    pq.setMaQuyen(rs.getInt("MaQuyen"));
                    pq.setTenQuyen(rs.getString("TenQuyen"));
                    pq.setMoTa(rs.getString("MoTa"));
                    dsQuyen.add(pq);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách quyền: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return dsQuyen;
    }

    public ArrayList<TaiKhoan_DTO> getTaiKhoanByQuyen(int maQuyen) {
        ArrayList<TaiKhoan_DTO> dsTaiKhoan = new ArrayList<>();
        try {
            if (openConnection()) {
                String sql = "SELECT * FROM TaiKhoan WHERE MaQuyen = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, maQuyen);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    TaiKhoan_DTO tk = new TaiKhoan_DTO();
                    tk.setMaTaiKhoan(rs.getInt("MaTaiKhoan"));
                    tk.setTenTK(rs.getString("TenTaiKhoan"));
                    tk.setMaQuyen(rs.getInt("MaQuyen"));
                    dsTaiKhoan.add(tk);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách tài khoản theo quyền: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return dsTaiKhoan;
    }

    public void savePermissions(ArrayList<CTPhanQuyenDTO> permissions) {
        try {
            if (openConnection()) {
                con.setAutoCommit(false);

                // Xóa quyền cũ trước khi thêm mới
                String deleteSQL = "DELETE FROM CTPhanQuyen WHERE MaQuyen = ?";
                PreparedStatement deleteStmt = con.prepareStatement(deleteSQL);
                if (!permissions.isEmpty()) {
                    deleteStmt.setInt(1, permissions.get(0).getMaQuyen());
                    deleteStmt.executeUpdate();
                }

                // Thêm quyền mới
                String insertSQL = "INSERT INTO CTPhanQuyen (MaQuyen, MaChucNang, MaHanhDong) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = con.prepareStatement(insertSQL);
                for (CTPhanQuyenDTO permission : permissions) {
                    insertStmt.setInt(1, permission.getMaQuyen());
                    insertStmt.setInt(2, permission.getMaChucNang());
                    insertStmt.setInt(3, permission.getMaHanhDong());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
                con.commit();
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu quyền: " + e.getMessage());
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Lỗi khi rollback: " + ex.getMessage());
            }
        } finally {
            closeConnection();
        }
    }

    public ArrayList<CTPhanQuyenDTO> getPermissionsByQuyen(int maQuyen) {
        ArrayList<CTPhanQuyenDTO> permissions = new ArrayList<>();
        try {
            if (openConnection()) {
                String sql = "SELECT * FROM CTPhanQuyen WHERE MaQuyen = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, maQuyen);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    CTPhanQuyenDTO permission = new CTPhanQuyenDTO();
                    permission.setMaQuyen(rs.getInt("MaQuyen"));
                    permission.setMaChucNang(rs.getInt("MaChucNang"));
                    permission.setMaHanhDong(rs.getInt("MaHanhDong"));
                    permissions.add(permission);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy quyền: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return permissions;
    }

}
