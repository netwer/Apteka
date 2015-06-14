package aptekaproj.controllers.repository;

import aptekaproj.models.Pharmacy;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Admin on 28.05.2015.
 */
public interface IPharmaciesRepository extends CrudRepository<Pharmacy, Integer> {
}
