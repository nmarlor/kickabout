package nmarlor.kickabout.date;

import java.sql.Date;

public interface DateService 
{
	/**
	 * Service for getting todays date and converting it to SQL format
	 * @return Date
	 */
	public Date getTodaysDate();
	
	/**
	 * Service for converting a string format to SQL Date format
	 * @param String date
	 * @return Date
	 */
	public Date stringToDate(String date);
}
