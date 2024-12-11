package dto.responses;

import dto.responses.subobjects.UserDataDTO;
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
public class UpdateCustomerResponseDTO {
    private Boolean success;
    private UserDataDTO user;
}
