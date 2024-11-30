package Controller;

import java.sql.Date;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import BUS.ChucVuBUS;
import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SuaNVController {

    private Runnable onAddSuccessCallback; // Callback khi thêm thành công

    @FXML
    private Label btnSua;

    @FXML
    private ComboBox<String> cbbChucVu;

    @FXML
    private DatePicker dpNgaySinh;

    @FXML
    private RadioButton rbtnNam;

    @FXML
    private RadioButton rbtnNu;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtMaNV;

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtSDT;

    @FXML
    private TextField txtTenNV;

    @FXML
    private Label validChucVu;

    @FXML
    private Label validDiaChi;

    @FXML
    private Label validMail;

    @FXML
    private Label validNgaySinh;

    @FXML
    private Label validSDT;

    @FXML
    private Label validTen;

    @FXML
    private Label btnClosePopUps;
    String SDT = "";
    // Setter để nhận callback từ controller bên ngoài
    public void setOnSuaSuccessCallback(Runnable callback) {
        this.onAddSuccessCallback = callback;
    }
    public void setMaNV(int maNV) {
        txtMaNV.setText(String.valueOf(maNV));
        loadDuLieuTuBang();
    }
    private void closePopup() {
        // Lấy đối tượng Stage của cửa sổ hiện tại và đóng nó
        Stage stage = (Stage) btnSua.getScene().getWindow();
        stage.close();
    }
    @FXML
    void closePopUps(MouseEvent event) {
        Stage stage = (Stage) btnClosePopUps.getScene().getWindow();
        stage.close();
    }
    public void loadDuLieuTuBang(){
        ChucVuBUS cvBUS = new ChucVuBUS();
        NhanVienBUS nv = new NhanVienBUS();
        int maNhanVien = Integer.parseInt(txtMaNV.getText());
        
        NhanVienDTO nvDTO = nv.getNhanVienByMaNV(maNhanVien);
        txtTenNV.setText(nvDTO.getTenNV());
        txtSDT.setText(nvDTO.getSoDienThoai());
        txtMail.setText(nvDTO.getEmail());
        txtDiaChi.setText(nvDTO.getDiaChi());
        boolean gioiTinh = nvDTO.isGioiTinh();
        if (gioiTinh) {
            rbtnNam.setSelected(true);
        }
        else{
            rbtnNu.setSelected(true);
        }
        String tenChucVu = cvBUS.getTenChucVuByMaChucVu(nvDTO.getMaChucVu());
        cbbChucVu.setValue(tenChucVu);
        // Chuyển đổi ngày sinh từ Date sang LocalDate và set cho DatePicker
        Date ngaySinhDate = nvDTO.getNgaySinh(); // Giả sử đây là java.util.Date
        LocalDate ngaySinhLocalDate = ngaySinhDate.toLocalDate();
        dpNgaySinh.setValue(ngaySinhLocalDate); // Set giá trị cho DatePicker
        SDT = txtSDT.getText();
        System.out.println(SDT);
    }
    
    public void initialize() {
        
        
        // Gọi phương thức để cập nhật dữ liệu cho TableView
        ObservableList<String> chucVuList = FXCollections.observableArrayList(
             "Quan ly","Nhan vien ban hang"
        );

        // Gán danh sách các chức vụ vào ComboBox
        cbbChucVu.setItems(chucVuList);
        ToggleGroup toggleGroup = new ToggleGroup();
        rbtnNam.setToggleGroup(toggleGroup);
        rbtnNu.setToggleGroup(toggleGroup);
        
    }

    @FXML
    void Click(MouseEvent event) {
        ChucVuBUS cv = new ChucVuBUS();
        NhanVienBUS nvBUS = new NhanVienBUS();
        validChucVu.setText("");
        validDiaChi.setText("");
        validMail.setText("");
        validNgaySinh.setText("");
        validSDT.setText("");
        validTen.setText("");

        boolean flag = true;
        String ten = txtTenNV.getText();
        String diaChi = txtDiaChi.getText();
        String soDienThoai = txtSDT.getText();
        String mail = txtMail.getText();
        boolean gioiTinh = false;
        if(rbtnNam.isSelected()){
            gioiTinh = true;
        }
        if(flag){
            if(ten.isEmpty()){
                validTen.setText("Không Được Bỏ Trống");
                flag = false;
            }
            else if(!ten.matches("^[A-ZĐÁÀÂÃẢẠẶĂẦẬÉÈÊẺẼỂỄỆẸÌĨỈỊÍÓÒÔỜỞỌỘỒỔÕƠỚỜỞỠÚÙŨỦỤƯỨỪỮỬỰÝỲỸỶỴ][a-záàâãảạặăầéèêẻẽểễệẹìĩỉịíóòôờởọộồổõơớờởỡúùũủụưứừữửựýỳỹỷỵ]*([\\s][A-ZĐÁÀÂÃẢẠẶĂẬẦÉÈÊẺẼỂỄỆẸÌĨỈỊÍÓÒÔỜỞỌỘỒỔÕƠỚỜỞỠÚÙŨỦỤƯỨỪỮỬỰÝỲỸỶỴ][a-záàâãảạặăầéèêẻẽểễệẹìĩỉịíóòôờởọộồổõơớờởỡúùũủụưứừữửựýỳỹỷỵ]*)+$")){
                validTen.setText("Tên Nhân Viên Không Đúng Định Dạng! Hãy Nhập Lại (Ví dụ: Nguyễn Văn A).");
                flag = false;
            }
            if(mail.isEmpty()){
                validMail.setText("Không Được Bỏ Trống");
                flag = false;
            }
            else if(!mail.matches("^[a-zA-Z0-9]*@gmail\\.com$")){
                validMail.setText("Không Đúng Định Dạng! Hãy Nhập Lại (Ví dụ: abc@gmail.com hoặc 123@gmail.com).");
                flag = false;
            }
            if(diaChi.isEmpty()){
                validDiaChi.setText("Không Được Bỏ Trống");
                flag = false;
            }
            else if(!diaChi.matches("^\\d+(\\/\\d+)?\\sDuong*([\\s][A-ZĐ][a-záàâãảạặăầéèêẻẽểễệẹìĩỉịíóòôờởọộồổõơớờởỡúùũủụưứừữửựýỳỹỷỵ]*)+$")){
                validDiaChi.setText("Không Đúng Định Dạng! Hãy Nhập Lại (Ví dụ: 123/32 Duong ABC hoặc 123 Duong ABC).");
                flag = false;
            }
            
            if(soDienThoai.isEmpty()){
                validSDT.setText("Không Được Bỏ Trống.");
                flag = false;
            }
            else if(!soDienThoai.matches("^0[0-9]{9}$")){
                validSDT.setText("Không Đúng Định Dạng! Hãy Nhập Lại (Ví dụ: 0987654321).");
                flag = false;
            }
            else if(!SDT.equals(soDienThoai)  && nvBUS.kiemTraSoDienThoai(soDienThoai)){
                validSDT.setText("Đã Có Số Điện Thoại " + soDienThoai + "! Hãy Nhập Lại.");
                flag = false;
            }
            if(dpNgaySinh.getValue() == null){
                validNgaySinh.setText("Chưa Chọn Ngày Sinh.");
                flag = false;
            }
            if(cbbChucVu.getSelectionModel().getSelectedItem() == null){
                validChucVu.setText("Chưa Chọn Chức Vụ.");
                flag = false;
            }
        }
        if(flag){
            
            int maChucVu = cv.getMaChucVuByTenChucVu(cbbChucVu.getSelectionModel().getSelectedItem().toString());
            NhanVienDTO nv = new NhanVienDTO();
            nv.setMaNV(Integer.parseInt(txtMaNV.getText()));
            nv.setTenNV(ten);
            nv.setSoDienThoai(soDienThoai);
            nv.setNgaySinh(Date.valueOf(dpNgaySinh.getValue()));
            nv.setGioiTinh(gioiTinh);
            nv.setDiaChi(diaChi);
            nv.setEmail(mail);
            nv.setMaChucVu(maChucVu);
            boolean check = nvBUS.updateNhanVienDTO(nv);
            if(check){
                JOptionPane.showMessageDialog(null,"Sửa Nhân Viên Thành Công.");
                if (onAddSuccessCallback != null) {
                    onAddSuccessCallback.run(); // Gọi callback
                }
                closePopup();
            }
            else{
                JOptionPane.showMessageDialog(null,"Sửa Nhân Viên Thất Bại.");
            }
        }
    }
    
}
