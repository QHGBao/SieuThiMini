package Controller;

import java.io.IOException;

import DTO.NhanVienDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminPaneController {

    private NhanVienDTO nvLogin;
    
    public void setNvLogin(NhanVienDTO nvLogin){
        this.nvLogin = nvLogin;
    }

    @FXML
    private StackPane contentPane;

    @FXML
    public void handleLogoutAction(ActionEvent event) throws IOException { // Xử lý sự kiện khi nhấn nút đăng xuất
        // Đóng cửa sổ hiện tại
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Mở lại cửa sổ đăng nhập
        Stage loginStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/LoginGUI.fxml"));
        Scene scene = new Scene(loader.load());
        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.setTitle("Login Page");
        loginStage.setScene(scene);
        loginStage.show();
    }

    @FXML
    void handleQLSPAction(ActionEvent event) {
        loadContent("QLSPGUI.fxml");

    }

    @FXML
    void handleNCCAction(ActionEvent event) {
        loadContent("NhaCungCapGUI.fxml");
    }

    @FXML
    void handlePNAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/PhieuNhapGUI.fxml"));
            Pane newContent = loader.load();
            PhieuNhapController pnController = loader.getController();
            pnController.setNvLogin(nvLogin);
            contentPane.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlePhanQuyenAction(ActionEvent event) {
        loadContent("PhanQuyenGUI.fxml");
    }

    @FXML
    void HandlePhieuHuyAction(ActionEvent event) {
        loadContent("Review+DeleteCancellationGUI.fxml");
    }

    @FXML
    void HandleKhachHangAction(ActionEvent event) {
        loadContent("QLKHGUI.fxml");
    }

    @FXML
    void HandleNhanVienAction(ActionEvent event) {
        loadContent("QLNVGUI.fxml");
    }

    private void loadContent(String fxmlFile) {
        try {
            Pane newContent = FXMLLoader.load(getClass().getResource("/GUI/" + fxmlFile));
            contentPane.getChildren().setAll(newContent); // Thay thế nội dung hiện tại
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
