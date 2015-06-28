package aptekaproj.OtherTests;

import aptekaproj.AptekaApplication;
import aptekaproj.helpers.enums.RolesNameEnum;
import aptekaproj.models.Role;
import aptekaproj.services.RoleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by org.apteka on 28.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetRoleTest {

    @Autowired
    private RoleService roleService;

    @Before
    public void setUp() throws Exception {    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetApothecaryStaff() throws Exception {
        Role role1 = roleService.getRoleByName(RolesNameEnum.ADMIN.toString());
        assertNotNull(role1);
        assertEquals(role1.getName(),RolesNameEnum.ADMIN.toString());

        Role role2 = roleService.getRoleByName(RolesNameEnum.APOTHECARY.toString());
        assertNotNull(role2);
        assertEquals(role2.getName(),RolesNameEnum.APOTHECARY.toString());

        Role role3 = roleService.getRoleByName(RolesNameEnum.DOCTOR.toString());
        assertNotNull(role3);
        assertEquals(role3.getName(),RolesNameEnum.DOCTOR.toString());

        Role role4 = roleService.getRoleByName(RolesNameEnum.PATIENT.toString());
        assertNotNull(role4);
        assertEquals(role4.getName(),RolesNameEnum.PATIENT.toString());

        Role role5 = roleService.getRoleByName(RolesNameEnum.PHARMACIST.toString());
        assertNotNull(role5);
        assertEquals(role5.getName(),RolesNameEnum.PHARMACIST.toString());
    }
}
