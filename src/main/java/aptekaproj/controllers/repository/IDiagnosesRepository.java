package aptekaproj.controllers.repository;
import aptekaproj.models.Diagnoses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IDiagnosesRepository extends CrudRepository<Diagnoses, Integer> {
}
