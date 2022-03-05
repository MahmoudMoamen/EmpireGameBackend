package buildings;

import units.Archer;
import units.Infantry;
import units.Unit;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;

public class Barracks extends MilitaryBuilding {

	public Barracks() {
		super(2000, 1000, 500);

	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException{
		super.upgrade();
		if(this.getLevel()==1){
			this.setLevel(2);
			this.setUpgradeCost(1500);
			this.setRecruitmentCost(550);
		}else{
			if(this.getLevel()==2){
				this.setLevel(3);
				this.setRecruitmentCost(600);
			}else
				throw new MaxLevelException();
		}
		this.setCoolDown(true);
	}
	
	public Unit recruit() throws BuildingInCoolDownException,
	MaxRecruitedException{
		int unitLevel=this.getLevel();
		Infantry b;
			if (unitLevel == 1)
				b = (new Infantry(1, 50, 0.5, 0.6, 0.7));

			else if (unitLevel == 2)
				b = (new Infantry(2, 50, 0.5, 0.6, 0.7));
			else
				b = (new Infantry(3, 60, 0.6, 0.7, 0.8));
			
			if(this.getCurrentRecruit()!=3){
				this.setCurrentRecruit(this.getCurrentRecruit()+1);
			}else{
				throw new MaxRecruitedException();
			}
			return b;
	}
}
