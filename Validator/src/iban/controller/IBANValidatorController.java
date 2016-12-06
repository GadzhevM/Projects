package iban.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import iban.accountbean.AccountProps;
import iban.jdbc.IBANJDBCTemplate;

@Controller
public class IBANValidatorController {
	
	private Map<String, String> swiftCode;
	private Map<String, Integer> ibanLength;
	private Timestamp timestamp;
	
	@Autowired
	IBANJDBCTemplate ibanTemplate;
	
	@RequestMapping(value="/iban", method=RequestMethod.GET)
	public ModelAndView getIBAN(){
		return new ModelAndView("IBANform", "command", new AccountProps());
	}
	
	@RequestMapping(value="/IBAN_validate", method=RequestMethod.POST)
	public ModelAndView validate(@ModelAttribute("SpringWeb") AccountProps acc, ModelMap model){
		
		if(swiftCode == null && ibanLength == null){
			swiftCode = new TreeMap<String, String>();
			ibanLength = new TreeMap<String, Integer>();
			ibanTemplate.loadFromDB(swiftCode, ibanLength);
			timestamp = new Timestamp (new Date().getTime());			
		}
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run(){
				if(ibanTemplate.updateFromDB(swiftCode,
											timestamp)){
					timestamp = new Timestamp(new Date().getTime());
				}	
			}
		}, 10000, 10000);
		
		String iban = acc.getIban().trim();
		
		if(iban.length() > 0){	
			iban = iban.replace(" ", "");
			
			if(checkLength(iban)){	
				if(isValid(iban)){
					if(getBankName(iban) != null){
						acc.setBank(getBankName(iban));
						model.addAttribute("bank", acc.getBank());
						acc.setSwift(getSwift(iban));
						model.addAttribute("swift", acc.getSwift());
						model.addAttribute("iban", iban);
					}else{
						model.addAttribute("msg", "Unexist bank for SWIFT code:"
								+getSwift(iban)+"!");
					}
				}else{
					model.addAttribute("msg", "Invalid IBAN!");
				}
			}else{
				model.addAttribute("msg", "Invalid IBAN lenght!");
			}						
		}
		return new ModelAndView("IBANform", "command", acc);
	}
	
	private boolean checkLength(String iban){
		String iso = iban.substring(0, 2);
		boolean validLeangth = false;
		
		if(ibanLength.containsKey(iso)){
			int length = ibanLength.get(iso);
			if(length == iban.length())
				validLeangth = true;
		}
		return validLeangth;
	}
	
	private boolean isValid(String iban){	
		String digitForm = new StringBuilder(iban.substring(4, iban.length()))
					.append(iban.substring(0, 4)).toString();
		
		char[] array = digitForm.toCharArray();
		StringBuilder sb = new StringBuilder();
		
		for(char c : array){
			int i = Character.getNumericValue(c);
			sb.append(i);
		}
		
		digitForm = sb.toString();	
		int 
			remainder = 0, 
			offset = 9;
		boolean hasMoreDigits = true;
		
		while(hasMoreDigits){	
			if(digitForm.length() <= offset){
				offset = digitForm.length();
				hasMoreDigits = false;
			}
			
			remainder = Integer.parseInt(digitForm.substring(0, offset)) % 97;	
			digitForm = new String(remainder + digitForm.substring(offset, digitForm.length()));
		}		
		
		if(remainder == 1) return true;
		else return false;		
	}

	
	private String getSwift(String iban){ 
		return new StringBuilder(iban.substring(4, 8)).append(iban.substring(0, 2)).toString();
	}
	
	private String getBankName(String iban){
		String 
			swift = getSwift(iban).substring(0, 4),
			bank = null;
		
		if(swiftCode.containsKey(swift)){	
			bank = swiftCode.get(swift);
		}
		return bank;
	}
}
