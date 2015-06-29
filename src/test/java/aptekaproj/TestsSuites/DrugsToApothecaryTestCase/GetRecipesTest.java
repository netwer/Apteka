package aptekaproj.TestsSuites.DrugsToApothecaryTestCase;

import aptekaproj.AptekaApplication;
import aptekaproj.models.PharmacyStaff;
import aptekaproj.models.Recipe;
import aptekaproj.services.PharmacyStaffService;
import aptekaproj.services.RecipeService;
import aptekaproj.viewModels.RecipeViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by org.apteka on 28.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetRecipesTest {

    @Autowired
    private RecipeService recipeService;

    private int pharmacyId;
    private int statusId;

    @Before
    public void setUp() throws Exception {
        pharmacyId = 3;
        statusId = 6;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetRecipes() throws Exception {
        List<RecipeViewModel> recipesForPharmacist = recipeService.getRecipesForPharmacyByStatus(pharmacyId, statusId);

        assertNotNull(recipesForPharmacist);
        for (RecipeViewModel recipe : recipesForPharmacist){
            Recipe recipe1 = recipeService.getRecipeById(recipe.recipeId);
            assertEquals(recipe1.getRecipeProgressStatusId(),statusId);
        }
    }
}
