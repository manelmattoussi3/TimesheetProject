package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;

@Service
public class ContratServiceImpl implements IContratService{
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	EmployeRepository employeRepository;
	
	@Override
	public Contrat ajouterContrat(Contrat contrat) {
		
		return contratRepoistory.save(contrat);
		
	}
	@Override
	public void affecterContratAEmploye(int contratId, int employeId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		
	}

	@Override
	public void deleteContratbyid(int idc) {
		Contrat c = contratRepoistory.findById(idc).get();
		contratRepoistory.delete(c);

	}
	
	@Override
	public List<Contrat> getAllContrat() {

		
		return (List<Contrat>) contratRepoistory.findAll();
		
}
	@Override
	public Contrat updateContrat(Contrat contrat) {
		return contratRepoistory.save(contrat);
		
	}
	@Override
	public Contrat getContrat(int id) {
		Contrat c =contratRepoistory.findById(id).get();
		return c;
	}
	@Override
	public Contrat findByTypeContrat(String typeContrat) {
		
		return contratRepoistory.findByTypeContrat(typeContrat);
	}
}
