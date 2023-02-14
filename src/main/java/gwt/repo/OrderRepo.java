package gwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gwt.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
	
}
