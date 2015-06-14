package aptekaproj.controllers.repository;
import aptekaproj.models.Drug;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IDrugsRepository extends CrudRepository<Drug, Integer> {
}
