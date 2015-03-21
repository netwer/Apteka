package aptekaproj.models;

import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

@Transactional
public interface IRolesRepository extends CrudRepository<Roles, Integer> {

    //public Roles getById(int id);
}
