package ua.com.jdev.view;

import javafx.fxml.FXML;
import ua.com.jdev.MainApp;

/**
 * @author modkomi
 * @since 07.02.2016
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Показать окно
     */
    @FXML
    private void handleRestoreEmployee() {
        mainApp.showRestoreEmployeeDialog();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
