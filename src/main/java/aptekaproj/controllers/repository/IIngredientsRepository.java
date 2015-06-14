package aptekaproj.controllers.repository;
import aptekaproj.models.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IIngredientsRepository extends CrudRepository<Ingredient, Integer> {
}
