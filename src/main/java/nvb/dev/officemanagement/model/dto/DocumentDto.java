package nvb.dev.officemanagement.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvb.dev.officemanagement.model.entity.OfficeEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DocumentDto {

    private Long id;

    @NotEmpty(message = "title cannot be empty.")
    private String title;

    @NotEmpty(message = "description cannot be empty.")
    private String description;

    private OfficeEntity office;

}
