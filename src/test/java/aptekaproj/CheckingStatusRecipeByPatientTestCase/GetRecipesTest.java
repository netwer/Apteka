package aptekaproj.CheckingStatusRecipeByPatientTestCase;

import aptekaproj.AptekaApplication;
import aptekaproj.models.Recipe;
import aptekaproj.services.RecipeService;
import aptekaproj.viewModels.PatientRecipeViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by org.apteka on 27.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetRecipesTest {
    @Autowired
    private RecipeService recipeService;

    private Integer patientId;

    @Before
    public void setUp() throws Exception {

        patientId = 14;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetRecipesForPatient() throws Exception {
        List<PatientRecipeViewModel> patientRecipeViewModels = recipeService.getRecipesForPatient(patientId);

        assertNotNull(patientRecipeViewModels);
        for(PatientRecipeViewModel patientRecipeViewModel : patientRecipeViewModels)
            assertEquals(patientRecipeViewModel.patientId,patientId);
    }
}
