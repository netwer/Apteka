package aptekaproj.ControllersTests.PharmaciesControllerTest;

import aptekaproj.AptekaApplication;
import aptekaproj.services.DrugService;
import aptekaproj.services.RoleService;
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

    private int recipeId;

    @Before
    public void setUp() throws Exception {
        recipeId = 17;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUpdateDrugToProduce() throws Exception {

    }
}
