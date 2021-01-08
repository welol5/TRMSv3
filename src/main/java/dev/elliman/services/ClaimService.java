package dev.elliman.services;

import java.util.List;
import java.util.Set;

import dev.elliman.beans.Claim;
import dev.elliman.beans.Event;
import dev.elliman.beans.Grading;
import dev.elliman.beans.Person;
import dev.elliman.beans.Stage;

public interface ClaimService {
	
	//read
	public List<Claim> getClaimsByPerson(Person person);
	public List<Claim> getDSUnapprovedClaims();
	public List<Claim> getDHUnapprovedClaims();
	public List<Claim> getBCUnapprovedClaims();
	public Claim getClaimByID(Integer id);
	
	//related read
	public List<Event> getEventTypes();
	public Event getEventByID(Integer id);
	public Stage getStageByID(Integer id);
	public Grading getGrading(Grading grading);
	
	//write
	public Claim makeClaim(Claim claim);
	public Boolean accept(Claim claim);
	public Boolean deny(Claim claim);
	public Boolean updateRiembersementAmount(Integer claimID, Double amount);
}
