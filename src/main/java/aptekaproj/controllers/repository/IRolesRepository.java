package aptekaproj.controllers.repository;
import aptekaproj.models.Role;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

@Transactional
public interface IRolesRepository extends CrudRepository<Role, Integer> {
}