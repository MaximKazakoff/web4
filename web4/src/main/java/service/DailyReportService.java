package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import javax.persistence.Column;
import javax.sound.midi.Soundbank;
import java.util.List;

public class DailyReportService {
    private Long earnings = 0L;
    private Long soldCars = 0L;

    public Long getEarnings() {
        return earnings;
    }

    public Long getSoldCars() {
        return soldCars;
    }

    public void setEarnings(Long earnings) {
        this.earnings += earnings;
        this.soldCars += 1;
    }

    public void zeroParams() {
        earnings = 0L;
        soldCars = 0L;
    }

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        System.out.println("class: DailyReportService method: getAllDailyReports");

        List<DailyReport> dailyReportList = new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
        System.out.println("dailyReportList:");

        if (dailyReportList.size() != 0) {
            dailyReportList.forEach(el -> System.out.println(el.toString()));
        } else {
            System.out.println("Доступных отчетов нет");
        }
        System.out.println(" ");
        return dailyReportList;
    }


    public DailyReport getLastReport() {
        System.out.println("class: DailyReportService method: getLastReport");

        DailyReport dailyReport = new DailyReportDao(sessionFactory.openSession()).getLastReport();
        System.out.println("dailyReport: ");
        System.out.println(dailyReport.toString());
        return dailyReport;
    }

    public void createEmptyReport() {
        System.out.println("class: DailyReportService method: createEmptyReport");
        System.out.println(" ");
        new DailyReportDao(sessionFactory.openSession()).createEmptyReport(new DailyReport(0L, 0L));
    }

    public void updateReport(DailyReport dailyReport) {
        System.out.println("class: DailyReportService method: updateReport");
        System.out.println("dailyReport:");
        System.out.println(dailyReport.toString());
        System.out.println(" ");
        new DailyReportDao(sessionFactory.openSession()).updateReport(dailyReport);
    }

    public void deleteReports() {
        System.out.println("class: DailyReportService method: deleteReports");
        System.out.println(" ");
        new DailyReportDao(sessionFactory.openSession()).deleteReports();
    }
}
