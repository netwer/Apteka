package aptekaproj.ControllersTests.PharmaciesControllerTest;

import aptekaproj.AptekaApplication;
import aptekaproj.models.ConcreteDrug;
import aptekaproj.models.Pharmacy;
import aptekaproj.models.PharmacyStaff;
import aptekaproj.services.*;
import aptekaproj.viewModels.RecipeDrugWithPharmacistViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by org.apteka on 29.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class UpdateDrugToProduceTest {
    @Autowired
    private DrugService drugService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ConcreteDrugService concreteDrugService;

    @Autowired
    private ConcreteIngredientService concreteIngredientService;

    @Autowired
    private PharmacyStaffService pharmacyStaffService;

    private int recipeId;
    private int lastApothecaryId;
    private int drugId;
    RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacist;

    @Before
    public void setUp() throws Exception {
        recipeId = 17;
        lastApothecaryId = 14;
        drugId = 1;
        recipeDrugWithPharmacist = new RecipeDrugWithPharmacistViewModel();
        recipeDrugWithPharmacist.recipeId = 17;
        recipeDrugWithPharmacist.drugId = 1;
        recipeDrugWithPharmacist.apothecaryId = 9;
    }

    @After
    public void tearDown() throws Exception {
        PharmacyStaff pharmacyStaff = pharmacyStaffService.getPharmacyStaffByUserId(lastApothecaryId);
        ConcreteDrug concreteDrug = concreteDrugService.getConcreteDrugByRecipeAndDrugIds(recipeId,drugId);
        concreteDrug.setPharmacyStaffId(pharmacyStaff.getId());
        concreteDrugService.saveConcreteDrug(concreteDrug);
    }

    @Test
    public void testUpdateDrugToProduce() throws Exception {
        concreteDrugService.updateDrugToProduce(recipeDrugWithPharmacist);
    }
}
