package aptekaproj.controllers.repository;
import aptekaproj.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IRecipesRepository extends CrudRepository<Recipe, Integer> {
}
