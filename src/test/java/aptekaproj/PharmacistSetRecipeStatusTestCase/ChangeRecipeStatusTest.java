package aptekaproj.PharmacistSetRecipeStatusTestCase;

import aptekaproj.AptekaApplication;
import aptekaproj.models.Recipe;
import aptekaproj.models.RecipeProgressStatus;
import aptekaproj.services.RecipeProgressStatusService;
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
public class ChangeRecipeStatusTest {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeProgressStatusService recipeProgressStatusService;

    private int recipeId;
    private String status;
    private boolean result;
    private RecipeViewModel recipeAfter;
    private RecipeViewModel recipeBefore;
    private RecipeProgressStatus recipeProgressStatusBefore;
    private RecipeProgressStatus recipeProgressStatusAfter;

    @Before
    public void setUp() throws Exception {
        recipeId = 14;
        status = "ГОТОВ";
        result = false;
        recipeBefore = new RecipeViewModel();
        recipeAfter = new RecipeViewModel();
        recipeProgressStatusBefore = new RecipeProgressStatus();
        recipeProgressStatusAfter = new RecipeProgressStatus();
    }

    @After
    public void tearDown() throws Exception {
        recipeId = 0;
        status = "";
        result = false;
        recipeBefore = null;
        recipeAfter = null;
        recipeProgressStatusBefore = null;
        recipeProgressStatusAfter = null;
    }

    @Test
    public void changeRecipeStatus() throws Exception {
        recipeBefore = recipeService.getRecipe(recipeId);
        recipeProgressStatusBefore = recipeProgressStatusService.getRecipeProgressStatusById(recipeBefore.recipeProgressStatusId);

        result = recipeService.changeStatus(recipeId,status);

        recipeAfter = recipeService.getRecipe(recipeId);
        recipeProgressStatusAfter = recipeProgressStatusService.getRecipeProgressStatusById(recipeAfter.recipeProgressStatusId);

        assertTrue(result);
        assertNotEquals(recipeBefore,recipeAfter);
        assertNotEquals(recipeBefore.recipeProgressStatusId,recipeAfter.recipeProgressStatusId);
        assertNotEquals(recipeProgressStatusBefore.getName(),recipeProgressStatusAfter.getName());
        assertEquals(recipeProgressStatusAfter.getName(),"ГОТОВ");


    }
}
