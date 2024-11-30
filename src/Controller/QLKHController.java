package Controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QLKHController {
    @FXML
    private Label btnSua;

    @FXML
    private Label btnThem;

    @FXML
    private TableView<KhachHangDTO> tbBang;

    @FXML
    private TableColumn<KhachHangDTO, Integer> tbDiemTichLuy;

    @FXML
    private TableColumn<KhachHangDTO, Integer> tbMaKH;

    @FXML
    private TableColumn<KhachHangDTO, String> tbSDT;

    @FXML
    private TableColumn<KhachHangDTO, String> tbTenKH;

    @FXML
    private TextField txtTimKiem;
    
    private Stage popupStage;
    private KhachHangBUS khBUS = new KhachHangBUS();
    private ObservableList<KhachHangDTO> list = FXCollections.observableArrayList();

    public void hienThiDSKhachHang() {
        list.clear();

        // Lấy danh sách nhân viên
        ArrayList<KhachHangDTO> arr = khBUS.getAllKhachHangDTO();
        
        for (KhachHangDTO x : arr) {
            // Thêm dữ liệu vào danh sách
            list.add(new KhachHangDTO(
                x.getMaKH(),
                x.getTenKH(),
                x.getSoDienThoai(),
                x.getDiemTichLuy()));
        }

        // Cập nhật bảng sau khi dữ liệu đã có
        tbBang.setItems(list);
        // Cập nhật cột Tên Nhân Viên
        tbTenKH.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenKH()));

        // Cập nhật cột Mã Nhân Viên
        tbMaKH.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaKH()).asObject());
        // Cập nhật cột Điểm Tích Lũy
        tbDiemTichLuy.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDiemTichLuy()).asObject());

        // Cập nhật cột Số Điện Thoại
        tbSDT.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
    }

    @FXML
    public void initialize() {
        // Gọi phương thức để cập nhật dữ liệu cho TableView
        hienThiDSKhachHang();

        // Tạo FilteredList từ ObservableList
        FilteredList<KhachHangDTO> filterData = new FilteredList<>(list, khachhang -> true);

        // Lắng nghe sự thay đổi trong TextField tìm kiếm
        txtTimKiem.textProperty().addListener((observable, oldvalue, newvalue) -> {
            filterData.setPredicate(khachhang -> {
                // Nếu không nhập gì thì TableView hiển thị tất cả
                if (newvalue.isEmpty() || newvalue.isBlank() || newvalue == null) {
                    return true;
                }

                // Chuyển chuỗi tìm kiếm thành chữ thường
                String timKiem = newvalue.toLowerCase();
                
                // Kiểm tra tìm kiếm với tên nhân viên và số điện thoại
                if (khachhang.getSoDienThoai().toLowerCase().contains(timKiem)) {
                    return true; // Tìm kiếm số điện thoại
                } else {
                    return false; // Không tìm thấy
                }
            });
            
            
        });

        // Tạo SortedList từ FilteredList và áp dụng sorting
        SortedList<KhachHangDTO> sortedData = new SortedList<>(filterData);

        // Liên kết comparator của SortedList với TableView để duy trì sắp xếp
        sortedData.comparatorProperty().bind(tbBang.comparatorProperty());

        // Gán SortedList vào TableView để hiển thị dữ liệu đã lọc và sắp xếp
        tbBang.setItems(sortedData); // setItems phải gọi trên TableView
        // txtTimKiem.setPromptText("Tìm Kiếm Theo Số Điện Thoại");
    }

    @FXML
    void SuaKH(MouseEvent event) {
        if(popupStage != null && popupStage.isShowing())
            return;
        try {
            boolean flag = false;
            int maKH = 0;
        // Kiểm tra nếu có dòng được chọn
            KhachHangDTO selectedRow = tbBang.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                // Lấy giá trị của dòng được chọn
                maKH = selectedRow.getMaKH();
                flag = true;
            }
            if(flag){
                // Nạp FXML của popup
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/SuaKH.fxml"));
                Parent root = loader.load();
                SuaKHController suaKHController = loader.getController();
                suaKHController.setMaKH(maKH);
                suaKHController.setOnSuaSuccessCallback(() -> hienThiDSKhachHang());

                // Tạo một cửa sổ Stage mới
                popupStage = new Stage();
                popupStage.setScene(new Scene(root)); // Thiết lập Scene cho popup
                popupStage.initStyle(StageStyle.TRANSPARENT); // hoặc StageStyle.TRANSPARENT để ẩn cả khung cửa sổ
                popupStage.setResizable(false);
                popupStage.show();
                tbBang.getSelectionModel().clearSelection();
            }
            else {
                JOptionPane.showMessageDialog(null,"Chưa Chọn Khách Hàng Để Sửa! Hãy Bấm Vào Dòng Có Khách Hàng Muốn Sửa");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ThemKH(MouseEvent event) {
        if(popupStage != null && popupStage.isShowing())
            return;
        try {
            // Nạp FXML của popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ThemKH.fxml"));
            Parent root = loader.load();
            
            ThemKHController themKHController = loader.getController();
            themKHController.setOnAddSuccessCallback(() -> hienThiDSKhachHang());

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

}
