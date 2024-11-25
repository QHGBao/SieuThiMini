package Controller;


import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class formNCCController {

    @FXML
    private Label labelTitle;

    @FXML
    private Pane containSave;

    @FXML
    private Label btnCancel;

    @FXML
    private ImageView btnCross;

    @FXML
    private Label btnSave;

     @FXML
    private TextField inputDiaChi;

    @FXML
    private TextField inputNguoiLH;

    @FXML
    private TextField inputSdt;

    @FXML
    private TextField inputTen;

    @FXML
    private Label warnDiachi;

    @FXML
    private Label warnNguoiLH;

    @FXML
    private Label warnSdt;

    @FXML
    private Label warnTen;

    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    private String option;
    private NhaCungCapDTO ncc;
    private NhaCungCapController nccController;

    public void setNccController(NhaCungCapController nccController){
        this.nccController = nccController;
    }

    public void setInforNCC(NhaCungCapDTO ncc){
        this.ncc = ncc;
    }

    public void setOption(String option) {
        this.option = option;
        if(option.equals("Sửa")){
            labelTitle.setText("Thông tin nhà cung cấp");
            inputTen.setText(ncc.getTenNCC());
            inputSdt.setText(ncc.getSdt());
            inputDiaChi.setText(ncc.getDiaChi());
            inputNguoiLH.setText(ncc.getNguoiLH());
        }
        if(option.equals("Tạo"))
            btnSave.setText("Tạo");
    }

    public boolean checkInput(){
        warnTen.setText("");
        warnSdt.setText("");
        warnDiachi.setText("");
        warnNguoiLH.setText("");
        boolean check = true;
        if(inputTen.getText().isEmpty()){
            warnTen.setText("Vui lòng nhập tên *");
            check = false;
        }
        if(inputSdt.getText().isEmpty()){
            warnSdt.setText("Vui lòng nhập số điện thoại *");
            check = false;
        } else if(!inputSdt.getText().matches("^0[0-9]{9}$")){
            warnSdt.setText("Vui lòng nhập đúng định dạng số điện thoại *");
            check = false;
        }
        if(inputDiaChi.getText().isEmpty()){
            warnDiachi.setText("Vui lòng nhập địa chỉ *");
            check = false;
        }
        if(inputNguoiLH.getText().isEmpty()) {
            warnNguoiLH.setText("Vui lòng nhập tên ngưới liên hệ *");
            check = false;
        }
        return check;
    }

    public void alertMessage(String message){
        if(message.contains("thành công")){
            Alert alSuccess = new Alert(AlertType.INFORMATION);
            alSuccess.setTitle("Thông báo");
            alSuccess.setHeaderText("Thành công");
            alSuccess.setContentText(message);
            alSuccess.showAndWait();
            Stage stage = (Stage) containSave.getScene().getWindow();
            stage.close();
        } else {
            Alert alFail = new Alert(AlertType.ERROR);
            alFail.setTitle("Thông báo");
            alFail.setHeaderText("Thất bại");
            alFail.setContentText(message);
            alFail.showAndWait();
            Stage stage = (Stage) containSave.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void btnCancelClicked(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnCancelEntered(MouseEvent event) {
        String currentStyle = btnCancel.getStyle();
        btnCancel.setStyle(currentStyle + "-fx-background-color:  #C0C0C0;");
    }

    @FXML
    void btnCancelExited(MouseEvent event) {
        String currentStyle = btnCancel.getStyle();
        btnCancel.setStyle(currentStyle + "-fx-background-color: transparent;");
    }

    @FXML
    void btnSaveClicked(MouseEvent event) {
        if(option.equals("Tạo")){
            NhaCungCapDTO ncc = new NhaCungCapDTO();
            if(checkInput()){
                ncc.setMaNCC(nccBUS.taoMaNCC());
                ncc.setTenNCC(inputTen.getText());
                ncc.setSdt(inputSdt.getText());
                ncc.setDiaChi(inputDiaChi.getText());
                ncc.setNguoiLH(inputNguoiLH.getText());
                ncc.setIs_Deleted(0);
                alertMessage(nccBUS.themNCC(ncc));
                nccController.refreshDataNCC();
            }
        }
        if(option.equals("Sửa")){
            if(checkInput()){
                ncc.setTenNCC(inputTen.getText());
                ncc.setDiaChi(inputDiaChi.getText());
                ncc.setSdt(inputSdt.getText());
                ncc.setNguoiLH(inputNguoiLH.getText());
                alertMessage(nccBUS.capNhatNCC(ncc));
                nccController.refreshDataNCC();;
            }
        }
    }

    @FXML
    void btnSaveEntered(MouseEvent event) {
        String currentStyle = btnSave.getStyle();
        btnSave.setStyle(currentStyle + "-fx-background-color:   #CFEBFC;");
    }

    @FXML
    void btnSaveExited(MouseEvent event) {
        String currentStyle = btnSave.getStyle();
        btnSave.setStyle(currentStyle + "-fx-background-color: transparent;");
    }

    @FXML
    void btnCloseForm(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
