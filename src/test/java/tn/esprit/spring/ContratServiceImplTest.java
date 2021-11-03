package tn.esprit.spring;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;

import tn.esprit.spring.services.IContratService;
import tn.esprit.spring.services.IEmployeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest

public class ContratServiceImplTest {
	@Autowired
	private IContratService service;
	@Autowired
	private IEmployeService serviceemp;

	@Autowired
	private ContratRepository repository;
	private static final Logger l = LogManager.getLogger(ContratServiceImplTest.class);
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@BeforeClass
	
	public static void before() {
		l.info("the tests are starting");
	}
	@Test
	@Rollback(false)
	@Order(1)
	public void testAjouterContrat() throws ParseException{

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2021-10-19");
		Contrat contrat =new Contrat(date, "CDD",2700);

		Employe e = new Employe ("manel","mattoussi","manel@esprit.tn",true,date,Role.INGENIEUR);
		Contrat c=	service.ajouterContrat(contrat);
		Employe emp=serviceemp.ajouterEmployee(e);
		service.affecterContratAEmploye(c.getReference(),emp.getId());


		if(e.getRole()==Role.ADMINISTRATEUR ||e.getRole()==Role.CHEF_DEPARTEMENT ||e.getRole()==Role.INGENIEUR || e.getRole()==Role.TECHNICIEN) {
			assertThat(c.getSalaire()).isGreaterThan(1000);
			l.info("le salaire est:"+c.getSalaire());
		}
		
		

		assertThat(c.getReference()).isGreaterThan(0);
		assertEquals(c.getDateDebut(), e.getDateembauche());


	}
	@Test
	@Order(2)
	public void testFindByTypeContratExist(){
		String typeContrat ="CDii";
		Contrat c =service.findByTypeContrat(typeContrat);

		assertThat(c.getTypeContrat()).isEqualTo(typeContrat);


		l.info("liste des contrats:"+c);
	}
	@Test
	@Order(3)
	public void testFindByTypeContratNotExist(){
		String typeContrat ="CDDii";
		Contrat c =service.findByTypeContrat(typeContrat);

		assertNull(c);


		l.info("liste des contrats:"+c);
	}
	@Test
	@Order(4)
	public void testGetContrat() {
		Contrat c = service.getContrat(4);


		if(c.getReference()== 4) {
			assertNotNull( service.getContrat(4));

			l.info("le contart est:"+c.toString());
		}

		else 
		{assertNull(c);
		l.error("contrat introuvable ");
		}

	}
	//@Before
	@Test
	@Order(5)
	public void testGetAllContrat()  {		
		try {
			List<Contrat>contrats=service.getAllContrat();
			assertThat(contrats).size().isGreaterThan(0);
			for(Contrat c:contrats) {
				l.info("Le contrat est: "+ c);}
			l.info("La taille de contrats  ,"+contrats.size());}
		catch (IllegalArgumentException ex){
			assertEquals("the table is empty", ex.getMessage());
		}
	}
	@Test
	@Rollback(false)
	@Order(6)
	public void testUpdateContrat() throws ParseException{

		Integer id =9;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse("2021-10-19");
			String typeContrat ="CDI";
			Contrat contrat =new Contrat(id,date, "CDI",200);
			service.updateContrat(contrat);
			//contrat.setTypeContrat(null);

			Contrat updatedContrat = service.getContrat(id);

			assertEquals("the type of contrat updated successfully","CDI",updatedContrat.getTypeContrat());
			l.info("Contrat updated successfully"+updatedContrat.getTypeContrat());
		}
		catch (IllegalArgumentException ex){
			assertEquals("id can not be null", ex.getMessage());
		}


	}

	@Test
	@Rollback(false)
	@Order(7)
	public void testDeleteContratbyid() {
		
		Integer id=98;
		boolean notExistAfterDelete =repository.findById(id).isPresent();
		boolean isExistBeforeDelete =repository.findById(id).isPresent();
		if(isExistBeforeDelete) {
			service.deleteContratbyid(id);
			assertTrue(isExistBeforeDelete);
			l.info("le contrat est supprimé ");}

		
		else {
			assertFalse(notExistAfterDelete);
			l.info("le contrat est introuvable ");}
	}


	@AfterClass
	
	public static void after() {
		l.info("the tests are finished");
	}

}
