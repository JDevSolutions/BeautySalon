package ua.com.jdev.view.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.com.jdev.model.ScheduleRecord;

public class ScheduleEditDialogController {

    @FXML
    private TextField timeScheduleField;
    @FXML
    private TextField employeeScheduleField;
    @FXML
    private TextField clientScheduleField;
    @FXML
    private TextField priceScheduleField;
    @FXML
    private CheckBox paidScheduleCheckBox;

    private Stage dialogStage;
    // TODO: 22.01.2016  
    private ScheduleRecord scheduleRecord;
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
     * @param scheduleRecord
     */
    public void setScheduleRecord(ScheduleRecord scheduleRecord) {
        this.scheduleRecord = scheduleRecord;

        timeScheduleField.setText(scheduleRecord.getTime());
        employeeScheduleField.setText(scheduleRecord.getEmployee());
        clientScheduleField.setText(scheduleRecord.getClient());
        priceScheduleField.setText(scheduleRecord.getPrice());
        paidScheduleCheckBox.setSelected(scheduleRecord.isPaid());
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
            scheduleRecord.setTime(timeScheduleField.getText());
            scheduleRecord.setEmployee(employeeScheduleField.getText());
            scheduleRecord.setClient(clientScheduleField.getText());
            scheduleRecord.setPrice(priceScheduleField.getText());
            scheduleRecord.setPaid(paidScheduleCheckBox.isSelected());

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

        if (timeScheduleField.getText() == null || timeScheduleField.getText().length() == 0) {
            errorMessage += "Время записи содержит ошибку!\n";
        }
        if (employeeScheduleField.getText() == null || employeeScheduleField.getText().length() == 0) {
            errorMessage += "Имя мастера содержит ошибку!\n";
        }
        if (clientScheduleField.getText() == null || clientScheduleField.getText().length() == 0) {
            errorMessage += "Имя клиента содержит ошибку!\n";
        }
        if (priceScheduleField.getText() != null || priceScheduleField.getText().length() != 0) {
            try {
                Double.parseDouble(priceScheduleField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Цена услуг содержит ошибку!\n";
            }
        }
        if (paidScheduleCheckBox.isSelected() && (priceScheduleField.getText() == null || priceScheduleField.getText().length() == 0)) {
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

    public TextField getTimeScheduleField() {
        return timeScheduleField;
    }

    public TextField getEmployeeScheduleField() {
        return employeeScheduleField;
    }

    public TextField getClientScheduleField() {
        return clientScheduleField;
    }

    public TextField getPriceScheduleField() {
        return priceScheduleField;
    }

    public CheckBox getPaidScheduleCheckBox() {
        return paidScheduleCheckBox;
    }
}
