package buildings;

import units.Cavalry;
import units.Infantry;
import units.Unit;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;

public class Stable extends MilitaryBuilding {

	public Stable() {
		super(2500, 1500, 600);

	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException{
		super.upgrade();
		if(this.getLevel()==1){
			this.setLevel(2);
			this.setUpgradeCost(2000);
			this.setRecruitmentCost(650);
		}else{
			if(this.getLevel()==2){
				this.setLevel(3);
				this.setRecruitmentCost(700);
			}else
				throw new MaxLevelException();
		}
		this.setCoolDown(true);
	}
	
	public Unit recruit() throws BuildingInCoolDownException,
	MaxRecruitedException{
	if(isCoolDown()){
		throw new BuildingInCoolDownException();
	}else{
	int unitLevel=this.getLevel();
	Cavalry c;
	if (unitLevel == 1)
		c = (new Cavalry(1, 40, 0.6, 0.7, 0.75));

	else if (unitLevel == 2)
		c = (new Cavalry(2, 40, 0.6, 0.7, 0.75));
	else
		c = (new Cavalry(3, 60, 0.7, 0.8, 0.9));
	
	if(this.getCurrentRecruit()!=3){
		this.setCurrentRecruit(this.getCurrentRecruit()+1);
	}else{
		throw new MaxRecruitedException();
	}
	return c;
	}
  }
}

