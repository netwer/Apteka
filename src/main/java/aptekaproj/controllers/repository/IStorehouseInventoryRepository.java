package aptekaproj.controllers.repository;
import aptekaproj.models.StorehouseInventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IStorehouseInventoryRepository extends CrudRepository<StorehouseInventory, Integer> {
}
