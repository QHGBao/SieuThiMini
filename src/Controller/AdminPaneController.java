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

    private int maNV;

    public void setMaNV(int maNV) {
        this.maNV = maNV;
        // Thực hiện các thao tác khởi tạo giao diện hoặc dữ liệu dựa vào mã nhân viên
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
    void handlePhanQuyenAction(ActionEvent event) {
        loadContent("PhanQuyenGUI.fxml");
    }

    @FXML
    void HandlePhieuHuyAction(ActionEvent event) {
        loadContent("CreateCancellationGUI.fxml");
    }

    @FXML
    void HandleKhachHangAction(ActionEvent event) {
        loadContent("QLKHGUI.fxml");
    }

    @FXML
    void HandleNhanVienAction(ActionEvent event) {
        loadContent("QLNVGUI.fxml");
    }

    @FXML
    void handleQLBHAction(ActionEvent event) {
        loadContent("QLBHGUI.fxml");

    }

    private void loadContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/" + fxmlFile));
            Pane newContent = loader.load(); // Load FXML
    
            // Nếu là QLBHGUI, truyền mã nhân viên qua controller
            if (fxmlFile.equals("QLBHGUI.fxml")) {
                QLBHController qlbhController = loader.getController();
                qlbhController.setMaNV(maNV); // Truyền mã nhân viên
            }
    
            // Thay thế nội dung của contentPane bằng nội dung mới
            contentPane.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
