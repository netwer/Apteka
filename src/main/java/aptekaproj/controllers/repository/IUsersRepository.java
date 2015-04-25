package aptekaproj.controllers.repository;
import aptekaproj.models.Users;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

@Transactional
public interface IUsersRepository extends CrudRepository<Users, Integer> {
}