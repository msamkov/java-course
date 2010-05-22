package net.azib.java.students.t092860.homework;

/**
 * List of events performed by an athlete.
 */
public enum Events {

	RACE_100M(Units.SECONDS),
	LONG_JUMP(Units.METERS),   
	SHOT_PUT(Units.METERS),     
	HIGH_JUMP(Units.METERS),   
	RACE_400M(Units.SECONDS),  
	HURDLES_110M(Units.SECONDS),     
	DISCUS_THROW(Units.METERS), 
	POLE_VAULT(Units.METERS),  
	JAVELIN_THROW(Units.METERS), 
	RACE_1500M(Units.SECONDS);
	
	public enum Units{
		SECONDS,
		METERS
	}
	
	private Events(Units units) {
		if(units == Units.METERS || units == Units.SECONDS)
			this.units = units;
		else
			this.units = Units.SECONDS;
    }

    public Units getUnits() { 
    	return units; 
    }
	
	private Units units;
}
