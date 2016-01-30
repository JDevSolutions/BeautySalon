package ua.com.jdev.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ua.com.jdev.MainApp;
import ua.com.jdev.adapter.PersonMaker;
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

    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, String> timeColumnSchedule;
    @FXML private TableColumn<Order, Employee> employeeColumnSchedule;
    @FXML private TableColumn<Order, Client> clientColumnSchedule;
    @FXML private TableColumn<Order, Double> priceColumnSchedule;
    @FXML private Button editBtnOrder;
    @FXML private Button deleteBtnOrder;

    @FXML private TableView<Goods> goodsTable;
    @FXML private TableColumn<Goods, String> codeColumnGoods;
    @FXML private TableColumn<Goods, String> nameColumnGoods;
    @FXML private TableColumn<Goods, Double> priceColumnGoods;
    @FXML private TableColumn<Goods, Integer> amountColumnGoods;
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
        editBtnOrder.setDisable(true);
        deleteBtnOrder.setDisable(true);
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
        priceColumnSchedule.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        // Goods
        codeColumnGoods.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        nameColumnGoods.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumnGoods.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        amountColumnGoods.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAmount()));
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
        orderTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    editBtnOrder.setDisable(false); deleteBtnOrder.setDisable(false);})
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

        orderTable.setItems(mainApp.getOrderData());
        goodsTable.setItems(mainApp.getGoodsData());
        employeeTable.setItems(mainApp.getEmployeeData());
        clientTable.setItems(mainApp.getClientData());
    }

    // TODO: 21.01.2016 объединить эти методы
    private void editOrder(Order order, OrderEditDialogController controller) {
        order.setTime(controller.getTimeOrderField().getText());
        order.setEmployee(PersonMaker.makeEmployee(controller.getEmployeeOrderField().getText()));
        order.setClient(PersonMaker.makeClient(controller.getClientOrderField().getText()));
        order.setPrice(Integer.parseInt(controller.getPriceOrderField().getText()));
    }
    private void editGoods(Goods goods, GoodsEditDialogController controller) {
        goods.setCode(controller.getCodeGoodsField().getText());
        goods.setName(controller.getNameGoodsField().getText());
        goods.setPrice(Double.parseDouble(controller.getPriceGoodsField().getText()));
        goods.setAmount(Integer.parseInt(controller.getAmountGoodsField().getText()));
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
    private void handleNewOrder() {
        Order tempOrder = new Order();
        boolean okClicked = mainApp.showOrderAddDialog(tempOrder);
        if (okClicked) {
            mainApp.getOrderData().add(tempOrder);
        }
    }

    /**
     * Called when the user clicks the update button. Opens a dialog to update
     * details for the selected schedule record.
     */
    @FXML
    private void handleEditOrder() {
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        //if (selectedOrder != null) {
        boolean okClicked = mainApp.showOrderEditDialog(selectedOrder);
        OrderEditDialogController controller = mainApp.getOrderEditController();
        if (okClicked) {
            editOrder(selectedOrder, controller);
        }
    }
    @FXML
    private void handleDeleteOrder() {
        int selectedIndex = orderTable.getSelectionModel().getSelectedIndex();
        orderTable.getItems().remove(selectedIndex);
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        DBHelper.delete(selectedOrder);
        if (orderTable.getItems().size() == 0) {
            editBtnOrder.setDisable(true);
            deleteBtnOrder.setDisable(true);
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to update
     * details for a new goods.
     */
    @FXML
    private void handleNewGoods() {
        Goods tempGoods = new Goods();
        boolean okClicked = mainApp.showGoodsAddDialog(tempGoods);
        if (okClicked) {
            mainApp.getGoodsData().add(tempGoods);
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
        GoodsEditDialogController controller = mainApp.getGoodsEditController();
        if (okClicked) {
            editGoods(selectedGoods, controller);
        }
    }
    @FXML
    private void handleDeleteGoods() {
        int selectedIndex = goodsTable.getSelectionModel().getSelectedIndex();
        goodsTable.getItems().remove(selectedIndex);
        Goods selectedGoods = goodsTable.getSelectionModel().getSelectedItem();
        DBHelper.delete(selectedGoods);
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
        boolean okClicked = mainApp.showEmployeeAddDialog(tempEmployee);
        if (okClicked) {
            mainApp.getEmployeeData().add(tempEmployee);
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
        EmployeeEditDialogController controller = mainApp.getEmployeeEditController();
        if (okClicked) {
            editEmployee(selectedEmployee, controller);
        }
    }
    @FXML
    private void handleDeleteEmployee() {
        int selectedIndex = employeeTable.getSelectionModel().getSelectedIndex();
        employeeTable.getItems().remove(selectedIndex);
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        DBHelper.delete(selectedEmployee);
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
        boolean okClicked = mainApp.showClientAddDialog(tempClient);
        if (okClicked) {
            mainApp.getClientData().add(tempClient);
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
        ClientEditDialogController controller = mainApp.getClientEditController();
            if (okClicked) {
                editClient(selectedClient, controller);
            }
    }

    @FXML
    private void handleDeleteClient() {
        int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
        clientTable.getItems().remove(selectedIndex);
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        DBHelper.delete(selectedClient);
        if (clientTable.getItems().size() == 0) {
            editBtnClient.setDisable(true);
            deleteBtnClient.setDisable(true);
        }
    }
}
