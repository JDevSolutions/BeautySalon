package ua.com.jdev.view.dialogs;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.com.jdev.model.EditingCellGoodsDouble;
import ua.com.jdev.model.EditingCellGoodsInteger;
import ua.com.jdev.model.Goods;

import java.io.IOException;

public class GoodsSaleDialogController {

    private Stage dialogStage;
    private boolean okClicked;

    public ObservableList<Goods> getGoodsData() {
        return goodsData;
    }

    private ObservableList<Goods> goodsData = FXCollections.observableArrayList();

    @FXML private TableView<Goods> goodsSaleTableView;
    @FXML private TableColumn<Goods, String> nameColumnSale;
    @FXML private TableColumn<Goods, Double> priceColumnSale;
    @FXML private TableColumn<Goods, Integer> amountColumnSale;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        goodsSaleTableView.setEditable(true);
        nameColumnSale.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumnSale.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        priceColumnSale.setCellFactory(param -> new EditingCellGoodsDouble());
        priceColumnSale.setOnEditCommit(event ->
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue()));
        amountColumnSale.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAmount()));
        amountColumnSale.setCellFactory(param -> new EditingCellGoodsInteger());
        amountColumnSale.setOnEditCommit(event ->
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue()));

    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks add
     */
    @FXML
    public boolean handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("GoodsSearchDialog.fxml"));
            AnchorPane page = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Поиск товара");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setResizable(false);

            GoodsSearchDialogController controller = loader.getController();
            controller.setParentController(this);
            controller.setDialogStage(stage);

            stage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Called when the user clicks delete
     */
    @FXML
    public void handleDelete() {

    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {

            //DBHelper.sale(goodsData);

            okClicked = true;
            dialogStage.close();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void updateTable() {
        goodsSaleTableView.setItems(goodsData);
    }
}
