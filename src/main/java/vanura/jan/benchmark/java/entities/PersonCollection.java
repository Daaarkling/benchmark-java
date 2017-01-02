
package vanura.jan.benchmark.java.entities;

import java.util.ArrayList;
import java.util.List;

public class PersonCollection {
    
	private List<Person> persons = new ArrayList<>();

   
	public void addPerson(Person person) {
		
		persons.add(person);
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	

}