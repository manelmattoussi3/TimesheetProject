package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public int ajouterEntreprise(Entreprise entreprise) {
		entrepriseRepoistory.save(entreprise);
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
			
		Optional<Entreprise> entreprise = entrepriseRepoistory.findById(entrepriseId);
		Optional<Departement> departement = deptRepoistory.findById(depId);
		if((entreprise.isPresent())&&(departement.isPresent()))
		{
			departement.get().setEntreprise(entreprise.get());
			deptRepoistory.save(departement.get());
		}
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		List<String> depNames = new ArrayList<>();
		Optional<Entreprise> entreprise = entrepriseRepoistory.findById(entrepriseId);
		if(entreprise.isPresent())
		{
			for(Departement dep : entreprise.get().getDepartements()){
				depNames.add(dep.getName());
			}
		}
		
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		Optional<Entreprise> entreprise = entrepriseRepoistory.findById(entrepriseId);
		if (entreprise.isPresent())
		{
			entrepriseRepoistory.delete(entreprise.get());
		}
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		Optional<Departement> departement = deptRepoistory.findById(depId);
		if (departement.isPresent())
		{
			if(departement.get().getEntreprise()!=null)
			{
			Entreprise e=departement.get().getEntreprise();
			e.removeDepartement(departement.get());
			}
			deptRepoistory.delete(departement.get());
		}
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		Entreprise e=null;
		Optional<Entreprise> entreprise = entrepriseRepoistory.findById(entrepriseId);
		if(entreprise.isPresent())
		{
			e=entreprise.get();
		}
		
		return e;
	}

}
