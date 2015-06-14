package aptekaproj.controllers.repository;
import aptekaproj.models.User;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

@Transactional
public interface IUsersRepository extends CrudRepository<User, Integer> {
}