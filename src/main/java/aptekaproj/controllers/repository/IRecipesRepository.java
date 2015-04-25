package aptekaproj.controllers.repository;
import aptekaproj.models.Recipes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IRecipesRepository extends CrudRepository<Recipes, Integer> {
}
