package ua.com.jdev.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ua.com.jdev.MainApp;
import ua.com.jdev.model.Client;
import ua.com.jdev.model.Employee;
import ua.com.jdev.view.dialogs.ClientEditDialogController;
import ua.com.jdev.view.dialogs.EmployeeEditDialogController;

public class TabsOverviewController {

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> firstNameColumnEmployee;
    @FXML private TableColumn<Employee, String> secondNameColumnEmployee;
    @FXML private TableColumn<Employee, String> lastNameColumnEmployee;
    @FXML private TableColumn<Employee, String> phoneColumnEmployee;
    @FXML private TableColumn<Employee, String> positionColumnEmployee;
    @FXML private Button editBtnEmployee;
    @FXML private Button deleteBtnEmployee;

    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, String> firstNameColumnClient;
    @FXML private TableColumn<Client, String> secondNameColumnClient;
    @FXML private TableColumn<Client, String> lastNameColumnClient;
    @FXML private TableColumn<Client, String> phoneColumnClient;
    @FXML private TableColumn<Client, String> cardNumberColumnClient;
    @FXML private Button editBtnClient;
    @FXML private Button deleteBtnClient;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public TabsOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        editBtnEmployee.setDisable(true);
        deleteBtnEmployee.setDisable(true);
        editBtnClient.setDisable(true);
        deleteBtnClient.setDisable(true);
        // Initialize all tabs
        // Employees
        firstNameColumnEmployee.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        secondNameColumnEmployee.setCellValueFactory(cellData -> cellData.getValue().secondNameProperty());
        lastNameColumnEmployee.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        phoneColumnEmployee.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        positionColumnEmployee.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        // Clients
        firstNameColumnClient.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        secondNameColumnClient.setCellValueFactory(cellData -> cellData.getValue().secondNameProperty());
        lastNameColumnClient.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        phoneColumnClient.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        cardNumberColumnClient.setCellValueFactory(cellData -> cellData.getValue().cardNumberProperty());

        // Listen for selection changes and show the person details when changed.
        employeeTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {editBtnEmployee.setDisable(false); deleteBtnEmployee.setDisable(false);})
        );
        clientTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {editBtnClient.setDisable(false); deleteBtnClient.setDisable(false);})
        );
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        employeeTable.setItems(mainApp.getEmployeeData());
        clientTable.setItems(mainApp.getClientData());
    }

    private void editClient(Client client, ClientEditDialogController controller) {
        // TODO: 21.01.2016 объединить эти методы
        client.setFirstName(controller.getFirstNameClientField().getText());
        client.setSecondName(controller.getSecondNameClientField().getText());
        client.setLastName(controller.getLastNameClientField().getText());
        client.setPhone(controller.getPhoneClientField().getText());
        client.setCardNumber(controller.getCardNumberClientField().getText());
    }
    private void editEmployee(Employee employee, EmployeeEditDialogController controller) {
        employee.setFirstName(controller.getFirstNameEmployeeField().getText());
        employee.setSecondName(controller.getSecondNameEmployeeField().getText());
        employee.setLastName(controller.getLastNameEmployeeField().getText());
        employee.setPhone(controller.getPhoneEmployeeField().getText());
        employee.setPosition(controller.getPositionEmployeeField().getText());
    }

    // ############################### BUTTONS HANDLING ##############################

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new employee.
     */
    @FXML
    private void handleNewEmployee() {
        Employee tempEmployee = new Employee();
        boolean okClicked = mainApp.showEmployeeEditDialog(tempEmployee);
        if (okClicked) {
            mainApp.getEmployeeData().add(tempEmployee);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected employee.
     */
    @FXML
    private void handleEditEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        //if (selectedEmployee != null) {
        boolean okClicked = mainApp.showEmployeeEditDialog(selectedEmployee);
        EmployeeEditDialogController controller = mainApp.getEmployeeController();
        if (okClicked) {
            editEmployee(selectedEmployee, controller);
        }
    }
    @FXML
    private void handleDeleteEmployee() {
        int selectedIndex = employeeTable.getSelectionModel().getSelectedIndex();
        employeeTable.getItems().remove(selectedIndex);
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new client.
     */
    @FXML
    private void handleNewClient() {
        Client tempClient = new Client();
        boolean okClicked = mainApp.showClientEditDialog(tempClient);
        if (okClicked) {
            mainApp.getClientData().add(tempClient);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected client.
     */
    @FXML
    private void handleEditClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        //if (selectedClient != null) {
            boolean okClicked = mainApp.showClientEditDialog(selectedClient);
        ClientEditDialogController controller = mainApp.getClientController();
            if (okClicked) {
                editClient(selectedClient, controller);
            }
    }

    @FXML
    private void handleDeleteClient() {
        int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
        clientTable.getItems().remove(selectedIndex);
    }
}
