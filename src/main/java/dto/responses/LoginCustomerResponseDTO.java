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
@AllArgsConstructor
@NoArgsConstructor
public class LoginCustomerResponseDTO {
    private Boolean success;
    private String accessToken;
    private String refreshToken;
    private UserDataDTO user;
}
