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

public class SysLogin extends JFrame { // DB�α����� ���� ������
	JFrame jframe = new JFrame();
	JPanel jpanel = new JPanel();
	JTextField SysId = new JTextField();
	JPasswordField SysPw = new JPasswordField();
	JLabel SysLog = new JLabel();
	JLabel IdLabel = new JLabel();
	JLabel PwLabel = new JLabel();
	JButton SysLoginb = new JButton();

	String id;
	String pw;

	SysLogin() {
		SysLogin_init();
	}

	public void SysLogin_init() {
		jframe.setSize(800, 800); // ��ü â ũ��
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // â �ݱ� ��ư ������ ������ ����
		jframe.setVisible(true);
		Font SysLogFontsz = new Font("����", Font.PLAIN, 30);

		jpanel.setLayout(null);
		jframe.add(jpanel);

		jpanel.add(IdLabel = new JLabel("ID"));
		IdLabel.setBounds(300, 350, 30, 30);
		jpanel.add(PwLabel = new JLabel("PW"));
		PwLabel.setBounds(300, 400, 30, 30);
		jpanel.add(SysLog = new JLabel("�ý��� �α���"));
		SysLog.setFont(SysLogFontsz);
		SysLog.setBounds(300, 150, 300, 200);
		jpanel.add(SysId = new JTextField());
		SysId.setBounds(350, 350, 150, 30);
		jpanel.add(SysPw = new JPasswordField());
		SysPw.setEchoChar('*');
		SysPw.setBounds(350, 400, 150, 30);
		jpanel.add(SysLoginb = new JButton("�α���"));
		SysLoginb.setBounds(350, 450, 150, 30);

		Testdb tdb = new Testdb();

		SysLoginb.addActionListener(new ActionListener() { // DB�α��� ��ư �̺�Ʈ
			public void actionPerformed(ActionEvent e) {
				id = SysId.getText();
				char spw[] = SysPw.getPassword();
				pw = "";
				for (char ch : spw) {
					Character.toString(ch);
					pw += (pw.equals("")) ? "" + ch + "" : "" + ch + "";
				}

				int flag = tdb.DB_Connect(id, pw);

				if (flag == 1) {
					JOptionPane.showMessageDialog(null, "ID �Ǵ� PW�� �߸��Ǿ����ϴ�.");
				} else {
					jframe.dispose();
					EmpLogin emplog = new EmpLogin();
					emplog.setSysIdPw(id, pw); // DB id,pw ���� �Ѱ��ִ� �κ�
					emplog.EmpLogin_init();
					
				}
			}
		});

	}
}

