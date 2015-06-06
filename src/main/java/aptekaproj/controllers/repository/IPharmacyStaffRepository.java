package aptekaproj.controllers.repository;

import aptekaproj.models.PharmacyStaff;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Admin on 06.06.2015.
 */
public interface IPharmacyStaffRepository extends CrudRepository<PharmacyStaff, Integer> {
}
