package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChiTietPNController {
    @FXML
    private Label btnClose;

    @FXML
    private ImageView btnPrintPDF;

    @FXML
    private TableColumn<?, ?> colGiaNhap;

    @FXML
    private TableColumn<?, ?> colSTT;

    @FXML
    private TableColumn<?, ?> colSoLuong;

    @FXML
    private TableColumn<?, ?> colTenSP;

    @FXML
    private TableColumn<?, ?> colThanhTien;

    @FXML
    private TableView<?> tableCTPN;

    @FXML
    private Label txtMaPN;

    @FXML
    private Label txtNCC;

    @FXML
    private Label txtThoiGian;

    @FXML
    private Label txtTongTien;

    @FXML
    private Label txtUser;

    @FXML
    void btnCloseClicked(MouseEvent event) {

    }

    @FXML
    void btnPrintPDFClicked(MouseEvent event) {

    }
}
