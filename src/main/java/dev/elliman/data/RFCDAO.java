package dev.elliman.data;

import java.util.List;

import dev.elliman.beans.Claim;
import dev.elliman.beans.RFC;

public interface RFCDAO {

	//read
	public List<RFC> getRFC(Integer claimID);
	
	//write
	public RFC makeRequest(RFC rfc);
	public Boolean answer(Integer id, String answer);
}
