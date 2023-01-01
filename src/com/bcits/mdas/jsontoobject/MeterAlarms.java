package com.bcits.mdas.jsontoobject;

public class MeterAlarms {
	private String meterId;

	private String[] alarmActive;

	private String meterAlarmId;

	private String alarmTime;

	private String sequenceNumber;

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String[] getAlarmActive() {
		return alarmActive;
	}

	public void setAlarmActive(String[] alarmActive) {
		this.alarmActive = alarmActive;
	}

	public String getMeterAlarmId() {
		return meterAlarmId;
	}

	public void setMeterAlarmId(String meterAlarmId) {
		this.meterAlarmId = meterAlarmId;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Override
	public String toString() {
		return "ClassPojo [meterId = " + meterId + ", alarmActive = "
				+ alarmActive + ", meterAlarmId = " + meterAlarmId
				+ ", alarmTime = " + alarmTime + ", sequenceNumber = "
				+ sequenceNumber + "]";
	}
}
