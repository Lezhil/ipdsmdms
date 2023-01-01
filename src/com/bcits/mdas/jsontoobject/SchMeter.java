package com.bcits.mdas.jsontoobject;

import java.util.Arrays;

public class SchMeter {

    private SchMetMain[] schMetMain;

   

    public SchMetMain[] getSchMetMain() {
		return schMetMain;
	}



	public void setSchMetMain(SchMetMain[] schMetMain) {
		this.schMetMain = schMetMain;
	}



	@Override
	public String toString() {
		return "SchMeter [schMetMain=" + Arrays.toString(schMetMain) + "]";
	}



	
}