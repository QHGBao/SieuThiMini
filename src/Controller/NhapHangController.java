package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

import BUS.NhaCungCapBUS;
import BUS.PhieuNhapBUS;
import DTO.PhieuNhapDTO;
import DTO.ProductDTO;
import DTO.CTPhieuNhapDTO;
import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NhapHangController implements Initializable {

    @FXML
    private Pane btnAdd;

    @FXML
    private Label btnClose;

    @FXML
    private Pane btnDelete;

    @FXML
    private Label btnNhap;

    @FXML
    private Pane btnNhapHang;

    @FXML
    private Pane btnRepair;

    @FXML
    private Pane btnRepair1;

    @FXML
    private Button btnThemNCC;

    @FXML
    private ComboBox<NhaCungCapDTO> cbbNCC;

    @FXML
    private TableColumn<PhieuNhapDTO.tableSPchon, Integer> colGiaNhap;

    @FXML
    private TableColumn<ProductDTO, Integer> colMaSP;

    @FXML
    private TableColumn<PhieuNhapDTO.tableSPchon, Integer> colMaSPChon;

    @FXML
    private TableColumn<ProductDTO, Integer> colSL;

    @FXML
    private TableColumn<PhieuNhapDTO.tableSPchon, Integer> colSLChon;

    @FXML
    private TableColumn<ProductDTO, String> colTenSP;

    @FXML
    private TableColumn<PhieuNhapDTO.tableSPchon, String> colTenSPChon;

    @FXML
    private TableView<ProductDTO> tableSP;

    @FXML
    private TableView<PhieuNhapDTO.tableSPchon> tableSelected;

    @FXML
    private TextField txtGiaNHap;

    @FXML
    private TextField txtMaPN;

    @FXML
    private TextField txtNhanVien;

    @FXML
    private TextField txtQuatity;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label txtTongTien;

    @FXML
    private ImageView iconNH;

    private Stage popupStage;
    private String thaotac = "Tạo";
    private NhanVienDTO nvLogin;
    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    private PhieuNhapBUS pnBUS = new PhieuNhapBUS();
    private PhieuNhapDTO.tablePNDTO pn;
    private ObservableList<NhaCungCapDTO> dsNCC;
    private ObservableList<ProductDTO> dsSP;
    private ObservableList<PhieuNhapDTO.tableSPchon> dsSPchon;
    private PhieuNhapController pnController;

    public void setPnController(PhieuNhapController pnController){
        this.pnController = pnController;
    }

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void alertMessage(String message){
        if(message.contains("thành công")){
            Alert alSuccess = new Alert(AlertType.INFORMATION);
            alSuccess.setTitle("Thông báo");
            alSuccess.setHeaderText("Thành công");
            alSuccess.setContentText(message);
            alSuccess.showAndWait();
        } else {
            Alert alFail = new Alert(AlertType.ERROR);
            alFail.setTitle("Thông báo");
            alFail.setHeaderText("Thất bại");
            alFail.setContentText(message);
            alFail.showAndWait();
        }
    }

    public void setNvLogin(NhanVienDTO nvLogin){
        this.nvLogin = nvLogin;
    }

    public void settinhTong() {
        int total = 0;
        for(PhieuNhapDTO.tableSPchon x : dsSPchon)
            total += x.getSoLuong()*x.getGiaNhap();
        String TongTien = String.format("%,d", total).replace(',', '.') + "đ";
        txtTongTien.setText(TongTien);
    }

    public void setSuaPN(String keyWord){
        if(keyWord.equals("Sửa")){
            thaotac = keyWord;
            txtMaPN.setText(String.valueOf(pn.getMaPN()));
            txtNhanVien.setText(pn.getTenNV());
            for(NhaCungCapDTO ncc : dsNCC){
                if(pn.getTenNCC().equals(ncc.getTenNCC())){
                    cbbNCC.setValue(ncc);
                    break;
                }
            }
            dsSPchon.setAll(pnBUS.getAllRowSPChon(pn.getMaPN()));
            tableSelected.refresh();
            settinhTong();
            btnNhap.setText("Cập Nhật");
            btnNhap.setStyle(btnNhap.getStyle() + "-fx-background-color: #6DCD8C");
        } else {
            txtMaPN.setText(String.valueOf(pnBUS.createNewCode()));
            txtNhanVien.setText(nvLogin.getTenNV());
        }
    }

    public void setPhieuNhap(PhieuNhapDTO.tablePNDTO pn){
        this.pn = pn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dsNCC = FXCollections.observableArrayList(nccBUS.getAllNCC());
        dsSP = FXCollections.observableArrayList(pnBUS.getAllSP());
        dsSPchon = FXCollections.observableArrayList();
        cbbNCC.setItems(dsNCC);
        colMaSP.setCellValueFactory(new PropertyValueFactory<ProductDTO, Integer>("maSP"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("tenSP"));
        colSL.setCellValueFactory(new PropertyValueFactory<ProductDTO, Integer>("soLuong"));
        tableSP.setItems(dsSP);
        colMaSPChon.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        colTenSPChon.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colSLChon.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        tableSelected.setItems(dsSPchon);
    }

    @FXML
    void btnAddClicked(MouseEvent event) {
        if(tableSP.getSelectionModel().getSelectedItem() != null){
            try {
                String quantityText = txtQuatity.getText();
                String priceText = txtGiaNHap.getText();
                int quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Số lượng phải là số nguyên dương!");
                    return;
                }
                int price = Integer.parseInt(priceText);
                if (price <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Giá phải là số nguyên dương!");
                    return;
                }
                boolean checkSp = true;
                ProductDTO sp = (ProductDTO) tableSP.getSelectionModel().getSelectedItem();
                for(PhieuNhapDTO.tableSPchon x : dsSPchon){
                    if(sp.getTenSP().equals(x.getTenSP())){
                        checkSp = false;
                        break;
                    }
                }
                if(checkSp){
                    PhieuNhapDTO pn = new PhieuNhapDTO();
                    PhieuNhapDTO.tableSPchon newRowSP = pn.new tableSPchon();
                    newRowSP.setTenSP(sp.getTenSP());
                    newRowSP.setMaSP(sp.getMaSP());
                    newRowSP.setSoLuong(quantity);
                    newRowSP.setGiaNhap(price);
                    dsSPchon.add(newRowSP);
                    txtGiaNHap.setEditable(false);
                    txtGiaNHap.setText("");
                    txtQuatity.setEditable(false);
                    txtQuatity.setText("");
                    tableSP.getSelectionModel().clearSelection();
                    settinhTong();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Sản phẩm đã được thêm");
                    alert.setContentText("Vui lòng chọn sản phẩm khác !");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Vui lòng nhập đúng định dạng số!");
            }
        }
    }

    @FXML
    void btnCloseClicked(MouseEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDeleteClicked(MouseEvent event) {
        if(tableSelected.getSelectionModel().getSelectedItem() != null)
            dsSPchon.remove(tableSelected.getSelectionModel().getSelectedItem());
        tableSelected.refresh();
        settinhTong();
    }

    @FXML
    void btnNhapHangClicked(MouseEvent event) {
        if(!thaotac.equals("Sửa")){
            if(!dsSPchon.isEmpty()){
                if(cbbNCC.getSelectionModel().getSelectedItem() != null){
                    PhieuNhapDTO pn = new PhieuNhapDTO();
                    pn.setMaPN(Integer.parseInt(txtMaPN.getText()));
                    pn.setNgayLap(new Timestamp(System.currentTimeMillis()));
                    pn.setMaNV(nvLogin.getMaNV());
                    pn.setMaNCC(cbbNCC.getSelectionModel().getSelectedItem().getMaNCC());
                    pn.setIs_Deleted(0);
                    alertMessage(pnBUS.themPN(pn));
                    for(PhieuNhapDTO.tableSPchon x : dsSPchon){
                        CTPhieuNhapDTO ctpn = new CTPhieuNhapDTO();
                        ctpn.setMaPN(pn.getMaPN());
                        ctpn.setMaSP(x.getMaSP());
                        ctpn.setSoLuong(x.getSoLuong());
                        ctpn.setGiaNhap(x.getGiaNhap());
                        pnBUS.themCTPN(ctpn);
                    }
                    pnController.refreshTable();
                    Stage stage = (Stage) btnNhapHang.getScene().getWindow();
                    stage.close();
                } else{
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Lỗi");
                    errorAlert.setHeaderText("Thất bại");
                    errorAlert.setContentText("Vui lòng chọn nhà cung cấp !!");
                    errorAlert.showAndWait();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Lỗi");
                errorAlert.setHeaderText("Thất bại");
                errorAlert.setContentText("Vui lòng chọn sản phẩm !!");
                errorAlert.showAndWait();
            }
        } else {
            if(!dsSPchon.isEmpty()){
                if(cbbNCC.getSelectionModel().getSelectedItem() != null){
                    PhieuNhapDTO pn = new PhieuNhapDTO();
                    pn.setMaPN(Integer.parseInt(txtMaPN.getText()));
                    pn.setNgayLap(new Timestamp(System.currentTimeMillis()));
                    pn.setMaNCC(cbbNCC.getSelectionModel().getSelectedItem().getMaNCC());
                    pn.setIs_Deleted(0);
                    alertMessage(pnBUS.capNhatPN(pn));
                    if(pnBUS.deleteAllCTPN(pn.getMaPN())){
                        for(PhieuNhapDTO.tableSPchon x : dsSPchon){
                            CTPhieuNhapDTO ctpn = new CTPhieuNhapDTO();
                            ctpn.setMaPN(pn.getMaPN());
                            ctpn.setMaSP(x.getMaSP());
                            ctpn.setSoLuong(x.getSoLuong());
                            ctpn.setGiaNhap(x.getGiaNhap());
                            pnBUS.themCTPN(ctpn);
                        }
                    }
                    pnController.refreshTable();
                    Stage stage = (Stage) btnNhapHang.getScene().getWindow();
                    stage.close();
                } else{
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Lỗi");
                    errorAlert.setHeaderText("Thất bại");
                    errorAlert.setContentText("Vui lòng chọn nhà cung cấp !!");
                    errorAlert.showAndWait();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Lỗi");
                errorAlert.setHeaderText("Thất bại");
                errorAlert.setContentText("Vui lòng chọn sản phẩm !!");
                errorAlert.showAndWait();
            }
        }
    }

    @FXML
    void btnRepairClicked(MouseEvent event) {
        if(tableSelected.getSelectionModel().getSelectedItem() != null){
            PhieuNhapDTO.tableSPchon selected = (PhieuNhapDTO.tableSPchon) tableSelected.getSelectionModel().getSelectedItem();
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nhập số lượng");
            dialog.setHeaderText("Vui lòng nhập số lượng sản phẩm:");
            dialog.setContentText("Số lượng:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int soLuong = Integer.parseInt(result.get());
                    if (soLuong > 0) {
                        for(PhieuNhapDTO.tableSPchon x : dsSPchon){
                            if(selected.getMaSP() == x.getMaSP()){
                                x.setSoLuong(soLuong);
                                tableSelected.refresh();
                                settinhTong();
                                break;
                            }
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Số lượng phải lớn hơn 0!");
                    }
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập một số nguyên hợp lệ!");
                }
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Thông Báo", "Bạn đã hủy thao tác.");
            }
        }
    }

    @FXML
    void btnRepair1Clicked(MouseEvent event) {
        if(tableSelected.getSelectionModel().getSelectedItem() != null){
            PhieuNhapDTO.tableSPchon selected = (PhieuNhapDTO.tableSPchon) tableSelected.getSelectionModel().getSelectedItem();
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nhập giá nhập");
            dialog.setHeaderText("Vui lòng nhập giá nhập sản phẩm:");
            dialog.setContentText("Giá nhập:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int giaNhap = Integer.parseInt(result.get());
                    if (giaNhap > 0) {
                        for(PhieuNhapDTO.tableSPchon x : dsSPchon){
                            if(selected.getMaSP() == x.getMaSP()){
                                x.setGiaNhap(giaNhap);
                                tableSelected.refresh();
                                settinhTong();
                                break;
                            }
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Giá nhập phải lớn hơn 0!");
                    }
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập một số nguyên dương hợp lệ!");
                }
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Thông Báo", "Bạn đã hủy thao tác.");
            }
        }
    }

    public void refreshCbb(){
        cbbNCC.getItems().clear();
        cbbNCC.getItems().addAll(nccBUS.getAllNCC());
    }

    @FXML
    void btnThemNCCnew(ActionEvent event) {
        if (popupStage != null && popupStage.isShowing())
            return;
        try {   
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/formNCC.fxml"));
            Parent parent = loader.load();
            formNCCController popAdd = loader.getController();
            Scene scene = new Scene(parent);
            popupStage = new Stage();
            popAdd.setOption("Tạo");
            popAdd.setNhapHangController(this);
            popupStage.setScene(scene);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchNameSanPham(KeyEvent event) {
        dsSP.clear();
        dsSP.addAll(pnBUS.searchSpArray(txtSearch.getText()));
    }

    @FXML
    void tableSPClicked(MouseEvent event) {
        if(tableSP.getSelectionModel().getSelectedItem() != null){
            txtGiaNHap.setEditable(true);
            txtQuatity.setEditable(true);
        }
    }
}