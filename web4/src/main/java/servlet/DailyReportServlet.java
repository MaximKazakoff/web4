package servlet;

import com.google.gson.Gson;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        Начальство может потребовать отчет по юрл `/report/last`
        за прошедший день, либо же за все дни, отправив запрос на `/report/all`.
         */
        Gson gson = new Gson();
        String json;
        if (req.getPathInfo().contains("all")) {
            json = gson.toJson(DailyReportService.getInstance().getAllDailyReports());
            resp.getWriter().write(json);
            resp.setStatus(200);
        } else if (req.getPathInfo().contains("last")) {
            json = gson.toJson(DailyReportService.getInstance().getLastReport());
            resp.getWriter().write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        Так же есть возможность удалить
        все данные об отчетах и машинах DELETE запросом на /report.
         */
        System.out.println("class DailyReportServlet method doDelete");
        DailyReportService.getInstance().deleteReports();
        DailyReportService.getInstance().getAllDailyReports();
        CarService.getInstance().deleteCars();
        CarService.getInstance().getAllCars();
    }
}
