package aptekaproj.OtherTests;

import aptekaproj.AptekaApplication;
import aptekaproj.models.RecipeHasDrugs;
import aptekaproj.services.RecipeHasDrugsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by org.apteka on 27.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetRecipeHasDrugsTest {
    @Autowired
    private RecipeHasDrugsService recipeHasDrugsService;

    private int recipeId;

    @Before
    public void setUp() throws Exception {

        recipeId = 17;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetRecipesHasDrugsByRecipeId() throws Exception {
        List<RecipeHasDrugs> recipeHasDrugsList = new ArrayList<>();
        List<RecipeHasDrugs> recipeHasDrugsList1 = recipeHasDrugsService.getRecipesHasDrugsByRecipeId(recipeId);


        assertNotNull(recipeHasDrugsList1);
        assertNotEquals(recipeHasDrugsList,recipeHasDrugsList1);
        for (RecipeHasDrugs recipeHasDrugs : recipeHasDrugsList1)
            assertEquals(recipeHasDrugs.getRecipeId(), recipeId);
    }
}
