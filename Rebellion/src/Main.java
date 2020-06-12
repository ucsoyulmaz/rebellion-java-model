import java.awt.EventQueue;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import org.json.*;

public class Main {

	public static double initialCopDensity;
	public static double initialCitizenDensity;
	public static double vision;
	public static double governmentLegitimacy;
	public static int maxJailTerm;	
	public static boolean stopMainValue;
	
		
	public static void run() throws JSONException, IOException {	
		
		
		// PrintWriter for printing output to a csv file
		FileWriter writer = new FileWriter("numeric_output.csv");
		
		// Titles of each column
		writer.append("Tick");
		writer.append(',');
		writer.append("Quiet Citizens");
		writer.append(',');
		writer.append("Active Citizens");
		writer.append(',');
		writer.append("Jailed Citizens");
		writer.append('\n');		    
	    
		// k value in the formula (constant)
		double constantK = 2.3;
		
		// difference value for limit for checking whether a citizen is active or not
		double threshold = 0.1;
		
		// Initialization of the random generator
		Random random = new Random();
		
		// Initialization of the array list for all citizens
		ArrayList<JSONObject> allCitizens = new ArrayList<JSONObject>();
		
		// Initialization of the array list for cops
		ArrayList<JSONObject> allCops = new ArrayList<JSONObject>();
		
		// Initialization of the array list for active citizens
		ArrayList<JSONObject> activeCitizens = new ArrayList<JSONObject>();
		
		// Initialization of the array list for quiet citizens
		ArrayList<JSONObject> quietCitizens = new ArrayList<JSONObject>();
		
		// Initialization of the array list for jailed citizens
		ArrayList<JSONObject> jailedCitizens = new ArrayList<JSONObject>();
		
		
		//******************************* INPUT ************************************
		// Getting the dimension variable from the terminal
		// I let our system to accept some dimension values less than 40 for testing
		
		int dimension = 40;
		
		//**************************************************************************
		
		
		// Creating a 2D array for the city
		JSONObject[][] city = new JSONObject[dimension][dimension];		
		int maxSize = dimension * dimension;
		
		
		// Initialization of the array list for empty slots
		ArrayList<JSONObject> emptySlots = new ArrayList<JSONObject>();
		
		// Filling the list with the actual empty slots
		for(int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				JSONObject slot = new JSONObject();
				slot.put("x_coordinate", i);
				slot.put("y_coordinate", j);
				emptySlots.add(slot);
			}
		}
		
		
		//******************************* INPUT ************************************
		// Getting the initial cop density and initial agent density
		
		int numberOfCops = (int) ((double) maxSize * (initialCopDensity / 100));

		
		int numberOfCitizens = (int) ((double) maxSize * (initialCitizenDensity / 100));
		//**************************************************************************
		
		
		
		//*************************** LOCATING COPS *************************************
		
		// Getting random values for the cops
		ArrayList<JSONObject> randomValuesForCops = new ArrayList<JSONObject>();
		for(int i = 0; i < numberOfCops; i++) {
			int randomValue = random.nextInt(100);
			JSONObject cop = new JSONObject();
			cop.put("person_id", i + 1);
			cop.put("random_value", randomValue);
			randomValuesForCops.add(cop);
		}
		
		// Sorting the list of cops based on their random values
		Collections.sort(randomValuesForCops, new Comparator<JSONObject>() {
		    @Override
		    public int compare(JSONObject jsonObjectA, JSONObject jsonObjectB) {
		        int compare = 0;
		        try
		        {
		            int keyA = jsonObjectA.getInt("random_value");
		            int keyB = jsonObjectB.getInt("random_value");
		            compare = Integer.compare(keyA, keyB);
		        }
		        catch(JSONException e)
		        {
		            e.printStackTrace();
		        }
		        return compare;
		    }
		});
		
		
		// Initialization of the TEMPORARY array list for active citizens
		ArrayList<JSONObject> activeCitizensTemp = new ArrayList<JSONObject>();
		
		// Initialization of the TEMPORARY array list for quiet citizens
		ArrayList<JSONObject> quietCitizensTemp = new ArrayList<JSONObject>();
				
		// Locating each cop on the city slots randomly
		while (randomValuesForCops.size() > 0) {
			Policeman police = new Policeman(randomValuesForCops.get(0).getInt("person_id"),
					randomValuesForCops.get(0).getInt("random_value"));
			
			// The index (in emptySlots array list) of the policeman that we are going to locate
			int index = police.locatePerson(emptySlots);
			
			int x_coordinate = emptySlots.get(index).getInt("x_coordinate");
			int y_coordinate = emptySlots.get(index).getInt("y_coordinate");
			
			// Creating a json object to put it inside 2d array as an element
			JSONObject cop = new JSONObject();
			cop.put("person_id", randomValuesForCops.get(0).getInt("person_id"));
			cop.put("person_type", "Policeman");
			cop.put("x_coordinate", x_coordinate);
			cop.put("y_coordinate", y_coordinate);
			
			allCops.add(cop);
			
			// Allocate the space in the 2D array with the json value related to that policeman
			city[x_coordinate][y_coordinate] = cop;
			
			//Remove the empty space slot from the array list (emptySlots)
			emptySlots.remove(index);
			randomValuesForCops.remove(0);
		}	
		
		//********************************************************************************	
		
		
		
		
		
		//*************************** LOCATING CITIZENS **************************************
		
		// Getting random values for the citizens
		ArrayList<JSONObject> randomValuesForCitizens = new ArrayList<JSONObject>();
		for(int i = 0; i < numberOfCitizens; i++) {
			int randomValue = random.nextInt(100);
			JSONObject citizen = new JSONObject();
			citizen.put("person_id", i + 1 + numberOfCops);
			citizen.put("random_value", randomValue);
			randomValuesForCitizens.add(citizen);
		}
		
		// Sorting the list of citizens based on their random values
		Collections.sort(randomValuesForCitizens, new Comparator<JSONObject>() {
		    @Override
		    public int compare(JSONObject jsonObjectA, JSONObject jsonObjectB) {
		        int compare = 0;
		        try
		        {
		            int keyA = jsonObjectA.getInt("random_value");
		            int keyB = jsonObjectB.getInt("random_value");
		            compare = Integer.compare(keyA, keyB);
		        }
		        catch(JSONException e)
		        {
		            e.printStackTrace();
		        }
		        return compare;
		    }
		});
				
		// Locating each citizen on the city slots randomly
		while (randomValuesForCitizens.size() > 0) {
			Citizen citizen = new Citizen(randomValuesForCitizens.get(0).getInt("person_id"),
					randomValuesForCitizens.get(0).getInt("random_value"));
			
			// The index (in emptySlots array list) of the citizen that we are going to locate
			int index = citizen.locatePerson(emptySlots);
			
			int x_coordinate = emptySlots.get(index).getInt("x_coordinate");
			int y_coordinate = emptySlots.get(index).getInt("y_coordinate");
			
			
			
			// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// Calculations related to activeness check
			
			double riskAversion = (double) random.nextInt(100) / (double) 100;
			
			int[] nearbyCopsAndRebels = citizen.countActiveCitizens(vision, city, x_coordinate, x_coordinate, dimension);
			double estimatedArrestProbablity = 1 - (Math.exp( (-constantK) * 
					Math.floor(nearbyCopsAndRebels[1] / (1 + nearbyCopsAndRebels[0]))
							));
			double netRisk = riskAversion * estimatedArrestProbablity;
			double perceivedHardship = (double) random.nextInt(100) / (double) 100;
			double greivance = perceivedHardship * (1 - governmentLegitimacy);	
			int randomJailTermValue = random.nextInt(maxJailTerm);
			
			// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			
			
			// Creating citizen json in order to use it in 2D array
			JSONObject agent = new JSONObject();
			agent.put("person_id", randomValuesForCitizens.get(0).getInt("person_id"));
			agent.put("person_type", "Citizen");
			agent.put("greivance", greivance);
			agent.put("perceivedHardship", perceivedHardship);
			agent.put("riskAversion", riskAversion);
			agent.put("randomJailTermValue", randomJailTermValue);
			
			// Not necessary for 2D array but, it is necessary for allCitizens array list.
			agent.put("x_coordinate", x_coordinate);
			agent.put("y_coordinate", y_coordinate);
			
			
			// Checking whether the citizen active or not
			if(greivance - netRisk > threshold) {
				agent.put("state", "active");
				activeCitizensTemp.add(agent);
			}
			else {
				agent.put("state", "quiet");
				quietCitizensTemp.add(agent);
			}
			
			allCitizens.add(agent);
			
			// Allocate the space in the 2D array with the json value related to that citizen
			city[x_coordinate][y_coordinate] = agent;
			
			//Remove the empty space slot from the array list (emptySlots)
			emptySlots.remove(index);
			randomValuesForCitizens.remove(0);
		}

	
		// Updating the initial states of these array lists.
		activeCitizens = activeCitizensTemp;
		quietCitizens = quietCitizensTemp;
				
		//********************************************************************************
		
		
		
		
		// ABOUT printing stuff:
		// For types --> X : Empty,    P: Policeman,	 C: Citizen
		// For states -->  X : Empty,	a: Active, 		q: quiet,	j: jailed
		
		int tick = 0;
		
		while(stopMainValue != true) {
			System.out.println();
			System.out.println("Quiet Citizens: " + quietCitizens.size() +
					",  Active Citizens: " + activeCitizens.size() +
					",  Jailed Citizens: " + jailedCitizens.size()); 
			
			
			//********************************************************************************
			// Cops try to catch active citizens within the VISION PATCH RADIUS are of them.
			
			ArrayList<JSONObject> objectsToBeRemoved = new ArrayList<JSONObject>();
			ArrayList<JSONObject> objectsToBeAdded = new ArrayList<JSONObject>();
			
			
			for(int i = 0; i < allCops.size(); i++) {
				Policeman police = new Policeman(allCops.get(i).getInt("person_id"));
				
				ArrayList<JSONObject> arrestableActiveCitizens = police.seekActiveAgents(allCops.get(i), vision, city, dimension);
				
				JSONObject chosenCitizen = new JSONObject();
				chosenCitizen.put("person_id", -1);
				
				if(arrestableActiveCitizens.size() > 0) {
					chosenCitizen = police.pickAnActiveAgent(arrestableActiveCitizens);
				}
				
				if(chosenCitizen.getInt("person_id") != -1) {
					
					// Move the cop from its initial position to that citizen's position
					city[allCops.get(i).getInt("x_coordinate")][allCops.get(i).getInt("y_coordinate")] = null;
					JSONObject emptySlot = new JSONObject();
					emptySlot.put("x_coordinate", allCops.get(i).getInt("x_coordinate"));
					emptySlot.put("y_coordinate", allCops.get(i).getInt("y_coordinate"));
					emptySlots.add(emptySlot);
					
					JSONObject cop = new JSONObject();
					cop.put("person_id", allCops.get(i).getInt("person_id"));
					cop.put("person_type", "Policeman");
					cop.put("x_coordinate", chosenCitizen.getInt("x_coordinate"));
					cop.put("y_coordinate", chosenCitizen.getInt("y_coordinate"));
					
					// store the elements that will be deleted because of their new versions.
					objectsToBeRemoved.add(allCops.get(i));
					
					objectsToBeAdded.add(cop);
					city[chosenCitizen.getInt("x_coordinate")][chosenCitizen.getInt("y_coordinate")] = cop;
							
					// Put that citizen to the jail
					jailedCitizens.add(chosenCitizen);
					activeCitizens.remove(chosenCitizen);
				}
			}
			
			for(int i = 0; i < objectsToBeRemoved.size(); i++) {
				allCops.remove(objectsToBeRemoved.get(i));
			}
			
			for(int i = 0; i < objectsToBeAdded.size(); i++) {
				allCops.add(objectsToBeAdded.get(i));
			}

			//********************************************************************************
			
			
						
			//********************************************************************************
			// Moving everybody
			
			// Creating a united array list for both type of people
			ArrayList<JSONObject> allPeople = new ArrayList<JSONObject>();
			
			for(int i = 0; i < allCitizens.size(); i++) {
				if(!jailedCitizens.contains(allCitizens.get(i))) {
					allPeople.add(allCitizens.get(i));
				}
			}
			
			for(int i = 0; i < allCops.size(); i++) {
				allPeople.add(allCops.get(i));
			}
			
			
			// Shuffle the list in order to be fair
			Collections.shuffle(allPeople);
			
			// Move them one by one
			for(int i = 0; i < allPeople.size(); i++) {
				if(allPeople.get(i).getString("person_type").equals("Citizen")) {
					Citizen citizen = new Citizen(allPeople.get(i).getInt("person_id"));
					
					ArrayList<JSONObject> allPossibleLocations = citizen.seekEmptySlots(allPeople.get(i), vision, city, dimension);
					JSONObject coordinates = citizen.pickASlot(allPossibleLocations);
					
					if(coordinates.getInt("x_coordinate") != -1) {
					
						int x_coordinate = coordinates.getInt("x_coordinate");
						int y_coordinate = coordinates.getInt("y_coordinate");
						int previous_x_coordinate = allPeople.get(i).getInt("x_coordinate");
						int previous_y_coordinate = allPeople.get(i).getInt("y_coordinate");
						
						
						// Creating a new json for updating the citizen info
						JSONObject updatedCitizen = new JSONObject();
						updatedCitizen.put("person_id", allPeople.get(i).getInt("person_id"));
						updatedCitizen.put("person_type", "Citizen");
						updatedCitizen.put("x_coordinate", x_coordinate);
						updatedCitizen.put("y_coordinate", y_coordinate);
						updatedCitizen.put("greivance", allPeople.get(i).getDouble("greivance"));
						updatedCitizen.put("perceivedHardship", allPeople.get(i).getDouble("perceivedHardship"));
						updatedCitizen.put("riskAversion", allPeople.get(i).getDouble("riskAversion"));
						updatedCitizen.put("randomJailTermValue", allPeople.get(i).getDouble("randomJailTermValue"));
						updatedCitizen.put("state", allPeople.get(i).getString("state"));
						
						
						//remove previous one
						boolean stopFlag = false;
						
						for (int j = 0; j < allCitizens.size() && stopFlag != true; j++) {
							if(allCitizens.get(j).getInt("person_id") == allPeople.get(i).getInt("person_id")) {
								allCitizens.remove(j);
								stopFlag = true;
							}
						}
						
						city[previous_x_coordinate][previous_y_coordinate] = null;
						
						//add the new one
						allCitizens.add(updatedCitizen);
						city[x_coordinate][y_coordinate] = updatedCitizen;
						
						
						//add the new empty slot element
						JSONObject slot = new JSONObject();
						slot.put("x_coordinate", previous_x_coordinate);
						slot.put("y_coordinate", previous_y_coordinate);
						emptySlots.add(slot);
						
						//delete the used one
						stopFlag = false;
						for (int j = 0; j < emptySlots.size() && stopFlag != true; j++) {
							if(emptySlots.get(j).getInt("x_coordinate") == x_coordinate
									&& emptySlots.get(j).getInt("y_coordinate") == y_coordinate) {
								emptySlots.remove(j);
								stopFlag = true;
							}
						}
					}
						
				}
				
				else if (allPeople.get(i).getString("person_type").equals("Policeman")) {
					
					Policeman police = new Policeman(allPeople.get(i).getInt("person_id"));
					
					ArrayList<JSONObject> availableEmptySlots = police.seekEmptySlots(allPeople.get(i), vision, city, dimension);
					JSONObject coordinates = police.pickASlot(availableEmptySlots);
					
					// if there is a valid position, execute the actions
					if(coordinates.getInt("x_coordinate") != -1) {
						int x_coordinate = coordinates.getInt("x_coordinate");
						int y_coordinate = coordinates.getInt("y_coordinate");
						int previous_x_coordinate = allPeople.get(i).getInt("x_coordinate");
						int previous_y_coordinate = allPeople.get(i).getInt("y_coordinate");
						
						// Creating a new json for updating the cop info
						JSONObject cop = new JSONObject();
						cop.put("person_id", allPeople.get(i).getInt("person_id"));
						cop.put("person_type", "Policeman");
						cop.put("x_coordinate", x_coordinate);
						cop.put("y_coordinate", y_coordinate);
						
						
						//remove previous one						
						boolean stopFlag = false;
						for (int j = 0; j < allCops.size() && stopFlag != true; j++) {
							if(allCops.get(j).getInt("person_id") == allPeople.get(i).getInt("person_id")) {
								allCops.remove(j);
								stopFlag = true;
							}
						}
						
						// make it null in 2D array
						city[previous_x_coordinate][previous_y_coordinate] = null;
						
						// Putting it to the emptyslots arraylist
						JSONObject emptySlot = new JSONObject();
						emptySlot.put("x_coordinate", previous_x_coordinate);
						emptySlot.put("y_coordinate", previous_y_coordinate);
						
						emptySlots.add(emptySlot);
						
						//add the new one
						allCops.add(cop);
						city[x_coordinate][y_coordinate] = cop;
								
						
						//delete the used one
						stopFlag = false;
						
						for (int j = 0; j < emptySlots.size() && stopFlag != true; j++) {
							if(emptySlots.get(j).getInt("x_coordinate") == x_coordinate
									&& emptySlots.get(j).getInt("y_coordinate") == y_coordinate) {
								emptySlots.remove(j);
								stopFlag = true;
							}
						}
					}
					
				}
			}
			
			//********************************************************************************			
			
			
			
			
			
			//-------------------------------- UPDATING IS HERE -----------------------------------
			
			//********************************************************************************
			// Updates of each tick is FOR THE CITIZENS WHO ARE IN THE JAIL handled here.
			
			ArrayList<JSONObject> jailedCitizensToBeRemoved = new ArrayList<JSONObject> ();
			ArrayList<JSONObject> jailedCitizensToBeAdded = new ArrayList<JSONObject> ();
			
			if(jailedCitizens.size() >= 1) {
				
				for(int i = 0; i < jailedCitizens.size(); i++) {
					
					// Free the citizen who has 1 tick left in the jail
					if(jailedCitizens.get(i).getInt("randomJailTermValue") <= 1) {
						
						int x = jailedCitizens.get(i).getInt("x_coordinate");
						int y = jailedCitizens.get(i).getInt("y_coordinate");
						
						if(city[x][y] == null) {
							jailedCitizensToBeRemoved.add(jailedCitizens.get(i));
							
							
							JSONObject citizenBack = new JSONObject();
							
							Citizen citizen = new Citizen(jailedCitizens.get(i).getInt("person_id"));
							
							int[] nearbyCopsAndRebels = citizen.countActiveCitizens(vision, city, x, y, dimension);
							double estimatedArrestProbablity = 1 - (Math.exp( (-constantK) * 
									Math.floor(nearbyCopsAndRebels[1] / (1 + nearbyCopsAndRebels[0]))
											));
							double netRisk = jailedCitizens.get(i).getDouble("riskAversion") * estimatedArrestProbablity;
							double greivance = jailedCitizens.get(i).getDouble("perceivedHardship") * (1 - governmentLegitimacy);	
							int randomJailTermValue = random.nextInt(maxJailTerm);
							
							citizenBack.put("person_id", jailedCitizens.get(i).getInt("person_id"));
							citizenBack.put("person_type", "Citizen");
							citizenBack.put("x_coordinate", x);
							citizenBack.put("y_coordinate", y);
							citizenBack.put("greivance", greivance);
							citizenBack.put("perceivedHardship", jailedCitizens.get(i).getDouble("perceivedHardship"));
							citizenBack.put("riskAversion",jailedCitizens.get(i).getDouble("riskAversion"));
							citizenBack.put("randomJailTermValue", randomJailTermValue);
							
							// Checking whether the citizen active or not
							if(greivance - netRisk > threshold) {
								citizenBack.put("state", "active");
								activeCitizens.add(citizenBack);
							}
							else {
								citizenBack.put("state", "quiet");
								quietCitizens.add(citizenBack);
							}
							
							allCitizens.add(citizenBack);
						}
					}
					
					// Decrease the tick level 1 if the citizen is not going to be free in this round
					else {
						
						JSONObject citizen = new JSONObject();
						
						citizen.put("person_id", jailedCitizens.get(i).getInt("person_id"));
						citizen.put("person_type", "Citizen");
						citizen.put("x_coordinate", jailedCitizens.get(i).getInt("x_coordinate"));
						citizen.put("y_coordinate", jailedCitizens.get(i).getInt("y_coordinate"));
						citizen.put("greivance", jailedCitizens.get(i).getDouble("greivance"));
						citizen.put("perceivedHardship", jailedCitizens.get(i).getDouble("perceivedHardship"));
						citizen.put("riskAversion",jailedCitizens.get(i).getDouble("riskAversion"));
						citizen.put("randomJailTermValue", jailedCitizens.get(i).getInt("randomJailTermValue") - 1);
						citizen.put("state","jailed");
						
						jailedCitizensToBeRemoved.add(jailedCitizens.get(i));
						jailedCitizensToBeAdded.add(citizen);
						
						int x = jailedCitizens.get(i).getInt("x_coordinate");
						int y = jailedCitizens.get(i).getInt("y_coordinate");
						
					}
					
				}
				
				
				for(int i = 0; i < jailedCitizensToBeRemoved.size(); i++) {
					jailedCitizens.remove(jailedCitizensToBeRemoved.get(i));
					allCitizens.remove(jailedCitizensToBeRemoved.get(i));
				}
				
				for(int i = 0; i < jailedCitizensToBeAdded.size(); i++) {
					jailedCitizens.add(jailedCitizensToBeAdded.get(i));
					allCitizens.add(jailedCitizensToBeAdded.get(i));
				}
				
			}
			
			//********************************************************************************
			
			
			
			
			
			//********************************************************************************
			// Updating the tick FOR THE CITIZENS WHO ARE NOT INSIDE THE JAIL
			
			ArrayList<JSONObject> citizensToBeRemoved = new ArrayList<JSONObject> ();
			ArrayList<JSONObject> citizensToBeAdded = new ArrayList<JSONObject> ();
			
			for(int i = 0; i < allCitizens.size(); i++) {
				
				// Execute it for ONLY the free citizens
				if(!jailedCitizens.contains(allCitizens.get(i))) {
															
					// Creating a new json for the updated version of the citizen
					JSONObject updatedCitizen = new JSONObject();
					
					Citizen citizen = new Citizen(allCitizens.get(i).getInt("person_id"));
					
					int[] nearbyCopsAndRebels = citizen.countActiveCitizens(vision, city, allCitizens.get(i).getInt("x_coordinate"), allCitizens.get(i).getInt("y_coordinate"), dimension);
					double estimatedArrestProbablity = 1 - (Math.exp( (-constantK) * 
							Math.floor(nearbyCopsAndRebels[1] / (1 + nearbyCopsAndRebels[0]))
									));
					double netRisk = allCitizens.get(i).getDouble("riskAversion") * estimatedArrestProbablity;
					double greivance = allCitizens.get(i).getDouble("perceivedHardship") * (1 - governmentLegitimacy);	
					
					updatedCitizen.put("person_id", allCitizens.get(i).getInt("person_id"));
					updatedCitizen.put("person_type", "Citizen");
					updatedCitizen.put("x_coordinate", allCitizens.get(i).getInt("x_coordinate"));
					updatedCitizen.put("y_coordinate", allCitizens.get(i).getInt("y_coordinate"));
					updatedCitizen.put("greivance", greivance);
					updatedCitizen.put("perceivedHardship", allCitizens.get(i).getDouble("perceivedHardship"));
					updatedCitizen.put("riskAversion",allCitizens.get(i).getDouble("riskAversion"));
					updatedCitizen.put("randomJailTermValue", allCitizens.get(i).getInt("randomJailTermValue"));
					
					// Checking whether the citizen active or not
					if(greivance - netRisk > threshold) {
						updatedCitizen.put("state", "active");
					}
					else {
						updatedCitizen.put("state", "quiet");
					}
					
					city[allCitizens.get(i).getInt("x_coordinate")][allCitizens.get(i).getInt("y_coordinate")] =  updatedCitizen;
					citizensToBeRemoved.add(allCitizens.get(i));
					citizensToBeAdded.add(updatedCitizen);
					
				}
				
			}
			
			for(int i = 0; i < citizensToBeRemoved.size(); i++) {
				
				allCitizens.remove(citizensToBeRemoved.get(i));
				
				boolean stopFlagForActiveList = false;
				
				for(int j = 0; j < activeCitizens.size() && stopFlagForActiveList != true; j++) {
					if(activeCitizens.get(j).getInt("person_id") == citizensToBeRemoved.get(i).getInt("person_id")) {
						activeCitizens.remove(j);
						stopFlagForActiveList = true;
					}
				}
				
				if(stopFlagForActiveList == false) {
					boolean stopFlagForQuietList = false;
					for(int j = 0; j < quietCitizens.size() && stopFlagForQuietList != true; j++) {
						if(quietCitizens.get(j).getInt("person_id") == citizensToBeRemoved.get(i).getInt("person_id")) {
							quietCitizens.remove(j);
							stopFlagForQuietList = true;
						}
					}
				}
			}
			
			for(int i = 0; i < citizensToBeAdded.size(); i++) {
				allCitizens.add(citizensToBeAdded.get(i));
				
				if(citizensToBeAdded.get(i).getString("state").equals("active")) {
					activeCitizens.add(citizensToBeAdded.get(i));
				}
				else if(citizensToBeAdded.get(i).getString("state").equals("quiet")) {
					quietCitizens.add(citizensToBeAdded.get(i));
				}
			}
			
			
			//********************************************************************************

			// writing the each row in the csv file
			writer.append(tick + "");
			writer.append(',');
			writer.append(quietCitizens.size() + "");
			writer.append(',');
			writer.append(activeCitizens.size() + "");
			writer.append(',');
			writer.append(jailedCitizens.size() + "");
			writer.append('\n');
			
			// flushing each row
			writer.flush();
			
			tick++;
			
			System.out.println();
	
			//-------------------------------- UPDATING IS DONE -----------------------------------	
			
		}
						
	}
	
}
