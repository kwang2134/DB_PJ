package testdb;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class EmpLogin extends JFrame { // ����� ������� �α����ϴ� ������
	JFrame jframe = new JFrame();
	JPanel jpanel = new JPanel();

	JLabel EmpLog = new JLabel();
	JLabel IdLabel = new JLabel();
	JLabel PwLabel = new JLabel();
	JTextField EmpId = new JTextField();
	JPasswordField EmpPwField = new JPasswordField();

	JButton EmpLoginb = new JButton();
	String id = "";
	String pw = "";
	String EmpPw = "";

	public void EmpLogin_init() {
		jframe.setSize(800, 800); // ��ü â ũ��
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // â �ݱ� ��ư ������ ������ ����
		jframe.setVisible(true);
		Font EmpLogFontsz = new Font("����", Font.PLAIN, 30);

		jpanel.setLayout(null);
		jframe.add(jpanel);

		jpanel.add(EmpLog = new JLabel("��� �α���"));
		EmpLog.setFont(EmpLogFontsz);
		EmpLog.setBounds(300, 150, 300, 200);

		jpanel.add(EmpId = new JTextField());
		EmpId.setBounds(350, 350, 150, 30);
		jpanel.add(EmpPwField = new JPasswordField());
		EmpPwField.setBounds(350, 400, 150, 30);

		jpanel.add(IdLabel = new JLabel("���"));
		IdLabel.setBounds(300, 350, 30, 30);
		jpanel.add(PwLabel = new JLabel("��й�ȣ"));
		PwLabel.setBounds(280, 400, 60, 30);

		jpanel.add(EmpLoginb = new JButton("�α���"));
		EmpLoginb.setBounds(300, 450, 200, 30);

		Testdb tdb = new Testdb();

		EmpLoginb.addActionListener(new ActionListener() { // ��� ���� ��ȯ �Լ��� �α���
			public void actionPerformed(ActionEvent e) {
				String Empid = EmpId.getText();
				char epw[] = EmpPwField.getPassword();
				EmpPw = "";
				for (char ch : epw) {
					Character.toString(ch);
					EmpPw += (EmpPw.equals("")) ? "" + ch + "" : "" + ch + "";
				}
				if (EmpPw.equals("")) {
					JOptionPane.showMessageDialog(null, "��� ��ȣ�� �Էµ��� �ʾҽ��ϴ�.");
				} else {
					String[] EmpData = tdb.EmpLogin(id, pw, Empid, EmpPw);
					if (EmpData[0].equals("Not Found")) {
						JOptionPane.showMessageDialog(null, "���� ���� �ʴ� ����Դϴ�.");
					} else if (EmpData[0].equals("Wrong Pw")) {
						JOptionPane.showMessageDialog(null, "��� ��ȣ�� �ùٸ��� �ʽ��ϴ�.");
					} else {
						MainUi mainui = new MainUi();
						mainui.setData(id, pw, EmpData);
						mainui.MainUi_init();
						jframe.dispose();
					}
				}
			}
		});

	}

	public void setSysIdPw(String sid, String spw) {
		this.id = sid;
		this.pw = spw;
	}
}