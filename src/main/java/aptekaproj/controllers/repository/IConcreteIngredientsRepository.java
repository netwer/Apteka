package aptekaproj.controllers.repository;

import aptekaproj.models.ConcreteIngredient;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Admin on 12.06.2015.
 */
public interface IConcreteIngredientsRepository extends CrudRepository<ConcreteIngredient, Integer> {
        }
