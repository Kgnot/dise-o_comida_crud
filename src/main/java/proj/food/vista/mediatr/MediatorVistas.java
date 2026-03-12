package proj.food.vista.mediatr;

import proj.food.vista.interfaces.CustomerView;
import proj.food.vista.interfaces.FoodView;
import proj.food.vista.interfaces.StartView;

public class MediatorVistas {

    private final StartView startView;
    private final FoodView foodView;
    private final CustomerView customerView;


    public MediatorVistas(StartView startView, FoodView foodView, CustomerView customerView) {
        this.startView = startView;
        this.foodView = foodView;
        this.customerView = customerView;
    }




}
