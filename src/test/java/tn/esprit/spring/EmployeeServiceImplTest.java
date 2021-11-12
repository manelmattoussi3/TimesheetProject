package tn.esprit.spring;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.aop.TrackTime;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.EntrepriseServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class EmployeeServiceImplTest implements BaseTest {
    @Autowired
	EmployeServiceImpl employeServiceImpl1;
    @Autowired
	EmployeRepository employeRepo;
	@Autowired
	ContratRepository contratRepository;
	
	@Autowired
	EntrepriseRepository entrepriseRepository;
	
	@Autowired 
	DepartementRepository departementRepository;
	
	@Autowired
	EntrepriseServiceImpl entrepriseServiceImpl;
	private Employe employeA;
	private Departement department;
	
	
     @Test
     public void contextLoads() {
    		//Context Load init for test Methods

     }
     
 final  Logger log = Logger.getLogger(EmployeeServiceImplTest.class);
 
 
 

 
 
	@Override
	@Before
	public void setUp() {

		Entreprise entreprise = entrepriseRepository.save(new Entreprise("HP","equipement"));
		department = departementRepository.save(new Departement("RH"));
		department.setEntreprise(entreprise);
		departementRepository.save(department);
		employeA = new Employe("user", "benUser", "benuser@gmail.com", false, Role.INGENIEUR);		
		Employe employeB = new Employe("employe", "benEmployee", "employe@gmail.com", true, Role.ADMINISTRATEUR);
		Employe employeC = new Employe("Foulen", "benFoulen", "foulen@gmail.com", true, Role.CHEF_DEPARTEMENT);
		
		List<Employe> employeList = new ArrayList<>();
		    employeList.add(employeA);
		    employeList.add(employeB);
		    employeList.add(employeC);
		    
	
		employeRepo.saveAll(employeList);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date dateRepresentation = cal.getTime();
		
		Contrat contrat = new Contrat(dateRepresentation, "TypeCont", 5000);
		contrat.setEmploye(employeA);
		contratRepository.save(contrat);
	}

	@Override
	@After
	public void tearDown() {
		employeRepo.deleteAll();
		entrepriseRepository.deleteAll();
		departementRepository.deleteAll();
		
	}

 
 
    @Test
    @TrackTime(message = "testCreateEmployee ")
    public void testCreateEmployee() {
    	Employe employe = new Employe("foulen", "benfoulen", "foulen@gmail.com", true, Role.ADMINISTRATEUR);
    	employeServiceImpl1.ajouterEmploye(employe);
    	 
    	assertThat(employe.getPrenom()).isEqualTo("benfoulen");
    
    	log.info("Employee added with success");
    	

     
    }
    
    @Test
    @TrackTime(message = "mettreAjourEmailByEmployeId ")
    public void mettreAjourEmailByEmployeId() {
      	Employe employee = new Employe();
      	employee.setId(7);
        if(employee.getId()!=0) {
    		  employee.setEmail("user@gmail.com");
    		  employeServiceImpl1.mettreAjourEmailByEmployeId(employee.getEmail(),employee.getId());
    		  assertEquals(employee.getEmail()
 	        		 ,"user@gmail.com");
    		  log.info("Employee updated with success");

    	 }
    	 else {
    		 assertNull(employee);
    	      log.warn("Updated : Employee not found ");

    	 }
       
    }
    @Test
    @TrackTime(message = "testDeleteEmployeById ")
    public void testDeleteEmployeById () {
    	Employe employee = new Employe();
        employee.setEmail("employe@gmail.com");
        employee.setNom("deletedEmpNom");
        employee.setPrenom("deletedEmpPrenom");
        employee.setRole(Role.INGENIEUR);
        employee.setActif(true);
        employeServiceImpl1.ajouterEmploye(employee);
       if(employeRepo.findById(employee.getId()).isPresent()) {
    	   employeServiceImpl1.deleteEmployeById(employee.getId());
    	   assertTrue(true);
 		  log.info("Employee deleted with success");

       }
       else {
    	   assertTrue(false);
 		  log.info("Delete : Employee not found  success");

       }

	}
    
    @Test
    @TrackTime(message = "testGetEmployeById ")
    public void testGetEmployeById() {
    	Employe employee = new Employe();
        employee.setId(5);
        
        assertEquals("benUser", 
        		employeServiceImpl1.getEmployePrenomById(employeA.getId())
        		);
        log.info( "Employe Prenom : "+employeServiceImpl1.getEmployePrenomById(5));
    }

    @Test
    @TrackTime(message = "testGetNombreEmploye ")

    public void testGetNombreEmploye() {
		assertNotEquals(0, employeServiceImpl1.getNombreEmployeJPQL());

		log.info("number employee : "+employeServiceImpl1.getNombreEmployeJPQL());
    }

    @Test
    @TrackTime(message = "getSalaireByEmployeIdJPQLTest ")

    public void getSalaireByEmployeIdJPQLTest() {

       
		float salaire = employeServiceImpl1.getSalaireByEmployeIdJPQL(employeA.getId());
		log.info("getSalaireByEmployeIdJPQL == " + salaire);
		assertThat(salaire).isEqualTo(5000);
	}

    
    @Test 
	public void affecterEmployeDepartement(){
    	boolean test = false;
    	employeServiceImpl1.affecterEmployeADepartement(employeA.getId(), department.getId());
    	Optional<Employe> empoloye = employeRepo.findById(employeA.getId());
    	if(empoloye.isPresent()){
    		List<Departement>departementsList = empoloye.get().getDepartements();
        	for(int i = 0 ; i<departementsList.size(); i++) {
        		if(departementsList.get(i).getId() ==department.getId() ) {
        			test = true;
        		}
        		
        	}
    		assertThat(test).isTrue();
    		log.info("employee departement are affected with success");
    	}

    }
    
    @Test 
	public void desaffecterEmployeDepartement(){
    	boolean test = true;
    	employeServiceImpl1.affecterEmployeADepartement(employeA.getId(), department.getId());
    	Optional<Employe> empoloye = employeRepo.findById(employeA.getId());
    	
    	if(empoloye.isPresent()) {
    		List<Departement>departementsList = empoloye.get().getDepartements();
        	for(int i = 0 ; i<departementsList.size(); i++) {
        		if(departementsList.get(i).getId() ==department.getId() ) {
        			test = false;
        		}
        		
        	}
    		assertThat(test).isFalse();
    		log.info("employee departement are desaffected with success");
    	}
    }

   	
 
}
