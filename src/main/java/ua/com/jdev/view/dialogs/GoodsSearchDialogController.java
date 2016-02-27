package ua.com.jdev.view.dialogs;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.com.jdev.dbase.DBHelper;
import ua.com.jdev.model.Goods;

public class GoodsSearchDialogController {

    private Stage dialogStage;
    private GoodsSaleDialogController parentController;
    private boolean okClicked;

    private ObservableList<Goods> goodsList = FXCollections.observableArrayList();

    @FXML
    private TextField goodsSearchField;
    @FXML
    private TableView<Goods> goodsTableView;
    @FXML
    private TableColumn<Goods, String> nameColumnOrder;
    @FXML
    private TableColumn<Goods, Double> priceColumnOrder;
    @FXML
    private TableColumn<Goods, Integer> amountColumnOrder;
    private Goods goods;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        goodsSearchField.setOnKeyTyped((event1 ->
                goodsTableView.setItems((ObservableList<Goods>) DBHelper.search(goodsSearchField.getText()))));
        //goodsSearchField.setOnKeyPressed((event) ->
        //        goodsTableView.setItems((ObservableList<Goods>) DBHelper.search(goodsSearchField.getText())));
        nameColumnOrder.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumnOrder.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        amountColumnOrder.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAmount()));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setParentController(GoodsSaleDialogController parentController) {
        this.parentController = parentController;
    }

    /**
     * *** BUTTONS HANDLING ***
     */
    @FXML
    private void handleOk() {
        //parentController.setGoodsData(goodsList);
        goods = goodsTableView.getSelectionModel().getSelectedItem();
        parentController.getGoodsData().add(goods);
        parentController.updateTable();
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

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
