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

public class EmpLogin extends JFrame { // 사원이 사번으로 로그인하는 페이지
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
		jframe.setSize(800, 800); // 전체 창 크기
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정
		jframe.setVisible(true);
		Font EmpLogFontsz = new Font("돋움", Font.PLAIN, 30);

		jpanel.setLayout(null);
		jframe.add(jpanel);

		jpanel.add(EmpLog = new JLabel("사원 로그인"));
		EmpLog.setFont(EmpLogFontsz);
		EmpLog.setBounds(300, 150, 300, 200);

		jpanel.add(EmpId = new JTextField());
		EmpId.setBounds(350, 350, 150, 30);
		jpanel.add(EmpPwField = new JPasswordField());
		EmpPwField.setBounds(350, 400, 150, 30);

		jpanel.add(IdLabel = new JLabel("사번"));
		IdLabel.setBounds(300, 350, 30, 30);
		jpanel.add(PwLabel = new JLabel("비밀번호"));
		PwLabel.setBounds(280, 400, 60, 30);

		jpanel.add(EmpLoginb = new JButton("로그인"));
		EmpLoginb.setBounds(300, 450, 200, 30);

		Testdb tdb = new Testdb();

		EmpLoginb.addActionListener(new ActionListener() { // 사원 정보 반환 함수로 로그인
			public void actionPerformed(ActionEvent e) {
				String Empid = EmpId.getText();
				char epw[] = EmpPwField.getPassword();
				EmpPw = "";
				for (char ch : epw) {
					Character.toString(ch);
					EmpPw += (EmpPw.equals("")) ? "" + ch + "" : "" + ch + "";
				}
				if (EmpPw.equals("")) {
					JOptionPane.showMessageDialog(null, "비밀 번호가 입력되지 않았습니다.");
				} else {
					String[] EmpData = tdb.EmpLogin(id, pw, Empid, EmpPw);
					if (EmpData[0].equals("Not Found")) {
						JOptionPane.showMessageDialog(null, "존재 하지 않는 사원입니다.");
					} else if (EmpData[0].equals("Wrong Pw")) {
						JOptionPane.showMessageDialog(null, "비밀 번호가 올바르지 않습니다.");
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