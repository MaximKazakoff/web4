package servlet;

import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {
    /*
    Каждый день в салон поступают машины разных марок, машин одной марки может быть не более 10ти штук.
    Новые поступления происходят в течение дня.
    На пост запрос на `/producer` с параметрами, по названию аналогичными полям класса Car,
    нужно ответить 200м статусом, если машина принята, и 403м, если нет.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        Long price = Long.parseLong(req.getParameter("price"));
        CarService carService = CarService.getInstance();

        Car car = new Car(brand, model, licensePlate, price);
        if (carService.getPermissionToAdd(brand)) {
            carService.addCar(car);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
