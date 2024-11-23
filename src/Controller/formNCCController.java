package Controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class formNCCController {

    @FXML
    private Label btnCancel;

    @FXML
    private ImageView btnCross;

    @FXML
    private Label btnSave;

    @FXML
    private Label warnDiachi;

    @FXML
    private Label warnNguoiLH;

    @FXML
    private Label warnSdt;

    @FXML
    private Label warnTen;

    @FXML
    void btnCancelClicked(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSaveClicked(MouseEvent event) {

    }

    @FXML
    void btnCloseForm(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
