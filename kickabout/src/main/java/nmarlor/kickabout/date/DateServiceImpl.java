package nmarlor.kickabout.date;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class DateServiceImpl implements DateService{

	@Override
	public Date getTodaysDate() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate = calendar.getTime();
		Date date = new Date(currentDate.getTime());
		return date;
	}

	@Override
	public Date stringToDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
		LocalDate dateToFormat = LocalDate.parse(date, formatter);
		Date formattedDate = Date.valueOf(dateToFormat);
		return formattedDate;
	}

	@Override
	public Time stringToTime(String time) {
		DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
		Time formattedTime = null;
		try {
			java.util.Date toFormat = formatter.parse(time);
			formattedTime = new Time(toFormat.getTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return formattedTime;
	}

}
