package dev.elliman.services;

import java.util.List;
import java.util.Set;

import dev.elliman.beans.Claim;
import dev.elliman.beans.Event;
import dev.elliman.beans.Grading;
import dev.elliman.beans.Person;
import dev.elliman.beans.Stage;
import dev.elliman.data.ClaimDAO;
import dev.elliman.data.ClaimDAOFactory;

public class ClaimServiceImpl implements ClaimService {
	private ClaimDAO claimDAO;
	
	public ClaimServiceImpl() {
		claimDAO = ClaimDAOFactory.getClaimDAO();
	}

	@Override
	public List<Claim> getClaimsByPerson(Person person) {
		return claimDAO.getClaimsByPerson(person);
	}

	@Override
	public Claim makeClaim(Claim claim) {
		return claimDAO.makeClaim(claim);
	}

	@Override
	public List<Claim> getDSUnapprovedClaims() {
		return claimDAO.getDSUnapprovedClaims();
	}

	@Override
	public Boolean accept(Claim claim) {
		return claimDAO.update(claim);
	}

	@Override
	public Claim getClaimByID(Integer id) {
		return claimDAO.getClaimByID(id);
	}

	@Override
	public List<Claim> getDHUnapprovedClaims() {
		return claimDAO.getDHUnapprovedClaims();
	}

	@Override
	public List<Claim> getBCUnapprovedClaims() {
		return claimDAO.getBCUnapprovedClaims();
	}

	@Override
	public List<Event> getEventTypes() {
		return claimDAO.getEventTypes();
	}

	@Override
	public Event getEventByID(Integer id) {
		return claimDAO.getEventByID(id);
	}

	@Override
	public Stage getStageByID(Integer id) {
		return claimDAO.getStageByID(id);
	}

	@Override
	public Grading getGrading(Grading grading) {
		return claimDAO.getGrading(grading);
	}

	@Override
	public Boolean deny(Claim claim) {
		return claimDAO.update(claim);
	}

	@Override
	public Boolean updateRiembersementAmount(Integer claimID, Double amount) {
		Claim claim = claimDAO.getClaimByID(claimID);
		claim.setEstimatedAmount(amount);
		return claimDAO.update(claim);
	}

}
