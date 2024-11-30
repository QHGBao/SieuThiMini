package Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import BUS.PhieuNhapBUS;
import DTO.PhieuNhapDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChiTietPNController implements Initializable {

    @FXML
    private Label btnClose;

    @FXML
    private ImageView btnPrintPDF;

    @FXML
    private TableColumn<PhieuNhapDTO.tableCtPNDTO, Integer> colGiaNhap;

    @FXML
    private TableColumn<?, String> colSTT;

    @FXML
    private TableColumn<PhieuNhapDTO.tableCtPNDTO, Integer> colSoLuong;

    @FXML
    private TableColumn<PhieuNhapDTO.tableCtPNDTO, String> colTenSP;

    @FXML
    private TableColumn<PhieuNhapDTO.tableCtPNDTO, Integer> colThanhTien;

    @FXML
    private TableView<PhieuNhapDTO.tableCtPNDTO> tableCTPN;

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

    private PhieuNhapDTO.tablePNDTO pn;
    private PhieuNhapBUS pnBUS = new PhieuNhapBUS();
    private ObservableList<PhieuNhapDTO.tableCtPNDTO> dsCTPn;

    public void setPhieuNhap(PhieuNhapDTO.tablePNDTO pn){
        this.pn = pn;
        if (pn != null) {
            dsCTPn = FXCollections.observableArrayList(pnBUS.getAllRowCT(pn.getMaPN()));
            setLayout();
            tableCTPN.setItems(dsCTPn);
        }
    }

    public void setLayout(){
        txtMaPN.setText(String.valueOf(pn.getMaPN()));
        txtNCC.setText(pn.getTenNCC());
        txtUser.setText(pn.getTenNV());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String thoigian = sdf.format(pn.getNgayLap());
        txtThoiGian.setText(thoigian);
        int total = 0;
        for (PhieuNhapDTO.tableCtPNDTO x: pnBUS.getAllRowCT(pn.getMaPN())){
            total = total+x.getThanhTien();
        }
        String TongTien = String.format("%,d", total).replace(',', '.') + "đ";
        txtTongTien.setText(TongTien);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colSTT.setCellValueFactory(param -> {
            int index = tableCTPN.getItems().indexOf(param.getValue());
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(index + 1));
        });
        colTenSP.setCellValueFactory(new PropertyValueFactory<PhieuNhapDTO.tableCtPNDTO, String>("tenSP"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<PhieuNhapDTO.tableCtPNDTO, Integer>("soLuong"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<PhieuNhapDTO.tableCtPNDTO, Integer>("giaNhap"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<PhieuNhapDTO.tableCtPNDTO, Integer>("thanhTien"));
    }

    @FXML
    void btnCloseClicked(MouseEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnPrintPDFClicked(MouseEvent event) {
        
    }
}
