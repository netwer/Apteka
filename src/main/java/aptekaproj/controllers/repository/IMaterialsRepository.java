package aptekaproj.controllers.repository;
import aptekaproj.models.Materials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IMaterialsRepository extends CrudRepository<Materials, Integer> {
}
