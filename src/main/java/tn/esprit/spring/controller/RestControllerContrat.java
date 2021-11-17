//package tn.esprit.spring.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import tn.esprit.spring.entities.Contrat;
//import tn.esprit.spring.services.IContratService;
//
//@RestController
//public class RestControllerContrat {
//	@Autowired
//	IContratService contratserv;
//	//http://localhost:8082/SpringMVC/servlet/getallContrat
//	@GetMapping("/getallContrat")
//	public List<Contrat> getallContrat() {
//		return contratserv.getAllContrat();
//
//	}
//	//http://localhost:8082/SpringMVC/servlet/ajouterContrat
//	@PostMapping("/ajouterContrat")
//	
//	public Contrat ajouterContrat(@RequestBody Contrat contrat) {
//		
//		return contratserv.ajouterContrat(contrat);
//	}
//	// http://localhost:8082/SpringMVC/servlet/affecterContratAEmploye/6/1
//	@PutMapping(value = "/affecterContratAEmploye/{idcontrat}/{idemp}") 
//	public void affecterContratAEmploye(@PathVariable("idcontrat")int contratId, @PathVariable("idemp")int employeId)
//	{
//		contratserv.affecterContratAEmploye(contratId, employeId);
//	}
//
//	// URL : http://localhost:8082/SpringMVC/servlet/deleteContratById/2
//	@DeleteMapping("/deleteContratById/{idcontrat}") 
//
//	public void deleteContratById(@PathVariable("idcontrat")int contratId) {
//		contratserv.deleteContratbyid(contratId);
//	}
//
//
//
//}
