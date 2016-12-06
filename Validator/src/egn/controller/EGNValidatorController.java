package egn.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egn.personaldata.PersonalData;

@Controller
public class EGNValidatorController {
	
	@RequestMapping(value="/egn", method=RequestMethod.GET)
	public ModelAndView getEGN(){
		return new ModelAndView("EGNform", "command", new PersonalData());
	}
	
	@RequestMapping(value="/EGN_validate", method = RequestMethod.POST)
	public ModelAndView validate(@ModelAttribute("SpringWeb")PersonalData pd, ModelMap model){
		
		String 
			birthDate = getBirthDate(pd.getEgn()),
			desc;
		if((desc = checkDate(birthDate)) == null){
			String sex = getSex(pd.getEgn());
			pd.setBirthDate(birthDate);
			pd.setSex(sex);
			model.addAttribute("birthDate", birthDate);
			model.addAttribute("sex", sex);
		}else{
			model.addAttribute("warning", "Ivalid EGN!");
			model.addAttribute("desc", desc);
		}
		
		return new ModelAndView("EGNform", "command", pd);
	}
	
	private String getBirthDate(String egn){
		
		/* *********************
		 * forming date from EGN
		 ***********************/
		
		StringBuilder message = new StringBuilder("");
		int month = Integer.parseInt(egn.substring(2, 4));
		String year = egn.substring(0, 2);
		
		if(month >= 21 && month <= 32){
			
			/* *********************************************
			 * check if the person was born before year 1900
			 ***********************************************/
			month = month-20;
			year = 18+year;						
		}else if(month >= 41 && month <= 52){
			
			/* ********************************************
			 * check if the person was born after year 1999
			 **********************************************/
			month = month-40;
			year = 20+year;
		}else if(month >= 1 && month <= 12){
			
			/* ******************************************************
			 * check if the person was born at year between 1900-1999
			 ********************************************************/
			year = 19+year;
		}
		
		message.append(year).append("-");
		
		if(month < 10)
			//append 0 to get proper date format
			message.append(0);
		
		message.append(month).append("-").append(egn.substring(4,6));
		
		return message.toString();
	}
	
	private String getSex(String egn){
		
		/* *****************************************
		 * the ninth digit, referring to person sex
		 * (even number - male, odd number - female) 
		 *******************************************/
		String sex;
		int nine = Integer.parseInt(egn.substring(8,9));
		
		if(nine%2 == 0){
			sex = "male";
		}else{
			sex = "female";
		}
		return sex;
	}
	
	private String checkDate(String date){
		String desc;
		int[] daysPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		if(date.length() == 10){
			
			/* ***********************************************************
			 * if month is incorrect, the date length will be less than 10
			 *************************************************************/
			
			int 
				presentYear = LocalDate.now().getYear(),
				year = Integer.parseInt(date.substring(0, 4)),
				presentMonth = LocalDate.now().getMonth().getValue(),
				month = Integer.parseInt(date.substring(5, 7)),
				day = Integer.parseInt(date.substring(8, date.length())),
				presentDay = LocalDate.now().getDayOfMonth();						
			if(presentYear > year || 
					(presentYear == year && 
						(presentMonth > month || 
								(presentMonth == month && presentDay >= day)
						)
					)
				){
				
				/* ************************************************
				 * check if the person isn't born after present day
				 **************************************************/
				
				if(year%4 == 0)
					daysPerMonth[1] = 29;
				
				if(daysPerMonth[month-1] >= day){
					desc = null;
				}else{
					desc = "Incorrect birth day identified!";
				}
				
			}else if(presentYear < year){
				desc = "Incorrect birth year identified!";
			}else if(presentMonth < month){
				desc = "Incorrect birth month identified!";
			}else{
				desc = "Incorrect birth day identified!";
			}
		}else{
			desc = "Incorrect birth month identified!";
		}
		return desc;
	}
}
