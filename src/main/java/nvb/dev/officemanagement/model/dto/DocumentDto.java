package nvb.dev.officemanagement.model.dto;

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
    private String title;
    private String description;
    private OfficeEntity office;

}
