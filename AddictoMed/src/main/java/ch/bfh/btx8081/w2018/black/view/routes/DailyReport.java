package ch.bfh.btx8081.w2018.black.view.routes;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import ch.bfh.btx8081.w2018.black.model.CaseServiceImpl;
import ch.bfh.btx8081.w2018.black.presenter.DailyReportPresenterImpl;
import ch.bfh.btx8081.w2018.black.view.DailyReportViewImpl;
import ch.bfh.btx8081.w2018.black.view.routes.applayout.ApplicationLayout;

@Route(value = "DailyReport", layout = ApplicationLayout.class)
public class DailyReport extends VerticalLayout {
	private static final long serialVersionUID = 3492033588664010645L;

	public DailyReport() {
		DailyReportViewImpl dailyReportViewImpl = new DailyReportViewImpl();
		CaseServiceImpl mainCasesModelImpl = new CaseServiceImpl();
		new DailyReportPresenterImpl(dailyReportViewImpl, mainCasesModelImpl);
		add(dailyReportViewImpl);
	}
}