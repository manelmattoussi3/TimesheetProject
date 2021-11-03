package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Contrat;

public interface IContratService {
	public List<Contrat> getAllContrat();
	public Contrat getContrat(int id);
	public Contrat ajouterContrat(Contrat contrat);
	public void affecterContratAEmploye(int contratId, int employeId);
	
	public Contrat updateContrat(Contrat contrat);
	
	public Contrat findByTypeContrat(String typeContrat);
	 public void deleteContratbyid(int idc);
}
