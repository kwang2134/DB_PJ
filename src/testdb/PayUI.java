package testdb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

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
	
	public PayUI() {
		frame.setTitle("급여 관리");
		frame.setResizable(false);
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
				String empInfo = searchTextField.getText();
				if ((filterCBox.getSelectedItem()).equals("사번")) {
					
				}
			}
		});
		functionPane.add(searchBtn);
		
		settelmentBtn = new JButton("정산");
		settelmentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//MainUI에 있는 정산 버튼 이벤트 넣을 예정
			}
		});
		functionPane.add(settelmentBtn);
		
		backBtn = new JButton("뒤로가기");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		functionPane.add(backBtn);
		
		frame.setSize(500,510);
		frame.setVisible(true);
	}
	
	public void setSysIdPw(String sid, String spw) {
		this.id = sid;
		this.pw = spw;
	}
}
