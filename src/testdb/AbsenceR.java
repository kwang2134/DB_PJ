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

public class AbsenceR extends JFrame { // 결근 사유 등록 페이지
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
		jframe.setBounds(50, 50, 400, 400); // 전체 창 크기
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정
		jframe.setVisible(true);

		jpanel.setLayout(null);
		jframe.add(jpanel);

		jpanel.add(reasonLabel = new JLabel("결근 사유"));
		reasonLabel.setBounds(170, 30, 80, 30);
		jpanel.add(reasonTextField = new JTextField());
		reasonTextField.setBounds(60, 80, 280, 180);
		jpanel.add(commitReason = new JButton("등록"));
		commitReason.setBounds(170, 300, 100, 50);

		commitReason.addActionListener(new ActionListener() { // 결근 사유 등록 버튼 이벤트
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat abdate = new SimpleDateFormat("YYYY/MM/dd");
				String date = "";
				Date time = new Date();
				date = abdate.format(time);
				int flag = tdb.absence(id, pw, EmpId, date, reasonTextField.getText());
				if (flag == 1) {
					JOptionPane.showMessageDialog(null, "결근 사유가 등록되었습니다");
				} else {
					JOptionPane.showMessageDialog(null, "이미 사유가 등록되었습니다");
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
