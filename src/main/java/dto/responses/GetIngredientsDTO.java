package dto.responses;

import dto.responses.subobjects.Data;
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
public class GetIngredientsDTO {
    private Boolean success;
    private List<Data> data;
}
