package DTO;

public class LoginDTO {
    private String tenTK; // Tên tài khoản
    private String matKhau; // Mật khẩu

    // Constructor
    public LoginDTO(String tenTK, String matKhau) {
        this.tenTK = tenTK;
        this.matKhau = matKhau;
    }

    // Getters and Setters
    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
