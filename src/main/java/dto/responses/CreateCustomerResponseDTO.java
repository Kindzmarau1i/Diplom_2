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
public class CreateCustomerResponseDTO {
    private Boolean success;
    private UserDataDTO user;
    private String accessToken;
    private String refreshToken;
}
