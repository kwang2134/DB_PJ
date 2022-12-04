package testdb;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

public class Testdb {
	Connection con = null;
	String url = "jdbc:oracle:thin:@localhost:1521:XE";;

	public Testdb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 적재 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("No Driver.");
		}
	}

	public int DB_Connect(String id, String password) {
		int flag = 0;
		try {
			con = DriverManager.getConnection(url, id, password);
			System.out.println("DB 연결 성공");
		} catch (SQLException e) {
			System.out.println("Connection Fail");
			flag = 1;
		}
		return flag;
	}

	public int isExistD(String id, String password) { // 급여 정보가 정산되어 DB에 존재하는지 체크하는 함수
		SimpleDateFormat month = new SimpleDateFormat("MM");
		Date time = new Date();
		String thisMonth = month.format(time);
		int preMonth = Integer.parseInt(thisMonth);
		preMonth = preMonth - 1;
		DB_Connect(id, password);
		int flag = 0;
		boolean exist;
		try {
			Statement stmt = con.createStatement();
			String sql = "select 월 from 급여정보 where 월 = '" + preMonth + "'";
			ResultSet rs = stmt.executeQuery(sql);
			exist = rs.next();
			if (exist)
				flag = 1;
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(flag);
		return flag;
	}

	public int insertSalary(String id, String password) { // 급여 정보 statement
		int count = 0;
		SimpleDateFormat year = new SimpleDateFormat("YYYY");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		Date time = new Date();
		String thisYear = year.format(time);
		String thisMonth = month.format(time);
		int preMonth = Integer.parseInt(thisMonth);
		preMonth = preMonth - 1;
		String MonthDb = Integer.toString(preMonth);
		String forDbDate = thisYear + "/" + MonthDb;
		System.out.println(forDbDate);
		DB_Connect(id, password);
		try {
			Statement stmt = con.createStatement();
			String sql2 = "insert all into 급여정보 values (사번,'" + forDbDate + "',null) select 사번 from 사원";
			count = stmt.executeUpdate(sql2);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public String[] EmpLogin(String id, String password, String EmpId, String EmpPw) { // 사원 정보 반환 함수 [0] 사번 [1] 이름 [2]
																						// // 부서
		String EmpData[] = new String[3];
		String EmpName = "";
		String EmpDept = "";
		String EmpPwDB = "";
		DB_Connect(id, password);
		boolean exist;
		try {
			Statement stmt = con.createStatement();
			String sql = "select 사번 from 사원 where 사번 ='" + EmpId + "'";
			ResultSet rs = stmt.executeQuery(sql);
			exist = rs.next();
			if (!exist) {
				EmpId = "Not Found";
			} else if (exist) {
				String sql1 = "select 이름, 부서, 비밀번호 from 사원 where 사번 = '" + EmpId + "'";
				ResultSet rs1 = stmt.executeQuery(sql1);
				while (rs1.next()) {
					EmpName = rs1.getString("이름");
					EmpDept = rs1.getString("부서");
					EmpPwDB = rs1.getString("비밀번호");
				}
				if (!EmpPwDB.equals(EmpPw)) {
					EmpId = "Wrong Pw";
				}
			}
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		EmpData[0] = EmpId;
		EmpData[1] = EmpName;
		EmpData[2] = EmpDept;
		return EmpData;

	}

	public int attend(String id, String pw, String EmpId, String attendday, String attendtime) { // 출근 preparedstatement
		DB_Connect(id, pw);
		int count = 0;
		try {
			String sql = "insert into 출퇴근정보 values (?,?,?,null,null,null)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, EmpId);
			pstmt.setString(2, attendday);
			pstmt.setString(3, attendtime);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void leave(String id, String pw, int EID, String leaveDate) { // 퇴근 callablestatement
		DB_Connect(id, pw);
		try {
			CallableStatement cstmt = con.prepareCall("{call WORKTIME_UPDATE(?,?)}");
			cstmt.setInt(1, EID);
			cstmt.setString(2, leaveDate);
			cstmt.executeUpdate();
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int absence(String id, String pw, String EmpId, String date, String reason) { // 결근 사유 등록 preparedstatement
		DB_Connect(id, pw);
		int count = 0;
		try {
			String sql = "insert into 결근정보 values (?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, EmpId);
			pstmt.setString(2, date);
			pstmt.setString(3, reason);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void NoReason(String id, String pw, String date) { // 무단 결근 프로시저 호출 callablestatement
		DB_Connect(id, pw);
		try {
			CallableStatement cstmt = con.prepareCall("{call ABSENCE_CHECK(?)}");
			cstmt.setString(1, date);
			cstmt.executeUpdate();
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public DefaultTableModel PaySearchAll(String id, String pw, DefaultTableModel model) { // 급여 화면 전체 직원 열람 statement
		DB_Connect(id, pw);
		try {
			Statement stmt = con.createStatement();
			String sql = "select * from 급여정보";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String empNum = rs.getString("사번");
				String mon = rs.getString("월");
				String pay = rs.getString("금액");

				Object obj[] = { empNum, mon, pay };
				model.addRow(obj);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}

	public DefaultTableModel PaySearch(String id, String pw, String type, String empInfo, DefaultTableModel model) { // 급여
																														// 관리
																														// 테이블
																														// 특정
																														// 직원
																														// 열람
																														// preparedStatement
		DB_Connect(id, pw);
		try {
			String sql = "select 급여정보.사번, 월, 금액 from 급여정보, 사원 where 사원.사번 = 급여정보.사번 and 사원."+ type +" = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, empInfo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String empNum = rs.getString("사번");
				String mon = rs.getString("월");
				String pay = rs.getString("금액");

				Object obj[] = { empNum, mon, pay };
				model.addRow(obj);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}


	public DefaultTableModel SearchAll(String id, String pw, DefaultTableModel model) { // 출퇴근정보 화면 전체 직원 열람 statement
		DB_Connect(id, pw);
		try {
			Statement stmt = con.createStatement();
			String sql = "select * from 출퇴근정보";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String empNum = rs.getString("사번");
				String attD = rs.getString("출근일");
				String attT = rs.getString("출근시간");
				String leaveT = rs.getString("퇴근시간");
				String workT = rs.getString("근무시간");
				String overT = rs.getString("연장근무");

				Object obj[] = { empNum, attD, attT, leaveT, workT, overT };
				model.addRow(obj);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public DefaultTableModel Search(String id, String pw, String type, String empInfo, DefaultTableModel model) { // 출퇴근정보 화면 특정 직원 열람

		DB_Connect(id, pw);
		try {
			String sql = "select 출퇴근정보.사번, 출근일, 출근시간, 퇴근시간, 근무시간, 연장근무 from 출퇴근정보, 사원 where 사원.사번 = 출퇴근정보.사번 and 사원."+ type +" = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, empInfo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String empNum = rs.getString("사번");
				String attD = rs.getString("출근일");
				String attT = rs.getString("출근시간");
				String leaveT = rs.getString("퇴근시간");
				String workT = rs.getString("근무시간");
				String overT = rs.getString("연장근무");

				Object obj[] = { empNum, attD, attT, leaveT, workT, overT };
				model.addRow(obj);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}

}