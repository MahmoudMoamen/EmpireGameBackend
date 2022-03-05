package units;

import exceptions.FriendlyFireException;

public class Archer extends Unit {

	public Archer(int level, int maxSoldierConunt, double idleUpkeep,
			double marchingUpkeep, double siegeUpkeep) {
		super(level, maxSoldierConunt, idleUpkeep, marchingUpkeep, siegeUpkeep);
	}

	public void attack(Unit target) throws FriendlyFireException {
        super.attack(target);
		double factor=0.0;
//		System.out.println(target.getClass().getName());
        if (target instanceof Archer){
        	switch(this.getLevel()){
        	case 1: factor=0.3;break;
        	case 2:factor=0.4;break;
        	case 3:factor=0.5;break;
        	default: break;
        	}
        }else if (target instanceof Infantry){
        	switch(this.getLevel()){
        	case 1: factor=0.2;break;
        	case 2:factor=0.3;break;
        	case 3:factor=0.4;break;
        	default: break;
        	}
        }else{
            	switch(this.getLevel()){
            	case 1: factor=0.1;break;
            	case 2:factor=0.1;break;
            	case 3:factor=0.2;break;
            	default: break;
            	}
        }
        double currentCnt=0.0;
        if(target.getCurrentSoldierCount()>0){
        	currentCnt = target.getCurrentSoldierCount() -(factor * this.getCurrentSoldierCount());
			target.setCurrentSoldierCount((int)currentCnt);
        }else if(target.getCurrentSoldierCount()==0)
        	target.getParentArmy().handleAttackedUnit(target);
	}
}
