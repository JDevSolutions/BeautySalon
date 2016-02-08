package ua.com.jdev.view.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.com.jdev.MainApp;
import ua.com.jdev.model.Employee;
import ua.com.jdev.model.Person;

/**
 * @author modkomi
 * @since 07.02.2016
 */
public class RestoreEmployeeDialogController {

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> phoneColumnEmployee;
    @FXML TableColumn<Person, String> fullNameColumn = new TableColumn<>("Full Name");
    @FXML private Button reestablishBtnEmployee;

    private MainApp mainApp;

    /**
     * Вызывает initialize
     */
    public RestoreEmployeeDialogController() {
    }

    /**
     * Действия перед появлением окна
     */
    @FXML
    private void initialize() {

        reestablishBtnEmployee.setDisable(true);

        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneColumnEmployee.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());

        employeeTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    reestablishBtnEmployee.setDisable(false);
                })
        );
    }

    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;

        employeeTable.setItems(mainApp.getEmployeeData());

        if (employeeTable.getItems().size() == 0) {
            reestablishBtnEmployee.setDisable(true);
        }
    }

    /**
     * Кнопка нажата
     */
    @FXML
    public void onClickMethod(){
        System.out.println("Button been activated");

//        dbHelper.restore();

    }

}
