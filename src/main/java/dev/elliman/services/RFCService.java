package dev.elliman.services;

import java.util.List;

import dev.elliman.beans.RFC;

public interface RFCService {

	//read
	public List<RFC> getRFCByClaim(Integer claimID);
	
	//write
	public RFC makeRFC(RFC rfc);
	public Boolean answerRFC(Integer id, String answer);
}
