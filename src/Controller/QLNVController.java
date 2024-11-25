package Controller;


import BUS.ChucVuBUS;
import BUS.NhanVienBUS;
import DTO.ChucVuDTO;
import DTO.NhanVienDTO;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class QLNVController {
    @FXML
    private Label btnThem;

    
    @FXML
    private Label btnSua;

    @FXML
    private Label btnXoa;

    @FXML
    private TableView<NhanVienDTO> tbBang;

    @FXML
    private TableColumn<NhanVienDTO, String> tbCHUCVU;

    @FXML
    private TableColumn<NhanVienDTO, String> tbDIACHI;

    @FXML
    private TableColumn<NhanVienDTO, String> tbEMAIL;

    @FXML
    private TableColumn<NhanVienDTO, String> tbGIOITINH;

    @FXML
    private TableColumn<NhanVienDTO, Integer> tbMANV;

    @FXML
    private TableColumn<NhanVienDTO, String> tbNGAYSINH;

    @FXML
    private TableColumn<NhanVienDTO, String> tbSDT;

    @FXML
    private TableColumn<NhanVienDTO, String> tbTENNV;
    
    @FXML
    private TextField txtTimKiem;

    private ObservableList<NhanVienDTO> list = FXCollections.observableArrayList();

    private NhanVienBUS nvBUS = new NhanVienBUS();
    private ChucVuBUS cvBUS = new ChucVuBUS();

    public void hienThiDSNhanVien() {
        list.clear();

        // Lấy danh sách nhân viên
        ArrayList<NhanVienDTO> arr = nvBUS.getAllNhanVienDTO();
        
        // Tạo bộ đệm (cache) chức vụ để tránh việc gọi lại cvBUS.getTenChucVuByMaChucVu()
        Map<Integer, String> chucVuCache = new HashMap<>();
        
        for (NhanVienDTO x : arr) {
            // Lưu thông tin chức vụ nếu chưa có trong bộ đệm
            String tenChucVu = chucVuCache.get(x.getMaChucVu());
            if (tenChucVu == null) {
                tenChucVu = cvBUS.getTenChucVuByMaChucVu(x.getMaChucVu());
                chucVuCache.put(x.getMaChucVu(), tenChucVu);
            }

            // Thêm dữ liệu vào danh sách
            list.add(new NhanVienDTO(
                x.getMaNV(),
                x.getTenNV(),
                x.isGioiTinh(), 
                x.getNgaySinh(), 
                x.getSoDienThoai(),
                x.getEmail(), 
                x.getDiaChi(), 
                x.getMaChucVu()));
        }

        // Cập nhật bảng sau khi dữ liệu đã có
        tbBang.setItems(list);

        // Cập nhật cột Chức Vụ
        tbCHUCVU.setCellValueFactory(cellData -> {
            NhanVienDTO nvDTO = cellData.getValue();
            String tenChucVu = chucVuCache.get(nvDTO.getMaChucVu());
            return new SimpleStringProperty(tenChucVu);
        });

        // Cập nhật cột Giới Tính
        tbGIOITINH.setCellValueFactory(cellData -> {
            NhanVienDTO nvDTO = cellData.getValue();
            return new SimpleStringProperty(nvDTO.isGioiTinh() ? "Nam" : "Nữ");
        });

        // Cập nhật cột Tên Nhân Viên
        tbTENNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenNV()));

        // Cập nhật cột Mã Nhân Viên
        tbMANV.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaNV()).asObject());

        // Cập nhật cột Địa Chỉ
        tbDIACHI.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiaChi()));

        // Cập nhật cột Email
        tbEMAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        // Cập nhật cột Số Điện Thoại
        tbSDT.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));

        // Cập nhật cột Ngày Sinh
        tbNGAYSINH.setCellValueFactory(cellData -> {
            NhanVienDTO nvDTO = cellData.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = nvDTO.getNgaySinh().toLocalDate().format(formatter);;  // Định dạng lại nếu cần
            return new SimpleStringProperty(formattedDate);
        });
    }


    @FXML
    public void initialize() {
        // Gọi phương thức để cập nhật dữ liệu cho TableView
        hienThiDSNhanVien();

        // Tạo FilteredList từ ObservableList
        FilteredList<NhanVienDTO> filterData = new FilteredList<>(list, nhanvien -> true);

        // Lắng nghe sự thay đổi trong TextField tìm kiếm
        txtTimKiem.textProperty().addListener((observable, oldvalue, newvalue) -> {
            filterData.setPredicate(nhanvien -> {
                // Nếu không nhập gì thì TableView hiển thị tất cả
                if (newvalue.isEmpty() || newvalue.isBlank() || newvalue == null) {
                    txtTimKiem.setPromptText("Tìm Kiếm Theo Tên Nhân Viên Hoặc Số Điện Thoại");
                    return true;
                }

                // Chuyển chuỗi tìm kiếm thành chữ thường
                String timKiem = newvalue.toLowerCase();
                
                // Kiểm tra tìm kiếm với tên nhân viên và số điện thoại
                if (nhanvien.getTenNV().toLowerCase().contains(timKiem)) {
                    return true; // Tìm kiếm tên
                } else if (nhanvien.getSoDienThoai().toLowerCase().contains(timKiem)) {
                    return true; // Tìm kiếm số điện thoại
                } else {
                    return false; // Không tìm thấy
                }
            });
        });

        // Tạo SortedList từ FilteredList và áp dụng sorting
        SortedList<NhanVienDTO> sortedData = new SortedList<>(filterData);

        // Liên kết comparator của SortedList với TableView để duy trì sắp xếp
        sortedData.comparatorProperty().bind(tbBang.comparatorProperty());

        // Gán SortedList vào TableView để hiển thị dữ liệu đã lọc và sắp xếp
        tbBang.setItems(sortedData); // setItems phải gọi trên TableView
    }


    private Stage popupStage;
    @FXML
    void ThemNV(MouseEvent event) {
        if(popupStage != null && popupStage.isShowing())
            return;
        try {
            // Nạp FXML của popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ThemNV.fxml"));
            Parent root = loader.load();
            
            ThemNVController themNVController = loader.getController();
            themNVController.setOnAddSuccessCallback(() -> hienThiDSNhanVien());

            // Tạo một cửa sổ Stage mới
            popupStage = new Stage();
            popupStage.setScene(new Scene(root)); // Thiết lập Scene cho popup
            popupStage.initStyle(StageStyle.TRANSPARENT); // hoặc StageStyle.TRANSPARENT để ẩn cả khung cửa sổ
            popupStage.setResizable(false);
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void SuaNV(MouseEvent event) {
        if(popupStage != null && popupStage.isShowing())
            return;
        try {
            boolean flag = false;
            int maNV = 0;
        // Kiểm tra nếu có dòng được chọn
            NhanVienDTO selectedRow = tbBang.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                // Lấy giá trị của dòng được chọn
                maNV = selectedRow.getMaNV();
                flag = true;
            }
            if(flag){
                // Nạp FXML của popup
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/SuaNV.fxml"));
                Parent root = loader.load();

                SuaNVController suaNVController = loader.getController();
                suaNVController.setMaNV(maNV);
                suaNVController.setOnSuaSuccessCallback(() -> hienThiDSNhanVien());

                // Tạo một cửa sổ Stage mới
                popupStage = new Stage();
                popupStage.setScene(new Scene(root)); // Thiết lập Scene cho popup
                popupStage.initStyle(StageStyle.TRANSPARENT); // hoặc StageStyle.TRANSPARENT để ẩn cả khung cửa sổ
                popupStage.setResizable(false);
                popupStage.show();
                tbBang.getSelectionModel().clearSelection();
            }
            else {
                JOptionPane.showMessageDialog(null,"Chưa Chọn Nhân Viên Để Sửa! Hãy Bấm Vào Dòng Có Nhân Viên Muốn Sửa");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void XoaNV(MouseEvent event) {
        int maNV = 0;
        // Kiểm tra nếu có dòng được chọn
        NhanVienDTO selectedRow = tbBang.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            // Lấy giá trị của dòng được chọn
            maNV = selectedRow.getMaNV();
        }
        boolean flag = nvBUS.deleteNhanVienDTO(maNV);
        if(flag){
            JOptionPane.showMessageDialog(null,"Xóa Nhân Viên Thành Công");
            hienThiDSNhanVien();
        }
        else{
            JOptionPane.showMessageDialog(null,"Xóa Nhân Viên Thất Bại");
        }
    }

    
}
