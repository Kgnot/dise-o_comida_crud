package proj.food;


import proj.food.config.database.factory.ConnectFactory;
import proj.food.config.database.factory.LocalDatabaseConnectionFactory;
import proj.food.entity.CustomerEntity;
import proj.food.repository.CustomerRepository;

public class MainApp {

    public static void main(String[] args) {
        ConnectFactory connectFactory = new LocalDatabaseConnectionFactory();
        var connectInstance = connectFactory.getConnectInstance();

        CustomerRepository customerRepository =
                new CustomerRepository(connectInstance.getEntityManagerFactory());
        customerRepository.save(new CustomerEntity(null, "Henry"));
        customerRepository.findAll().forEach(System.out::println);

    }
}
