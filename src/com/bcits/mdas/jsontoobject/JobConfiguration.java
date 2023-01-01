package com.bcits.mdas.jsontoobject;


public class JobConfiguration
{
private Commands[] commands;

private String name;

public Commands[] getCommands ()
{
return commands;
}

public void setCommands (Commands[] commands)
{
this.commands = commands;
}



@Override
public String toString()
{
return "ClassPojo [commands = "+commands+", name = "+name+"]";
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
}


