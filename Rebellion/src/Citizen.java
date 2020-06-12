import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class Citizen extends Person{
	
	public Citizen(int personId) {
		super(personId);
	}

	public Citizen(int personId, int randomValue) {
		super(personId, randomValue);
	}

	public int locatePerson(ArrayList<JSONObject> emptySlots) {
		Random random = new Random();
		int randomSlot = random.nextInt((emptySlots.size()));
		return randomSlot;
	}
	
	
	public ArrayList<JSONObject> seekEmptySlots(JSONObject citizen, double vision, JSONObject[][] city, int dimension) throws JSONException {

		ArrayList<JSONObject> emptyLocationsAround = new ArrayList<JSONObject>();
		int xCoordinateCitizen = citizen.getInt("x_coordinate");
		int yCoordinateCitizen = citizen.getInt("y_coordinate");

		for(int j = 1; j <= vision; j++) {
			for(int k = 1; k <= vision; k++) {
				if(xCoordinateCitizen - j >= 0) {
					if(city[xCoordinateCitizen - j][yCoordinateCitizen] == null) {
						if (!emptyLocationsAround.contains(city[xCoordinateCitizen - j][yCoordinateCitizen])) { 
							JSONObject location = new JSONObject();
							location.put("x_coordinate", xCoordinateCitizen - j);
							location.put("y_coordinate", yCoordinateCitizen);
							emptyLocationsAround.add(location);
						} 
					}

					if(yCoordinateCitizen + k < dimension) {
						if(city[xCoordinateCitizen - j][yCoordinateCitizen + k] == null) {
							if (!emptyLocationsAround.contains(city[xCoordinateCitizen - j][yCoordinateCitizen + k])) { 
								JSONObject location = new JSONObject();
								location.put("x_coordinate", xCoordinateCitizen - j);
								location.put("y_coordinate", yCoordinateCitizen + k);
								emptyLocationsAround.add(location); 
							} 
						}
					}

					if(yCoordinateCitizen - k >=  0) {
						if(city[xCoordinateCitizen - j][yCoordinateCitizen - k] == null) {
							if (!emptyLocationsAround.contains(city[xCoordinateCitizen - j][yCoordinateCitizen - k])) { 
								JSONObject location = new JSONObject();
								location.put("x_coordinate", xCoordinateCitizen - j);
								location.put("y_coordinate", yCoordinateCitizen - k);
								emptyLocationsAround.add(location);				            
							} 
						}
					}
				}


				if(yCoordinateCitizen + j < dimension) {
					if(city[xCoordinateCitizen][yCoordinateCitizen + j] == null) {
						if (!emptyLocationsAround.contains(city[xCoordinateCitizen][yCoordinateCitizen + j])) { 
							JSONObject location = new JSONObject();
							location.put("x_coordinate", xCoordinateCitizen);
							location.put("y_coordinate", yCoordinateCitizen + j);
							emptyLocationsAround.add(location);
						} 
					}

					if(xCoordinateCitizen + k < dimension) {
						if(city[xCoordinateCitizen + k][yCoordinateCitizen + j] == null) {
							if (!emptyLocationsAround.contains(city[xCoordinateCitizen + k][yCoordinateCitizen + j])) { 
								JSONObject location = new JSONObject();
								location.put("x_coordinate", xCoordinateCitizen + k);
								location.put("y_coordinate", yCoordinateCitizen + j);
								emptyLocationsAround.add(location);				            
							} 
						}
					}
				}


				if(yCoordinateCitizen - j >= 0) {
					if(city[xCoordinateCitizen][yCoordinateCitizen - j] == null) {
						if (!emptyLocationsAround.contains(city[xCoordinateCitizen][yCoordinateCitizen - j])) { 
							JSONObject location = new JSONObject();
							location.put("x_coordinate", xCoordinateCitizen);
							location.put("y_coordinate", yCoordinateCitizen - j);
							emptyLocationsAround.add(location);			            
						} 
					}

					if(xCoordinateCitizen + k < dimension) {
						if(city[xCoordinateCitizen + k][yCoordinateCitizen - j] == null) {
							if (!emptyLocationsAround.contains(city[xCoordinateCitizen + k][yCoordinateCitizen - j])) { 
								JSONObject location = new JSONObject();
								location.put("x_coordinate", xCoordinateCitizen + k);
								location.put("y_coordinate", yCoordinateCitizen - j);
								emptyLocationsAround.add(location);				            
							} 
						}
					}
				}

				if(xCoordinateCitizen + j < dimension) {
					if(city[xCoordinateCitizen + j][yCoordinateCitizen] == null) {
						if (!emptyLocationsAround.contains(city[xCoordinateCitizen + j][yCoordinateCitizen])) { 
							JSONObject location = new JSONObject();
							location.put("x_coordinate", xCoordinateCitizen + j);
							location.put("y_coordinate", yCoordinateCitizen);
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
	
	public int[] countActiveCitizens(double vision, JSONObject[][] city, int xCoordinateCitizen, int yCoordinateCitizen, int dimension) throws JSONException {
		
		ArrayList<JSONObject> nearbyActiveCitizens = new ArrayList<JSONObject>();
		ArrayList<JSONObject> nearbyCops = new ArrayList<JSONObject>();
		
		for(int j = 1; j <= vision; j++) {
			for(int k = 1; k <= vision; k++) {
				if(xCoordinateCitizen - j >= 0) {
					if(city[xCoordinateCitizen - j][yCoordinateCitizen] != null) {
						if (city[xCoordinateCitizen - j][yCoordinateCitizen].get("person_type").equals("Citizen")) { 
							if(city[xCoordinateCitizen - j][yCoordinateCitizen].get("state").equals("active")) {
								if(!nearbyActiveCitizens.contains(city[xCoordinateCitizen - j][yCoordinateCitizen])) {
									nearbyActiveCitizens.add(city[xCoordinateCitizen - j][yCoordinateCitizen]);
								}
							}
						}
						else {
							if(!nearbyCops.contains(city[xCoordinateCitizen - j][yCoordinateCitizen])) {
								nearbyCops.add(city[xCoordinateCitizen - j][yCoordinateCitizen]);
							}
						}
					}

					if(yCoordinateCitizen + k < dimension) {
						if(city[xCoordinateCitizen - j][yCoordinateCitizen + k] != null) {
							if (city[xCoordinateCitizen - j][yCoordinateCitizen + k].get("person_type").equals("Citizen")) { 
								if(city[xCoordinateCitizen - j][yCoordinateCitizen + k].get("state").equals("active")) {
									if(!nearbyActiveCitizens.contains(city[xCoordinateCitizen - j][yCoordinateCitizen + k])) {
										nearbyActiveCitizens.add(city[xCoordinateCitizen - j][yCoordinateCitizen + k]);
									}
								}
							}
							else {
								if(!nearbyCops.contains(city[xCoordinateCitizen - j][yCoordinateCitizen + k])) {
									nearbyCops.add(city[xCoordinateCitizen - j][yCoordinateCitizen + k]);
								}
							}
						}
					}

					if(yCoordinateCitizen - k >=  0) {
						if(city[xCoordinateCitizen - j][yCoordinateCitizen - k] != null) {
							if (city[xCoordinateCitizen - j][yCoordinateCitizen - k].get("person_type").equals("Citizen")) { 
								if(city[xCoordinateCitizen - j][yCoordinateCitizen - k].get("state").equals("active")) {
									if(!nearbyActiveCitizens.contains(city[xCoordinateCitizen - j][yCoordinateCitizen - k])) {
										nearbyActiveCitizens.add(city[xCoordinateCitizen - j][yCoordinateCitizen - k]);
									}
								}
							} 
							else {
								if(!nearbyCops.contains(city[xCoordinateCitizen - j][yCoordinateCitizen - k])) {
									nearbyCops.add(city[xCoordinateCitizen - j][yCoordinateCitizen - k]);
								}
							}
						}
					}
				}


				if(yCoordinateCitizen + j < dimension) {
					if(city[xCoordinateCitizen][yCoordinateCitizen + j] != null) {
						if (city[xCoordinateCitizen][yCoordinateCitizen + j].get("person_type").equals("Citizen")) { 
							if(city[xCoordinateCitizen][yCoordinateCitizen + j].get("state").equals("active")) {
								if(!nearbyActiveCitizens.contains(city[xCoordinateCitizen][yCoordinateCitizen + j])) {
									nearbyActiveCitizens.add(city[xCoordinateCitizen][yCoordinateCitizen + j]);
								}
							}
						}
						else {
							if(!nearbyCops.contains(city[xCoordinateCitizen][yCoordinateCitizen + j])) {
								nearbyCops.add(city[xCoordinateCitizen][yCoordinateCitizen + j]);
							}
						}
					}

					if(xCoordinateCitizen + k < dimension) {
						if(city[xCoordinateCitizen + k][yCoordinateCitizen + j] != null) {
							if (city[xCoordinateCitizen + k][yCoordinateCitizen + j].get("person_type").equals("Citizen")) { 
								if(city[xCoordinateCitizen + k][yCoordinateCitizen + j].get("state").equals("active")) {
									if(!nearbyActiveCitizens.contains(city[xCoordinateCitizen + k][yCoordinateCitizen + j])) {
										nearbyActiveCitizens.add(city[xCoordinateCitizen + k][yCoordinateCitizen + j]);
									}
								}
							}
							else {
								if(!nearbyCops.contains(city[xCoordinateCitizen + k][yCoordinateCitizen + j])) {
									nearbyCops.add(city[xCoordinateCitizen + k][yCoordinateCitizen + j]);
								}
							}
						}
					}
				}


				if(yCoordinateCitizen - j >= 0) {
					if(city[xCoordinateCitizen][yCoordinateCitizen - j] != null) {
						if (city[xCoordinateCitizen][yCoordinateCitizen - j].get("person_type").equals("Citizen")) { 
							if(city[xCoordinateCitizen][yCoordinateCitizen - j].get("state").equals("active")) {
								if(!nearbyActiveCitizens.contains(city[xCoordinateCitizen][yCoordinateCitizen - j])) {
									nearbyActiveCitizens.add(city[xCoordinateCitizen][yCoordinateCitizen - j]);
								}
							}
						}
						else {
							if(!nearbyCops.contains(city[xCoordinateCitizen][yCoordinateCitizen - j])) {
								nearbyCops.add(city[xCoordinateCitizen][yCoordinateCitizen - j]);
							}
						}
					}

					if(xCoordinateCitizen + k < dimension) {
						if(city[xCoordinateCitizen + k][yCoordinateCitizen - j] != null) {
							if (city[xCoordinateCitizen + k][yCoordinateCitizen - j].get("person_type").equals("Citizen")) { 
								if(city[xCoordinateCitizen + k][yCoordinateCitizen - j].get("state").equals("active")) {
									if(!nearbyActiveCitizens.contains(city[xCoordinateCitizen + k][yCoordinateCitizen - j])) {
										nearbyActiveCitizens.add(city[xCoordinateCitizen + k][yCoordinateCitizen - j]);
									}
								}
							} 
							else {
								if(!nearbyCops.contains(city[xCoordinateCitizen + k][yCoordinateCitizen - j])) {
									nearbyCops.add(city[xCoordinateCitizen + k][yCoordinateCitizen - j]);
								}
							}
						}
					}
				}

				if(xCoordinateCitizen + j < dimension) {
					if(city[xCoordinateCitizen + j][yCoordinateCitizen] != null) {
						if (city[xCoordinateCitizen + j][yCoordinateCitizen].get("person_type").equals("Citizen")) { 
							if(city[xCoordinateCitizen + j][yCoordinateCitizen].get("state").equals("active")) {
								if(!nearbyActiveCitizens.contains(city[xCoordinateCitizen + j][yCoordinateCitizen])) {
									nearbyActiveCitizens.add(city[xCoordinateCitizen + j][yCoordinateCitizen]);
								}
							}
						}
						else {
							if(!nearbyCops.contains(city[xCoordinateCitizen + j][yCoordinateCitizen])) {
								nearbyCops.add(city[xCoordinateCitizen + j][yCoordinateCitizen]);
							}
						}
					}
				}
			}

		}
		
		int[] resultArr = new int[2];
		resultArr[0] = nearbyActiveCitizens.size();
		resultArr[1] = nearbyCops.size();
		
		return resultArr;
	}
	
	public boolean isCloseToAnyRebel(double vision, JSONObject[][] city, int xCoordinateCitizen, int yCoordinateCitizen, int dimension) throws JSONException {
		
		
		for(int j = 1; j <= vision; j++) {
			for(int k = 1; k <= vision; k++) {
				if(xCoordinateCitizen - j >= 0) {
					if(city[xCoordinateCitizen - j][yCoordinateCitizen] != null) {
						if (city[xCoordinateCitizen - j][yCoordinateCitizen].get("person_type").equals("Citizen")) { 
							if(city[xCoordinateCitizen - j][yCoordinateCitizen].get("state").equals("active")) {
								return true;
							}
						}
					}

					if(yCoordinateCitizen + k < dimension) {
						if(city[xCoordinateCitizen - j][yCoordinateCitizen + k] != null) {
							if (city[xCoordinateCitizen - j][yCoordinateCitizen + k].get("person_type").equals("Citizen")) { 
								if(city[xCoordinateCitizen - j][yCoordinateCitizen + k].get("state").equals("active")) {
									return true;
								}
							}
						}
					}

					if(yCoordinateCitizen - k >=  0) {
						if(city[xCoordinateCitizen - j][yCoordinateCitizen - k] != null) {
							if (city[xCoordinateCitizen - j][yCoordinateCitizen - k].get("person_type").equals("Citizen")) { 
								if(city[xCoordinateCitizen - j][yCoordinateCitizen - k].get("state").equals("active")) {
									return true;
								}
							} 
						}
					}
				}


				if(yCoordinateCitizen + j < dimension) {
					if(city[xCoordinateCitizen][yCoordinateCitizen + j] != null) {
						if (city[xCoordinateCitizen][yCoordinateCitizen + j].get("person_type").equals("Citizen")) { 
							if(city[xCoordinateCitizen][yCoordinateCitizen + j].get("state").equals("active")) {
								return true;
							}
						}
					}

					if(xCoordinateCitizen + k < dimension) {
						if(city[xCoordinateCitizen + k][yCoordinateCitizen + j] != null) {
							if (city[xCoordinateCitizen + k][yCoordinateCitizen + j].get("person_type").equals("Citizen")) { 
								if(city[xCoordinateCitizen + k][yCoordinateCitizen + j].get("state").equals("active")) {
									return true;
								}
							}
						}
					}
				}


				if(yCoordinateCitizen - j >= 0) {
					if(city[xCoordinateCitizen][yCoordinateCitizen - j] != null) {
						if (city[xCoordinateCitizen][yCoordinateCitizen - j].get("person_type").equals("Citizen")) { 
							if(city[xCoordinateCitizen][yCoordinateCitizen - j].get("state").equals("active")) {
								return true;
							}
						}
					}

					if(xCoordinateCitizen + k < dimension) {
						if(city[xCoordinateCitizen + k][yCoordinateCitizen - j] != null) {
							if (city[xCoordinateCitizen + k][yCoordinateCitizen - j].get("person_type").equals("Citizen")) { 
								if(city[xCoordinateCitizen + k][yCoordinateCitizen - j].get("state").equals("active")) {
									return true;
								}
							} 
						}
					}
				}

				if(xCoordinateCitizen + j < dimension) {
					if(city[xCoordinateCitizen + j][yCoordinateCitizen] != null) {
						if (city[xCoordinateCitizen + j][yCoordinateCitizen].get("person_type").equals("Citizen")) { 
							return true;
						}
					}
				}
			}

		}
		
		return false;
	}

}