package dev.elliman.services;

import dev.elliman.beans.Person;

public interface PersonService {
	
	//read
	public Person login(String username, String password);
	public Person getAutoDS();
	public Person getAutoDH();
	
	//write
	public Boolean update(Person person);
}
