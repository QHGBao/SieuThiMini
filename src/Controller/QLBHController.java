package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class QLBHController {

    @FXML
    private TableColumn<?, ?> sellColPrice;

    @FXML
    private TableColumn<?, ?> sellColProduct;

    @FXML
    private TableColumn<?, ?> sellColQuantity;

    @FXML
    private AnchorPane sellForm;

    @FXML
    private GridPane sellGridPane;

    @FXML
    private Button sellInBTN;

    @FXML
    private ScrollPane sellScrollPane;

    @FXML
    private TableView<?> sellTableView;

    @FXML
    private Label sellThanhTien;

    @FXML
    private Button sellThanhToanBTN;

    @FXML
    private Label sellTienGiam;

    @FXML
    private TextField sellTienKhachTra;

    @FXML
    private Label sellTienTraLai;

    @FXML
    private Label sellTongTien;

    @FXML
    private Button sellXoaBTN;

}
