package nvb.dev.officemanagement.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "username cannot be empty.")
    @Column(name = "username", nullable = false)
    private String username;

    @NotEmpty(message = "password cannot be empty.")
    @Column(name = "password", nullable = false)
    private String password;

    @NotEmpty(message = "role cannot be empty.")
    @Column(name = "role", nullable = false)
    private String role;

}
