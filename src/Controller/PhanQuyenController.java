package Controller;

import java.util.ArrayList;

import BUS.PhanQuyenBUS;
import DTO.PhanQuyenDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;

public class PhanQuyenController {
    @FXML
    private ComboBox<String> cbbChucvu;

    private PhanQuyenBUS phanQuyenBUS;

    public PhanQuyenController() {
        phanQuyenBUS = new PhanQuyenBUS();
    }

    @FXML
    private void initialize() {
        loadTenQuyenToComboBox();
    }

    private void loadTenQuyenToComboBox() {
        // Lấy danh sách tất cả quyền
        ArrayList<PhanQuyenDTO> dsQuyen = phanQuyenBUS.getALLQuyen();

        // Trích xuất cột `TenQuyen` và thêm vào ComboBox
        for (PhanQuyenDTO quyen : dsQuyen) {
            cbbChucvu.getItems().add(quyen.getTenQuyen());
        }

        // Nếu muốn chọn giá trị mặc định
        if (!dsQuyen.isEmpty()) {
            cbbChucvu.setValue(dsQuyen.get(0).getTenQuyen());
        }
    }

    @FXML
    private Button btnSave;

    @FXML
    private CheckBox checkTt1;

    @FXML
    private CheckBox checkTt2;

    @FXML
    private CheckBox checkTt3;

    @FXML
    private CheckBox checkTt4;

    @FXML
    private CheckBox checkTt5;

    @FXML
    private CheckBox checkTt6;

    @FXML
    private CheckBox checkTt7;

    @FXML
    private CheckBox checkTt8;

    @FXML
    private CheckBox checkTt9;

    @FXML
    private CheckBox checkXem1;

    @FXML
    private CheckBox checkXem2;

    @FXML
    private CheckBox checkXem3;

    @FXML
    private CheckBox checkXem4;

    @FXML
    private CheckBox checkXem5;

    @FXML
    private CheckBox checkXem6;

    @FXML
    private CheckBox checkXem7;

    @FXML
    private CheckBox checkXem8;

    @FXML
    private CheckBox checkXem9;

    @FXML
    private TableColumn<?, ?> clName;

    @FXML
    private TableColumn<?, ?> clSTT;

    @FXML
    private TitledPane ncc;

    @FXML
    private TitledPane qlhd;

    @FXML
    private TitledPane qlpn;

    @FXML
    private TitledPane qlpq;

    @FXML
    private TitledPane qlsp;

    @FXML
    private TitledPane qltk;

    @FXML
    private TitledPane qltkhd;

    @FXML
    private TitledPane qltknh;

    @FXML
    private TitledPane qltkt;

    @FXML
    private TableView<?> tbDS;

}
