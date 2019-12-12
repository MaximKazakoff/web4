package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        System.out.println("class: CarService method: getAllCars");

        List<Car> listCars = new CarDao(sessionFactory.openSession()).getAllCars();
        System.out.println("listCars:");
        listCars.forEach(el -> System.out.println(el.toString()));
        System.out.println(" ");
        return listCars;
    }

    public Car getCar(String brand, String model, String licensePlate) {
        System.out.println("class: CarService method: getCar");
        System.out.println("Params: brand " + brand + ", model " + model + ", model " + licensePlate);
        Car car = new CarDao(sessionFactory.openSession()).getCar(brand, model, licensePlate);
        System.out.println(car.toString());
        System.out.println(" ");
        return car;
    }

    public void deleteCar(Car car) {
        System.out.println("class: CarService method: deleteCar");
        System.out.println(car.toString());
        System.out.println(" ");
        // Удалить машину из списка доступных для продажи
        new CarDao(sessionFactory.openSession()).deleteCar(car);
    }

    public void addCar(Car car) {
        System.out.println("class: CarService method: addCar");
        System.out.println(car.toString());
        System.out.println(" ");
        new CarDao(sessionFactory.openSession()).addCar(car);
    }

    public Boolean getPermissionToAdd(String brand) { // permission to add
        System.out.println("class: CarService method: getPermissionToAdd");
        System.out.println("Param brand " + brand);
        System.out.println(" ");
        if (new CarDao(sessionFactory.openSession()).getCarCountByBrand(brand) <= 10) {
            return true;
        } else {
            return false;
        }

    }

    public void deleteCars() {
        System.out.println("class: CarService method: deleteCars");
        System.out.println(" ");
        new CarDao(sessionFactory.openSession()).deleteCars();
    }


}
