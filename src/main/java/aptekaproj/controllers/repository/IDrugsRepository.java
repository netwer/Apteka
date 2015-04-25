package aptekaproj.controllers.repository;
import aptekaproj.models.Drugs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IDrugsRepository extends CrudRepository<Drugs, Integer> {
}
