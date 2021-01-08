package dev.elliman.services;

import java.util.List;

import dev.elliman.beans.Claim;
import dev.elliman.beans.RFC;
import dev.elliman.beans.Stage;
import dev.elliman.data.ClaimDAO;
import dev.elliman.data.ClaimDAOFactory;
import dev.elliman.data.RFCDAO;
import dev.elliman.data.RFCDAOFactory;

public class RFCServiceImpl implements RFCService {
	
	private RFCDAO rfcDAO;
	
	public RFCServiceImpl() {
		rfcDAO = RFCDAOFactory.getRFCDAO();
	}

	@Override
	public RFC makeRFC(RFC rfc) {
		return rfcDAO.makeRequest(rfc);
	}

	@Override
	public List<RFC> getRFCByClaim(Integer claimID) {
		return rfcDAO.getRFC(claimID);
	}

	@Override
	public Boolean answerRFC(Integer id, String answer) {
		return rfcDAO.answer(id,answer);
	}
}
