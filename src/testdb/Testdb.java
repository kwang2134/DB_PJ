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
			System.out.println("����̹� ���� ����");
		} catch (ClassNotFoundException e) {
			System.out.println("No Driver.");
		}
	}

	public int DB_Connect(String id, String password) {
		int flag = 0;
		try {
			con = DriverManager.getConnection(url, id, password);
			System.out.println("DB ���� ����");
		} catch (SQLException e) {
			System.out.println("Connection Fail");
			flag = 1;
		}
		return flag;
	}

	public int isExistD(String id, String password) { // �޿� ������ ����Ǿ� DB�� �����ϴ��� üũ�ϴ� �Լ�
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
			String sql = "select �� from �޿����� where �� = '" + preMonth + "'";
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

	public int insertSalary(String id, String password) { // �޿� ���� statement
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
			String sql2 = "insert all into �޿����� values (���,'" + forDbDate + "',null) select ��� from ���";
			count = stmt.executeUpdate(sql2);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public String[] EmpLogin(String id, String password, String EmpId, String EmpPw) { // ��� ���� ��ȯ �Լ� [0] ��� [1] �̸� [2]
																						// // �μ�
		String EmpData[] = new String[3];
		String EmpName = "";
		String EmpDept = "";
		String EmpPwDB = "";
		DB_Connect(id, password);
		boolean exist;
		try {
			Statement stmt = con.createStatement();
			String sql = "select ��� from ��� where ��� ='" + EmpId + "'";
			ResultSet rs = stmt.executeQuery(sql);
			exist = rs.next();
			if (!exist) {
				EmpId = "Not Found";
			} else if (exist) {
				String sql1 = "select �̸�, �μ�, ��й�ȣ from ��� where ��� = '" + EmpId + "'";
				ResultSet rs1 = stmt.executeQuery(sql1);
				while (rs1.next()) {
					EmpName = rs1.getString("�̸�");
					EmpDept = rs1.getString("�μ�");
					EmpPwDB = rs1.getString("��й�ȣ");
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

	public int attend(String id, String pw, String EmpId, String attendday, String attendtime) { // ��� preparedstatement
		DB_Connect(id, pw);
		int count = 0;
		try {
			String sql = "insert into ��������� values (?,?,?,null,null,null)";
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

	public void leave(String id, String pw, int EID, String leaveDate) { // ��� callablestatement
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

	public int absence(String id, String pw, String EmpId, String date, String reason) { // ��� ���� ��� preparedstatement
		DB_Connect(id, pw);
		int count = 0;
		try {
			String sql = "insert into ������� values (?,?,?)";
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

	public void NoReason(String id, String pw, String date) { // ���� ��� ���ν��� ȣ�� callablestatement
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

	public DefaultTableModel PaySearchAll(String id, String pw, DefaultTableModel model) { // �޿� ȭ�� ��ü ���� ���� statement
		DB_Connect(id, pw);
		try {
			Statement stmt = con.createStatement();
			String sql = "select * from �޿�����";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String empNum = rs.getString("���");
				String mon = rs.getString("��");
				String pay = rs.getString("�ݾ�");

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

	public DefaultTableModel PaySearch(String id, String pw, String type, String empInfo, DefaultTableModel model) { // �޿�
																														// ����
																														// ���̺�
																														// Ư��
																														// ����
																														// ����
																														// preparedStatement
		DB_Connect(id, pw);
		try {
			String sql = "select �޿�����.���, ��, �ݾ� from �޿�����, ��� where ���.��� = �޿�����.��� and ���."+ type +" = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, empInfo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String empNum = rs.getString("���");
				String mon = rs.getString("��");
				String pay = rs.getString("�ݾ�");

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

	/*
	 * public DefaultTableModel SearchAll(String id, String pw, DefaultTableModel
	 * model) { // ��������� ȭ�� ��ü ���� ���� statement DB_Connect(id, pw); try { Statement
	 * stmt = con.createStatement(); String sql = "select * from ���������"; ResultSet
	 * rs = stmt.executeQuery(sql); while (rs.next()) { String empNum =
	 * rs.getString("���"); String attD = rs.getString("�����"); String attT =
	 * rs.getString("��ٽð�"); String leaveT = rs.getString("��ٽð�"); String workT =
	 * rs.getString("�ٹ��ð�"); String overT = rs.getString("����ٹ�");
	 * 
	 * Object obj[] = { empNum, attD, attT, leaveT, workT, overT };
	 * model.addRow(obj); } stmt.close(); rs.close(); } catch (SQLException e) {
	 * e.printStackTrace(); } return model; }
	 */
}