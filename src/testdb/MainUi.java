package testdb;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainUi extends JFrame { // 사원 메인 페이지
	JFrame jframe = new JFrame();
	JPanel jpanel = new JPanel();
	JButton Salarybtn;
	JButton EmpInfo;
	JButton attendb;
	JButton leaveb;
	JButton Logout;
	JButton Absenceb;
	JLabel MainUiLabel;
	JLabel EmpNameLabel;
	JLabel attendtimeLabel;
	JLabel leavetimeLabel;
	String id;
	String pw;
	String MEmpId;
	String EmpData[];
	String attendtime = "";
	String leavetime = "";

	public void MainUi_init() {
		Testdb tdb = new Testdb();
		jframe.setBounds(50, 50, 800, 800);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정
		jframe.setVisible(true);

		jpanel.setLayout(null);
		jframe.add(jpanel);

		Font MainUiFontsz = new Font("돋움", Font.PLAIN, 30);
		jpanel.add(MainUiLabel = new JLabel("출퇴근 시스템"));
		MainUiLabel.setFont(MainUiFontsz);
		MainUiLabel.setBounds(300, 150, 300, 100);

		EmpData = tdb.EmpLogin(id, pw, MEmpId);

		jpanel.add(EmpNameLabel = new JLabel(EmpData[1] + "님 반갑습니다"));
		EmpNameLabel.setBounds(10, 10, 150, 50);
		jpanel.add(attendtimeLabel = new JLabel(attendtime));
		attendtimeLabel.setBounds(400, 350, 100, 50);
		jpanel.add(leavetimeLabel = new JLabel(leavetime));
		leavetimeLabel.setBounds(400, 450, 100, 50);

		jpanel.add(Salarybtn = new JButton("급여정산"));
		Salarybtn.setBounds(600, 70, 100, 30);
		jpanel.add(EmpInfo = new JButton("사원 정보"));
		EmpInfo.setBounds(600, 70, 100, 30);
		jpanel.add(attendb = new JButton("출근"));
		attendb.setBounds(250, 350, 100, 50);
		jpanel.add(leaveb = new JButton("퇴근"));
		leaveb.setBounds(250, 450, 100, 50);
		jpanel.add(Logout = new JButton("로그아웃"));
		Logout.setBounds(350, 650, 100, 50);
		jpanel.add(Absenceb = new JButton("결근 사유 등록"));
		Absenceb.setBounds(600, 600, 150, 50);

		if (EmpData[2].equals("인사")) {
			EmpInfo.setVisible(true);
			Salarybtn.setVisible(false);
		} else if (EmpData[2].equals("재무")) {
			Salarybtn.setVisible(true);
			EmpInfo.setVisible(false);
		} else {
			Salarybtn.setVisible(false);
			EmpInfo.setVisible(false);
		}
		attendb.addActionListener(new ActionListener() { // 출근 버튼 이벤트
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat attendt = new SimpleDateFormat("HH:mm");
				SimpleDateFormat attendDb = new SimpleDateFormat("YYYY/MM/dd");
				String attendDateDb = "";
				Date time = new Date();
				attendDateDb = attendDb.format(time);
				attendtime = attendt.format(time);
				attendtimeLabel.setText(attendtime);
				int flag = tdb.attend(id, pw, EmpData[0], attendDateDb, attendtime);
				if (flag == 1) {
					JOptionPane.showMessageDialog(null, "출근이 완료되었습니다");
				} else {
					JOptionPane.showMessageDialog(null, "이미 출근이 완료되었습니다");
				}

			}
		});

		leaveb.addActionListener(new ActionListener() { // 퇴근 버튼 이벤트
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat leavet = new SimpleDateFormat("HH:mm");
				Date time = new Date();
				leavetime = leavet.format(time);
				leavetimeLabel.setText(leavetime);
				// tdb.leave(id, pw, leavetime); DB 프로시저 호출 함수
			}
		});

		Salarybtn.addActionListener(new ActionListener() { // 급여 페이지에서 쓸 급여정산 버튼 이벤트
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
					JOptionPane.showMessageDialog(null, "급여 정산이 이미 완료되었습니다.");
				} else if (today.equals("22")) {
					int count = tdb.insertSalary(id, pw);
					JOptionPane.showMessageDialog(null, "사원" + count + "명의 급여가 정산되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "급여 정산일이 아닙니다.");
				}

			}

		});

		Logout.addActionListener(new ActionListener() { // 로그아웃 사원 로그인 페이지로 이동
			public void actionPerformed(ActionEvent e) {
				jframe.dispose();
				EmpLogin emplog = new EmpLogin();
				emplog.setSysIdPw(id, pw);
			}
		});

		Absenceb.addActionListener(new ActionListener() { // 결근 사유 페이지 이동
			public void actionPerformed(ActionEvent e) {
				AbsenceR abui = new AbsenceR();
				abui.setSysIdPw(id, pw, EmpData[0]);
				abui.AbsenceR_init();
			}
		});
	}

	public void setData(String sid, String spw, String empid) { // DB id,pw 정보
		this.id = sid;
		this.pw = spw;
		this.MEmpId = empid;

	}

}
