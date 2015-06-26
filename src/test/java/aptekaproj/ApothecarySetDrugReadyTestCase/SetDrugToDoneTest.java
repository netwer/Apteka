package aptekaproj.ApothecarySetDrugReadyTestCase;

import aptekaproj.AptekaApplication;
import aptekaproj.models.RecipeHasDrugs;
import aptekaproj.services.DrugService;
import aptekaproj.viewModels.DrugToProduceViewModel;
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
 * Created by org.apteka on 26.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class SetDrugToDoneTest {

    @Autowired
    private DrugService drugService;
    private int apothecaryId;
    private int recipesHasDrugsId;
    private int countDrugsToProduceBefore;
    private int countDrugsToProduceAfter;
    private RecipeHasDrugs recipeHasDrugs;

    @Before
    public void setUp() throws Exception {
        apothecaryId = 14;
        recipesHasDrugsId = 27;
        countDrugsToProduceBefore = 0;
        countDrugsToProduceAfter = 0;
        recipeHasDrugs = new RecipeHasDrugs();
    }

    @After
    public void tearDown() throws Exception {
        recipeHasDrugs = null;
    }

    @Test
    public void getAllDrugs() throws Exception {
        List<DrugToProduceViewModel> drugToProduceListBefore = drugService.getDrugsToProduce(apothecaryId);
        countDrugsToProduceBefore = drugToProduceListBefore.size();

        recipeHasDrugs = drugService.drugToDone(recipesHasDrugsId);

        List<DrugToProduceViewModel> drugToProduceListAfter = drugService.getDrugsToProduce(apothecaryId);
        countDrugsToProduceAfter = drugToProduceListAfter.size();

        assertNotNull(recipeHasDrugs);
        assertEquals(countDrugsToProduceBefore-1,countDrugsToProduceAfter);
        assertTrue(recipeHasDrugs.getDone());
    }
}
