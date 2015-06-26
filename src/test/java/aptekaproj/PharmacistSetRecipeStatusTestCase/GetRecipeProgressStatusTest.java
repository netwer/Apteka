package aptekaproj.PharmacistSetRecipeStatusTestCase;

import aptekaproj.AptekaApplication;
import aptekaproj.services.RecipeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by org.apteka on 27.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetRecipeProgressStatusTest {

    @Autowired
    private RecipeService recipeService;

    private int recipeId;
    private String status;
    private boolean result;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void changeRecipeStatus() throws Exception {
    }
}
