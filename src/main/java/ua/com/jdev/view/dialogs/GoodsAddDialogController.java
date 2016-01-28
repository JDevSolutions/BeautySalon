package ua.com.jdev.view.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.com.jdev.dbase.DBHelper;
import ua.com.jdev.model.Goods;

public class GoodsAddDialogController {

    @FXML
    private TextField codeGoodsField;
    @FXML
    private TextField nameGoodsField;
    @FXML
    private TextField priceGoodsField;
    @FXML
    private TextField amountGoodsField;

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

        codeGoodsField.setText(goods.getCode());
        nameGoodsField.setText(goods.getName());
        priceGoodsField.setText(goods.getPrice());
        amountGoodsField.setText(goods.getAmount());
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
        if (isInputValid()) {
            goods.setCode(codeGoodsField.getText());
            goods.setName(nameGoodsField.getText());
            goods.setPrice(priceGoodsField.getText());
            goods.setAmount(amountGoodsField.getText());

            DBHelper.insert(goods);

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (codeGoodsField.getText() == null || codeGoodsField.getText().length() == 0) {
            errorMessage += "Код товара содержит ошибку!\n";
        }
        if (nameGoodsField.getText() == null || nameGoodsField.getText().length() == 0) {
            errorMessage += "Название товара содержит ошибку!\n";
        }
        if (priceGoodsField.getText() == null || priceGoodsField.getText().length() == 0) {
            errorMessage += "Цена товара содержит ошибку!\n";
        }

        if (amountGoodsField.getText() == null || amountGoodsField.getText().length() == 0) {
            errorMessage += "Количество товаров содержит ошибку\n";
        } else {
            // Проверка на наличие любых символов кроме цифр
            try {
                Integer.parseInt(amountGoodsField.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorMessage += "Количество товаров должно содержать только цифры!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибки в полях товара");
            alert.setHeaderText("Исправьте ошибки в полях, пожалуйста");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public TextField getCodeGoodsField() {
        return codeGoodsField;
    }

    public TextField getNameGoodsField() {
        return nameGoodsField;
    }

    public TextField getPriceGoodsField() {
        return priceGoodsField;
    }

    public TextField getAmountGoodsField() {
        return amountGoodsField;
    }
}
