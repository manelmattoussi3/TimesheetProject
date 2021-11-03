package tn.esprit.spring.entities;

import javax.persistence.Entity;

@Entity
public class MissionExterne extends Mission {
	
	private static final long serialVersionUID = -3046278688391172322L;
	
	
	private String emailFacturation;
	
	
	private float tauxJournalierMoyen;

	
	public MissionExterne() {
		super();
	}

	
	
	

}
