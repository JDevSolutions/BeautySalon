package ua.com.jdev.view.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.com.jdev.dbase.DBHelper;
import ua.com.jdev.model.Client;

public class ClientAddDialogController {

    @FXML
    private TextField firstNameClientField;
    @FXML
    private TextField secondNameClientField;
    @FXML
    private TextField lastNameClientField;
    @FXML
    private TextField phoneClientField;
    @FXML
    private TextField cardNumberClientField;

    private Stage dialogStage;
    private Client client;
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
     * Sets the person to be edited in the dialog.
     *
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;

        firstNameClientField.setText(client.getFirstName());
        secondNameClientField.setText(client.getSecondName());
        lastNameClientField.setText(client.getLastName());
        phoneClientField.setText(client.getPhone());
        cardNumberClientField.setText(client.getCardNumber());
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
            client.setFirstName(firstNameClientField.getText());
            client.setSecondName(secondNameClientField.getText());
            client.setLastName(lastNameClientField.getText());
            client.setPhone(phoneClientField.getText());
            client.setCardNumber(cardNumberClientField.getText());

            DBHelper.insert(client);

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

        if (firstNameClientField.getText() == null || firstNameClientField.getText().length() == 0) {
            errorMessage += "Имя клиента содержит ошибку!\n";
        }
        if (secondNameClientField.getText() == null || secondNameClientField.getText().length() == 0) {
            errorMessage += "Отчество клиента содержит ошибку!\n";
        }
        if (lastNameClientField.getText() == null || lastNameClientField.getText().length() == 0) {
            errorMessage += "Фамилия клиента содержит ошибку!\n";
        }
        if (phoneClientField.getText() == null || phoneClientField.getText().length() == 0) {
            errorMessage += "Поле \"Телефон\" обязательно!\n";
        } else {
            if (phoneClientField.getText().length() != 10) {
                errorMessage += "Номер телефона клиента должен состоять из 10 цифр!\n";
            } else {
                // Проверка, что в поле только цифры
                try {
                    Long.parseLong(phoneClientField.getText());
                } catch (NumberFormatException e) {
                    errorMessage += "Номер телефона должен состоять только из цифр!\n";
                }
            }
        }

        // TODO: 21.01.2016 доделать проверку на валидность данных
        // TODO: 21.01.2016 СКОЛЬКО ЦИФР ДОЛЖНО СОДЕРЖАТЬСЯ В КАРТЕ??????
//        if (!cardNumberClientField.getText().equals("")) {
//            errorMessage += "Бонусная карта клиента должна содержать 10 знаков!\n";
//        } else {
//            // Проверка, что в поле только цифры
//            try {
//                Integer.parseInt(cardNumberClientField.getText());
//            } catch (NumberFormatException e) {
//                errorMessage += "Номер бонусной карты должен состоять только из цифр!\n";
//            }
//        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибки в полях");
            alert.setHeaderText("Исправьте ошибки в полях, пожалуйста");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public TextField getFirstNameClientField() {
        return firstNameClientField;
    }

    public TextField getSecondNameClientField() {
        return secondNameClientField;
    }

    public TextField getLastNameClientField() {
        return lastNameClientField;
    }

    public TextField getPhoneClientField() {
        return phoneClientField;
    }

    public TextField getCardNumberClientField() {
        return cardNumberClientField;
    }
}
