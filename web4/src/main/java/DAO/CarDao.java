package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import util.DBHelper;

import java.util.List;
import java.util.Queue;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> dailyReports = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public Car getCar(String brand, String model, String licensePlate) {
        Criteria criteria = session.createCriteria(Car.class);
        Criterion brandCr = Restrictions.eq("brand", brand);
        Criterion modelCr = Restrictions.eq("model", model);
        Criterion licensePlateCr = Restrictions.eq("licensePlate", licensePlate);
        Conjunction logicalExpression = Restrictions.and(brandCr, modelCr, licensePlateCr);
        criteria.add(logicalExpression);
        Car car = (Car) criteria.uniqueResult();
        session.close();
        return car;
    }

    public void deleteCar(Car car) {
        Transaction tx = session.beginTransaction();
        session.delete(car);
        tx.commit();
        session.close();
    }

    public void addCar(Car car) {
        Transaction tx = session.beginTransaction();
        session.save(car);
        tx.commit();
        session.close();
    }

    public void deleteCars() {
        Transaction tx = session.beginTransaction();
        session.createQuery("DELETE FROM Car").executeUpdate();
        tx.commit();
        session.close();
    }

    public int getCarCountByBrand(String brand) {
        System.out.println("class: CarDao method: getCarCountByBrand");
        System.out.println("Param brand " + brand);
        int result = session.createCriteria(Car.class).add(Restrictions.eq("brand", brand)).list().size();
        session.close();
        System.out.println("result " + result);
        return result;
    }


}
