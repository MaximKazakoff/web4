package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastReport() {
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.createCriteria(DailyReport.class).setProjection(Projections.max("id")).uniqueResult();
        DailyReport dailyReport = (DailyReport) session.createCriteria(DailyReport.class).add(Restrictions.eq("id", id - 1L)).uniqueResult();
        transaction.commit();
        session.close();
        return dailyReport;
    }

    public void createEmptyReport(DailyReport dailyReport) {
        Transaction tx = session.beginTransaction();
        session.save(dailyReport);
        tx.commit();
        session.close();
    }

    public void updateReport(DailyReport dailyReport) {
        Transaction tx = session.beginTransaction();
        session.save(dailyReport);
        tx.commit();
        session.close();

    }

    public void deleteReports() {
        Transaction tx = session.beginTransaction();
        session.createQuery("DELETE FROM DailyReport").executeUpdate();
        tx.commit();
        session.close();
    }
}
