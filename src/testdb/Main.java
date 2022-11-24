package testdb;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
	String id;
	String pw;

	public static void main(String[] args) {
		SysLogin ui = new SysLogin();
	}

	public void setSysIdPw(String sid, String spw) {
		this.id = sid;
		this.pw = spw;
	}

	public Main() {
		Testdb tdb = new Testdb();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				tdb.NoReason(id, pw);
			}
		};
		Timer t = new Timer(); // 00:00분 마다 무단결근 프로시저 호출(정상 작동 되는지 체크 x)
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		t.schedule(task, cal.getTime(), 1000 * 60 * 60 * 24);

	}

}
