package aptekaproj.OtherTests;

import aptekaproj.AptekaApplication;
import aptekaproj.helpers.enums.ProgressStatusEnum;
import aptekaproj.models.RecipeProgressStatus;
import aptekaproj.services.RecipeProgressStatusService;
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
public class GetRecipeProgressStatusByIdTest {

    @Autowired
    private RecipeProgressStatusService recipeProgressStatusService;

    private RecipeProgressStatus recipeProgressStatus;

    @Before
    public void setUp() throws Exception {
        recipeProgressStatus = new RecipeProgressStatus();
    }

    @After
    public void tearDown() throws Exception {
        recipeProgressStatus = null;
    }

    @Test
    public void getRecipeProgressStatus() throws Exception {
        recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusById(1);
        assertNotNull(recipeProgressStatus);
        assertEquals(recipeProgressStatus.getName(), ProgressStatusEnum.CREATED.toString());

        recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusById(2);
        assertNotNull(recipeProgressStatus);
        assertEquals(recipeProgressStatus.getName(), ProgressStatusEnum.UPDATED.toString());

        recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusById(3);
        assertNotNull(recipeProgressStatus);
        assertEquals(recipeProgressStatus.getName(), ProgressStatusEnum.DONE.toString());

        recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusById(4);
        assertNotNull(recipeProgressStatus);
        assertEquals(recipeProgressStatus.getName(), ProgressStatusEnum.IN_PROCESS.toString());

        recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusById(5);
        assertNotNull(recipeProgressStatus);
        assertEquals(recipeProgressStatus.getName(), ProgressStatusEnum.PACKAGE.toString());

        recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusById(6);
        assertNotNull(recipeProgressStatus);
        assertEquals(recipeProgressStatus.getName(), ProgressStatusEnum.WAITING_PROCESS.toString());

        recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusById(7);
        assertNotNull(recipeProgressStatus);
        assertEquals(recipeProgressStatus.getName(), ProgressStatusEnum.ISSUED.toString());
    }
}
