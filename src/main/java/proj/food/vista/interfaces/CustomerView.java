package proj.food.vista.interfaces;

import proj.food.entity.CustomerEntity;

import java.util.List;

public interface CustomerView {

    void showMenu();

    void showCustomerList(List<CustomerEntity> entities);

    void showError(String message);

    void exit();
}
