package nvb.dev.officemanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_document")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "title cannot be empty.")
    @Column(name = "title", nullable = false)
    private String title;

    @NotEmpty(message = "description cannot be empty.")
    @Column(name = "description", nullable = false)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private OfficeEntity office;

    public DocumentEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
