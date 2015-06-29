package aptekaproj.TestsSuites.ApothecarySetDrugReadyTestCase;

import aptekaproj.AptekaApplication;
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

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by org.apteka on 26.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetAllDrugsForCurrentApothecaryTest {

    @Autowired
    private DrugService drugService;

    private int apothecaryId;

    @Before
    public void setUp() throws Exception {
        apothecaryId = 14;
    }

    @After
    public void tearDown() throws Exception {
        apothecaryId = 0;
    }

    @Test
    public void getAllDrugs() throws Exception {
        List<DrugToProduceViewModel> drugToProduceList = drugService.getDrugsToProduce(apothecaryId);

        assertNotNull(drugToProduceList);
        assertNotEquals(drugToProduceList.size(),0);
    }
}
