package aptekaproj.TestsSuites.DrugsToApothecaryTestCase;

import aptekaproj.AptekaApplication;
import aptekaproj.models.ConcreteDrug;
import aptekaproj.services.ConcreteDrugService;
import aptekaproj.services.ConcreteIngredientService;
import aptekaproj.services.DrugService;
import aptekaproj.services.RoleService;
import aptekaproj.viewModels.RecipeDrugWithPharmacistViewModel;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by org.apteka on 29.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class DrugsToProduceTest {
    @Autowired
    private DrugService drugService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ConcreteDrugService concreteDrugService;

    @Autowired
    private ConcreteIngredientService concreteIngredientService;

    private int recipeId;

    private List<RecipeDrugWithPharmacistViewModel> recipeDrugWithPharmacistList;

    @Before
    public void setUp() throws Exception {

        recipeId = 17;
        recipeDrugWithPharmacistList = new ArrayList<>();
        RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacist1 = new RecipeDrugWithPharmacistViewModel();
        recipeDrugWithPharmacist1.recipeId = 17;
        recipeDrugWithPharmacist1.drugId = 1;
        recipeDrugWithPharmacist1.apothecaryId = 14;
        recipeDrugWithPharmacistList.add(recipeDrugWithPharmacist1);

        RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacist2 = new RecipeDrugWithPharmacistViewModel();
        recipeDrugWithPharmacist2.recipeId = 17;
        recipeDrugWithPharmacist2.drugId = 2;
        recipeDrugWithPharmacist2.apothecaryId = 14;
        recipeDrugWithPharmacistList.add(recipeDrugWithPharmacist2);

        RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacist3 = new RecipeDrugWithPharmacistViewModel();
        recipeDrugWithPharmacist3.recipeId = 17;
        recipeDrugWithPharmacist3.drugId = 3;
        recipeDrugWithPharmacist3.apothecaryId = 9;
        recipeDrugWithPharmacistList.add(recipeDrugWithPharmacist3);

        RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacist4 = new RecipeDrugWithPharmacistViewModel();
        recipeDrugWithPharmacist4.recipeId = 17;
        recipeDrugWithPharmacist4.drugId = 4;
        recipeDrugWithPharmacist4.apothecaryId = 9;
        recipeDrugWithPharmacistList.add(recipeDrugWithPharmacist4);
    }

    @After
    public void tearDown() throws Exception {
        List<ConcreteDrug> concreteDrugsToProduce = concreteDrugService.getConcreteDrugsByRecipeId(recipeId);
        concreteDrugService.deleteConcreteDrugs(concreteDrugsToProduce);
        concreteIngredientService.deleteConcreteIngredientService(concreteDrugsToProduce);
    }

    @Test
    public void testDrugToProduce() throws Exception {
        boolean result = concreteDrugService.drugsToProduce(recipeDrugWithPharmacistList);
        assertTrue(result);
    }
}
