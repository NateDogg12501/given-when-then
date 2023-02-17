package gwt.test.chain.given;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.transaction.Transactional;

import gwt.entity.Item;
import gwt.entity.Order;
import gwt.repo.OrderRepo;

public class OrderScenarios extends GivenScenarioChain {

	private OrderRepo orderRepo;
	private EntityManager entityManager;
	
	private Order.OrderBuilder orderBuilder;
	
	public OrderScenarios() {
		super();
		
		orderRepo = this.getBean(OrderRepo.class);
		entityManager = this.getBean(EntityManagerFactory.class).createEntityManager();
		orderBuilder = Order.builder();
	}
	
	public OrderScenarios withId(Long id) {
		orderBuilder.id(id);
		return this;
	}
	
	public OrderScenarios withItemSkus(Long... skus) {
		orderBuilder.items(Stream.of(skus).map(sku -> Item.builder().sku(sku).build()).collect(Collectors.toList()));
		return this;
	}
	
	@Override
	protected GivenScenarioChain closeCurrentChain() {
		Order o = orderBuilder.build();
		
		if (o.getId() != null) {
			EntityTransaction txn = entityManager.getTransaction();
			txn.begin();
			entityManager.createNativeQuery("alter sequence order_id_seq restart with ?").setParameter(1, o.getId()).executeUpdate();
			txn.commit();				
		}

		orderRepo.save(orderBuilder.build());
		
		return this;
	}

}
