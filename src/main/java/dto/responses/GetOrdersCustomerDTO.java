package dto.responses;

import dto.responses.subobjects.CustomerOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersCustomerDTO {
    private Boolean success;
    private List<CustomerOrderDTO> orders;
    private Long total;
    private Long totalToday;
}
