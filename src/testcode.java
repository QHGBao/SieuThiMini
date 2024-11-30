import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// formNCC
// NhaCungCapGUI
// ChiTietPN
// PhieuNhapGUI
// NhapHang

public class testcode extends Application{
    @Override
    public void start(Stage primaryStage) {
        try {
<<<<<<< HEAD
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/PhanQuyenGUI.fxml"));  
=======
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/PhieuNhapGUI.fxml"));  
>>>>>>> 7c373f9859b7aae3c1607927e754ca9d12ee1d39
            Scene scene = new Scene(root);
            primaryStage.setTitle("QL NhaCungCap Page");
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
