package dev.elliman.data;

import dev.elliman.beans.Person;

public interface PersonDAO {

	//read
	public Person getPersonByUsername(String username);
	
	//write
	public Boolean update(Person person);
}
