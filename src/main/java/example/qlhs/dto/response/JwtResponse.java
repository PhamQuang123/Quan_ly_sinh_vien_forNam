package example.qlhs.dto.response;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private String accessToken;
}
