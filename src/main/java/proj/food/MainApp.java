package proj.food;

import proj.food.config.database.factory.ConnectFactory;
import proj.food.config.database.factory.LocalDatabaseConnectionFactory;
import proj.food.entity.CustomerEntity;
import proj.food.repository.CustomerRepository;
import proj.food.vista.implementation.CustomerViewConsole;
import proj.food.vista.implementation.CustomerViewFX;
import proj.food.vista.implementation.CustomerViewSwing;
import proj.food.vista.interfaces.CustomerView;

public class MainApp {

    public static void main(String[] args) {
        ConnectFactory connectFactory = new LocalDatabaseConnectionFactory();
        var connectInstance = connectFactory.getConnectInstance();

        CustomerRepository customerRepository =
                new CustomerRepository(connectInstance.getEntityManagerFactory());
        customerRepository.save(new CustomerEntity(null, "Henry"));
        customerRepository.findAll().forEach(System.out::println);

//        CustomerView customerView = new CustomerViewSwing();
//        CustomerView customerView = new CustomerViewConsole();
        CustomerView customerView = new CustomerViewFX();
        customerView.showMenu();
    }
}



