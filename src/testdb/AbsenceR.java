package testdb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AbsenceR extends JFrame { // ��� ���� ��� ������
	JFrame jframe = new JFrame();
	JPanel jpanel = new JPanel();

	JLabel reasonLabel = new JLabel();
	JTextField reasonTextField = new JTextField();
	JButton commitReason = new JButton();

	String id;
	String pw;
	String EmpId;

	public void AbsenceR_init() {
		Testdb tdb = new Testdb();
		jframe.setBounds(50, 50, 400, 400); // ��ü â ũ��
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // â �ݱ� ��ư ������ ������ ����
		jframe.setVisible(true);

		jpanel.setLayout(null);
		jframe.add(jpanel);

		jpanel.add(reasonLabel = new JLabel("��� ����"));
		reasonLabel.setBounds(170, 30, 80, 30);
		jpanel.add(reasonTextField = new JTextField());
		reasonTextField.setBounds(60, 80, 280, 180);
		jpanel.add(commitReason = new JButton("���"));
		commitReason.setBounds(170, 300, 100, 50);

		commitReason.addActionListener(new ActionListener() { // ��� ���� ��� ��ư �̺�Ʈ
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat abdate = new SimpleDateFormat("YYYY/MM/dd");
				String date = "";
				Date time = new Date();
				date = abdate.format(time);
				int flag = tdb.absence(id, pw, EmpId, date, reasonTextField.getText());
				if (flag == 1) {
					JOptionPane.showMessageDialog(null, "��� ������ ��ϵǾ����ϴ�");
				} else {
					JOptionPane.showMessageDialog(null, "�̹� ������ ��ϵǾ����ϴ�");
				}
			}
		});

	}

	public void setSysIdPw(String sid, String spw, String EmpId) {
		this.id = sid;
		this.pw = spw;
		this.EmpId = EmpId;
	}

}
