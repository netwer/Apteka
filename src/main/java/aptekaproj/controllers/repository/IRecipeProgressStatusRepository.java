package aptekaproj.controllers.repository;

import aptekaproj.models.RecipeProgressStatus;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Admin on 28.05.2015.
 */
public interface IRecipeProgressStatusRepository extends CrudRepository<RecipeProgressStatus, Integer> {
}
