package gwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gwt.entity.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item,Long> {

}
