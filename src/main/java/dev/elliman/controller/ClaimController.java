package dev.elliman.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dev.elliman.beans.Claim;
import dev.elliman.beans.Event;
import dev.elliman.beans.Grading;
import dev.elliman.beans.Person;
import dev.elliman.beans.Stage;
import dev.elliman.services.ClaimService;
import dev.elliman.services.ClaimServiceImpl;
import dev.elliman.services.PersonService;
import dev.elliman.services.PersonServiceImpl;
import io.javalin.http.Context;

public class ClaimController {
	private static ClaimService cs = new ClaimServiceImpl();
	private static PersonService ps = new PersonServiceImpl();
	
	public static void getClaimsByPerson(Context ctx) {
		Person p = ctx.sessionAttribute("user");
		List<Claim> claims = cs.getClaimsByPerson(p);
		setUrgentClaimField(claims);
		
		if(claims != null) {
			ctx.status(200);
			ctx.json(claims);
		} else {
			ctx.status(400);
		}
	}
	
	public static void getEventTypes(Context ctx) {
		List<Event> events = cs.getEventTypes();
		
		if(events != null) {
			ctx.status(200);
			ctx.json(events);
		} else {
			ctx.status(500);
		}
	}
	
	public static void getDSUnapprovedClaims(Context ctx) {
		//Person p = ctx.sessionAttribute("user");
		List<Claim> claims = cs.getDSUnapprovedClaims();
		setUrgentClaimField(claims);
		
		if(claims != null) {
			ctx.status(200);
			ctx.json(claims);
		} else {
			ctx.status(500);
		}
	}
	
	public static void getDHUnapprovedClaims(Context ctx) {
		//Person p = ctx.sessionAttribute("user");
		List<Claim> claims = cs.getDHUnapprovedClaims();
		setUrgentClaimField(claims);
		
		if(claims != null) {
			ctx.status(200);
			ctx.json(claims);
		} else {
			ctx.status(500);
		}
	}
	
	public static void getBCUnapprovedClaims(Context ctx) {
		//Person p = ctx.sessionAttribute("user");
		List<Claim> claims = cs.getBCUnapprovedClaims();
		setUrgentClaimField(claims);
		
		if(claims != null) {
			ctx.status(200);
			ctx.json(claims);
		} else {
			ctx.status(500);
		}
	}
	
	private static void setUrgentClaimField(List<Claim> claims) {
		LocalDateTime ldt = LocalDateTime.now();
		ldt = ldt.plusDays(14);
		for(Claim c : claims) {
			if(c.getEventDate().compareTo(ldt) < 0) {
				c.setIsUrgent(true);
			} else {
				c.setIsUrgent(false);
			}
		}
	}
	
	public static void accept(Context ctx) {
		Claim claim = cs.getClaimByID(Integer.valueOf(ctx.pathParam("id")));
		Person person = ctx.sessionAttribute("user");
		boolean accepted = true;
		
		if(person.getRole().getId() == 3) {
			claim.setDsa(person);
			Stage s = new Stage();
			s.setId(2);
			s.setName("Pending department head review");
			claim.setApprovalStage(s);
			claim.setLastApproved(LocalDateTime.now());
		} else if(person.getRole().getId() == 2) {
			if(claim.getDsa() == null) {
				claim.setDsa(person);
			}
			claim.setDha(person);
			Stage s = new Stage();
			s.setId(3);
			s.setName("Pending benifits coordinator review");
			claim.setApprovalStage(s);
			claim.setLastApproved(LocalDateTime.now());
		} else if(person.getRole().getId() == 1) {
			if(claim.getApprovalStage().getId() == 3) {
				claim.setBca(person);
				Stage s = new Stage();
				s.setId(4);
				s.setName("Accepted");
				claim.setApprovalStage(s);
				claim.setLastApproved(LocalDateTime.now());
			} else if(claim.getApprovalStage().getId() == 6) {
				claim.setPassingApproval(person);
				Stage s = new Stage();
				s.setId(7);
				s.setName("Complete");
				claim.setApprovalStage(s);
				claim.setLastApproved(LocalDateTime.now());
				
				//increase amount claimed
				Person employ = claim.getPerson();
				Double claimed = employ.getAmountClaimed();
				claimed += claim.getEstimatedAmount();
				employ.setAmountClaimed(claimed);
				ps.update(employ);
			}
		} else if(person.getRole().getId() == 4) {
			//submitting for grade review
			Stage s = new Stage();
			s.setId(6);
			s.setName("Pending grade review");
			claim.setApprovalStage(s);
			claim.setLastApproved(LocalDateTime.now());
		}
		
		if(!cs.accept(claim)) {
			accepted = false;
		}
		
		if(accepted) {
			ctx.status(200);
		} else {
			ctx.status(500);
		}
	}
	
	public static void makeClaim(Context ctx) {
		String stringClaim = ctx.body();
		stringClaim = stringClaim.substring(1, stringClaim.length()-1);
		
		//break down the json into the fields and values
		Claim c = new Claim();
		Grading g = new Grading();
		LocalDateTime ldt = null;
		String dateString = null;
		String timeString = null;
		String[] parts = stringClaim.split(",");
		for(String part : parts) {
			String[] sections = part.split(":");
			String field = sections[0].substring(1, sections[0].length()-1);
			String valueSection = sections[1];
			
			if("title".equals(field)) {
				c.setTitle(valueSection.substring(1, valueSection.length()-1));
			} else if("event".equals(field)) {
				Integer eventID = Integer.valueOf(valueSection);
				Event e = cs.getEventByID(eventID);
				c.setEvent(e);
			} else if("hasPresentation".equals(field)) {
				if("true".equals(valueSection)) {
					g.setHasPresentation(true);
				} else {
					g.setHasPresentation(false);
				}
			} else if("passingPercentage".equals(field)) {
				if(!"".equals(valueSection)) {
					if(valueSection.substring(1, valueSection.length()-1).isEmpty()) {
						g.setPassingPercentage(null);
					} else  {
						Double passingPercentage = Double.valueOf(valueSection.substring(1, valueSection.length()-1));
						g.setPassingPercentage(passingPercentage);
					}
				}
			} else if("passingLetter".equals(field)) {
				if(!"\"N/A\"".equals(valueSection)) {
					g.setPassingLetter(valueSection.substring(1, valueSection.length()-1));
				} else {
					g.setPassingLetter(null);
				}
			} else if("eventDate".equals(field)) {
				dateString = valueSection.substring(1, valueSection.length()-1);
			} else if("eventTime".equals(field)) {
				timeString = sections[1].substring(1) + ":" + sections[2].substring(0, sections[2].length()-1);
			} else if("eventLocation".equals(field)) {
				c.setEventLocation(valueSection.substring(1, valueSection.length()-1));
			} else if("description".equals(field)) {
				c.setDescription(valueSection.substring(1, valueSection.length()-1));
			} else if("price".equals(field)) {
				Double price = Double.valueOf(valueSection.substring(1, valueSection.length()-1));
				c.setPrice(price);
			} else if("justification".equals(field)) {
				c.setJustification(valueSection.substring(1, valueSection.length()-1));
			} else if("hoursMissed".equals(field)) {
				Integer hoursMissed = Integer.valueOf(valueSection.substring(1, valueSection.length()-1));
				c.setHoursMissed(hoursMissed);
			} else if("estimatedRembersment".contentEquals(field)) {
				Double estimated = Double.valueOf(valueSection.substring(1, valueSection.length()-1));
				c.setEstimatedAmount(estimated);
			}
		}
		String dateTime = dateString + "T" + timeString + ":00";
		ldt = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		c.setEventDate(ldt);
		c.setApprovalStage(cs.getStageByID(1));
		Person p = ctx.sessionAttribute("user");
		c.setPersonID(p);
		c.setGrading(cs.getGrading(g));
		c.setLastApproved(LocalDateTime.now());
		
		cs.makeClaim(c);
	}
	
	public static void denyClaim(Context ctx) {
		Integer id = Integer.valueOf(ctx.pathParam("id"));
		Claim c = cs.getClaimByID(id);
		Stage s = new Stage();
		s.setId(0);
		s.setName("Denied");
		c.setApprovalStage(s);
		
		String denialReason = ctx.body();
		c.setDenialReason(denialReason);
		cs.deny(c);
	}
	
	public static void autoApproveClaims() {
		List<Claim> dsUnapproved = cs.getDSUnapprovedClaims();
		Person autoDS = ps.getAutoDS();
		for(Claim c : dsUnapproved) {
			if(Math.abs(c.getLastApproved().getDayOfYear() - LocalDateTime.now().getDayOfYear()) > 7) {
				c.setDsa(autoDS);
				cs.accept(c);
			}
		}
		
		List<Claim> dhUnapproved = cs.getDHUnapprovedClaims();
		Person autoDH = ps.getAutoDH();
		for(Claim c : dhUnapproved) {
			if(Math.abs(c.getLastApproved().getDayOfYear() - LocalDateTime.now().getDayOfYear()) > 7) {
				c.setDha(autoDH);
				cs.accept(c);
			}
		}
	}
	
	public static void changeReimbursementAmount(Context ctx) {
		Integer claimID = null;
		Double amount = null;
		String body = ctx.body();
		body = body.substring(1, body.length()-1);
		String[] parts = body.split(",");
		for(String part : parts) {
			String field = part.split(":")[0];
			field = field.substring(1,field.length()-1);
			String value = part.split(":")[1];
			value = value.substring(1, value.length()-1);
			
			if("id".equals(field)) {
				claimID = Integer.valueOf(value);
			} else if("amount".equals(field)) {
				amount = Double.valueOf(value);
			}
		}
		Boolean success = cs.updateRiembersementAmount(claimID, amount);
		
		if(success) {
			ctx.status(200);
		} else {
			ctx.status(500);
		}
	}
}
