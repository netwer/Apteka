package aptekaproj.OtherTests;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by org.apteka on 27.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetRecipeViewModelTest {

    @Autowired
    private RecipeService recipeService;

    private int recipeId;
    private RecipeViewModel recipeViewModel;

    @Before
    public void setUp() throws Exception {
        recipeId = 14;
        recipeViewModel = new RecipeViewModel();
    }

    @After
    public void tearDown() throws Exception {
        recipeId = 0;
        recipeViewModel = null;
    }

    @Test
    public void getRecipeViewModel() throws Exception {
        recipeViewModel = recipeService.getRecipe(recipeId);

        assertNotNull(recipeViewModel);
        assertEquals(recipeViewModel.recipeId,recipeId);
    }
}
