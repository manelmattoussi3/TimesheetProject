package tn.esprit.spring;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.services.ITimesheetService;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMission {

	private static final Logger L = LogManager.getLogger(TestMission.class);

    @Autowired 
    ITimesheetService   timesheetService ; 
		

    @Autowired
    MissionRepository missionReposit ;


    private Mission mission;


		
       @Test
       public void ajouterMissionTest() {
			
	      assertThat(timesheetService.ajouterMission(new Mission ("mission de devloppement","Departement module rh ") )).isGreaterThan(0);
			
       }

	 
     @Test
     public void TestaffecterMissionADepartement() {

		
	      timesheetService.affecterMissionADepartement(2, 1);

	       assertThat(missionReposit.findById(2).get().getDepartement().getId()).isEqualTo(1);
      }
		
		
      //  @Test
        //public void TestfindAllMissionByEmployeJPQL() {

	      //  assertThat(timesheetService.findAllMissionByEmployeJPQL(1).size()).isGreaterThan(0);

        //}
		

        @Test
        public void testDeleteMissionById() {
			
		
			
            this.mission = new Mission() ; 
			   
		    this.mission.setName("mission de test delete ");
		    this.mission.setDescription("description mission delete de test");
		    int missionId = timesheetService.ajouterMission(this.mission);
		    timesheetService.deleteMissionById(missionId);
			   
		    L.info("Mission supprimee ");
		    Optional <Mission> mi =  missionReposit.findById(missionId); 
		    Assert.assertFalse(mi.isPresent());   
		
			
			
        }


          @Test
          public void testGetMissionById () {
	
	        this.mission = new Mission() ;
	        this.mission.setName(" name mission get");
	        this.mission.setDescription("description mission get");
	        int missId =  timesheetService.ajouterMission(this.mission);
	        timesheetService.getMissionById(missId);
	        assertThat(this.mission).isNotNull();
	        
	        L.info(" get mission"+  missId + " successfuly get by id  " );
	        
           }
          
         
          
           @Test
           public void testMettreAjourDescriptionByMissionId() {
        	   
        	   try {
        		   String descriptionModif = " modification de description ";
        		   timesheetService.mettreAjourDescriptionByMissionId(descriptionModif, 2  );
        		   Mission m = timesheetService.getMissionById(2);
        		   
        		   assertThat(m.getDescription()).isEqualTo(descriptionModif);
        		   
        		   
        	   }catch (Exception m) {
        		   L.error(" Erreur de mettre a jour la description   " +m );
        	   }
        	   
           }

	

	
	
}
