import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalenderAdapter implements CalenderInterface {

	private Calendar calendar = new GregorianCalendar();

	@Override
	public int getHour() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	@Override
	public int getMinute() {
		return calendar.get(Calendar.MINUTE);
	}

	@Override
	public int getSecond() {
		return calendar.get(Calendar.SECOND);
	}

}

