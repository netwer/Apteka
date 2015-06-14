package aptekaproj.controllers.repository;
import aptekaproj.models.DrugProgressStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IDrugProgressStatusesRepository extends CrudRepository<DrugProgressStatus, Integer> {
}
