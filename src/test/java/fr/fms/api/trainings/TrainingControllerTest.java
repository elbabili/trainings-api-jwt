package fr.fms.api.trainings;

import fr.fms.api.trainings.service.TrainingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import fr.fms.api.trainings.service.TrainingServiceImpl;
import fr.fms.api.trainings.web.TrainingController;

@SpringBootTest
@AutoConfigureMockMvc
     //déclenche le test sur le controller cible
public class TrainingControllerTest {
    @Autowired
    private MockMvc mockMvc;    //va servir à appeler la méthode perform

    @MockBean                   //indispensable pour appeler le service
    private TrainingServiceImpl trainingService;    //la méthode get("/trainings") va appeler :
                                                 //allTrainings(){ return  trainingService.getTrainings(); }
    @Test
    public void testGetTrainings() throws Exception {
        mockMvc.perform(get("/trainings"))  //requete en get sur le controller avec cette url
                .andExpect(status().isOk());              //nous attendons une réponse http 200
    }
}
