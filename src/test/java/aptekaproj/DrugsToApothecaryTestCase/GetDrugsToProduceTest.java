package aptekaproj.DrugsToApothecaryTestCase;

import aptekaproj.AptekaApplication;
import aptekaproj.helpers.enums.RolesNameEnum;
import aptekaproj.models.Role;
import aptekaproj.models.User;
import aptekaproj.services.RecipeService;
import aptekaproj.services.RoleService;
import aptekaproj.viewModels.OrderMissingViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by org.apteka on 28.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetDrugsToProduceTest {

    @Autowired
    private RecipeService recipeService;

    private int recipeId;
    private int pharmacistId;

    @Before
    public void setUp() throws Exception {
        recipeId = 17;
        pharmacistId = 10;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetDrugsToProduce() throws Exception {
        /*OrderMissingViewModel orderMissingViewModel = recipeService.getOrderMissing(pharmacistId,recipeId);

        assertNotNull(orderMissingViewModel);
        assertEquals(orderMissingViewModel.drugViewModels.size(),3);
        assertEquals(orderMissingViewModel.apothecaryUsers.size(),1);*/
    }
}
