package Controller;

import BUS.AccountBUS;
import DTO.AccountDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AccountController {
    @FXML
    private Label btnThem;
    @FXML
    private Label btnSua;
    @FXML
    private Label btnXoa;
    @FXML
    private TextField txtTimKiem;
    @FXML
    private TableView<AccountDTO> tbBang;
    @FXML
    private TableColumn<AccountDTO, Integer> tbMATK;
    @FXML
    private TableColumn<AccountDTO, String> tbTENTK;
    @FXML
    private TableColumn<AccountDTO, String> tbMATKHAU;
    @FXML
    private TableColumn<AccountDTO, Integer> tbMANV;
    @FXML
    private TableColumn<AccountDTO, Integer> tbMAQUYEN;
    @FXML
    private TableColumn<AccountDTO, String> tbTRANGTHAI;

    private ObservableList<AccountDTO> list = FXCollections.observableArrayList();
    private AccountBUS accBUS = new AccountBUS();
    private Stage popupStage;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadAccountData();
        setupSearch();
    }

    private void setupTableColumns() {
        tbMATK.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaTK()).asObject());
        tbTENTK.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTenTK()));
        tbMATKHAU.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMatKhau()));
        tbMANV.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaNV()).asObject());
        tbMAQUYEN.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaQuyen()).asObject());
        tbTRANGTHAI.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getIsDeleted() == 0 ? "Hoạt động" : "Đã khóa"));
    }

    private void loadAccountData() {
        list.clear();
        list.addAll(accBUS.getAllAccounts());
        tbBang.setItems(list);
    }

    private void setupSearch() {
        FilteredList<AccountDTO> filteredData = new FilteredList<>(list, p -> true);
        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(account -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(account.getMaTK()).contains(lowerCaseFilter) ||
                        account.getTenTK().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<AccountDTO> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbBang.comparatorProperty());
        tbBang.setItems(sortedData);
    }

    @FXML
    void ThemTK(MouseEvent event) {
        if (popupStage != null && popupStage.isShowing())
            return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/AccountForm.fxml"));
            Parent root = loader.load();

            AccountFormController formController = loader.getController();
            formController.setMode("ADD");
            formController.setOnSuccessCallback(this::loadAccountData);

            popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.initStyle(StageStyle.TRANSPARENT);
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SuaTK(MouseEvent event) {
        AccountDTO selectedAccount = tbBang.getSelectionModel().getSelectedItem();
        if (selectedAccount == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn tài khoản cần sửa!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/AccountForm.fxml"));
            Parent root = loader.load();

            AccountFormController formController = loader.getController();
            formController.setMode("EDIT"); // Đặt chế độ sửa
            formController.setAccount(selectedAccount); // Gửi thông tin tài khoản được chọn
            formController.setOnSuccessCallback(this::loadAccountData);

            popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.initStyle(StageStyle.TRANSPARENT);
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void XoaTK(MouseEvent event) {
        AccountDTO selectedAccount = tbBang.getSelectionModel().getSelectedItem();
        if (selectedAccount == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn tài khoản cần khóa!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Xác nhận khóa tài khoản");
        alert.setContentText("Bạn có chắc muốn khóa tài khoản này?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            selectedAccount.setIsDeleted(1);
            if (accBUS.updateAccount(selectedAccount)) {
                loadAccountData();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã khóa tài khoản thành công!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể khóa tài khoản!");
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
