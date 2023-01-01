package com.bcits.controller;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bcits.service.InventoryService;

@Controller
public class InventoryController {
	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager entityManager;
	@Autowired
	private InventoryService Inventoryservice;
	
	@RequestMapping(value="/inventoryMDAS", method = { RequestMethod.GET })
    public String getmodeminventory(Model model){
        Object[] modem = Inventoryservice.getAllModemDetails();
        model.addAttribute("total",modem[0]);
        model.addAttribute("installed",modem[1]); 
        model.addAttribute("instock",modem[2]); 
        model.addAttribute("notWorking",modem[3]); 
        return "Modeminventory";
    }
	
	@RequestMapping(value = "/getModeminventoryDataMDAS/{val}", method = { RequestMethod.GET })
	public @ResponseBody Object getModemMasterData(@PathVariable("val") int val,HttpServletRequest request) 
	{
		List<?> inv=null;
		String qry="SELECT * FROM meter_data.modem_master ";
		
		if(val==0) {
			qry+="";
		} else if(val==1) {
			qry+="WHERE installed = '1' ";
		} else if(val==2) {
			qry+="WHERE installed = '0' ";
		} else if(val==3) {
			qry+="WHERE working_status = '0' ";
		}
		qry+=" ORDER BY modem_serial_no";
		System.out.println(qry);
		try {
			inv=entityManager.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inv;
	}

}
