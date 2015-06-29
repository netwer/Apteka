package aptekaproj.OtherTests;

import aptekaproj.AptekaApplication;
import aptekaproj.models.Recipe;
import aptekaproj.services.RecipeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by org.apteka on 27.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetRecipeTest {
    @Autowired
    private RecipeService recipeService;

    private int recipeId;

    @Before
    public void setUp() throws Exception {

        recipeId = 14;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        Recipe recipe1 = recipeService.getRecipeById(recipeId);


        assertNotNull(recipe1);
        assertNotEquals(recipe,recipe1);
        assertEquals(recipe1.getId(), recipeId);
    }
}
