package Controller;

import java.util.ArrayList;
import java.util.List;

import BUS.PhanQuyenBUS;
import DTO.PhanQuyenDTO;
import DTO.TaiKhoan_DTO;
import DTO.CTPhanQuyenDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PhanQuyenController {
    @FXML
    private TableColumn<TaiKhoan_DTO, String> clName;
    @FXML
    private TableColumn<TaiKhoan_DTO, Integer> clSTT;
    @FXML
    private TableView<TaiKhoan_DTO> tbDS;
    @FXML
    private ComboBox<String> cbbChucVu;
    @FXML
    private ArrayList<CheckBox> xemCheckBoxes;
    @FXML
    private ArrayList<CheckBox> thaoTacCheckBoxes;

    private final PhanQuyenBUS phanQuyenBUS;
    private PhanQuyenDTO selectedQuyen;
    private ArrayList<CTPhanQuyenDTO> selectedPermissions;

    public PhanQuyenController() {
        phanQuyenBUS = new PhanQuyenBUS();
        selectedPermissions = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        xemCheckBoxes = new ArrayList<>();
        thaoTacCheckBoxes = new ArrayList<>();

        xemCheckBoxes.addAll(List.of(xem1, xem2, xem3, xem4, xem5, xem6, xem7, xem8, xem9, xem10, xem11));
        thaoTacCheckBoxes.addAll(List.of(thaoTac1, thaoTac2, thaoTac3, thaoTac4, thaoTac5, thaoTac6, thaoTac7, thaoTac8,
                thaoTac9, thaoTac10, thaoTac11));

        // Gán UserData cho CheckBox
        for (int i = 0; i < xemCheckBoxes.size(); i++) {
            xemCheckBoxes.get(i).setUserData((i + 1) + "-1"); // Mã chức năng (1, 2, ...) và hành động (1)
        }
        for (int i = 0; i < thaoTacCheckBoxes.size(); i++) {
            thaoTacCheckBoxes.get(i).setUserData((i + 1) + "-2"); // Mã chức năng (1, 2, ...) và hành động (2)
        }

        loadTenQuyenToComboBox();
        setupTableColumns();
        setupCheckBoxes();
    }

    private void loadTenQuyenToComboBox() {
        ArrayList<PhanQuyenDTO> dsQuyen = phanQuyenBUS.getALLQuyen();
        for (PhanQuyenDTO quyen : dsQuyen) {
            cbbChucVu.getItems().add(quyen.getTenQuyen());
        }
        if (!dsQuyen.isEmpty()) {
            cbbChucVu.setValue(dsQuyen.get(0).getTenQuyen());
        }
        cbbChucVu.setOnAction(event -> {
            String selectedTenQuyen = cbbChucVu.getSelectionModel().getSelectedItem();
            selectedQuyen = getPhanQuyenByTenQuyen(selectedTenQuyen);
            if (selectedQuyen != null) {
                System.out.println("Chọn chức vụ: " + selectedQuyen.getTenQuyen());
                loadPermissions();
            }
        });
    }

    private PhanQuyenDTO getPhanQuyenByTenQuyen(String tenQuyen) {
        return phanQuyenBUS.getALLQuyen().stream()
                .filter(quyen -> quyen.getTenQuyen().equals(tenQuyen))
                .findFirst()
                .orElse(null);
    }

   

    private void setupTableColumns() {
        clSTT.setCellFactory(column -> new TableCell<TaiKhoan_DTO, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || getIndex() >= tbDS.getItems().size() ? null : String.valueOf(getIndex() + 1));
            }
        });
        clName.setCellValueFactory(new PropertyValueFactory<>("tenTK"));
    }

    private void setupCheckBoxes() {
        for (CheckBox checkBox : xemCheckBoxes) {
            checkBox.setOnAction(event -> updatePermission(checkBox));
        }
        for (CheckBox checkBox : thaoTacCheckBoxes) {
            checkBox.setOnAction(event -> updatePermission(checkBox));
        }
    }

    private void updatePermission(CheckBox checkBox) {
        if (selectedQuyen == null) {
            System.out.println("Chưa chọn chức vụ!");
            return;
        }

        String[] parts = checkBox.getUserData().toString().split("-");
        int maChucNang = Integer.parseInt(parts[0]);
        int maHanhDong = Integer.parseInt(parts[1]);

        if (checkBox.isSelected()) {
            CTPhanQuyenDTO newPermission = new CTPhanQuyenDTO();
            newPermission.setMaQuyen(selectedQuyen.getMaQuyen());
            newPermission.setMaChucNang(maChucNang);
            newPermission.setMaHanhDong(maHanhDong);
            selectedPermissions.add(newPermission);
        } else {
            selectedPermissions.removeIf(p -> p.getMaChucNang() == maChucNang && p.getMaHanhDong() == maHanhDong);
        }
    }


    @FXML
    void btnSave(ActionEvent event) {
        if (selectedQuyen == null) {
            System.out.println("Chưa chọn chức vụ!");
            return;
        }
        System.out.println("Lưu quyền cho chức vụ: " + selectedQuyen.getTenQuyen());
        phanQuyenBUS.savePermissions(selectedPermissions);
        System.out.println("Lưu quyền thành công!");
    }

    private void loadPermissions() {
        if (selectedQuyen == null)
            return;

        selectedPermissions = phanQuyenBUS.getPermissionsByQuyen(selectedQuyen.getMaQuyen());
        for (CheckBox checkBox : xemCheckBoxes) {
            setCheckBoxState(checkBox);
        }
        for (CheckBox checkBox : thaoTacCheckBoxes) {
            setCheckBoxState(checkBox);
        }
    }

    private void setCheckBoxState(CheckBox checkBox) {
        if (checkBox.getUserData() == null) {
            System.out.println("CheckBox không có UserData: " + checkBox.getText());
            return;
        }

        String[] parts = checkBox.getUserData().toString().split("-");
        int maChucNang = Integer.parseInt(parts[0]);
        int maHanhDong = Integer.parseInt(parts[1]);

        boolean isSelected = selectedPermissions.stream()
                .anyMatch(p -> p.getMaChucNang() == maChucNang && p.getMaHanhDong() == maHanhDong);

        checkBox.setSelected(isSelected);
    }

    @FXML
    private CheckBox thaoTac1, thaoTac2, thaoTac3, thaoTac4, thaoTac5, thaoTac6, thaoTac7, thaoTac8, thaoTac9,
            thaoTac10, thaoTac11;
    @FXML
    private CheckBox xem1, xem2, xem3, xem4, xem5, xem6, xem7, xem8, xem9, xem10, xem11;
}