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

public class SysLogin extends JFrame { // DB로그인을 위한 페이지
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
		jframe.setSize(800, 800); // 전체 창 크기
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정
		jframe.setVisible(true);
		Font SysLogFontsz = new Font("돋움", Font.PLAIN, 30);

		jpanel.setLayout(null);
		jframe.add(jpanel);

		jpanel.add(IdLabel = new JLabel("ID"));
		IdLabel.setBounds(300, 350, 30, 30);
		jpanel.add(PwLabel = new JLabel("PW"));
		PwLabel.setBounds(300, 400, 30, 30);
		jpanel.add(SysLog = new JLabel("시스템 로그인"));
		SysLog.setFont(SysLogFontsz);
		SysLog.setBounds(300, 150, 300, 200);
		jpanel.add(SysId = new JTextField());
		SysId.setBounds(350, 350, 150, 30);
		jpanel.add(SysPw = new JPasswordField());
		SysPw.setEchoChar('*');
		SysPw.setBounds(350, 400, 150, 30);
		jpanel.add(SysLoginb = new JButton("로그인"));
		SysLoginb.setBounds(350, 450, 150, 30);

		Testdb tdb = new Testdb();

		SysLoginb.addActionListener(new ActionListener() { // DB로그인 버튼 이벤트
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
					JOptionPane.showMessageDialog(null, "ID 또는 PW가 잘못되었습니다.");
				} else {
					jframe.dispose();
					EmpLogin emplog = new EmpLogin();
					emplog.setSysIdPw(id, pw); // DB id,pw 정보 넘겨주는 부분
					emplog.EmpLogin_init();
					
				}
			}
		});

	}
}

