import java.util.ArrayList;

import org.json.JSONObject;

abstract class Person {

	private int personId;
	private int randomValue;
	
	public Person(int personId) {
		this.personId = personId;
	}
	
	public Person(int personId, int randomValue) {
		this.personId = personId;
		this.randomValue = randomValue;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getRandomValue() {
		return randomValue;
	}

	public void setRandomValue(int randomValue) {
		this.randomValue = randomValue;
	}
	
	public abstract int locatePerson(ArrayList<JSONObject> emptySlots);
	
}
