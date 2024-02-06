package nvb.dev.officemanagement.model.response;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;
    private String refreshToken;

}
