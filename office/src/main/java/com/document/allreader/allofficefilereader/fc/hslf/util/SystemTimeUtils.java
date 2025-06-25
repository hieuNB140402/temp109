

package com.document.allreader.allofficefilereader.fc.hslf.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public final class SystemTimeUtils
{

    public static Date getDate(byte[] data)
    {
        return getDate(data, 0);
    }


    public static Date getDate(byte[] data, int offset)
    {
        Calendar cal = new GregorianCalendar();

        cal.set(Calendar.YEAR, LittleEndian.getShort(data, offset));
        cal.set(Calendar.MONTH, LittleEndian.getShort(data, offset + 2) - 1);
        cal.set(Calendar.DAY_OF_MONTH, LittleEndian.getShort(data, offset + 6));
        cal.set(Calendar.HOUR_OF_DAY, LittleEndian.getShort(data, offset + 8));
        cal.set(Calendar.MINUTE, LittleEndian.getShort(data, offset + 10));
        cal.set(Calendar.SECOND, LittleEndian.getShort(data, offset + 12));
        cal.set(Calendar.MILLISECOND, LittleEndian.getShort(data, offset + 14));

        return cal.getTime();
    }


    public static void storeDate(Date date, byte[] dest)
    {
        storeDate(date, dest, 0);
    }


    public static void storeDate(Date date, byte[] dest, int offset)
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        LittleEndian.putShort(dest, offset + 0, (short)cal.get(Calendar.YEAR));
        LittleEndian.putShort(dest, offset + 2, (short)(cal.get(Calendar.MONTH) + 1));
        LittleEndian.putShort(dest, offset + 4, (short)(cal.get(Calendar.DAY_OF_WEEK) - 1));
        LittleEndian.putShort(dest, offset + 6, (short)cal.get(Calendar.DAY_OF_MONTH));
        LittleEndian.putShort(dest, offset + 8, (short)cal.get(Calendar.HOUR_OF_DAY));
        LittleEndian.putShort(dest, offset + 10, (short)cal.get(Calendar.MINUTE));
        LittleEndian.putShort(dest, offset + 12, (short)cal.get(Calendar.SECOND));
        LittleEndian.putShort(dest, offset + 14, (short)cal.get(Calendar.MILLISECOND));
    }
}
