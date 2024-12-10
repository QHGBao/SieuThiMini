package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import DTO.QuanLyGiamGiaSpDTO;
import java.sql.SQLException;
import java.time.LocalDate;

import BUS.QuanLyGiamGiaSpBUS;
import BUS.SanPhamBUS;
import DAO.ConnectManager;
import DAO.QuanLyGiamGiaSpDAO;
import DTO.SanPhamDTO;
import DTO.SanPhamKmDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class QuanLyGiamGiaSpController {
    @FXML
    private TableView<QuanLyGiamGiaSpDTO> tblQLGgSP;

    @FXML
    private TableColumn<QuanLyGiamGiaSpDTO, Integer> colMaKM;

    @FXML
    private TableColumn<QuanLyGiamGiaSpDTO, String> colTenKM;

    @FXML
    private TableColumn<QuanLyGiamGiaSpDTO, String> colNgayBD;

    @FXML
    private TableColumn<QuanLyGiamGiaSpDTO, String> colNgayKT;

    @FXML
    private TableColumn<QuanLyGiamGiaSpDTO, Integer> colPhanTramGiam;

    private QuanLyGiamGiaSpBUS bus = new QuanLyGiamGiaSpBUS();

    @FXML
    private TextField txtMaKMTao;

    @FXML
    private TextField txtTenCTTao;

    @FXML
    private DatePicker dpNgayBDTao;

    @FXML
    private DatePicker dpNgayKTTao;

    @FXML
    private TextField txtPhanTramGiamTao;

    @FXML
    private TableView<SanPhamDTO> tblSP;

    @FXML
    private TableColumn<SanPhamDTO, Integer> colMaSP1;

    @FXML
    private TableColumn<SanPhamDTO, String> colTenSP1;

    @FXML
    private TableColumn<SanPhamDTO, String> colMoTa1;

    @FXML
    private TableColumn<SanPhamDTO, Integer> colSoLuong1;

    @FXML
    private TableColumn<SanPhamDTO, String> colHinhAnh1;

    @FXML
    private TableColumn<SanPhamDTO, Double> colGiaBan1;

    @FXML
    private TableColumn<SanPhamDTO, Integer> colMaLoai1;

    @FXML
    private TableView<SanPhamDTO> tblSPchon;

    @FXML
    private TableColumn<SanPhamDTO, Integer> colMaSP;

    @FXML
    private TableColumn<SanPhamDTO, String> colTenSP;

    @FXML
    private TableColumn<SanPhamDTO, String> colMoTa;

    @FXML
    private TableColumn<SanPhamDTO, Integer> colSoLuong;

    @FXML
    private TableColumn<SanPhamDTO, String> colHinhAnh;

    @FXML
    private TableColumn<SanPhamDTO, Double> colGiaBan;

    @FXML
    private TableColumn<SanPhamDTO, Integer> colMaLoai;

    @FXML
    private Button btnTao;

    @FXML
    private Button btnSuaCT;
    
    private void refreshTable() {
        List<QuanLyGiamGiaSpDTO> khuyenMaiList = bus.getAllGiamGiaSP(); // Lấy danh sách mới từ BUS
        ObservableList<QuanLyGiamGiaSpDTO> data = FXCollections.observableArrayList(khuyenMaiList);
        tblQLGgSP.setItems(data);
    }

    @FXML
    private void btnThemSP(ActionEvent event) {
        // Lấy dòng được chọn từ bảng tblSP (bảng đầu tiên)
        SanPhamDTO selectedSanPham = tblSP.getSelectionModel().getSelectedItem();

        if (selectedSanPham == null) {
            // Nếu không có dòng nào được chọn
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn một sản phẩm trước khi thêm!");
            return;
        }

        
        if (!tblSPchon.getItems().stream().anyMatch(t -> t.getMaSP() == selectedSanPham.getMaSP())) {
            tblSPchon.getItems().add(selectedSanPham);
        } 
        else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Sản phẩm đã có trong bảng");
        }
    }

    @FXML
    private void btnXoaSPchon(ActionEvent event){
        SanPhamDTO xoaSP = tblSPchon.getSelectionModel().getSelectedItem();

        if (xoaSP == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn 1 sản phẩm trước khi xoá");
        }

        tblSPchon.getItems().remove(xoaSP);
            
    }

     @FXML
     private void btnXoaCT(ActionEvent event) {
        QuanLyGiamGiaSpDTO xoaCT = tblQLGgSP.getSelectionModel().getSelectedItem();
    
        if (xoaCT == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn 1 chương trình trước khi xoá");
        } else {
            try {
                // Gọi phương thức XoaCT từ lớp DAO
                QuanLyGiamGiaSpDAO dao = new QuanLyGiamGiaSpDAO();
                dao.XoaCT(xoaCT.getMaKM());  // Xóa chương trình theo mã giảm giá (maKM)
                
                // Xóa khỏi bảng hiển thị sau khi xóa thành công
                tblQLGgSP.getItems().remove(xoaCT);
                showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Chương trình giảm giá đã được xóa thành công!");
    
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra trong quá trình xóa chương trình.");
            }
        }
    }
    
 

    

    private String validateKhuyenMai(String tenKM,  int maKM, Date ngayBD, Date ngayKT, int ptGiam) {
        if (tenKM == null || tenKM.isEmpty()) {
            return "Tên khuyến mãi không được để trống!";
        }

        if (maKM < 0){
            return "Mã khuyến mãi không hợp lệ";
        }
        
        if (ngayBD == null || ngayKT == null) {
            return "Ngày bắt đầu và ngày kết thúc không được để trống!";
        }
        if (ngayBD.after(ngayKT)) {
            return "Ngày bắt đầu không được sau ngày kết thúc!";
        }
        if (ptGiam < 0 || ptGiam > 100) {
            return "Phần trăm giảm giá phải nằm trong khoảng 0 đến 100!";
        }
        return null; // Không có lỗi
    }

    private boolean isMaKMExists(int maKM) {
        return tblQLGgSP.getItems().stream().anyMatch(km -> km.getMaKM() == maKM);
    }
    
    @FXML
    private void btnTaoKm(ActionEvent event){
        try {
            // Lấy dữ liệu từ các trường nhập liệu
            int maKM = Integer.parseInt(txtMaKMTao.getText() == null ? "" : txtMaKMTao.getText().trim());
            String tenKM = txtTenCTTao.getText() == null ? "" : txtTenCTTao.getText().trim();
            Date ngayBD = Date.valueOf(dpNgayBDTao.getValue());

            Date ngayKT = Date.valueOf(dpNgayKTTao.getValue());
            int ptGiam = Integer.parseInt(txtPhanTramGiamTao.getText() == null ? "" : txtPhanTramGiamTao.getText().trim());
    
            String errorMessage = validateKhuyenMai(tenKM, maKM, ngayBD, ngayKT, ptGiam);
            if (errorMessage != null) {
                showAlert(Alert.AlertType.WARNING, "Lỗi dữ liệu", errorMessage);
                return;
            }
            
            if (isMaKMExists(maKM)) {
                showAlert(Alert.AlertType.WARNING, "Lỗi mã khuyến mãi", "Mã khuyến mãi đã tồn tại, vui lòng chọn mã khác!");
                return;
            }
            
            // Tạo đối tượng mới
            QuanLyGiamGiaSpDTO newKM = new QuanLyGiamGiaSpDTO(maKM, tenKM, ngayBD, ngayKT, ptGiam);
            ArrayList<SanPhamKmDTO> smkmDtos = new ArrayList<>();

            ArrayList<Integer> danhSachMaSP = new ArrayList<>();
            for (SanPhamKmDTO dto : smkmDtos) {
                danhSachMaSP.add(dto.getMaSP()); // Lấy mã sản phẩm từ dto
            }

            for (Integer maSP : danhSachMaSP) {
                smkmDtos.add(new SanPhamKmDTO(maSP, maKM)); // Thêm sản phẩm vào danh sách
            }
            // In danh sách mã sản phẩm ra console
            System.out.println("Danh sách mã sản phẩm: " + danhSachMaSP);

            

            
            
            // Gửi dữ liệu đến BUS để thêm vào cơ sở dữ liệu
            boolean isAdded = bus.addGiamGiaSP(newKM, smkmDtos);
            if (isAdded) {
                // Nếu thêm thành công, cập nhật bảng
                tblQLGgSP.getItems().add(newKM);
                
                // Xóa dữ liệu trong các trường nhập
                clearInputFields();
                tblSPchon.getItems().clear();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm khuyến mãi thành công!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Không thể thêm khuyến mãi!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi định dạng", "Vui lòng nhập đầy đủ thông tin!");
        }
    
    }


    

    @FXML
    private void btnSuaCT(ActionEvent event) {
        // Lấy dòng khuyến mãi được chọn từ bảng tblQLGgSP
        QuanLyGiamGiaSpDTO selectedKM = tblQLGgSP.getSelectionModel().getSelectedItem();

        if (selectedKM == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn một chương trình khuyến mãi để sửa!");
            return;
        }

        try {
            // Lấy danh sách sản phẩm khuyến mãi từ BUS dựa vào Mã KM
            
            int maKM = selectedKM.getMaKM();
            SanPhamBUS sanPhamBUS = new SanPhamBUS();
            //ArrayList<SanPhamDTO> danhSachSanPham = sanPhamBUS.getSanPhamByMaKM(maKM);
            
            // Hiển thị danh sách sản phẩm vào bảng tblSPchon
            ObservableList<SanPhamDTO> dataKM = FXCollections.observableArrayList(sanPhamBUS.getSanPhamByMaKM(maKM));
            //System.out.println("Danh sách sản phẩm: " + danhSachSanPham);
            tblSPchon.setItems(dataKM);

            // Điền dữ liệu của chương trình khuyến mãi vào các trường nhập liệu
            txtMaKMTao.setText(String.valueOf(selectedKM.getMaKM()));
            txtTenCTTao.setText(selectedKM.getTenKM());
            dpNgayBDTao.setValue(selectedKM.getNgayBD().toLocalDate());
            dpNgayKTTao.setValue(selectedKM.getNgayKT().toLocalDate());
            txtPhanTramGiamTao.setText(String.valueOf(selectedKM.getPtGiam()));

         
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách sản phẩm của chương trình khuyến mãi!");
        }
    }


    private void clearInputFields() {
        txtMaKMTao.clear(); // Xóa nội dung TextField Mã KM
        txtTenCTTao.clear(); // Xóa nội dung TextField Tên KM
        dpNgayBDTao.setValue(null); // Xóa nội dung DatePicker Ngày Bắt Đầu
        dpNgayKTTao.setValue(null); // Xóa nội dung DatePicker Ngày Kết Thúc
        txtPhanTramGiamTao.clear(); // Xóa nội dung TextField Phần Trăm Giảm
    }

    
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadTableData() {
        ArrayList<QuanLyGiamGiaSpDTO> danhSach = bus.getAllGiamGiaSP();
        ObservableList<QuanLyGiamGiaSpDTO> data = FXCollections.observableArrayList(danhSach);
        tblQLGgSP.setItems(data);
    }

    @FXML
    public void initialize() {
        txtMaKMTao.setText(String.valueOf(bus.createCode()));
        // Cấu hình các cột
        colMaKM.setCellValueFactory(new PropertyValueFactory<>("maKM"));
        colTenKM.setCellValueFactory(new PropertyValueFactory<>("tenKM"));
        colNgayBD.setCellValueFactory(new PropertyValueFactory<>("ngayBD"));
        colNgayKT.setCellValueFactory(new PropertyValueFactory<>("ngayKT"));
        colPhanTramGiam.setCellValueFactory(new PropertyValueFactory<>("ptGiam"));

        loadTableData();

        

        colMaSP1.setCellValueFactory(new PropertyValueFactory<>("MaSP"));
        colTenSP1.setCellValueFactory(new PropertyValueFactory<>("TenSP"));
        colMoTa1.setCellValueFactory(new PropertyValueFactory<>("MoTa"));
        colSoLuong1.setCellValueFactory(new PropertyValueFactory<>("SoLuong"));
        colHinhAnh1.setCellValueFactory(new PropertyValueFactory<>("HinhAnh"));
        colGiaBan1.setCellValueFactory(new PropertyValueFactory<>("GiaBan"));
        colMaLoai1.setCellValueFactory(new PropertyValueFactory<>("MaLoai"));

        loadSanPhamData();

        colMaSP.setCellValueFactory(new PropertyValueFactory<>("MaSP"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("TenSP"));
        colMoTa.setCellValueFactory(new PropertyValueFactory<>("MoTa"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("SoLuong"));
        colHinhAnh.setCellValueFactory(new PropertyValueFactory<>("HinhAnh"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<>("GiaBan"));
        colMaLoai.setCellValueFactory(new PropertyValueFactory<>("MaLoai"));

        
        
    }

    


    private void loadSanPhamData() {
        SanPhamBUS sanPhamBUS = new SanPhamBUS();
        ArrayList<SanPhamDTO> danhSachSanPham = sanPhamBUS.getAllSanPham();
    
        ObservableList<SanPhamDTO> SPdata = FXCollections.observableArrayList(danhSachSanPham);
        tblSP.setItems(SPdata);
    }    

    
    
    
    
}
