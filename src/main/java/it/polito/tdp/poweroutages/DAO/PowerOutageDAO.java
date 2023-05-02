package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutages> getAllPONerc(Nerc n){
		
		String sql = "SELECT id, nerc_id, customers_affected, date_event_began, date_event_finished "
				+ "FROM PowerOutages "
				+ "WHERE nerc_id = ? "
				+ "ORDER BY date_event_began ASC";
		
		List<PowerOutages> ritorno = new ArrayList<PowerOutages>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n.getId());
			
			ResultSet res = st.executeQuery();
			

			while (res.next()) {
				
				LocalDate x = res.getDate("date_event_began").toLocalDate();
				LocalTime y = res.getTime("date_event_began").toLocalTime();
				LocalDate x1 = res.getDate("date_event_finished").toLocalDate();
				LocalTime y1 = res.getTime("date_event_finished").toLocalTime();
				
				PowerOutages p = new PowerOutages(res.getInt("id"), n, res.getInt("customers_affected"),
						LocalDateTime.of(x,y), LocalDateTime.of(x1, y1));
				ritorno.add(p);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return ritorno;
	}
	

}
