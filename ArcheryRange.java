package buildings;

import java.io.BufferedReader;
import java.io.FileReader;

import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Unit;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;

public class ArcheryRange extends MilitaryBuilding {

	public ArcheryRange() {
		super(1500, 800, 400);

	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException{
		super.upgrade();
		if(this.getLevel()==1){
			this.setLevel(2);
			this.setUpgradeCost(700);
			this.setRecruitmentCost(450);
		}else{
			if(this.getLevel()==2){
				this.setLevel(3);
				this.setRecruitmentCost(500);
			}else
				throw new MaxLevelException();
		}
		this.setCoolDown(true);
	}
	
	public Unit recruit() throws BuildingInCoolDownException,
	MaxRecruitedException{
		int unitLevel=this.getLevel();
		Archer ar;
		if (unitLevel == 1)
			ar = (new Archer(1, 60, 0.4, 0.5, 0.6));

		else if (unitLevel == 2)
			ar = (new Archer(2, 60, 0.4, 0.5, 0.6));
		else
			ar = (new Archer(3, 70, 0.5, 0.6, 0.7));
		
		if(this.getCurrentRecruit()!=3){
			this.setCurrentRecruit(this.getCurrentRecruit()+1);
		}else{
			throw new MaxRecruitedException();
		}
		return ar;
	}
}
