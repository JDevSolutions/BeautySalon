package ua.com.jdev.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ua.com.jdev.MainApp;
import ua.com.jdev.dbase.DBHelper;
import ua.com.jdev.model.Client;
import ua.com.jdev.model.Employee;
import ua.com.jdev.model.Goods;
import ua.com.jdev.model.Order;
import ua.com.jdev.view.dialogs.ClientEditDialogController;
import ua.com.jdev.view.dialogs.EmployeeEditDialogController;
import ua.com.jdev.view.dialogs.GoodsEditDialogController;
import ua.com.jdev.view.dialogs.OrderEditDialogController;

public class TabsOverviewController {

    @FXML private TableView<Order> scheduleTable;
    @FXML private TableColumn<Order, String> timeColumnSchedule;
    @FXML private TableColumn<Order, String> employeeColumnSchedule;
    @FXML private TableColumn<Order, String> clientColumnSchedule;
    @FXML private TableColumn<Order, String> priceColumnSchedule;
    @FXML private Button editBtnSchedule;
    @FXML private Button deleteBtnSchedule;

    @FXML private TableView<Goods> goodsTable;
    @FXML private TableColumn<Goods, String> codeColumnGoods;
    @FXML private TableColumn<Goods, String> nameColumnGoods;
    @FXML private TableColumn<Goods, String> priceColumnGoods;
    @FXML private TableColumn<Goods, String> amountColumnGoods;
    @FXML private Button editBtnGoods;
    @FXML private Button deleteBtnGoods;

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

    // ######### TEST ############
    private ObservableList<Order> orderData = FXCollections.observableArrayList();
    private ObservableList<Goods> goodsData = FXCollections.observableArrayList();
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private ObservableList<Client> clientData = FXCollections.observableArrayList();

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public TabsOverviewController() {
        //orderData.add(new Order("12:30", "Alina Antonenko", "Lilya Marchenko"));
        orderData = (ObservableList<Order>) DBHelper.getData("orders");
        //goodsData.add(new Goods("0154", "Краска для волос", "49.90", "1"));
        goodsData = (ObservableList<Goods>) DBHelper.getData("goods");
        //employeeData.add(new Employee("Anna", "Petrovna", "Ivanova", "380671597535", "Administrator"));
        employeeData = (ObservableList<Employee>) DBHelper.getData("employees");
        //clientData.add(new Client("Olga", "Ivanovna", "Safronova", "380503219876"));
        clientData = (ObservableList<Client>) DBHelper.getData("clients");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        editBtnSchedule.setDisable(true);
        deleteBtnSchedule.setDisable(true);
        editBtnGoods.setDisable(true);
        deleteBtnGoods.setDisable(true);
        editBtnEmployee.setDisable(true);
        deleteBtnEmployee.setDisable(true);
        editBtnClient.setDisable(true);
        deleteBtnClient.setDisable(true);
        // Initialize all tabs
        // Schedule
        timeColumnSchedule.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        employeeColumnSchedule.setCellValueFactory(cellData -> cellData.getValue().employeeProperty());
        clientColumnSchedule.setCellValueFactory(cellData -> cellData.getValue().clientProperty());
        priceColumnSchedule.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        // Goods
        codeColumnGoods.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        nameColumnGoods.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumnGoods.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        amountColumnGoods.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
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
        scheduleTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {editBtnSchedule.setDisable(false); deleteBtnSchedule.setDisable(false);})
        );
        goodsTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {editBtnGoods.setDisable(false); deleteBtnGoods.setDisable(false);})
        );
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

        scheduleTable.setItems(orderData);
        goodsTable.setItems(goodsData);
        employeeTable.setItems(employeeData);
        clientTable.setItems(clientData);
    }

    // TODO: 21.01.2016 объединить эти методы
    private void editScheduleRecord(Order order, OrderEditDialogController controller) {
        order.setTime(controller.getTimeOrderField().getText());
        order.setEmployee(controller.getEmployeeOrderField().getText());
        order.setClient(controller.getClientOrderField().getText());
        order.setPrice(controller.getPriceOrderField().getText());
    }
    private void editGoods(Goods goods, GoodsEditDialogController controller) {
        goods.setCode(controller.getCodeGoodsField().getText());
        goods.setName(controller.getNameGoodsField().getText());
        goods.setPrice(controller.getPriceGoodsField().getText());
        goods.setAmount(controller.getAmountGoodsField().getText());
    }
    private void editEmployee(Employee employee, EmployeeEditDialogController controller) {
        employee.setFirstName(controller.getFirstNameEmployeeField().getText());
        employee.setSecondName(controller.getSecondNameEmployeeField().getText());
        employee.setLastName(controller.getLastNameEmployeeField().getText());
        employee.setPhone(controller.getPhoneEmployeeField().getText());
        employee.setPosition(controller.getPositionEmployeeField().getText());
    }
    private void editClient(Client client, ClientEditDialogController controller) {
        client.setFirstName(controller.getFirstNameClientField().getText());
        client.setSecondName(controller.getSecondNameClientField().getText());
        client.setLastName(controller.getLastNameClientField().getText());
        client.setPhone(controller.getPhoneClientField().getText());
        client.setCardNumber(controller.getCardNumberClientField().getText());
    }

    // ############################### BUTTONS HANDLING ##############################

    /**
     * Called when the user clicks the new button. Opens a dialog to update
     * details for a new schedule record.
     */
    @FXML
    private void handleNewScheduleRecord() {
        Order tempRecord = new Order();
        boolean okClicked = mainApp.showScheduleEditDialog(tempRecord);
        if (okClicked) {
            orderData.add(tempRecord);
        }
    }

    /**
     * Called when the user clicks the update button. Opens a dialog to update
     * details for the selected schedule record.
     */
    @FXML
    private void handleEditScheduleRecord() {
        Order selectedRecord = scheduleTable.getSelectionModel().getSelectedItem();
        //if (selectedRecord != null) {
        boolean okClicked = mainApp.showScheduleEditDialog(selectedRecord);
        OrderEditDialogController controller = mainApp.getScheduleController();
        if (okClicked) {
            editScheduleRecord(selectedRecord, controller);
        }
    }
    @FXML
    private void handleDeleteScheduleRecord() {
        int selectedIndex = scheduleTable.getSelectionModel().getSelectedIndex();
        scheduleTable.getItems().remove(selectedIndex);
        if (scheduleTable.getItems().size() == 0) {
            editBtnSchedule.setDisable(true);
            deleteBtnSchedule.setDisable(true);
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to update
     * details for a new goods.
     */
    @FXML
    private void handleNewGoods() {
        Goods tempGoods = new Goods();
        boolean okClicked = mainApp.showGoodsEditDialog(tempGoods);
        if (okClicked) {
            goodsData.add(tempGoods);
        }
    }

    /**
     * Called when the user clicks the update button. Opens a dialog to update
     * details for the selected goods.
     */
    @FXML
    private void handleEditGoods() {
        Goods selectedGoods = goodsTable.getSelectionModel().getSelectedItem();
        //if (selectedGoods != null) {
        boolean okClicked = mainApp.showGoodsEditDialog(selectedGoods);
        GoodsEditDialogController controller = mainApp.getGoodsController();
        if (okClicked) {
            editGoods(selectedGoods, controller);
        }
    }
    @FXML
    private void handleDeleteGoods() {
        int selectedIndex = goodsTable.getSelectionModel().getSelectedIndex();
        goodsTable.getItems().remove(selectedIndex);
        if (goodsTable.getItems().size() == 0) {
            editBtnGoods.setDisable(true);
            deleteBtnGoods.setDisable(true);
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to update
     * details for a new employee.
     */
    @FXML
    private void handleNewEmployee() {
        Employee tempEmployee = new Employee();
        boolean okClicked = mainApp.showEmployeeEditDialog(tempEmployee);
        if (okClicked) {
            employeeData.add(tempEmployee);
        }
    }

    /**
     * Called when the user clicks the update button. Opens a dialog to update
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
        if (employeeTable.getItems().size() == 0) {
            editBtnEmployee.setDisable(true);
            deleteBtnEmployee.setDisable(true);
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to update
     * details for a new client.
     */
    @FXML
    private void handleNewClient() {
        Client tempClient = new Client();
        boolean okClicked = mainApp.showClientEditDialog(tempClient);
        if (okClicked) {
            clientData.add(tempClient);
        }
    }

    /**
     * Called when the user clicks the update button. Opens a dialog to update
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
        if (clientTable.getItems().size() == 0) {
            editBtnClient.setDisable(true);
            deleteBtnClient.setDisable(true);
        }
    }
}
