package ua.com.jdev.view.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.com.jdev.model.Employee;

public class EmployeeEditDialogController {

    @FXML
    private TextField firstNameEmployeeField;
    @FXML
    private TextField secondNameEmployeeField;
    @FXML
    private TextField lastNameEmployeeField;
    @FXML
    private TextField phoneEmployeeField;
    @FXML
    private TextField positionEmployeeField;

    private Stage dialogStage;
    private Employee employee;
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
     * @param employee
     */
    public void setClient(Employee employee) {
        this.employee = employee;

        firstNameEmployeeField.setText(employee.getFirstName());
        secondNameEmployeeField.setText(employee.getSecondName());
        lastNameEmployeeField.setText(employee.getLastName());
        phoneEmployeeField.setText(employee.getPhone());
        positionEmployeeField.setText(employee.getPosition());
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
            employee.setFirstName(firstNameEmployeeField.getText());
            employee.setSecondName(secondNameEmployeeField.getText());
            employee.setLastName(lastNameEmployeeField.getText());
            employee.setPhone(phoneEmployeeField.getText());
            employee.setPosition(positionEmployeeField.getText());

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

        if (firstNameEmployeeField.getText() == null || firstNameEmployeeField.getText().length() == 0) {
            errorMessage += "Имя сотрудника содержит ошибку!\n";
        }
        if (secondNameEmployeeField.getText() == null || secondNameEmployeeField.getText().length() == 0) {
            errorMessage += "Отчество сотрудника содержит ошибку!\n";
        }
        if (lastNameEmployeeField.getText() == null || lastNameEmployeeField.getText().length() == 0) {
            errorMessage += "Фамилия сотрудника содержит ошибку!\n";
        }

        if (phoneEmployeeField.getText().length() != 12) {
            errorMessage += "Номер телефона сотрудника должен состоять из 12 цифр!\n";
        } else {
            // Проверка, что в поле только цифры
            try {
                Long.parseLong(phoneEmployeeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Номер телефона должен состоять только из цифр!\n";
            }
        }

        if (positionEmployeeField.getText() == null || positionEmployeeField.getText().equals("")) {
            errorMessage += "Введите должность сотрудника!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибки в полях сотрудника");
            alert.setHeaderText("Исправьте ошибки в полях, пожалуйста");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public TextField getFirstNameEmployeeField() {
        return firstNameEmployeeField;
    }

    public TextField getSecondNameEmployeeField() {
        return secondNameEmployeeField;
    }

    public TextField getLastNameEmployeeField() {
        return lastNameEmployeeField;
    }

    public TextField getPhoneEmployeeField() {
        return phoneEmployeeField;
    }

    public TextField getPositionEmployeeField() {
        return positionEmployeeField;
    }
}
