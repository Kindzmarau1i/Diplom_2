package dto.responses.subobjects;

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
public class Data {
    private String _id;
    private String name;
    private String type;
    private Long proteins;
    private Long fat;
    private Long carbohydrates;
    private Long calories;
    private Long price;
    private String image;
    private String image_mobile;
    private String image_large;
    private Long __v;
}
