package tn.esprit.spring;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEntrepriseService;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceTest {
	
	@Autowired
	IEntrepriseService service;
	
	@Autowired
	EntrepriseRepository rep;
	
	@Autowired
	DepartementRepository drep;
	
	private static final Logger l = LogManager.getLogger(EntrepriseServiceTest.class);	
	
	@Test
	public void testAjouterEntreprise()
	{
		try {
			l.info("In testAjouterEntreprise():");
			Entreprise e=new Entreprise("testAjout", "testAjout");
			ArrayList <Entreprise> liste1 =(ArrayList<Entreprise>) rep.findAll();
			int size1=liste1.size();
			l.info("nombre d'entreprises avant l'ajout: " + size1);
			l.info("Je vais ajouter une entreprise.");
			int id=service.ajouterEntreprise(e);
			ArrayList <Entreprise> liste2 =(ArrayList<Entreprise>) rep.findAll();
			int size2=liste2.size();
			l.info("nombre d'entreprises apres l'ajout: " + size2);
			l.info("comparaison size avant et apres.");
			assertTrue(size2==size1+1);
			service.deleteEntrepriseById(id);
			l.info("je supprime l'entreprise.");
			l.info("Out testAjouterEntreprise() sans erreurs.");
		}catch (Exception e) { l.error("Erreur dans testAjouterEntreprise() : " + e); }
	}
	
	
	
	@Test
	public void testAjouterDepartement()
	{
		try {
			l.info("In testAjouterDepartement():");
			Departement d=new Departement("Ressources Humaines.");
			d.setEntreprise(null);
			l.info("Je vais creer un departement.");
			int id=service.ajouterDepartement(d);
			l.info("Je vais ajouter un departement.");
			l.info("Id du departement que je viens d'ajouter: "+id);
			l.info("je teste si l'id du departement est bien different de 0.");
			assertTrue(id!=0);
			l.info("je supprime le departement.");
			service.deleteDepartementById(id);
			l.info("Out testAjouterDepartement() sans erreurs.");
		}catch (Exception e) { l.error("Erreur dans testAjouterDepartement() : " + e); }
	}
	


	@Test
	public void testAffecterDepartement()
	{
		try {
			l.info("In testAjouterDepartement():");
			l.info("Je vais creer une entreprise.");
			Entreprise e=new Entreprise("testAjout", "testAjout");
			l.info("Je vais creer un departement.");
			Departement d=new Departement("Ressources Humaines");
			l.info("Je vais ajouter l'entreprise.");
			int identreprise=service.ajouterEntreprise(e);
			l.info("Je vais ajouter le departement.");
			int iddepartement=service.ajouterDepartement(d);
			l.info("Je vais affecter le departement a l'entreprise.");
			service.affecterDepartementAEntreprise(iddepartement, identreprise);
			l.info("Je vais reprendre le departement depuis la base de donnée.");
			Optional<Departement> d1=drep.findById(iddepartement);
			if(d1.isPresent())
			{
				Departement dep=d1.get();
				l.info("Je vais tester si l'entreprise_id du departement est égale a l'id de l'entreprise auquel je l'ai affecté.");
				assertTrue(dep.getEntreprise().getId()==identreprise);
				l.info("Je vais supprimer le departement.");
				service.deleteDepartementById(iddepartement);
				l.info("Je vais supprimer l'entreprise.");
				service.deleteEntrepriseById(identreprise);
				l.info("Out testAffecterDepartement() sans erreurs.");
			}
		}catch (Exception e) { l.error("Erreur dans testAffecterDepartement() : " + e); }
	}
	
	
	@Test
	public void testSupprimerEntreprise()
	{
		try{
			l.info("In testSupprimerEntreprise():");
			l.info("Je vais creer une entreprise.");
			Entreprise e=new Entreprise("testAjout", "testAjout");
			l.info("Je vais ajouter l'entreprise.");
			int identreprise=service.ajouterEntreprise(e);
			l.info("Je vais supprimer l'entreprise.");
			service.deleteEntrepriseById(identreprise);
			l.info("Je vais m'assurer que la methode getEntrepriseById() retourne null.");
			assertNull(service.getEntrepriseById(identreprise));
			l.info("Out testSupprimerEntreprise() sans erreurs.");
		}catch (Exception e) { l.error("Erreur dans testSupprimerEntreprise() : " + e); }
	}
	
	
	
	@Test
	public void testSupprimerDepartement()
	{
		try{
			l.info("In testSupprimerEntreprise():");
			l.info("Je vais creer un departement.");
			Departement d=new Departement("Ressources Humaines");
			d.setEntreprise(null);
			l.info("Je vais ajouter un departement.");
			int iddepartement=service.ajouterDepartement(d);
			l.info("Je vais supprimer le departement.");
			service.deleteDepartementById(iddepartement);
			l.info("Je vais m'assurer que la methode findDepartementById() retourne null.");
			assertNull(drep.findById(iddepartement).orElse(null));
		}catch (Exception e) { l.error("Erreur dans testSupprimerDepartement() : " + e); }
	}
}
