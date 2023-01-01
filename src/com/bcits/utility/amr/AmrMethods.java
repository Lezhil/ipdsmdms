package com.bcits.utility.amr;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;

public class AmrMethods {
  /** author A M REMITH / AUTOMATIC METER READER*/
 

	public static boolean folderExists(String rtfolder) {
		File rootFolder= new File(rtfolder);
		if(!rootFolder.exists())
		{
			try {
				rootFolder.mkdir();
				System.out.println("Created new folder");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Folder creation failed");
				return false;
			}
		}else
		{
			return true;
		}
	}

	
	public static final String UNWANTED_SPACE = "(\\r| |\\n|\\r\\n)+";

	public static String getOctetStringDates(String hexString) {
		try {
			GXByteBuffer buff = new GXByteBuffer();
			buff.set(fromHexString(hexString.replaceAll(UNWANTED_SPACE, "")));
			// Get year.
			int year = buff.getUInt16();
			// Get month
			int month = buff.getUInt8();
			// Get day
			int day = buff.getUInt8();
			// Skip week day
			buff.getUInt8();
			// Get time.
			int hour = buff.getUInt8();
			int minute = buff.getUInt8();
			int second = buff.getUInt8();
			int ms = buff.getUInt8() & 0xFF;
			if (ms != 0xFF) {
				ms *= 10;
			} else {
				ms = 0;
			}
			int deviation = buff.getInt16();
			int status = buff.getUInt8();
			GXDateTime dt = new GXDateTime();
			dt.setStatus(ClockStatus.forValue(status));
			java.util.Set<DateTimeSkips> skip = new HashSet<DateTimeSkips>();
			if (year < 1 || year == 0xFFFF) {
				skip.add(DateTimeSkips.YEAR);
				java.util.Calendar tm = java.util.Calendar.getInstance();
				year = tm.get(Calendar.YEAR);
			}
			dt.setDaylightSavingsBegin(month == 0xFE);
			dt.setDaylightSavingsEnd(month == 0xFD);
			if (month < 1 || month > 12) {
				skip.add(DateTimeSkips.MONTH);
				month = 0;
			} else {
				month -= 1;
			}
			if (day == -1 || day == 0 || day > 31) {
				skip.add(DateTimeSkips.DAY);
				day = 1;
			} else if (day < 0) {
				Calendar cal = Calendar.getInstance();
				day = cal.getActualMaximum(Calendar.DATE) + day + 3;
			}
			if (hour < 0 || hour > 24) {
				skip.add(DateTimeSkips.HOUR);
				hour = 0;
			}
			if (minute < 0 || minute > 60) {
				skip.add(DateTimeSkips.MINUTE);
				minute = 0;
			}
			if (second < 0 || second > 60) {
				skip.add(DateTimeSkips.SECOND);
				second = 0;
			}
			// If ms is Zero it's skipped.
			if (ms < 1 || ms > 1000) {
				skip.add(DateTimeSkips.MILLISECOND);
				ms = 0;
			}
			java.util.Calendar tm = java.util.Calendar.getInstance();
			tm.clear();
			tm.set(year, month, day, hour, minute, second);
			if (ms != 0) {
				tm.set(Calendar.MILLISECOND, ms);
			}
			// If summer time.
			if ((status & ClockStatus.DAYLIGHT_SAVE_ACTIVE.getValue()) != 0) {
				tm.add(Calendar.HOUR, 1);
			}
			dt.setValue(tm.getTime(), deviation);
			dt.setSkip(skip);

			return dt.toString(); // RETURNING THE CONVERTED DATE IN STRING
									// FORMAT
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return hexString; // RETURNING THE SAME STRING
		}

	}
    
    public static byte[] fromHexString(final String encoded) {
        if ((encoded.length() % 2) != 0) {
            throw new IllegalArgumentException("Input string must contain an even number of characters");
        }

        final byte result[] = new byte[encoded.length() / 2];
        final char enc[] = encoded.toCharArray();
        for (int i = 0; i < enc.length; i += 2) {
            StringBuilder curr = new StringBuilder(2);
            curr.append(enc[i]).append(enc[i + 1]);
            result[i / 2] = (byte) Integer.parseInt(curr.toString(), 16);
        }
        return result;
    }

	public static boolean isMonthFirst(String dateTime) {
		try {
			if (dateTime.contains("/")) {
				if (Integer.parseInt(dateTime.split("/")[0]) > 12) {
					return false;
				}
			}
			if (dateTime.contains("-")) {
				if (Integer.parseInt(dateTime.split("-")[0]) > 12) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
}