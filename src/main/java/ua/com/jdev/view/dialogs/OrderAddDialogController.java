package ua.com.jdev.view.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.com.jdev.adapter.PersonMaker;
import ua.com.jdev.dbase.DBHelper;
import ua.com.jdev.dbase.DBRepository;
import ua.com.jdev.model.Order;

import java.sql.SQLException;

public class OrderAddDialogController {

    @FXML
    private TextField timeOrderField;
    @FXML
    private TextField employeeOrderField;
    @FXML
    private TextField clientOrderField;
    @FXML
    private TextField priceOrderField;
    @FXML
    private CheckBox paidOrderCheckBox;

    private Stage dialogStage;
    // TODO: 22.01.2016  
    private Order order;
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
     * Sets the record to be edited in the dialog.
     *
     * @param order
     */
    public void setOrder(Order order) {
        this.order = order;

        timeOrderField.setText(order.getTime());
        employeeOrderField.setText((order.getEmployee() == null) ? "" : order.getEmployee().toString());
        clientOrderField.setText((order.getClient() == null) ? "" : order.getClient().toString());
        priceOrderField.setText(String.valueOf(order.getPrice()));
        paidOrderCheckBox.setSelected(order.isPaid());
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
    private void handleOk() throws SQLException {
        if (isInputValid()) {
            order.setTime(timeOrderField.getText());
            order.setEmployee(DBRepository.getEmployeeById(employeeOrderField.getText()));
            order.setClient(DBRepository.getClientById(clientOrderField.getText()));
            /*order.setEmployee(PersonMaker.makeEmployee(employeeOrderField.getText().trim()));
            order.setClient(PersonMaker.makeClient(clientOrderField.getText().trim()));
            */order.setPrice(Double.parseDouble(priceOrderField.getText()));
            order.setPaid(paidOrderCheckBox.isSelected());

            DBHelper.insert(order);

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

        if (timeOrderField.getText() == null || timeOrderField.getText().length() == 0) {
            errorMessage += "Время записи содержит ошибку!\n";
        }
        if (employeeOrderField.getText() == null || employeeOrderField.getText().length() == 0) {
            errorMessage += "Имя мастера содержит ошибку!\n";
        }
        if (clientOrderField.getText() == null || clientOrderField.getText().length() == 0) {
            errorMessage += "Имя клиента содержит ошибку!\n";
        }
        if (priceOrderField.getText() == null || priceOrderField.getText().length() != 0) {
            priceOrderField.setText("0.0");
        } else {
            try {
                Double.parseDouble(priceOrderField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Цена услуг содержит ошибку!\n";
            }
        }
        if (paidOrderCheckBox.isSelected() && (priceOrderField.getText() == null || priceOrderField.getText().length() == 0)) {
            errorMessage += "Если улуга оплачена укажите цену!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибки в полях записи");
            alert.setHeaderText("Исправьте ошибки в полях, пожалуйста");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public TextField getTimeOrderField() {
        return timeOrderField;
    }

    public TextField getEmployeeOrderField() {
        return employeeOrderField;
    }

    public TextField getClientOrderField() {
        return clientOrderField;
    }

    public TextField getPriceOrderField() {
        return priceOrderField;
    }
}
