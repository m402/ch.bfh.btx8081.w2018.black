/**
 * 
 */
package ch.bfh.btx8081.w2018.black.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ch.bfh.btx8081.w2018.black.model.ifaces.AnamnesisService;

/**
 * @author moritz
 *
 */
public class AnamnesisServiceImpl implements AnamnesisService {

	private final static Logger LOGGER = Logger.getLogger(AnamnesisServiceImpl.class.getName());
	public class AnamnesisImpl implements Anamnesis {
		private int id = -1;
		private String chiefComplaint = null;
		private LocalDate startDate = null;
		private String selfAnamnesis = null;
		private String familyAnamnesis = null;
		private String socialAnamnesis = null;
		private String systemAnamnesis = null;
		private String foreignAnamnesis = null;
		private String additionalInformation = null;
		private LocalDate created = null;
		private LocalDate modified = null;
		private LocalDate deleted = null;

		public AnamnesisImpl(int id, String chiefComplaint, LocalDate startDate, String selfAnamnesis,
				String familyAnamnesis, String socialAnamnesis, String systemAnamnesis, String foreignAnamnesis,
				String additionalInformation) {
			super();
			this.id = id;
			this.chiefComplaint = chiefComplaint;
			this.startDate = startDate;
			this.selfAnamnesis = selfAnamnesis;
			this.familyAnamnesis = familyAnamnesis;
			this.socialAnamnesis = socialAnamnesis;
			this.systemAnamnesis = systemAnamnesis;
			this.foreignAnamnesis = foreignAnamnesis;
			this.additionalInformation = additionalInformation;
		}

		@Override
		public int getID() {
			return id;
		}

		public void setID(int id) {
			this.id = id;
		}
		@Override
		public String getChiefComplaint() {
			return chiefComplaint;
		}

		public void setChiefComplaint(String chiefComplaint) {
			this.chiefComplaint = chiefComplaint;
		}

		@Override
		public LocalDate getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}

		@Override
		public String getSelfAnamnesis() {
			return selfAnamnesis;
		}

		public void setSelfAnamnesis(String selfAnamnesis) {
			this.selfAnamnesis = selfAnamnesis;
		}

		@Override
		public String getFamilyAnamnesis() {
			return familyAnamnesis;
		}

		public void setFamilyAnamnesis(String familyAnamnesis) {
			this.familyAnamnesis = familyAnamnesis;
		}

		@Override
		public String getSocialAnamnesis() {
			return socialAnamnesis;
		}

		public void setSocialAnamnesis(String socialAnamnesis) {
			this.socialAnamnesis = socialAnamnesis;
		}

		@Override
		public String getSystemAnamnesis() {
			return this.systemAnamnesis;
		}

		public void setSystemAnamnesis(String systemAnamnesis) {
			this.systemAnamnesis = systemAnamnesis;
		}

		@Override
		public String getForeignAnamnesis() {
			return foreignAnamnesis;
		}

		public void setForeignAnamnesis(String foreignAnamnesis) {
			this.foreignAnamnesis = foreignAnamnesis;
		}

		@Override
		public String getAdditionalInformation() {
			return additionalInformation;
		}

		public void setAdditionalInformation(String additionalInformation) {
			this.additionalInformation = additionalInformation;
		}

		@Override
		public LocalDate getCreated() {
			return created;
		}

		@Override
		public LocalDate getModified() {
			return modified;
		}

		@Override
		public LocalDate getDeleted() {
			return deleted;
		}

	}
	private DataSource ds;

	public AnamnesisServiceImpl() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/postgres");
		} catch (NamingException e) {
			LOGGER.severe("Can't connect to db. Error: " + e.getMessage());
		}
	}


	/* (non-Javadoc)
	 * @see ch.bfh.btx8081.w2018.black.model.ifaces.MainAnamnesisModel#getAnamnesisList(int)
	 */
	@Override
	public Anamnesis getAnamnesis(int caseID) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Anamnesis anamnesis = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("SELECT * FROM anamnesis WHERE case_id = ?");
			ps.setInt(1, caseID);
			rs = ps.executeQuery();
			if (rs.next()) {
				LocalDate localStartDate = null;
				Date startDate = rs.getDate("chief_complaint_start_date");
				if(startDate != null) {
					localStartDate = startDate.toLocalDate();
				}
				anamnesis = new AnamnesisImpl(rs.getInt("anamnesis_id"), rs.getString("chief_complaint"),
						localStartDate,
						rs.getString("self_anamnesis"),
						rs.getString("family_anamnesis"), rs.getString("social_anamnesis"),
						rs.getString("system_anamnesis"), rs.getString("foreign_anamnesis"),
						rs.getString("additional_information"));
			}
		} catch (SQLException e) {
			LOGGER.warning("Patient query went wrong...\nError: " + e.getMessage());
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return anamnesis;
	}
}
