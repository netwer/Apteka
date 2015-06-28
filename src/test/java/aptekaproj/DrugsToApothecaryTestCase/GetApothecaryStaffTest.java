package aptekaproj.DrugsToApothecaryTestCase;

import aptekaproj.AptekaApplication;
import aptekaproj.helpers.enums.RolesNameEnum;
import aptekaproj.models.Role;
import aptekaproj.models.User;
import aptekaproj.services.PharmacyStaffService;
import aptekaproj.services.RoleService;
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
public class GetApothecaryStaffTest {

    @Autowired
    private PharmacyStaffService pharmacyStaffService;

    @Autowired
    private RoleService roleService;

    private int pharmacistId;

    @Before
    public void setUp() throws Exception {
        pharmacistId = 10;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetApothecaryStaff() throws Exception {
        List<User> apothecariesList = pharmacyStaffService.getApothecariesStaffs(pharmacistId);
        Role apothecaryRole = roleService.getRoleByName(RolesNameEnum.APOTHECARY.toString());

        assertNotNull(apothecariesList);
        for (User user : apothecariesList){
            assertEquals(user.getRoleId(),(int)apothecaryRole.getId());
        }
    }
}
