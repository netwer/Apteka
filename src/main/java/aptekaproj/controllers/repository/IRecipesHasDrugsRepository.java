package aptekaproj.controllers.repository;
import aptekaproj.models.RecipeHasDrugs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IRecipesHasDrugsRepository extends CrudRepository<RecipeHasDrugs, Integer> {
}
