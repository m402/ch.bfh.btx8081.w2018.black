package ch.bfh.btx8081.w2018.black.view.routes;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import ch.bfh.btx8081.w2018.black.view.MainReportViewImpl;
import ch.bfh.btx8081.w2018.black.view.routes.applayout.ApplicationLayout;

/**
 * @author Roger Tschanz
 * Route for the Main View of the different Reports
 */

@Route(value = "Report", layout = ApplicationLayout.class)
public class MainViewReport extends VerticalLayout {

	/**
	 * Shows Main View of Reports
	 */
	public MainViewReport() {
		MainReportViewImpl reportView = new MainReportViewImpl();
		add(reportView);
	}
}