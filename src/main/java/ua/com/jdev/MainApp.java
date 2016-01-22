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
import ua.com.jdev.model.Client;
import ua.com.jdev.model.Employee;
import ua.com.jdev.model.Goods;
import ua.com.jdev.model.ScheduleRecord;
import ua.com.jdev.view.TabsOverviewController;
import ua.com.jdev.view.dialogs.ClientEditDialogController;
import ua.com.jdev.view.dialogs.EmployeeEditDialogController;
import ua.com.jdev.view.dialogs.GoodsEditDialogController;
import ua.com.jdev.view.dialogs.ScheduleEditDialogController;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ScheduleEditDialogController scheduleController;
    private ClientEditDialogController clientController;
    private EmployeeEditDialogController employeeController;
    private GoodsEditDialogController goodsController;

    /**
     * The data as an observable list of Persons, Goods and Schedule.
     */
    private ObservableList<ScheduleRecord> scheduleRecordData = FXCollections.observableArrayList();
    private ObservableList<Goods> goodsData = FXCollections.observableArrayList();
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private ObservableList<Client> clientData = FXCollections.observableArrayList();

    public MainApp() {
        scheduleRecordData.add(new ScheduleRecord("12:30", "Alina Antonenko", "Lilya Marchenko"));
        goodsData.add(new Goods("0154", "Краска для волос", "49.90"));
        employeeData.add(new Employee("Anna", "Petrovna", "Ivanova", "380671597535", "Administrator"));
        clientData.add(new Client("Olga", "Ivanovna", "Safronova", "380503219876"));
    }

    public static void main(String[] args) {
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

    public ObservableList<ScheduleRecord> getScheduleRecordData() {
        return scheduleRecordData;
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
     * @param scheduleRecord the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showScheduleEditDialog(ScheduleRecord scheduleRecord) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialogs/ScheduleEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование записи о визите клиента");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the goods into the controller.
            scheduleController = loader.getController();
            scheduleController.setDialogStage(dialogStage);
            scheduleController.setScheduleRecord(scheduleRecord);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return scheduleController.isOkClicked();

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

            // Set the goods into the controller.
            goodsController = loader.getController();
            goodsController.setDialogStage(dialogStage);
            goodsController.setGoods(goods);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return goodsController.isOkClicked();

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

            // Set the employee into the controller.
            employeeController = loader.getController();
            employeeController.setDialogStage(dialogStage);
            employeeController.setClient(employee);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return employeeController.isOkClicked();

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

            // Set the client into the controller.
            clientController = loader.getController();
            clientController.setDialogStage(dialogStage);
            clientController.setClient(client);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return clientController.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ScheduleEditDialogController getScheduleController() {
        return scheduleController;
    }

    public GoodsEditDialogController getGoodsController() {
        return goodsController;
    }

    public EmployeeEditDialogController getEmployeeController() {
        return employeeController;
    }

    public ClientEditDialogController getClientController() {
        return clientController;
    }
}
