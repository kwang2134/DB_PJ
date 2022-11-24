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

public class MainUi extends JFrame { // ��� ���� ������
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
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // â �ݱ� ��ư ������ ������ ����
		jframe.setVisible(true);

		jpanel.setLayout(null);
		jframe.add(jpanel);

		Font MainUiFontsz = new Font("����", Font.PLAIN, 30);
		jpanel.add(MainUiLabel = new JLabel("����� �ý���"));
		MainUiLabel.setFont(MainUiFontsz);
		MainUiLabel.setBounds(300, 150, 300, 100);

		EmpData = tdb.EmpLogin(id, pw, MEmpId);

		jpanel.add(EmpNameLabel = new JLabel(EmpData[1] + "�� �ݰ����ϴ�"));
		EmpNameLabel.setBounds(10, 10, 150, 50);
		jpanel.add(attendtimeLabel = new JLabel(attendtime));
		attendtimeLabel.setBounds(400, 350, 100, 50);
		jpanel.add(leavetimeLabel = new JLabel(leavetime));
		leavetimeLabel.setBounds(400, 450, 100, 50);

		jpanel.add(Salarybtn = new JButton("�޿�����"));
		Salarybtn.setBounds(600, 70, 100, 30);
		jpanel.add(EmpInfo = new JButton("��� ����"));
		EmpInfo.setBounds(600, 70, 100, 30);
		jpanel.add(attendb = new JButton("���"));
		attendb.setBounds(250, 350, 100, 50);
		jpanel.add(leaveb = new JButton("���"));
		leaveb.setBounds(250, 450, 100, 50);
		jpanel.add(Logout = new JButton("�α׾ƿ�"));
		Logout.setBounds(350, 650, 100, 50);
		jpanel.add(Absenceb = new JButton("��� ���� ���"));
		Absenceb.setBounds(600, 600, 150, 50);

		if (EmpData[2].equals("�λ�")) {
			EmpInfo.setVisible(true);
			Salarybtn.setVisible(false);
		} else if (EmpData[2].equals("�繫")) {
			Salarybtn.setVisible(true);
			EmpInfo.setVisible(false);
		} else {
			Salarybtn.setVisible(false);
			EmpInfo.setVisible(false);
		}
		attendb.addActionListener(new ActionListener() { // ��� ��ư �̺�Ʈ
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
					JOptionPane.showMessageDialog(null, "����� �Ϸ�Ǿ����ϴ�");
				} else {
					JOptionPane.showMessageDialog(null, "�̹� ����� �Ϸ�Ǿ����ϴ�");
				}

			}
		});

		leaveb.addActionListener(new ActionListener() { // ��� ��ư �̺�Ʈ
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat leavet = new SimpleDateFormat("HH:mm");
				Date time = new Date();
				leavetime = leavet.format(time);
				leavetimeLabel.setText(leavetime);
				// tdb.leave(id, pw, leavetime); DB ���ν��� ȣ�� �Լ�
			}
		});

		Salarybtn.addActionListener(new ActionListener() { // �޿� ���������� �� �޿����� ��ư �̺�Ʈ
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

		Logout.addActionListener(new ActionListener() { // �α׾ƿ� ��� �α��� �������� �̵�
			public void actionPerformed(ActionEvent e) {
				jframe.dispose();
				EmpLogin emplog = new EmpLogin();
				emplog.setSysIdPw(id, pw);
			}
		});

		Absenceb.addActionListener(new ActionListener() { // ��� ���� ������ �̵�
			public void actionPerformed(ActionEvent e) {
				AbsenceR abui = new AbsenceR();
				abui.setSysIdPw(id, pw, EmpData[0]);
				abui.AbsenceR_init();
			}
		});
	}

	public void setData(String sid, String spw, String empid) { // DB id,pw ����
		this.id = sid;
		this.pw = spw;
		this.MEmpId = empid;

	}

}