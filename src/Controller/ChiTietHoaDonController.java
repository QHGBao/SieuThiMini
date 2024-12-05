package Controller;

import java.util.ArrayList;

import BUS.HoaDonBUS;
import DTO.CTHoaDonDTO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ChiTietHoaDonController {

    @FXML
    private TableView<CTHoaDonDTO> CTHoaDonTableView;

    @FXML
    private Label TongTienLB;

    @FXML
    private TableColumn<CTHoaDonDTO, Integer> sellColPrice;

    @FXML
    private TableColumn<CTHoaDonDTO, String> sellColProduct;

    @FXML
    private TableColumn<CTHoaDonDTO, Integer> sellColQuantity;

    @FXML
    private TableColumn<CTHoaDonDTO, Integer> sellColSumPrice;

    private HoaDonBUS hoaDonBUS = new HoaDonBUS();

    @FXML
    void handleCancel(ActionEvent event) {
        Stage stage = (Stage) TongTienLB.getScene().getWindow(); // Lấy đối tượng Stage của cửa sổ hiện tại
        stage.close(); // Đóng cửa sổ
    }

    public void loadChiTietHoaDon(int maHD) {
        // Lấy danh sách chi tiết hóa đơn từ DAO
        ArrayList<CTHoaDonDTO> chiTietHoaDonData = hoaDonBUS.getChiTietHoaDonByMaHD(maHD);

        // Chuyển dữ liệu thành ObservableList để hiển thị trong TableView
        ObservableList<CTHoaDonDTO> chiTietHoaDonList = FXCollections.observableArrayList(chiTietHoaDonData);

        // Cấu hình các cột trong TableView
        sellColProduct.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTenSP()));
        sellColQuantity.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSoLuong()).asObject());
        sellColPrice.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getGiaBan()).asObject());

        // Thành tiền = Giá * Số lượng
        sellColSumPrice
                .setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getThanhTien()).asObject());

        // Đưa dữ liệu vào TableView
        CTHoaDonTableView.setItems(chiTietHoaDonList);

        int tongTien = 0;
        for (CTHoaDonDTO cthd : chiTietHoaDonData) {
            tongTien += cthd.getThanhTien(); // Cộng thành tiền của từng sản phẩm
        }
        TongTienLB.setText(String.format("%,d VNĐ", tongTien));
    }

}
