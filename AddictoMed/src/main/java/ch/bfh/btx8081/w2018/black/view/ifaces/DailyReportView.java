package ch.bfh.btx8081.w2018.black.view.ifaces;

import java.time.LocalDate;
import java.util.Date;

public interface DailyReportView {
	public void generateDailyReport(Date date);
	
	interface DailyReportGenerateListener {
		public void generateDailyReport(LocalDate date);
	}
	public void addDailyReportGenerateListener(DailyReportGenerateListener listener);
}
