package ch.bfh.btx8081.w2018.black.presenter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import ch.bfh.btx8081.w2018.black.model.CaseServiceImpl;
import ch.bfh.btx8081.w2018.black.model.PDFCreatorImpl;
import ch.bfh.btx8081.w2018.black.model.ifaces.CaseService.Case;
import ch.bfh.btx8081.w2018.black.presenter.ifaces.DailyReportPresenter;
import ch.bfh.btx8081.w2018.black.view.DailyReportViewImpl;


public class DailyReportPresenterImpl implements DailyReportPresenter {

	DailyReportViewImpl view = null;
	CaseServiceImpl casesModel = null;

	public DailyReportPresenterImpl(DailyReportViewImpl view, CaseServiceImpl casesModel) {
		this.view = view;
		this.casesModel = casesModel;
		view.addDailyReportGenerateListener(this);
	}

	/**
	 * Creates the Daily-Report as PDF containing all Cases between the selected startDate and EndDate
	 * @param startDate, endDate
	 * @return void
	 */
	@Override
	public void generateDailyReport(LocalDate date) {
		StringBuilder sb = new StringBuilder();
		List<Case> caselist = casesModel.generateWeeklyReport(date, date); //
		sb.append("\nReport für " + date + "\n\n");
		for(Case casesModel:caselist) {
			int caseId = casesModel.getCaseID();
			LocalDate startDateSB = casesModel.getStartDate();
			LocalDate endDateSB = casesModel.getEndDate();
			String place = casesModel.getPlace();
			sb.append("Case-ID: " + caseId + "\n");
			sb.append("StartDate: " + startDateSB + "\n");
			sb.append("EndDate: " + endDateSB + "\n");
			sb.append("Place: " + place + "\n\n");
		}
		try {
			PDFCreatorImpl pdf = new PDFCreatorImpl();
			pdf.setHeading("Daily Report");
			pdf.setContent(sb.toString());
			view.downloadDailyReport(pdf.getDocumet(), "DailyReport.pdf");
			pdf.close();
		} catch (IOException e) {
		}
	}

}