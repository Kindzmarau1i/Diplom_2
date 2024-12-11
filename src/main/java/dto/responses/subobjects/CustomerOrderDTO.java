package dto.responses.subobjects;

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
public class CustomerOrderDTO {
    private String _id;
    private List<String> ingredients;
    private String status;
    private String name;
    private String createdAt;
    private String updatedAt;
    private Long number;
}
