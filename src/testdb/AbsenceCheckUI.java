package testdb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AbsenceCheckUI extends JFrame {
	String id;
	String pw;
	JFrame jframe = new JFrame();
	JPanel jpanel = new JPanel();

	JLabel absCheckLabel = new JLabel();
	JTextField absTextField = new JTextField();
	JButton commitbtn = new JButton();

	public void AbsenceCheckUI_init() {
		Testdb tdb = new Testdb();
		jframe.setSize(400, 400); // 전체 창 크기
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정
		jframe.setVisible(true);

		jpanel.setLayout(null);
		jframe.add(jpanel);

		jpanel.add(absCheckLabel = new JLabel("무단 결근 체크"));
		absCheckLabel.setBounds(160, 30, 120, 30);
		jpanel.add(absTextField = new JTextField());
		absTextField.setBounds(100, 120, 200, 100);
		jpanel.add(commitbtn = new JButton("체크"));
		commitbtn.setBounds(150, 300, 100, 50);

		commitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tdb.NoReason(id, pw, absTextField.getText());
			}
		});
	}

	public void setSysIdPw(String sid, String spw) {
		this.id = sid;
		this.pw = spw;
	}

}
