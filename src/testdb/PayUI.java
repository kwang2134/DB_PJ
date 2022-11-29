package testdb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PayUI extends JFrame { //급여 관리 페이지
	JFrame frame = new JFrame();
	
	private Container tablePane;
	private JScrollPane scrollTable;
	private DefaultTableModel model;
	private JTable payTable;
	
	private Container functionPane;
	private JComboBox filterCBox;
	private JTextField searchTextField;
	private JButton searchBtn;
	private JButton settelmentBtn;
	private JButton backBtn;
	
	private String[] searchOpt = {"사번", "이름"};
	private String[] colName = {"사번", "월", "금액"};
	private String id;
	private String pw;
	private String EmpId;
	
	public PayUI() {
		Testdb tdb = new Testdb();
		frame.setTitle("급여 관리");
		frame.setResizable(false);
		frame.setSize(500,510);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		
		tablePane = frame.getContentPane();
		functionPane = frame.getContentPane();
		functionPane.setLayout(new FlowLayout());
		
		model = new DefaultTableModel(colName, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		payTable = new JTable(model);
		scrollTable = new JScrollPane(payTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tablePane.add(scrollTable);
		
		filterCBox = new JComboBox(searchOpt);
		functionPane.add(filterCBox);
		
		searchTextField = new JTextField(10);
		functionPane.add(searchTextField);
		
		searchBtn = new JButton("검색");
		searchBtn.addActionListener(new ActionListener() { //검색 버튼 이벤트
			public void actionPerformed(ActionEvent e) {
				String type = filterCBox.getSelectedItem().toString();
				String empInfo = searchTextField.getText();
				if (empInfo.equals(null)) { //검색창 공백이면 전체 출력
					model = tdb.PaySearchAll(id, pw, model);
				}
				else {
					model = tdb.PaySearch(id, pw, type, empInfo, model);
				}
				payTable.repaint();
			}
		});
		functionPane.add(searchBtn);
		
		settelmentBtn = new JButton("정산");
		settelmentBtn.addActionListener(new ActionListener() { //정산 버튼 이벤트
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat day = new SimpleDateFormat("dd");
				SimpleDateFormat month = new SimpleDateFormat("mm");
				Date time = new Date();
				String today = day.format(time);
				String thisMonth = month.format(time);
				int preMonth = Integer.parseInt(thisMonth);
				preMonth = preMonth - 1;

				int flag = tdb.isExistD(id, pw);
				if (flag == 1) {
					JOptionPane.showMessageDialog(null, "급여 정산이 이미 완료되었습니다.");
				} else if (today.equals("22")) {
					int count = tdb.insertSalary(id, pw);
					JOptionPane.showMessageDialog(null, "사원" + count + "명의 급여가 정산되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "급여 정산일이 아닙니다.");
				}
			}
		});
		functionPane.add(settelmentBtn);
		
		backBtn = new JButton("뒤로가기");
		backBtn.addActionListener(new ActionListener() { //뒤로가기(메인화면) 버튼 이벤트
			public void actionPerformed(ActionEvent e) {
				MainUi mainui = new MainUi();
				mainui.setData(id, pw, EmpId);
				mainui.MainUi_init();
				frame.dispose();
			}
		});
		functionPane.add(backBtn);
		
		frame.setVisible(true);
	}
	
	public void setSysIdPw(String sid, String spw, String EmpId) {
		this.id = sid;
		this.pw = spw;
		this.EmpId = EmpId;
	}
}
