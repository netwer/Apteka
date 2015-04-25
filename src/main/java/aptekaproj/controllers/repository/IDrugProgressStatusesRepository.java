package aptekaproj.controllers.repository;
import aptekaproj.models.DrugProgressStatuses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IDrugProgressStatusesRepository extends CrudRepository<DrugProgressStatuses, Integer> {
}
