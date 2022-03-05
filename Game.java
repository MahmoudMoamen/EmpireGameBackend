package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import buildings.Farm;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Unit;

public class Game {
	private Player player;
	private ArrayList<City> availableCities;
	private ArrayList<Distance> distances;
	private final int maxTurnCount = 30;
	private int currentTurnCount;

	public Game(String playerName, String playerCity) throws IOException {

		player = new Player(playerName);
		availableCities = new ArrayList<City>();
		distances = new ArrayList<Distance>();
		currentTurnCount = 1;
		loadCitiesAndDistances();
		for (City c : availableCities) {
			if (c.getName().equals(playerCity))

				player.getControlledCities().add(c);

			else
				loadArmy(c.getName(), c.getName().toLowerCase() + "_army.csv");

		}
	}

	private void loadCitiesAndDistances() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("distances.csv"));
		String currentLine = br.readLine();
		ArrayList<String> names = new ArrayList<String>();

		while (currentLine != null) {

			String[] content = currentLine.split(",");
			if (!names.contains(content[0])) {
				availableCities.add(new City(content[0]));
				names.add(content[0]);
			} else if (!names.contains(content[1])) {
				availableCities.add(new City(content[1]));
				names.add(content[1]);
			}
			distances.add(new Distance(content[0], content[1], Integer
					.parseInt(content[2])));
			currentLine = br.readLine();

		}
		br.close();
	}

	public void loadArmy(String cityName, String path) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String currentLine = br.readLine();
		Army resultArmy = new Army(cityName);
		while (currentLine != null) {
			String[] content = currentLine.split(",");
			String unitType = content[0].toLowerCase();
			int unitLevel = Integer.parseInt(content[1]);
			Unit u = null;
			if (unitType.equals("archer")) {

				if (unitLevel == 1)
					u = (new Archer(1, 60, 0.4, 0.5, 0.6));

				else if (unitLevel == 2)
					u = (new Archer(2, 60, 0.4, 0.5, 0.6));
				else
					u = (new Archer(3, 70, 0.5, 0.6, 0.7));
			} else if (unitType.equals("infantry")) {
				if (unitLevel == 1)
					u = (new Infantry(1, 50, 0.5, 0.6, 0.7));

				else if (unitLevel == 2)
					u = (new Infantry(2, 50, 0.5, 0.6, 0.7));
				else
					u = (new Infantry(3, 60, 0.6, 0.7, 0.8));
			} else if (unitType.equals("cavalry")) {
				if (unitLevel == 1)
					u = (new Cavalry(1, 40, 0.6, 0.7, 0.75));

				else if (unitLevel == 2)
					u = (new Cavalry(2, 40, 0.6, 0.7, 0.75));
				else
					u = (new Cavalry(3, 60, 0.7, 0.8, 0.9));
			}
			resultArmy.getUnits().add(u);
			u.setParentArmy(resultArmy);
			currentLine = br.readLine();
		}
		br.close();
		for (City c : availableCities) {
			if (c.getName().toLowerCase().equals(cityName.toLowerCase()))
				c.setDefendingArmy(resultArmy);
		}
	}

	public ArrayList<City> getAvailableCities() {
		return availableCities;
	}

	public ArrayList<Distance> getDistances() {
		return distances;
	}

	public int getCurrentTurnCount() {
		return currentTurnCount;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getMaxTurnCount() {
		return maxTurnCount;
	}

	public void setCurrentTurnCount(int currentTurnCount) {
		this.currentTurnCount = currentTurnCount;
	}

	public void targetCity(Army army, String targetName) {
		if (army.getDistancetoTarget() <= 0) {
			if (army.getCurrentLocation() != targetName
					&& ((army.getCurrentLocation().equals("Cairo") && targetName
							.equals("Rome")) || (army.getCurrentLocation()
							.equals("Rome") && targetName.equals("Cairo")))) {
				army.setDistancetoTarget(6);
			} else if (army.getCurrentLocation() != targetName
					&& ((army.getCurrentLocation().equals("Cairo") && targetName
							.equals("Sparta")) || (army.getCurrentLocation()
							.equals("Sparta") && targetName.equals("Cairo")))) {
				army.setDistancetoTarget(5);
			} else {
				army.setDistancetoTarget(9);
			}
			army.setTarget(targetName);
		}
	}

	public void endTurn() {
		currentTurnCount++;
		for (int i = 0; i < player.getControlledCities().size(); i++) {
			for (int j = 0; j < player.getControlledCities().get(i)
					.getEconomicalBuildings().size(); j++) {
				player.getControlledCities().get(i).getEconomicalBuildings()
						.get(j).setCoolDown(false);
				if (player.getControlledCities().get(i)
						.getEconomicalBuildings().get(j) instanceof Farm) {
					player.setFood(player.getControlledCities().get(i)
							.getEconomicalBuildings().get(j).harvest()
							+ player.getFood());
				} else {
					player.setTreasury(player.getControlledCities().get(i)
							.getEconomicalBuildings().get(j).harvest()
							+ player.getTreasury());
				}
			}
			for (int j = 0; j < player.getControlledCities().get(i)
					.getMilitaryBuildings().size(); j++) {
				player.getControlledCities().get(i).getMilitaryBuildings()
						.get(j).setCoolDown(false);
				player.getControlledCities().get(i).getMilitaryBuildings()
						.get(j).setCurrentRecruit(0);
			}
		}
			for (int j = 0; j < availableCities.size(); j++) {
				if (availableCities.get(j).isUnderSiege()) {
					availableCities.get(j)
							.setTurnsUnderSiege(
									availableCities.get(j)
											.getTurnsUnderSiege()+1);
			for(int x=0;x<availableCities.get(j).getDefendingArmy().getUnits().size();x++)
					availableCities
							.get(j)
							.getDefendingArmy()
							.getUnits()
							.get(x)
							.setCurrentSoldierCount(
									(int) (availableCities.get(j)
											.getDefendingArmy().getUnits()
											.get(x).getCurrentSoldierCount() - (availableCities.get(j)
											.getDefendingArmy().getUnits()
											.get(x).getCurrentSoldierCount() * 0.1)));
				}
			}
		for (int j = 0; j < player.getControlledArmies().size(); j++) {
			player.setFood(player.getFood()
					- player.getControlledArmies().get(j).foodNeeded());
			if (!player.getControlledArmies().get(j).getTarget().equals("")) {
				player.getControlledArmies()
						.get(j)
						.setDistancetoTarget(
								player.getControlledArmies().get(j)
										.getDistancetoTarget() - 1);
				if (player.getControlledArmies().get(j).getDistancetoTarget() == 0) {
					player.getControlledArmies()
							.get(j)
							.setCurrentLocation(
									player.getControlledArmies().get(j)
											.getTarget());
					player.getControlledArmies().get(j).setTarget("");
				}
			}
			if (player.getFood() == 0.0) {
				for (int y = 0; y < player.getControlledArmies().get(j)
						.getUnits().size(); y++) {
					player.getControlledArmies()
							.get(j)
							.getUnits()
							.get(y)
							.setCurrentSoldierCount(
									(int) (player.getControlledArmies().get(j)
											.getUnits().get(y)
											.getCurrentSoldierCount() - (player
											.getControlledArmies().get(j)
											.getUnits().get(y)
											.getCurrentSoldierCount() * 0.1)));
				}
			}
		}
	}
	
	
}
