package engine;

import java.util.ArrayList;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import units.Army;
import units.Status;
import units.Unit;

public class Player {
	private String name;
	private ArrayList<City> controlledCities;
	private ArrayList<Army> controlledArmies;
	private double treasury;
	private double food;

	public Player(String name) {
		this.name = name;
		this.controlledCities = new ArrayList<City>();
		this.controlledArmies = new ArrayList<Army>();
	}

	public double getTreasury() {
		return treasury;
	}

	public void setTreasury(double treasury) {
		this.treasury = treasury;
	}

	public double getFood() {
		return food;
	}

	public void setFood(double food) {
		this.food = food;
	}

	public String getName() {
		return name;
	}

	public ArrayList<City> getControlledCities() {
		return controlledCities;
	}

	public ArrayList<Army> getControlledArmies() {
		return controlledArmies;
	}

	public void recruitUnit(String type, String cityName)
			throws BuildingInCoolDownException, MaxRecruitedException,
			NotEnoughGoldException {
		City c = null;
		MilitaryBuilding b = null;
		for (int i = 0; i < getControlledCities().size(); i++) {
			if (getControlledCities().get(i).getName().equals(cityName))
				c = getControlledCities().get(i);
		}
		for (int i = 0; i < c.getMilitaryBuildings().size(); i++) {
			b = c.getMilitaryBuildings().get(i);
			if (type.equals("Archer")) {
				// if(c.getMilitaryBuildings().get(i).isCoolDown())
				// throw new BuildingInCoolDownException();
				// else{
				if (treasury >= b.getRecruitmentCost()) {
					treasury -= b.getRecruitmentCost();
				} else {
					throw new NotEnoughGoldException();
				}
				if (b instanceof ArcheryRange) {
					c.getDefendingArmy().getUnits().add(b.recruit());
				}
				// }
			} else if (type.equals("Infantry")) {
				// if(c.getMilitaryBuildings().get(i).isCoolDown())
				// throw new BuildingInCoolDownException();
				// else{
				if (treasury >= b.getRecruitmentCost()) {
					treasury -= b.getRecruitmentCost();
				} else {
					throw new NotEnoughGoldException();
				}
				if (b instanceof Barracks) {
					c.getDefendingArmy().getUnits().add(b.recruit());
				}
				// }
			} else if (type.equals("Cavalry")) {
				// if(c.getMilitaryBuildings().get(i).isCoolDown())
				// throw new BuildingInCoolDownException();
				// else{
				if (treasury >= b.getRecruitmentCost()) {
					treasury -= b.getRecruitmentCost();
				} else {
					throw new NotEnoughGoldException();
				}
				if (b instanceof Stable) {
					c.getDefendingArmy().getUnits().add(b.recruit());
					if (treasury >= b.getRecruitmentCost()) {
						treasury -= b.getRecruitmentCost();
					} else {
						throw new NotEnoughGoldException();
					}
				}
				// }
			}
		}
	}

	public void build(String type, String cityName)
			throws NotEnoughGoldException {
		for (int i = 0; i < controlledCities.size(); i++) {
			if (controlledCities.get(i).getName().equals(cityName)) {
				if (type.equals("Farm")) {
					int c = 0;
					for (int j = 0; j < controlledCities.get(i)
							.getEconomicalBuildings().size(); j++) {
						if (controlledCities.get(i).getEconomicalBuildings()
								.get(j) instanceof Farm)
							c++;
						if (c == 0) {
							Farm b = new Farm();
							if (treasury < b.getCost())
								throw new NotEnoughGoldException();
							else {
								controlledCities.get(i)
										.getEconomicalBuildings().add(b);
								treasury -= b.getCost();
								b.setCoolDown(true);
							}
						}
					}
				} else if (type.equals("Market")) {
					int c = 0;
					for (int j = 0; j < controlledCities.get(i)
							.getEconomicalBuildings().size(); j++) {
						if (controlledCities.get(i).getEconomicalBuildings()
								.get(j) instanceof Market)
							c++;
						if (c == 0) {
							Market b = new Market();
							if (treasury < b.getCost())
								throw new NotEnoughGoldException();
							else {
								controlledCities.get(i)
										.getEconomicalBuildings().add(b);
								treasury -= b.getCost();
								b.setCoolDown(true);
							}
						}
					}
				}
			} else if (type.equals("ArcheryRange")) {
				int c = 0;
				for (int j = 0; j < controlledCities.get(i)
						.getMilitaryBuildings().size(); j++) {
					if (controlledCities.get(i).getMilitaryBuildings().get(j) instanceof ArcheryRange)
						c++;
					if (c == 0) {
						ArcheryRange b = new ArcheryRange();
						if (treasury < b.getCost())
							throw new NotEnoughGoldException();
						else {
							controlledCities.get(i).getMilitaryBuildings()
									.add(b);
							treasury -= b.getCost();
							b.setCoolDown(true);
						}
					}
				}
			} else if (type.equals("Barracks")) {
				int c = 0;
				for (int j = 0; j < controlledCities.get(i)
						.getMilitaryBuildings().size(); j++) {
					if (controlledCities.get(i).getMilitaryBuildings().get(j) instanceof Barracks)
						c++;
					if (c == 0) {
						Barracks b = new Barracks();
						if (treasury < b.getCost())
							throw new NotEnoughGoldException();
						else {
							controlledCities.get(i).getMilitaryBuildings()
									.add(b);
							treasury -= b.getCost();
							b.setCoolDown(true);
						}
					}
				}
			} else {
				int c = 0;
				for (int j = 0; j < controlledCities.get(i)
						.getMilitaryBuildings().size(); j++) {
					if (controlledCities.get(i).getMilitaryBuildings().get(j) instanceof Stable)
						c++;
					if (c == 0) {
						Stable b = new Stable();
						if (treasury < b.getCost())
							throw new NotEnoughGoldException();
						else {
							controlledCities.get(i).getMilitaryBuildings()
									.add(b);
							treasury -= b.getCost();
							b.setCoolDown(true);
						}
					}
				}
			}
		}
	}

	public void upgradeBuilding(Building b) throws NotEnoughGoldException,
			BuildingInCoolDownException, MaxLevelException {
		if (treasury >= b.getUpgradeCost()) {
			b.upgrade();
		} else
			throw new NotEnoughGoldException();
	}
	
	public void initiateArmy(City city,Unit unit){
		Army a =new Army(city.getName());
		a.getUnits().add(unit);
		city.getDefendingArmy().getUnits().remove(unit);
		unit.setParentArmy(a);
		this.controlledArmies.add(a);
	}
	
	public void laySiege(Army army,City city) throws TargetNotReachedException,
	FriendlyCityException{
		if(controlledCities.contains(city))
			throw new FriendlyCityException();
		else{
			if(army.getDistancetoTarget()==-1)
				throw new TargetNotReachedException();
			else{
				army.setCurrentStatus(Status.BESIEGING);
				city.setTurnsUnderSiege(city.getTurnsUnderSiege()+1);
				city.setUnderSiege(true);
			}
		}
	}
}
