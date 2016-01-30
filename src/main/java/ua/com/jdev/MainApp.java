package ua.com.jdev;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.com.jdev.dbase.DBHelper;
import ua.com.jdev.model.Client;
import ua.com.jdev.model.Employee;
import ua.com.jdev.model.Goods;
import ua.com.jdev.model.Order;
import ua.com.jdev.view.TabsOverviewController;
import ua.com.jdev.view.dialogs.*;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private OrderAddDialogController orderAddController;
    private OrderEditDialogController orderEditController;
    private ClientAddDialogController clientAddController;
    private ClientEditDialogController clientEditController;
    private EmployeeAddDialogController employeeAddController;
    private EmployeeEditDialogController employeeEditController;
    private GoodsAddDialogController goodsAddController;
    private GoodsEditDialogController goodsEditController;

    /**
     * The data as an observable list of Persons, Goods and Schedule.
     */
    private ObservableList<Order> orderData = FXCollections.observableArrayList();
    private ObservableList<Goods> goodsData = FXCollections.observableArrayList();
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private ObservableList<Client> clientData = FXCollections.observableArrayList();

    public MainApp() {
        orderData = (ObservableList<Order>) DBHelper.getData("orders");
        goodsData = (ObservableList<Goods>) DBHelper.getData("goods");
        employeeData = (ObservableList<Employee>) DBHelper.getData("employees");
        clientData = (ObservableList<Client>) DBHelper.getData("clients");
    }

    public static void main(String[] args) {
        //BasicConfigurator.configure();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Beauty Salon");

        initRootLayout();
        showTabsOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show thr scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: add log4j
        }
    }

    /**
     * Shows tabs overview inside the root layout.
     */
    public void showTabsOverview() {
        try {
            // Load tabs overview
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TabsOverview.fxml"));
            AnchorPane tabsOverview = loader.load();

            // Set tabs overview into the center of root layout
            rootLayout.setCenter(tabsOverview);

            // Give the controller access to the main app
            TabsOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 21.01.2016 add log4j
        }
    }

    /**
     * Returns the main stage.
     * @return - main stage return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Order> getOrderData() {
        return orderData;
    }

    public ObservableList<Goods> getGoodsData() {
        return goodsData;
    }

    public ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }

    public ObservableList<Client> getClientData() {
        return clientData;
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param order the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */

    public boolean showOrderAddDialog(Order order) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialogs/OrderAddDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавление записи о визите клиента");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goods into the controller.
            orderAddController = loader.getController();
            orderAddController.setDialogStage(dialogStage);
            orderAddController.setOrder(order);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return orderAddController.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showOrderEditDialog(Order order) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialogs/OrderEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование записи о визите клиента");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goods into the controller.
            orderEditController = loader.getController();
            orderEditController.setDialogStage(dialogStage);
            orderEditController.setOrder(order);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return orderEditController.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showGoodsAddDialog(Goods goods) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialogs/GoodsAddDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавление записи о товаре");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goods into the controller.
            goodsAddController = loader.getController();
            goodsAddController.setDialogStage(dialogStage);
            goodsAddController.setGoods(goods);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return goodsAddController.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showGoodsEditDialog(Goods goods) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialogs/GoodsEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование записи о товаре");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goods into the controller.
            goodsEditController = loader.getController();
            goodsEditController.setDialogStage(dialogStage);
            goodsEditController.setGoods(goods);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return goodsEditController.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showEmployeeAddDialog(Employee employee) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialogs/EmployeeAddDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавление записи о сотруднике");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the employee into the controller.
            employeeAddController = loader.getController();
            employeeAddController.setDialogStage(dialogStage);
            employeeAddController.setClient(employee);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return employeeAddController.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showEmployeeEditDialog(Employee employee) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialogs/EmployeeEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование записи о сотруднике");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the employee into the controller.
            employeeEditController = loader.getController();
            employeeEditController.setDialogStage(dialogStage);
            employeeEditController.setClient(employee);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return employeeEditController.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showClientAddDialog(Client client) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialogs/ClientAddDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавление записи о клиенте");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the client into the controller.
            clientAddController = loader.getController();
            clientAddController.setDialogStage(dialogStage);
            clientAddController.setClient(client);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return clientAddController.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showClientEditDialog(Client client) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialogs/ClientEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование записи о клиенте");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the client into the controller.
            clientEditController = loader.getController();
            clientEditController.setDialogStage(dialogStage);
            clientEditController.setClient(client);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return clientEditController.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public OrderEditDialogController getOrderEditController() {
        return orderEditController;
    }

    public ClientEditDialogController getClientEditController() {
        return clientEditController;
    }

    public EmployeeEditDialogController getEmployeeEditController() {
        return employeeEditController;
    }

    public GoodsEditDialogController getGoodsEditController() {
        return goodsEditController;
    }
}
