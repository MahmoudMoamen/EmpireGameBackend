package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Farm extends EconomicBuilding {

	public Farm() {
		super(1000, 500);
	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException{
		super.upgrade();
		if(this.getLevel()==1){
			this.setLevel(2);
			this.setUpgradeCost(700);
		}else{
			if(this.getLevel()==2)
				this.setLevel(3);
			else
				throw new MaxLevelException();
		}
		this.setCoolDown(true);
	}
	
	public int harvest(){
		int food=0;
		switch(this.getLevel()){
			case 1: food=500; break;
			case 2: food=700; break;
			case 3: food=1000;
			default: break;
		}
		return food;
	}
}
