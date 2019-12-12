package servlet;

import com.google.gson.Gson;
import model.Car;
import model.DailyReport;
import service.CarService;
import service.DailyReportService;

import javax.persistence.Column;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomerServlet extends HttpServlet {
    /*
    Покупатели могут запросить список имеющихся  машин по url `/customer`GET запросом
    и купить с помощью POST запроса на тот же url,
    передав параметры марки машины, названия и госномера.
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        Покупатели могут запросить список имеющихся  машин по url `/customer`GET запросом
         */
        System.out.println("CLASS CustomerServlet METHOD doGet");

        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());
        resp.getWriter().write(json);
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        Покупатели могут купить с помощью POST запроса на тот же url,
        передав параметры марки машины, названия и госномера.
         */
        System.out.println("BUY CAR");

        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");

        if (brand != null & model != null & licensePlate != null) {
            DailyReportService dailyReportService = DailyReportService.getInstance();
            CarService carService = CarService.getInstance();
            // Покупка и удаление из списка доступных
            Car car = carService.getCar(brand, model, licensePlate);
            CarService.getInstance().deleteCar(car);
            // Обновление отчета по дню
            System.out.println("class CustomerServlet method doPost");
            System.out.println("car.getPrice() " + car.getPrice());
            dailyReportService.setEarnings(car.getPrice());
            System.out.println("dailyReportService.getEarnings(): " + dailyReportService.getEarnings());
            System.out.println("dailyReportService.getSoldCars() " + dailyReportService.getSoldCars());
        }

    }
}
