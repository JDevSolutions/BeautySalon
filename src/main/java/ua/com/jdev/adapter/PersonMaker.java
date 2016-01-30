package ua.com.jdev.adapter;

import ua.com.jdev.model.Client;
import ua.com.jdev.model.Employee;

/**
 * Класс-Адаптер для конвертирования строк из столбцов "Сотрудник" и "Клиент" вкладки "График" в соответстующие объекты
 *
 * Переменная @text должна хранить в себе имя, отчество и фамилию
 * разделенные пробелами
 */

public class PersonMaker {

    public static Employee makeEmployee(String text) {
        String[] array = text.trim().split(" ");
        return new Employee(array[0], array[1], array[2]);
    }

    public static Client makeClient(String text) {
        String[] array = text.trim().split(" ");
        return new Client(array[0], array[1], array[2]);
    }
}
