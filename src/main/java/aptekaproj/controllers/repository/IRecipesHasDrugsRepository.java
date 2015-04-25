package aptekaproj.controllers.repository;
import aptekaproj.models.RecipesHasDrugs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IRecipesHasDrugsRepository extends CrudRepository<RecipesHasDrugs, Integer> {
}
