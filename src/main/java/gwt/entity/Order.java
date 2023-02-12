package gwt.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	private Long id;
	private List<Item> items;
	
	public BigDecimal getTotalPrice() {
		return items.stream().map(m -> m.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
}
