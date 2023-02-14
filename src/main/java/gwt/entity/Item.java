package gwt.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="items")
public class Item {

	@Id
	private Long sku;
	private String description;
	private BigDecimal price;
	
	@ManyToMany(mappedBy = "items")
	private List<Order> orders;
}
