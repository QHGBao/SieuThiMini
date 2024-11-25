package Controller;

import java.io.IOException;

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
    void handlePhanQuyenAction(ActionEvent event) {
        loadContent("PhanQuyenGUI.fxml");
    }

    @FXML
    void HandlePhieuHuyAction(ActionEvent event) {
        loadContent("Review+DeleteCancellationGUI.fxml");
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
