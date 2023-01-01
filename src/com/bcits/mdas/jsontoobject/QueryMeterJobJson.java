package com.bcits.mdas.jsontoobject;


public class QueryMeterJobJson
{
private String totalSuccessfulDevices;

private String jobType;

private String createTime;

private String meterGroup;

private String status;

private String updateTime;

private String timeoutTime;

private String totalProcessedDevices;

private String totalFailedDevices;

private JobConfiguration jobConfiguration;

private String scheduledTime;

private String jobName;

public String getTotalSuccessfulDevices ()
{
return totalSuccessfulDevices;
}

public void setTotalSuccessfulDevices (String totalSuccessfulDevices)
{
this.totalSuccessfulDevices = totalSuccessfulDevices;
}

public String getJobType ()
{
return jobType;
}

public void setJobType (String jobType)
{
this.jobType = jobType;
}

public String getCreateTime ()
{
return createTime;
}

public void setCreateTime (String createTime)
{
this.createTime = createTime;
}

public String getMeterGroup ()
{
return meterGroup;
}

public void setMeterGroup (String meterGroup)
{
this.meterGroup = meterGroup;
}

public String getStatus ()
{
return status;
}

public void setStatus (String status)
{
this.status = status;
}

public String getUpdateTime ()
{
return updateTime;
}

public void setUpdateTime (String updateTime)
{
this.updateTime = updateTime;
}



public String getTotalProcessedDevices ()
{
return totalProcessedDevices;
}

public void setTotalProcessedDevices (String totalProcessedDevices)
{
this.totalProcessedDevices = totalProcessedDevices;
}

public String getTotalFailedDevices ()
{
return totalFailedDevices;
}

public void setTotalFailedDevices (String totalFailedDevices)
{
this.totalFailedDevices = totalFailedDevices;
}

public JobConfiguration getJobConfiguration ()
{
return jobConfiguration;
}

public void setJobConfiguration (JobConfiguration jobConfiguration)
{
this.jobConfiguration = jobConfiguration;
}



public String getJobName ()
{
return jobName;
}

public void setJobName (String jobName)
{
this.jobName = jobName;
}

public String getTimeoutTime() {
	return timeoutTime;
}

public void setTimeoutTime(String timeoutTime) {
	this.timeoutTime = timeoutTime;
}

public String getScheduledTime() {
	return scheduledTime;
}

public void setScheduledTime(String scheduledTime) {
	this.scheduledTime = scheduledTime;
}

@Override
public String toString()
{
return "ClassPojo [totalSuccessfulDevices = "+totalSuccessfulDevices+", jobType = "+jobType+", createTime = "+createTime+", meterGroup = "+meterGroup+", status = "+status+", updateTime = "+updateTime+", timeoutTime = "+timeoutTime+", totalProcessedDevices = "+totalProcessedDevices+", totalFailedDevices = "+totalFailedDevices+", jobConfiguration = "+jobConfiguration+", scheduledTime = "+scheduledTime+", jobName = "+jobName+"]";
}
}
