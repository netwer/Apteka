package aptekaproj.PharmacistSetRecipeStatusTestCase;

import aptekaproj.AptekaApplication;
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

import static org.junit.Assert.*;

/**
 * Created by org.apteka on 27.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetAllRecipesForPharmacistTest {

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
        pharmacyId = 0;
        statusId = 0;
    }

    @Test
    public void getAllRecipes() throws Exception {
        List<RecipeViewModel> recipeViewModels = recipeService.getRecipesForPharmacyByStatus(pharmacyId, statusId);

        assertNotNull(recipeViewModels);
        assertNotEquals(recipeViewModels.size(),0);
    }
}
