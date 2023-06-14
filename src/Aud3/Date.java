package Aud3;

import java.util.Objects;

public class Date implements  Comparable<Date>{

    private static final int FIRST_YEAR = 1800;
    private static final int LAST_YEAR = 2500;
    private static final int DAYS_IN_YEAR = 365;
    private static final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] daysTillFIrstOfMonth;
    private static final int[] daysTIllFirstOfYear;

    private int days;
    static {
        //iinicijaliza na static promenlivi
        daysTillFIrstOfMonth = new int[12];
        daysTillFIrstOfMonth[0] = 0;
        for (int i = 1; i <= 12; i++) {
            daysTillFIrstOfMonth[i] = daysTillFIrstOfMonth[i - 1] + daysOfMonth[i];
        }
        daysTIllFirstOfYear = new int[LAST_YEAR - FIRST_YEAR + 1];
        int currentYear = FIRST_YEAR;
        for (int i = 1; i < currentYear; i++) {
            daysTIllFirstOfYear[i] = daysTIllFirstOfYear[i - 1] + DAYS_IN_YEAR;
            if (isLeapYear(currentYear))
                daysTIllFirstOfYear[i]++;
            currentYear++;
        }

    }

    public Date(int day, int month, int year) {
    if (!isDateInvalid(year))
        throw new RuntimeException("Invalid year");
    int days=0;
    days+=daysTIllFirstOfYear[year-FIRST_YEAR];
    days+= daysTillFIrstOfMonth[month-1];
    if(isLeapYear(year) && month>=2)
        days++;
    days+=day;
    this.days=days;

    }
    private boolean  isDateInvalid(int year){
        return (year< FIRST_YEAR || year>LAST_YEAR);
    }
    public int substract(Date date) {
    return Math.abs(this.days-date.days);
    }

    public int add(Date date) {
    return  this.days+date.days;
    }



    private static boolean isLeapYear(int year) {
        return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));
    }


    public static void main(String[] args) {

    }

    @Override
    public int compareTo(Date o) {
        if (this.days < o.days)
            return -1;
        else if (this.days > o.days)
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        int allDays=days;
        int i;
        for (i=0;i<daysTIllFirstOfYear.length;i++)
        {
            if(daysTIllFirstOfYear[i]>= allDays)
                break;
        }
        allDays-=daysTIllFirstOfYear[i-1];
        int year=i-1+FIRST_YEAR;
        if (isLeapYear(year))
            allDays--;
        for(i=0;i<daysTillFIrstOfMonth.length;i++)
        {
            if(daysTillFIrstOfMonth[i]>=allDays)
                break;
        }
        allDays-=daysTillFIrstOfMonth[i-1];
        int month=i;
        return String.format("%02d.%02.%04d",allDays,month,year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return days == date.days;
    }

    @Override
    public int hashCode() {
        return Objects.hash(days);
    }
}
