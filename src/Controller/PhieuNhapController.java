package Controller;

<<<<<<< HEAD
public class PhieuNhapController {
    
=======
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BUS.PhieuNhapBUS;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PhieuNhapController implements Initializable {
    @FXML
    private Pane btnDelete;

    @FXML
    private Pane btnDuyet;

    @FXML
    private Label btnExcel;

    @FXML
    private Label btnNhap;

    @FXML
    private Pane btnRepair;

    @FXML
    private Pane btnReset;

    @FXML
    private Pane btnSee;

    @FXML
    private TableColumn<PhieuNhapDTO.tablePNDTO, Timestamp> colDate;

    @FXML
    private TableColumn<PhieuNhapDTO.tablePNDTO, Integer> colMaPN;

    @FXML
    private TableColumn<PhieuNhapDTO.tablePNDTO, String> colNCC;

    @FXML
    private TableColumn<PhieuNhapDTO.tablePNDTO, String> colNhanVien;

    @FXML
    private TableColumn<PhieuNhapDTO.tablePNDTO, String> colTrangThai;

    @FXML
    private TableView<PhieuNhapDTO.tablePNDTO> tablePN;

    @FXML
    private DatePicker txtNgBD;

    @FXML
    private DatePicker txtNgKT;

    @FXML
    private TextField txtSearch;
    

    private Stage popupStage;
    private PhieuNhapBUS pnBUS = new PhieuNhapBUS();
    private ObservableList<PhieuNhapDTO.tablePNDTO> dsTable;
    private NhanVienDTO nvLogin;

    public void setNvLogin(NhanVienDTO nvLogin){
        this.nvLogin = nvLogin;
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

    public void refreshTable(){
        dsTable.clear();
        dsTable.addAll(pnBUS.getAllRow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dsTable = FXCollections.observableArrayList(pnBUS.getAllRow());
        colMaPN.setCellValueFactory(new PropertyValueFactory<PhieuNhapDTO.tablePNDTO, Integer>("maPN"));
        colDate.setCellValueFactory(new PropertyValueFactory<PhieuNhapDTO.tablePNDTO, Timestamp>("ngayLap"));
        colNhanVien.setCellValueFactory(new PropertyValueFactory<PhieuNhapDTO.tablePNDTO, String>("tenNV"));
        colNCC.setCellValueFactory(new PropertyValueFactory<PhieuNhapDTO.tablePNDTO, String>("tenNCC"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<PhieuNhapDTO.tablePNDTO, String>("trangThai"));
        tablePN.setItems(dsTable);
    }

    @FXML
    void btnDeleteClicked(MouseEvent event) {
        if(tablePN.getSelectionModel().getSelectedItem() !=null){
            PhieuNhapDTO.tablePNDTO selected = (PhieuNhapDTO.tablePNDTO) tablePN.getSelectionModel().getSelectedItem();
            if(selected.getTrangThai().equals("Chờ Xử Lý")){
                Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
                confirmAlert.setTitle("Xác nhận xóa");
                confirmAlert.setHeaderText("Bạn có chắc muốn xóa không?");
                confirmAlert.setContentText("Việc này không thể hoàn tác.");
                ButtonType result = confirmAlert.showAndWait().orElse(ButtonType.CANCEL);
                if(result == ButtonType.OK){
                    alertMessage(pnBUS.xoaPN(selected.getMaPN()));
                    refreshTable();
                }
            } else {
                Alert alFail = new Alert(AlertType.ERROR);
                alFail.setTitle("Thông báo");
                alFail.setHeaderText("Thất bại");
                alFail.setContentText("Phiếu nhập đã duyệt không thể xóa !!");
                alFail.showAndWait();
            }
        }
    }

    @FXML
    void btnDeleteEntered(MouseEvent event) {
        String currentStyle = btnDelete.getStyle();
        btnDelete.setStyle(currentStyle + "-fx-background-color: #efe8d4;");
    }

    @FXML
    void btnDeleteExited(MouseEvent event) {
        String currentStyle = btnDelete.getStyle();
        btnDelete.setStyle(currentStyle + "-fx-background-color: transparent;");
    }

    @FXML
    void btnDuyetClicked(MouseEvent event) {
        if(tablePN.getSelectionModel().getSelectedItem() != null){
            PhieuNhapDTO.tablePNDTO selected = (PhieuNhapDTO.tablePNDTO) tablePN.getSelectionModel().getSelectedItem();
            if(selected.getTrangThai().equals("Chờ Xử Lý")){
                Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
                confirmAlert.setTitle("Xác nhận duyệt");
                confirmAlert.setHeaderText("Bạn có chắc muốn duyệt không?");
                confirmAlert.setContentText("Việc này không thể hoàn tác.");
                ButtonType result = confirmAlert.showAndWait().orElse(ButtonType.CANCEL);
                if (result == ButtonType.OK) {
                    alertMessage(pnBUS.duyetPN(selected.getMaPN()));
                    refreshTable();
                }
            }
        }
    }

    @FXML
    void btnDuyetEntered(MouseEvent event) {
        String currentStyle = btnDuyet.getStyle();
        btnDuyet.setStyle(currentStyle + "-fx-background-color: #efe8d4;");
    }

    @FXML
    void btnDuyetExited(MouseEvent event) {
        String currentStyle = btnDuyet.getStyle();
        btnDuyet.setStyle(currentStyle + "-fx-background-color: transparent;");
    }

    @FXML
    void btnExcelCliked(MouseEvent event) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("DS Phiếu");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Mã PN");
            headerRow.createCell(1).setCellValue("Ngày Lập");
            headerRow.createCell(2).setCellValue("Nhân Viên");
            headerRow.createCell(3).setCellValue("Nhà Cung Cấp");
            headerRow.createCell(4).setCellValue("Trạng Thái");
            for (int i = 0; i < tablePN.getItems().size(); i++) {
                PhieuNhapDTO.tablePNDTO pn = tablePN.getItems().get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(pn.getMaPN());
                row.createCell(1).setCellValue(pn.getNgayLap());
                row.createCell(2).setCellValue(pn.getTenNV());
                row.createCell(3).setCellValue(pn.getTenNCC());
                row.createCell(4).setCellValue(pn.getTrangThai());
            }
            try (FileOutputStream fileOut = new FileOutputStream("DanhSachPhieuNhap.xlsx")) {
                workbook.write(fileOut);
                workbook.close();
                Alert alSuccess = new Alert(AlertType.INFORMATION);
                alSuccess.setTitle("Thông báo");
                alSuccess.setHeaderText("Thành công");
                alSuccess.setContentText("Xuất file Excel thành công !!");
                alSuccess.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnNhapClicked(MouseEvent event) {
        if (popupStage != null && popupStage.isShowing())
            return;
        try {   
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/NhapHang.fxml"));
            Parent parent = loader.load();
            NhapHangController formNH = loader.getController();
            Scene scene = new Scene(parent);
            formNH.setNvLogin(nvLogin);
            formNH.setSuaPN("Tạo");
            popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnRepairClicked(MouseEvent event) {
        if(tablePN.getSelectionModel().getSelectedItems() != null){
            PhieuNhapDTO.tablePNDTO selected = (PhieuNhapDTO.tablePNDTO) tablePN.getSelectionModel().getSelectedItem();
            if(selected.getTrangThai().equals("Chờ Xử Lý")){
                if (popupStage != null && popupStage.isShowing())
                    return;
                try {   
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/NhapHang.fxml"));
                    Parent parent = loader.load();
                    NhapHangController form = loader.getController();
                    Scene scene = new Scene(parent);
                    form.setPhieuNhap(selected);
                    form.setSuaPN("Sửa");
                    popupStage = new Stage();
                    popupStage.setScene(scene);
                    popupStage.initStyle(StageStyle.UNDECORATED);
                    popupStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
        }
    }

    @FXML
    void btnRepairEntered(MouseEvent event) {
        String currentStyle = btnRepair.getStyle();
        btnRepair.setStyle(currentStyle + "-fx-background-color: #efe8d4;");
    }

    @FXML
    void btnRepairExited(MouseEvent event) {
        String currentStyle = btnRepair.getStyle();
        btnRepair.setStyle(currentStyle + "-fx-background-color: transparent;");
    }

    @FXML
    void btnResetClicked(MouseEvent event) {
        
    }

    @FXML
    void btnSeeClicked(MouseEvent event) {
        if(tablePN.getSelectionModel().getSelectedItem() != null){
            PhieuNhapDTO.tablePNDTO tableChoice = (PhieuNhapDTO.tablePNDTO) tablePN.getSelectionModel().getSelectedItem();
            tablePN.getSelectionModel().clearSelection();
            if (popupStage != null && popupStage.isShowing())
                return;
            try {   
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ChiTietPN.fxml"));
                Parent parent = loader.load();
                ChiTietPNController popUpCTPN = loader.getController();
                Scene scene = new Scene(parent);
                popUpCTPN.setPhieuNhap(tableChoice);
                popupStage = new Stage();
                popupStage.setScene(scene);
                popupStage.initStyle(StageStyle.UNDECORATED);
                popupStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnSeeEntered(MouseEvent event) {
        String currentStyle = btnSee.getStyle();
        btnSee.setStyle(currentStyle + "-fx-background-color: #efe8d4;");
    }

    @FXML
    void btnSeeExited(MouseEvent event) {
        String currentStyle = btnSee.getStyle();
        btnSee.setStyle(currentStyle + "-fx-background-color: transparent;");
    }

    @FXML
    void searchReleased(KeyEvent event) {
        dsTable.clear();
        dsTable.addAll(pnBUS.searchPnArray(txtSearch.getText()));
        tablePN.setItems(dsTable);
    }
>>>>>>> 7c373f9859b7aae3c1607927e754ca9d12ee1d39
}
