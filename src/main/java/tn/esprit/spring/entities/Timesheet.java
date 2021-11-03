package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Timesheet implements Serializable{

	

	@EmbeddedId
	private TimesheetPK timesheetPK;
	
	
	@ManyToOne
    @JoinColumn(name = "idMission", referencedColumnName = "id", insertable=false, updatable=false)
	private Mission mission;
	
	
	
	@ManyToOne
    @JoinColumn(name = "idEmploye", referencedColumnName = "id", insertable=false, updatable=false)
	private Employe employe;
	
	
	private boolean isValide;
	


	
	
}
