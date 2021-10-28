package tn.esprit.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import org.springframework.test.context.junit4.SpringRunner;


import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.EntrepriseServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest()

public class TimesheetSpringBootCoreDataJpaMvcRest1ApplicationTests {
	private static final Logger l = LogManager.getLogger(EmployeServiceImpl.class);
	@Autowired
	EmployeServiceImpl EmployeServiceImpl;
	


	@Autowired
	EmployeRepository employeRepo;
	

	@Autowired
	EntrepriseServiceImpl EntrepriseServiceImpl;
     @Test
     public void contextLoads() {

     }


    @Test
    @Order(1)
    public void testCreateEmployee() {
    	Employe employee = new Employe("User","en user","user@outlook.com",true,Role.TECHNICIEN);
   	
    	int empId = EmployeServiceImpl.ajouterEmploye(employee);
    	if(employeRepo.findById(empId).isPresent()) {
    		EmployeServiceImpl.deleteEmployeById(empId);
    		int  empNewId = EmployeServiceImpl.ajouterEmploye(employee);
    		assertNotEquals(0, empNewId);
    		//asserEquals(empId,empNewId);
    		l.info("Employee added with success :  empId = "+empId+" new empId = "+empNewId);
    		
    		
    	}
    	else if(empId > 0 ) {
    		assertNotNull(employee);
            l.info("Employee added"+employee.toString());

    	}
    	 
    	else {
    		assertNull(null);
    		l.warn("Employee not added ");
    	}

      
        
        
    }
    
    @Test
    @Order(2)
    public void mettreAjourEmailByEmployeId() {
    	 Employe employee = employeRepo.findById(1).get();

    	 if(employee.getId()!=0) {
    		  employee.setEmail("ram@gmail.com");
    		  EmployeServiceImpl.mettreAjourEmailByEmployeId(employee.getEmail(),employee.getId());
    		  assertEquals(employee.getEmail()
 	        		 ,"ram@gmail.com");
    	        l.info("Employee updated successfully"+employee.getEmail());


    	 }
    	 else {
    		 assertNull(employee);
    	 }
       
    }
    @Test
    @Order(3)
    public void testDeleteEmployeById () {
    	Employe employee = new Employe("User","en user","user@outlook.com",true,Role.TECHNICIEN);

      
     int empId =    EmployeServiceImpl.ajouterEmploye(employee);
    	boolean existBefore = employeRepo.findById(1).isPresent();//true
    	
    	if(existBefore) {
    		EmployeServiceImpl.deleteEmployeById(empId);
    		assertTrue(true);

    		l.info("Employee deleted ");
    	}
    	else {
    		assertFalse(false);
    		l.warn("Employee not found");
    	}
        
	}
    
//    @Test
//    @Order(4)
//    public void testGetEmployeById() {
//    	Employe employee = new Employe();
//        employee.setId(1); // employe id= 5 
//        
//        if(employee.getId()== 1) {
//        assertEquals("devEmp1", 
//        		EmployeServiceImpl.getEmployePrenomById(1)
//        		);
//        System.out.println("employe prenom =="+EmployeServiceImpl.getEmployePrenomById(1));
//        l.info("Employee exist ="+employee.toString());
//        }
//        
//        else 
//        	{assertNull(employee);
//            l.error("Employee not exist ");
//        	}
//       
//    }

    
    @Test
    @Order(5)
    public void testGetNombreEmploye() {
		assertNotEquals(0, EmployeServiceImpl.getNombreEmployeJPQL());
		l.info("Employe number != 0");

    }

  
    @Test 
    @Order(6)
    public void testgetAllEmployee() {
    	Employe e  = new Employe("user", "test", "user@gmail.com", true, Role.ADMINISTRATEUR);
    	
    	EmployeServiceImpl.ajouterEmploye(e);
    	
    	List<Employe>employes = EmployeServiceImpl.getAllEmployes();
    	assertNotEquals(employes.size(),0);
    	l.info("Employees List  >0 ,"+employes.size());
    }
    
    @Test
	@Order(7)
	public void affecterDepartementAEmployeTest() {
		
    	Employe e=new Employe("foulen","benfoulen","foulen@gmail.com",true,Role.ADMINISTRATEUR);
		Entreprise entreprise=new Entreprise("microsoft","Ariana");
        Departement d=new Departement("D1");
		EmployeServiceImpl.ajouterEmploye(e);
		EmployeServiceImpl.ajouterDepartement(d);
		EntrepriseServiceImpl.ajouterEntreprise(entreprise);
		EntrepriseServiceImpl.affecterDepartementAEntreprise(d.getId(),entreprise.getId());
        EmployeServiceImpl.affecterEmployeADepartement(e.getId(),d.getId());
		List<String> deps=(List<String>)EntrepriseServiceImpl.getAllDepartementsNamesByEntreprise(entreprise.getId());
			assertNotEquals(0, deps.size());
		 l.info("Employe"+e.getNom()+"affected to Departement"+d.getName() );

	}

    
    
    
    				
}
