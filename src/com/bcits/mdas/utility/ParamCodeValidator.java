package com.bcits.mdas.utility;

public class ParamCodeValidator {

	String mtrMake;
	String manufacturerCode;
	
	String kwh="P7-1-5-2-0";
	String kvarh_lag="P7-2-4-2-0";
	String kvarh_lead="P7-2-1-2-0";
	String kvah="P7-3-5-2-0";
	String kw="P7-4-5-2-0";
	String kva="P7-6-5-2-0";
	String sys_pf_billing="P4-4-4-1-0";

	public ParamCodeValidator(String mtrMake, String manufacturerCode) {
		super();
		this.mtrMake = mtrMake;
		this.manufacturerCode = manufacturerCode;
	}
	public ParamCodeValidator() {
		super();
	}

	public String getD3RecCode(String code) {
		String param="";
		
		if(code.startsWith("P7-1-")) {
			
			if("SECURE".equalsIgnoreCase(mtrMake)) {
				if(code.equals("P7-1-18-1-0")|| code.equals("P7-1-5-1-0")) {
					param=kwh; //ACTIVE ENERGY
				}
			}
			else if("L&T".equalsIgnoreCase(mtrMake)) {
				if(code.equals("P7-1-5-1-0") || code.equals("P7-1-5-1-0")|| code.equals("P7-1-5-2-0")) {
					param=kwh; //ACTIVE ENERGY
				}
			}
			else if("Genus".equalsIgnoreCase(mtrMake)) {
				if(code.equals("P7-1-5-1-0") || code.equals("P7-1-18-1-0")|| code.equals("P7-1-5-2-0" ) || code.equals("P7-1-13-2-0" )) {
					param=kwh; //ACTIVE ENERGY
				}
			}
			else if("HPL".equalsIgnoreCase(mtrMake)) {
				if(code.equals("P7-1-13-2-0") || code.equals("P7-1-13-1-0")) {
					param=kwh; //ACTIVE ENERGY
				}
			} else {

				if(code.equalsIgnoreCase(kwh)) {
					param=kwh;
				}
				if(code.equalsIgnoreCase(kvah)) {
					param=kvah;
				}
				if(code.equalsIgnoreCase(kvarh_lag)) {
					param=kvarh_lag;
				}
				if(code.equalsIgnoreCase(kvarh_lead)) {
					param=kvarh_lead;
				}
				if(code.equalsIgnoreCase(kva)) {
					param=kva;
				}
				if(code.equalsIgnoreCase(kw)) {
					param=kw;
				}
				
			}
			
			
		} else if(code.startsWith("P7-2-")) {
			
			String[] t=code.split("-");
			String l=t[2];
			String laglead=getLagLead(l);
			
			if("lag".equalsIgnoreCase(laglead)) {
				param=kvarh_lag;
			} else if("lead".equalsIgnoreCase(laglead)) {
				param=kvarh_lead;
			} 
			
		} else if(code.startsWith("P7-3-")) {
			
			param=kvah; //APARENT ENERGY
			
		} else if(code.startsWith("P7-4-")) {
			
			param=kw; //ACTIVE DEMAND	
			
		} else if(code.startsWith("P7-6-")) {
			
			param=kva; //APARENT DEMAND
			
		}   else if(code.startsWith("P4-4-")) {
			
			param=sys_pf_billing; //POWER FACTOR
			
		} 
		return param;
	}

	private String getLagLead(String l) {
		String res="";
		switch (l) {
		case "1":
			res="lag";
			break;
		case "2":
			res="lead";
			break;
		case "3":
			res="lead";
			break;
		case "4":
			res="lag";
			break;
		case "5":
			res="lag";
			break;
		case "6":
			res="lead";
			break;
		case "7":
			res="lag";
			break;
		case "8":
			res="lead";
			break;
		case "9":
			res="lag";
			break;
		case "10":
			res="lag";
			break;
		case "11":
			res="lead";
			break;
		case "12":
			res="lead";
			break;
		case "19":
			res="lead";
			break;
		case "20":
			res="lag";
			break;

		default:
			break;
		}
		
		return res;
	}
	
	public String readD4tags(String code) {
		String[] t=code.split("-");
		String res="";
		
		if("P1".equalsIgnoreCase(t[0])) {
			if("2".equals(t[1])) {
				if("1".equals(t[2])) {
					res="v_r";
				}
				else if("2".equals(t[2])) {
					res="v_y";
				}
				else if("3".equals(t[2])) {
					res="v_b";
				}
			}
			
		} 
		else if("P2".equalsIgnoreCase(t[0])) {
			if("1".equals(t[1])) {
				if("1".equals(t[2])) {
					res="i_r";
				}
				else if("2".equals(t[2])) {
					res="i_y";
				}
				else if("3".equals(t[2])) {
					res="i_b";
				}
			}
		}
		
		else if("P7".equalsIgnoreCase(t[0])) {
			if("2".equals(t[1])) {
				String l=t[2];
				String laglead=getLagLead(l);
				if("lag".equalsIgnoreCase(laglead)) {
					res="kvarh_lag";
				} else if("lead".equalsIgnoreCase(laglead)) {
					res="kvarh_lead";
				} 
				
			}
			else if("1".equals(t[1])){
				res="kwh";
			}
			else if("3".equals(t[1])){
				res="kvah";
			}
		}
		else if("P4".equals(t[0])){
			if("1".equals(t[1])){
				res="r_pf";
			}
			else if("2".equals(t[1])){
				res="y_pf";
			}
			else if("3".equals(t[1])){
				res="b_pf";
			}
		}
		else if("P9".equalsIgnoreCase(t[0])) {
			res="frequency";
		}
		return res;
		
	}
	
	//Instataneous data(D2_data)
	public String readD2tags(String code) {
		String[] t=code.split("-");
		String res="";
		
		if("P1".equalsIgnoreCase(t[0])) {
			if("2".equals(t[1])) {
				if("1".equals(t[2])) {
					res="v_r";
				}
				else if("2".equals(t[2])) {
					res="v_y";
				}
				else if("3".equals(t[2])) {
					res="v_b";
				}
			}
			
		} 
		else if("P2".equalsIgnoreCase(t[0])) {
			if("1".equals(t[1])) {
				if("1".equals(t[2])) {
					res="i_r";
				}
				else if("2".equals(t[2])) {
					res="i_y";
				}
				else if("3".equals(t[2])) {
					res="i_b";
				}
			}
		}
		
		else if("P7".equalsIgnoreCase(t[0])) {
			if("2".equals(t[1])) {
				String l=t[2];
				String laglead=getLagLead(l);
				if("lag".equalsIgnoreCase(laglead)) {
					res="kvarh_lag";
				} else if("lead".equalsIgnoreCase(laglead)) {
					res="kvarh_lead";
				} 
			}
			else if("1".equals(t[1])){
				res="kwh";
			}
			else if("3".equals(t[1])){
				res="kvah";
			}
		}
		else if("P4".equals(t[0])){
			if("1".equals(t[1])){
				res="r_pf";
			}
			else if("2".equals(t[1])){
				res="y_pf";
			}
			else if("3".equals(t[1])){
				res="b_pf";
			}
		}
		else if("P9".equalsIgnoreCase(t[0])) {
			res="frequency";
		}
		else if("P8".equals(t[0]))
		{
			res="phase_sequence";
		}
		else if("P3".equals(t[0])){ //P3-2-4-1-0
			if("2".equals(t[1])){
				res="signed_active_power";
			}
			else if("3".equals(t[1])){
				res="signed_reactive_power";
			}
			else if("4".equals(t[1])){
				res="kVA";
			}
			
		}
		
		return res;
		
	}
	
	
	private String readD5tag(String code) {
		String[] t=code.split("-");
		String res="";
		
		if("P1".equalsIgnoreCase(t[0])) {
			if("2".equals(t[1])) {
				if("1".equals(t[2])) {
					res="v_r";
				}
				else if("2".equals(t[2])) {
					res="v_y";
				}
				else if("3".equals(t[2])) {
					res="v_b";
				}
			}
			
		} 
		else if("P2".equalsIgnoreCase(t[0])) {
			if("1".equals(t[1])) {
				if("1".equals(t[2])) {
					res="i_r";
				}
				else if("2".equals(t[2])) {
					res="i_y";
				}
				else if("3".equals(t[2])) {
					res="i_b";
				}
			}
		}
		
		else if("P4".equalsIgnoreCase(t[0])) {
			if("1".equals(t[1])) {
				res="pf_r";
			}
			else if("2".equals(t[1])) {
				res="pf_y";
			}
			else if("3".equals(t[1])) {
				res="pf_b";
			}
		}
		else if("P7".equalsIgnoreCase(t[0])) {
			res="kwh";
		}
		return res;
	}
	
	

}
