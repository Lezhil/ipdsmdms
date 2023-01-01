package com.bcits.mdas.jsontoobject;

public class SearchMeterJson {
    

private String createTime;

private String nodeId;

private String meterId;

private String updateTime;

private String vendor;

private String firmwareVersion;

private String hardwareVersion;

public String getCreateTime ()
{
return createTime;
}

public void setCreateTime (String createTime)
{
this.createTime = createTime;
}

public String getNodeId ()
{
return nodeId;
}

public void setNodeId (String nodeId)
{
this.nodeId = nodeId;
}

public String getMeterId ()
{
return meterId;
}

public void setMeterId (String meterId)
{
this.meterId = meterId;
}

public String getUpdateTime ()
{
return updateTime;
}

public void setUpdateTime (String updateTime)
{
this.updateTime = updateTime;
}

public String getVendor ()
{
return vendor;
}

public void setVendor (String vendor)
{
this.vendor = vendor;
}

public String getFirmwareVersion ()
{
return firmwareVersion;
}

public void setFirmwareVersion (String firmwareVersion)
{
this.firmwareVersion = firmwareVersion;
}

public String getHardwareVersion ()
{
return hardwareVersion;
}

public void setHardwareVersion (String hardwareVersion)
{
this.hardwareVersion = hardwareVersion;
}

@Override
public String toString() {
	return "SearchMeterJson [createTime=" + createTime + ", nodeId=" + nodeId
			+ ", meterId=" + meterId + ", updateTime=" + updateTime
			+ ", vendor=" + vendor + ", firmwareVersion=" + firmwareVersion
			+ ", hardwareVersion=" + hardwareVersion + "]";
}





}
