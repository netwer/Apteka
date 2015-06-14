package aptekaproj.controllers.repository;

import aptekaproj.models.ConcreteDrug;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Admin on 12.06.2015.
 */
public interface IConcreteDrugsRepository extends CrudRepository<ConcreteDrug, Integer> {
}