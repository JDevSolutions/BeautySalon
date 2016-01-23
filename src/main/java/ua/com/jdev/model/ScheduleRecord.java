package ua.com.jdev.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScheduleRecord extends Base {

    private StringProperty time;
    private StringProperty employee;
    private StringProperty client;

    public ScheduleRecord() {
        this(null, null, null);
    }

    public ScheduleRecord(String time, String employee, String client) {
        this.time = new SimpleStringProperty(time);
        this.employee = new SimpleStringProperty(employee);
        this.client = new SimpleStringProperty(client);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getEmployee() {
        return employee.get();
    }

    public StringProperty employeeProperty() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee.set(employee);
    }

    public String getClient() {
        return client.get();
    }

    public StringProperty clientProperty() {
        return client;
    }

    public void setClient(String client) {
        this.client.set(client);
    }
}
