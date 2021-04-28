package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

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
	
	public List<PowerOutage> getAllPowerOutage(){
		String sql = "SELECT id, nerc_id, customers_affected, date_event_began, date_event_finished FROM poweroutages";
		List<PowerOutage> list= new ArrayList<>();
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			while(rs.next()) {
				Nerc nerc= new Nerc(rs.getInt("nerc_id"),null);
				LocalDateTime inizio= rs.getTimestamp("date_event_began").toLocalDateTime();
				LocalDateTime fine= rs.getTimestamp("date_event_finished").toLocalDateTime();
				PowerOutage p = new PowerOutage(rs.getInt("id"),rs.getInt("customers_affected"),inizio,fine,nerc);
				list.add(p);
			}
			List<Nerc> temp= this.getNercList();
			for (Nerc n : temp) {
				for (PowerOutage p : list)
					if (n.equals(p.getNerc()))
						p.setNerc(n);
			}
			conn.close();
		} catch(SQLException e) {
			throw new RuntimeException("Errore DB",e);
		}
		return list;
	}
	
	public List<PowerOutage> getPowerOutageByNerc (Nerc nerc, long maxOre){
		long oreInSec= maxOre*3600;
		String sql= "SELECT id, nerc_id, customers_affected, date_event_began, date_event_finished "
				+ "FROM poweroutages "
				+ "WHERE nerc_id=? AND (to_seconds(date_event_finished) - to_seconds(date_event_began) <= ?)"
				+ "ORDER BY YEAR(date_event_began) ASC";
		List<PowerOutage> list= new ArrayList<>();
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, nerc.getId());
			st.setLong(2, oreInSec);
			ResultSet rs= st.executeQuery();
			while(rs.next()) {
				LocalDateTime inizio= rs.getTimestamp("date_event_began").toLocalDateTime();
				LocalDateTime fine= rs.getTimestamp("date_event_finished").toLocalDateTime();
				PowerOutage p = new PowerOutage(rs.getInt("id"),rs.getInt("customers_affected"),inizio,fine,nerc);
				list.add(p);
			}
			conn.close();
		} catch(SQLException e) {
			throw new RuntimeException("Errore DB",e);
		}
		return list;
	}
}
