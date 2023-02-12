package gwt.dto.request;

import java.math.BigDecimal;
import java.util.List;

import gwt.dto.response.ItemResponseDto;
import gwt.dto.response.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {

	private Long sku;
	private String description;
	private BigDecimal price;
	
}
