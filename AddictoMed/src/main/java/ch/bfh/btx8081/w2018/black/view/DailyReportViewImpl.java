package ch.bfh.btx8081.w2018.black.view;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import ch.bfh.btx8081.w2018.black.view.ifaces.DailyReportView;
import ch.bfh.btx8081.w2018.black.view.ifaces.DailyReportView.DailyReportGenerateListener;


public class DailyReportViewImpl extends VerticalLayout implements DailyReportView {
	
	private static final long serialVersionUID = -2687287531996500897L;
	private DatePicker DatePickerDailyReport = new DatePicker();
	private LocalDate dailyReportDate;
	private Label dateLabel = new Label("");
	private List<DailyReportGenerateListener> dailyReportListeners = new ArrayList<>();
	private Button btnDailyReport = new Button("Report");
	private Anchor downloadlink = new Anchor();

	public DailyReportViewImpl() {
		DatePickerDailyReport.setLabel("Reportdatum");
		btnDailyReport.addClickListener(event -> {
			try {
				System.out.println(dailyReportListeners);
				for(DailyReportGenerateListener listener:dailyReportListeners) {
					listener.generateDailyReport(dailyReportDate);
				}
			}
			catch (NumberFormatException e) {
			}
		});
		
		DatePickerDailyReport.addValueChangeListener(event -> {
			dailyReportDate = event.getValue();
			dateLabel.setText(dailyReportDate.toString());
		});
		
		add(DatePickerDailyReport, btnDailyReport, downloadlink);
	}

	
	public void generateDailyReport(Date date) {
		VerticalLayout reportcolumn = new VerticalLayout();
		reportcolumn.setWidth("100%");

	}

	@Override
	public void addDailyReportGenerateListener(DailyReportGenerateListener listener) {
		dailyReportListeners.add(listener);
	}
	
	
	public void downloadDailyReport(InputStream in, String filename) {
		StreamResource myResource = new StreamResource(filename, () -> in);
		downloadlink.setHref(myResource);
		downloadlink.getElement().setAttribute("download", true);
		UI.getCurrent().getPage().executeJavaScript("$0.click()", downloadlink);
	}


}
