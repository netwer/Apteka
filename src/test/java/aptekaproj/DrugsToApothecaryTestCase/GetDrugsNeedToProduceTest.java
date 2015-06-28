package aptekaproj.DrugsToApothecaryTestCase;

import aptekaproj.AptekaApplication;
import aptekaproj.services.DrugService;
import aptekaproj.services.RoleService;
import aptekaproj.viewModels.DrugViewModel;
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
public class GetDrugsNeedToProduceTest {

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
    public void testGetDrugsToProduce() throws Exception {
        List<DrugViewModel> drugViewModelList = drugService.getDrugsNeedsToProduce(recipeId);

        assertNotNull(drugViewModelList);
        int count = 0;
        for(DrugViewModel drugViewModel : drugViewModelList){
            assertEquals(drugViewModel.needsToProduce,true);
            assertEquals(drugViewModel.recipeId,recipeId);
            count++;
        }
        assertEquals(drugViewModelList.size(),count);
    }
}
