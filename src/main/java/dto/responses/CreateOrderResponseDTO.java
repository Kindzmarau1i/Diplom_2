package dto.responses;

import dto.responses.subobjects.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponseDTO {
    private Boolean success;
    private String name;
    private OrderDTO order;
}
