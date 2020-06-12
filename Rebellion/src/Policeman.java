import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class Policeman extends Person{
	
	public Policeman(int personId) {
		super(personId);
	}

	public Policeman(int personId, int randomValue) {
		super(personId, randomValue);
	}

	public int locatePerson(ArrayList<JSONObject> emptySlots) {
		Random random = new Random();
		int randomSlot = random.nextInt((emptySlots.size()));
		return randomSlot;
	}
	
	public ArrayList<JSONObject> seekActiveAgents(JSONObject police, double vision, JSONObject[][] city, int dimension) throws JSONException {
		
		ArrayList<JSONObject> activeCitizensAround = new ArrayList<JSONObject>();
		int xCoordinateCop = police.getInt("x_coordinate");
		int yCoordinateCop = police.getInt("y_coordinate");
							
		for(int j = 1; j <= vision; j++) {
			for(int k = 1; k <= vision; k++) {
				if(xCoordinateCop - j >= 0) {
					if(city[xCoordinateCop - j][yCoordinateCop] != null) {
						// For the elements on direct up
						if(city[xCoordinateCop - j][yCoordinateCop].get("person_type") == "Citizen") {
							if(city[xCoordinateCop - j][yCoordinateCop].get("state") == "active") {
								
								if (!activeCitizensAround.contains(city[xCoordinateCop - j][yCoordinateCop])) { 
									activeCitizensAround.add(city[xCoordinateCop - j][yCoordinateCop]); 
					            } 
								
							}
						}
					}
					
					if(yCoordinateCop + k < dimension) {
						if(city[xCoordinateCop - j][yCoordinateCop + k] != null) {
							// For the elements on right top corner
							if(city[xCoordinateCop - j][yCoordinateCop + k].get("person_type") == "Citizen") {
								if(city[xCoordinateCop - j][yCoordinateCop + k].get("state") == "active") {
									
									if (!activeCitizensAround.contains(city[xCoordinateCop - j][yCoordinateCop + k])) { 
										activeCitizensAround.add(city[xCoordinateCop - j][yCoordinateCop + k]); 
						            } 
									
								}
							}
						}
					}
					
					if(yCoordinateCop - k >=  0) {
						if(city[xCoordinateCop - j][yCoordinateCop - k] != null) {
							// For the elements on left top corner
							if(city[xCoordinateCop - j][yCoordinateCop - k].get("person_type") == "Citizen") {
								if(city[xCoordinateCop - j][yCoordinateCop - k].get("state") == "active") {
									
									if (!activeCitizensAround.contains(city[xCoordinateCop - j][yCoordinateCop - k])) { 
										activeCitizensAround.add(city[xCoordinateCop - j][yCoordinateCop - k]); 
						            } 
									
								}
							}
						}
					}
				}
				
				
				if(yCoordinateCop + j < dimension) {
					if(city[xCoordinateCop][yCoordinateCop + j] != null) {
						// For the elements on right
						if(city[xCoordinateCop][yCoordinateCop + j].get("person_type") == "Citizen") {
							if(city[xCoordinateCop][yCoordinateCop + j].get("state") == "active") {
								
								if (!activeCitizensAround.contains(city[xCoordinateCop][yCoordinateCop + j])) { 
									activeCitizensAround.add(city[xCoordinateCop][yCoordinateCop + j]); 
					            } 
								
							}
						}
					}
					
					if(xCoordinateCop + k < dimension) {
						if(city[xCoordinateCop + k][yCoordinateCop + j] != null) {
							// For the elements on right bottom corner
							if(city[xCoordinateCop + k][yCoordinateCop + j].get("person_type") == "Citizen") {
								if(city[xCoordinateCop + k][yCoordinateCop + j].get("state") == "active") {
									
									if (!activeCitizensAround.contains(city[xCoordinateCop + k][yCoordinateCop + j])) { 
										activeCitizensAround.add(city[xCoordinateCop + k][yCoordinateCop + j]); 
						            } 
									
								}
							}
						}
					}
				}
				
				
				if(yCoordinateCop - j >= 0) {
					if(city[xCoordinateCop][yCoordinateCop - j] != null) {
						// For the elements on left
						if(city[xCoordinateCop][yCoordinateCop - j].get("person_type") == "Citizen") {
							if(city[xCoordinateCop][yCoordinateCop - j].get("state") == "active") {
								
								if (!activeCitizensAround.contains(city[xCoordinateCop][yCoordinateCop - j])) { 
									activeCitizensAround.add(city[xCoordinateCop][yCoordinateCop - j]); 
					            } 
								
							}
						}
					}
					
					if(xCoordinateCop + k < dimension) {
						if(city[xCoordinateCop + k][yCoordinateCop - j] != null) {
							// For the elements on left bottom corner
							if(city[xCoordinateCop + k][yCoordinateCop - j].get("person_type") == "Citizen") {
								if(city[xCoordinateCop + k][yCoordinateCop - j].get("state") == "active") {
									
									if (!activeCitizensAround.contains(city[xCoordinateCop + k][yCoordinateCop - j])) { 
										activeCitizensAround.add(city[xCoordinateCop + k][yCoordinateCop - j]); 
						            } 
									
								}
							}
						}
					}
				}
				
				if(xCoordinateCop + j < dimension) {
					if(city[xCoordinateCop + j][yCoordinateCop] != null) {
						// For the elements on bottom
						if(city[xCoordinateCop + j][yCoordinateCop].get("person_type") == "Citizen") {
							if(city[xCoordinateCop + j][yCoordinateCop].get("state") == "active") {
								
								if (!activeCitizensAround.contains(city[xCoordinateCop + j][yCoordinateCop])) { 
									activeCitizensAround.add(city[xCoordinateCop + j][yCoordinateCop]); 
					            } 
								
							}
						}
					}
				}
			}
			
		}
		
		return activeCitizensAround;
	}
	
	public JSONObject pickAnActiveAgent(ArrayList<JSONObject> arrestableActiveCitizens) {
		
		Random random = new Random();
		int randomCitizenIndex = random.nextInt((arrestableActiveCitizens.size()));
		
		return arrestableActiveCitizens.get(randomCitizenIndex);
	}
	
	
public ArrayList<JSONObject> seekEmptySlots(JSONObject police, double vision, JSONObject[][] city, int dimension) throws JSONException {
		
		ArrayList<JSONObject> emptyLocationsAround = new ArrayList<JSONObject>();
		int xCoordinateCop = police.getInt("x_coordinate");
		int yCoordinateCop = police.getInt("y_coordinate");
							
		for(int j = 1; j <= vision; j++) {
			for(int k = 1; k <= vision; k++) {
				if(xCoordinateCop - j >= 0) {
					if(city[xCoordinateCop - j][yCoordinateCop] == null) {
						if (!emptyLocationsAround.contains(city[xCoordinateCop - j][yCoordinateCop])) { 
							JSONObject location = new JSONObject();
							location.put("x_coordinate", xCoordinateCop - j);
							location.put("y_coordinate", yCoordinateCop);
							emptyLocationsAround.add(location);
			            } 
					}
					
					if(yCoordinateCop + k < dimension) {
						if(city[xCoordinateCop - j][yCoordinateCop + k] == null) {
							if (!emptyLocationsAround.contains(city[xCoordinateCop - j][yCoordinateCop + k])) { 
								JSONObject location = new JSONObject();
								location.put("x_coordinate", xCoordinateCop - j);
								location.put("y_coordinate", yCoordinateCop + k);
								emptyLocationsAround.add(location); 
				            } 
						}
					}
					
					if(yCoordinateCop - k >=  0) {
						if(city[xCoordinateCop - j][yCoordinateCop - k] == null) {
							if (!emptyLocationsAround.contains(city[xCoordinateCop - j][yCoordinateCop - k])) { 
								JSONObject location = new JSONObject();
								location.put("x_coordinate", xCoordinateCop - j);
								location.put("y_coordinate", yCoordinateCop - k);
								emptyLocationsAround.add(location);				            
							} 
						}
					}
				}
				
				
				if(yCoordinateCop + j < dimension) {
					if(city[xCoordinateCop][yCoordinateCop + j] == null) {
						if (!emptyLocationsAround.contains(city[xCoordinateCop][yCoordinateCop + j])) { 
							JSONObject location = new JSONObject();
							location.put("x_coordinate", xCoordinateCop);
							location.put("y_coordinate", yCoordinateCop + j);
							emptyLocationsAround.add(location);
			            } 
					}
					
					if(xCoordinateCop + k < dimension) {
						if(city[xCoordinateCop + k][yCoordinateCop + j] == null) {
							if (!emptyLocationsAround.contains(city[xCoordinateCop + k][yCoordinateCop + j])) { 
								JSONObject location = new JSONObject();
								location.put("x_coordinate", xCoordinateCop + k);
								location.put("y_coordinate", yCoordinateCop + j);
								emptyLocationsAround.add(location);				            
							} 
						}
					}
				}
				
				
				if(yCoordinateCop - j >= 0) {
					if(city[xCoordinateCop][yCoordinateCop - j] == null) {
						if (!emptyLocationsAround.contains(city[xCoordinateCop][yCoordinateCop - j])) { 
							JSONObject location = new JSONObject();
							location.put("x_coordinate", xCoordinateCop);
							location.put("y_coordinate", yCoordinateCop - j);
							emptyLocationsAround.add(location);			            
						} 
					}
					
					if(xCoordinateCop + k < dimension) {
						if(city[xCoordinateCop + k][yCoordinateCop - j] == null) {
							if (!emptyLocationsAround.contains(city[xCoordinateCop + k][yCoordinateCop - j])) { 
								JSONObject location = new JSONObject();
								location.put("x_coordinate", xCoordinateCop + k);
								location.put("y_coordinate", yCoordinateCop - j);
								emptyLocationsAround.add(location);				            
							} 
						}
					}
				}
				
				if(xCoordinateCop + j < dimension) {
					if(city[xCoordinateCop + j][yCoordinateCop] == null) {
						if (!emptyLocationsAround.contains(city[xCoordinateCop + j][yCoordinateCop])) { 
							JSONObject location = new JSONObject();
							location.put("x_coordinate", xCoordinateCop + j);
							location.put("y_coordinate", yCoordinateCop);
							emptyLocationsAround.add(location);			            
						} 
					}
				}
			}
			
		}
		
		return emptyLocationsAround;
	}
	
	public JSONObject pickASlot(ArrayList<JSONObject> availableLocations) throws JSONException {
		
		Random random = new Random();
		
		if(availableLocations.size() == 0) {
			JSONObject nullJson = new JSONObject();
			nullJson.put("x_coordinate", -1);
			nullJson.put("y_coordinate", -1);
			return nullJson;
		}
		else {
			int randomLocationIndex = random.nextInt((availableLocations.size()));
			return availableLocations.get(randomLocationIndex);
		}
	}
}