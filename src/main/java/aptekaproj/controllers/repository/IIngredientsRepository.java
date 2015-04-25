package aptekaproj.controllers.repository;
import aptekaproj.models.Ingredients;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IIngredientsRepository extends CrudRepository<Ingredients, Integer> {
}
