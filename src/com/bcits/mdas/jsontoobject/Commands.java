package com.bcits.mdas.jsontoobject;


public class Commands
{
private String active;

private String type;

public String getActive ()
{
return active;
}

public void setActive (String active)
{
this.active = active;
}

public String getType ()
{
return type;
}

public void setType (String type)
{
this.type = type;
}

@Override
public String toString()
{
return "ClassPojo [active = "+active+", type = "+type+"]";
}
}

