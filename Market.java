package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Market extends EconomicBuilding {

	public Market() {
		super(1500, 700);
	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException{
		super.upgrade();
		if(this.getLevel()==1){
		this.setLevel(2);
		this.setUpgradeCost(1000);
		}else{
			if(this.getLevel()==2)
				this.setLevel(3);
			else
				throw new MaxLevelException();
		}
		this.setCoolDown(true);
	}
	
	public int harvest(){
		int gold=0;
		switch(this.getLevel()-1){
		case 0: gold=1000; break;
		case 1: gold=1500; break;
		case 2: gold=2000;
		default: break;
		}
		return gold;
	}
}
