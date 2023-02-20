package gwt.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {

	@Id
	@GeneratedValue(generator="order_id_seq")
	@SequenceGenerator(name="order_id_seq",sequenceName="order_id_seq", allocationSize=1)
	private Long id;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	  name = "order_item",
	  joinColumns = @JoinColumn(name = "order_id"),
	  inverseJoinColumns = @JoinColumn(name = "item_sku")
	)
	private List<Item> items;
	
	@Transient
	public BigDecimal getTotalPrice() {
		return items.stream().map(m -> m.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
}
