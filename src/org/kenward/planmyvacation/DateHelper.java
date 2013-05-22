package org.kenward.planmyvacation;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class DateHelper {

	int dhYear;
	int dhMonth;
	int dhDay;
	Calendar cal;
	
	public DateHelper(int dhYear, int dhMonth, int dhDay) {
		this.dhYear = dhYear;
		this.dhMonth = dhMonth;
		this.dhDay = dhDay;
		this.cal = new GregorianCalendar(dhYear, dhMonth, dhDay);
	}
	public DateHelper(String inDate) {
		String[] stDate = inDate.split("/");
		this.dhYear = Integer.parseInt(stDate[2]);
		this.dhMonth = Integer.parseInt(stDate[0]) - 1;
		this.dhDay = Integer.parseInt(stDate[1]);
		this.cal = new GregorianCalendar(this.dhYear, this.dhMonth, this.dhDay);	
	}
	public int getDhYear() {
		return dhYear;
	}
	public void setDhYear(int dhYear) {
		this.dhYear = dhYear;
	}
	public int getDhMonth() {
		return dhMonth;
	}
	public void setDhMonth(int dhMonth) {
		this.dhMonth = dhMonth;
	}
	public int getDhDay() {
		return dhDay;
	}
	public void setDhDay(int dhDay) {
		this.dhDay = dhDay;
	}
	public Calendar getCal() {
		return cal;
	}
	public void setCal(Calendar cal) {
		this.cal = cal;
	}
	@Override
	public String toString() {
		int m = dhMonth + 1;
		return  m + "/" + dhDay + "/" + dhYear;
	}
	
	public int howManyDays(){
		int days = 0;
		Calendar gc = this.getCal();
		DateTime dt = new DateTime(gc);
		Days d = Days.daysBetween(DateTime.now(), dt);
		days = d.getDays();

		return days;
	}
	
	
	
}
