package testdb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PayUI extends JFrame { //�޿� ���� ������
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
	
	private String[] searchOpt = {"���", "�̸�"};
	private String[] colName = {"���", "��", "�ݾ�"};
	private String id;
	private String pw;
	private String EmpId;
	
	public PayUI() {
		Testdb tdb = new Testdb();
		frame.setTitle("�޿� ����");
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
		
		searchBtn = new JButton("�˻�");
		searchBtn.addActionListener(new ActionListener() { //�˻� ��ư �̺�Ʈ
			public void actionPerformed(ActionEvent e) {
				String type = filterCBox.getSelectedItem().toString();
				String empInfo = searchTextField.getText();
				if (empInfo.equals(null)) { //�˻�â �����̸� ��ü ���
					model = tdb.PaySearchAll(id, pw, model);
				}
				else {
					model = tdb.PaySearch(id, pw, type, empInfo, model);
				}
				payTable.repaint();
			}
		});
		functionPane.add(searchBtn);
		
		settelmentBtn = new JButton("����");
		settelmentBtn.addActionListener(new ActionListener() { //���� ��ư �̺�Ʈ
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
					JOptionPane.showMessageDialog(null, "�޿� ������ �̹� �Ϸ�Ǿ����ϴ�.");
				} else if (today.equals("22")) {
					int count = tdb.insertSalary(id, pw);
					JOptionPane.showMessageDialog(null, "���" + count + "���� �޿��� ����Ǿ����ϴ�.");
				} else {
					JOptionPane.showMessageDialog(null, "�޿� �������� �ƴմϴ�.");
				}
			}
		});
		functionPane.add(settelmentBtn);
		
		backBtn = new JButton("�ڷΰ���");
		backBtn.addActionListener(new ActionListener() { //�ڷΰ���(����ȭ��) ��ư �̺�Ʈ
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
