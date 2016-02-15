package ua.com.jdev.view.dialogs;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import ua.com.jdev.model.Goods;

public class GoodsSaleDialogController {

    private Stage dialogStage;
    private Goods goods;
    private boolean okClicked;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
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
     * Sets the goods to be edited in the dialog.
     *
     * @param goods
     */
    public void setGoods(Goods goods) {
        this.goods = goods;

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
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {

            //DBHelper.sale(goods);

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
}
