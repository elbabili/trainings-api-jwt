package fr.fms.api.trainings;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//Les annotations ci-dessous chargent le contexte spring permettant de réaliser des requetes sur un controller
@SpringBootTest //Injecte le service nécessaire ici, plus besoin de l'annotation MockBean
@AutoConfigureMockMvc
class ApiTrainingApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetTrainingsAndTestName() throws Exception {
        mockMvc.perform(get("/trainings"))
                .andExpect(status().isOk())     //vérif si status 200 ok
                .andExpect(jsonPath("$[0].name",is("Java")));
                //vérifie si la réponse contient une valeur précise :
                    //$ pointe sur la racine de la structure JSON.
                    //[0] indique qu’on veut vérifier le premier élément de la liste.
                    //name désigne l’attribut qu’on veut consulter.
    }
}
