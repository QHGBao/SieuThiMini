package Controller;

import java.util.ArrayList;

import BUS.PhanQuyenBUS;
import DTO.PhanQuyenDTO;
import DTO.TaiKhoan_DTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PhanQuyenController {

    @FXML
    private ComboBox<String> cbbChucVu;

    private PhanQuyenBUS phanQuyenBUS;

    public PhanQuyenController() {
        phanQuyenBUS = new PhanQuyenBUS();
    }

    @FXML
    private void initialize() {
        loadTenQuyenToComboBox();
        setupTableColumns();
    }

    private void loadTenQuyenToComboBox() {
        // Lấy danh sách tất cả quyền
        ArrayList<PhanQuyenDTO> dsQuyen = phanQuyenBUS.getALLQuyen();

        // Trích xuất cột `TenQuyen` và thêm vào ComboBox
        for (PhanQuyenDTO quyen : dsQuyen) {
            cbbChucVu.getItems().add(quyen.getTenQuyen());
        }

        // Nếu muốn chọn giá trị mặc định
        if (!dsQuyen.isEmpty()) {
            cbbChucVu.setValue(dsQuyen.get(0).getTenQuyen());
        }

        cbbChucVu.setOnAction(event -> {
            String selectedTenQuyen = cbbChucVu.getSelectionModel().getSelectedItem();
            PhanQuyenDTO selectedQuyen = getPhanQuyenByTenQuyen(selectedTenQuyen);
            if (selectedQuyen != null) {
                loadTaiKhoanByQuyen(selectedQuyen.getMaQuyen());
            }
        });
    }

    private PhanQuyenDTO getPhanQuyenByTenQuyen(String tenQuyen) {
        ArrayList<PhanQuyenDTO> dsQuyen = phanQuyenBUS.getALLQuyen();
        for (PhanQuyenDTO quyen : dsQuyen) {
            if (quyen.getTenQuyen().equals(tenQuyen)) {
                return quyen;
            }
        }
        return null;
    }

    private void loadTaiKhoanByQuyen(int maQuyen) {
        ArrayList<TaiKhoan_DTO> dsTaiKhoan = phanQuyenBUS.getTaiKhoanByQuyen(maQuyen);
        ObservableList<TaiKhoan_DTO> observableList = FXCollections.observableArrayList(dsTaiKhoan);
        tbDS.setItems(observableList);
    }

    private void setupTableColumns() {
        clSTT.setCellFactory(column -> new TableCell<TaiKhoan_DTO, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= tbDS.getItems().size()) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1)); // Tự động đánh số thứ tự
                }
            }
        });
        clName.setCellValueFactory(new PropertyValueFactory<>("tenTK"));
    }

    @FXML
    private TableColumn<TaiKhoan_DTO, String> clName;

    @FXML
    private TableColumn<TaiKhoan_DTO, Integer> clSTT;

    @FXML
    private TableView<TaiKhoan_DTO> tbDS;

}
