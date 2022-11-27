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

public class Testdb {
	Connection con = null;
	String url = "jdbc:oracle:thin:@175.214.129.222:1521:XE";

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
		SimpleDateFormat month = new SimpleDateFormat("MM");
		Date time = new Date();
		String thisMonth = month.format(time);
		int preMonth = Integer.parseInt(thisMonth);
		preMonth = preMonth - 1;
		DB_Connect(id, password);
		try {
			Statement stmt = con.createStatement();
			String sql2 = "insert all into �޿����� values (���,'" + preMonth + "',null) select ��� from ���";
			count = stmt.executeUpdate(sql2);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public String[] EmpLogin(String id, String password, String EmpId) { // ��� ���� ��ȯ �Լ� [0] ��� [1] �̸� [2] �μ�
		String EmpData[] = new String[3];
		String EmpName = "";
		String EmpDept = "";
		DB_Connect(id, password);
		boolean exist;
		try {
			Statement stmt = con.createStatement();
			String sql = "select ��� from ��� where ��� ='" + EmpId + "'";
			ResultSet rs = stmt.executeQuery(sql);
			exist = rs.next();
			if (exist) {
				String sql1 = "select �̸�, �μ� from ��� where ��� = '" + EmpId + "'";
				ResultSet rs1 = stmt.executeQuery(sql1);
				while (rs1.next()) {
					EmpName = rs1.getString("�̸�");
					EmpDept = rs1.getString("�μ�");
				}
			} else {
				EmpId = "Not Found";
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

	public void leave(String id, String pw, String leavetime) { // ��� callablestatement
		DB_Connect(id, pw);
		try {
			CallableStatement cstmt = con.prepareCall("{��� ���ν���[(?)]}");
			cstmt.setString(1, leavetime);
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

	public void NoReason(String id, String pw) { // ���� ��� ���ν��� ȣ�� callablestatement
		DB_Connect(id, pw);
		try {
			CallableStatement cstmt = con.prepareCall("{���� ��� ���ν���}");
			cstmt.executeUpdate();
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	public void Search(String id, String pw, String empInfo) { // �޿� ���� ���̺� �˻�(�̿ϼ�)
		DB_Connect(id, pw);
		try {
			Statement stmt = con.createStatement();
			String numStmt = "select * from �޿����� where ��� = '" + empInfo + "'";
			ResultSet rs = stmt.executeQuery(numStmt);
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	*/
}