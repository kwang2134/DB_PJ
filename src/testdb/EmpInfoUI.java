package testdb;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EmpInfoUI extends JFrame {
JFrame frame = new JFrame();
	
	private Container tablePane;
	private JScrollPane scrollTable;
	private DefaultTableModel model;
	private JTable empTable;
	
	private Container functionPane;
	private JComboBox filterCBox;
	private JTextField searchTextField;
	private JButton searchBtn;
	private JButton editBtn;
	private JButton backBtn;
	
	private String[] searchOpt = {"사번", "이름"};
	private String[] colName = {"사번", "출근일", "출근시간", "퇴근시간", "근무시간", "연장근무"};
	private String id;
	private String pw;
	private String EmpId;
	
	public EmpInfoUI() {
		frame.setTitle("직원 관리");
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
		empTable = new JTable(model);
		scrollTable = new JScrollPane(empTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tablePane.add(scrollTable);
		
		filterCBox = new JComboBox(searchOpt);
		functionPane.add(filterCBox);
		
		searchTextField = new JTextField(10);
		functionPane.add(searchTextField);
		
		searchBtn = new JButton("검색");
		searchBtn.addActionListener(new ActionListener() { //검색 버튼 이벤트
			public void actionPerformed(ActionEvent e) {
				String empInfo = searchTextField.getText();
				if ((filterCBox.getSelectedItem()).equals("사번")) {
					
				}
				else if ((filterCBox.getSelectedItem()).equals("이름")) {
					
				}
			}
		});
		functionPane.add(searchBtn);
		
		editBtn = new JButton("수정");
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		functionPane.add(editBtn);
		
		backBtn = new JButton("뒤로가기");
		backBtn.addActionListener(new ActionListener() {
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
