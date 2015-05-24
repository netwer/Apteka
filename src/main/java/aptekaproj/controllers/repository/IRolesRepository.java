package aptekaproj.controllers.repository;
import aptekaproj.models.Roles;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

@Transactional
public interface IRolesRepository extends CrudRepository<Roles, Integer> {
}