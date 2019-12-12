package servlet;

import model.DailyReport;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewDayServlet extends HttpServlet {
    /*
    Смена дней происходит запросом на `/newday`. После смены дня, салон обязан сформировать отчет,
    в котором содержится суммарная выручка за день и количество проданных машин
    сервлет /newday только обновляет день, он сам не генерит и не возвращает никакие отчеты.
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        DailyReportService dailyReportService =  DailyReportService.getInstance();
        dailyReportService.updateReport(new DailyReport(dailyReportService.getEarnings(), dailyReportService.getSoldCars()));
        dailyReportService.zeroParams();
        dailyReportService.createEmptyReport();

    }
}
