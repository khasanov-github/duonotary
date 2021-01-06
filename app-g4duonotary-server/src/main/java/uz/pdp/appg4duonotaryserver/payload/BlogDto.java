package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {
    private UUID id;
    private UUID photoId;
    private String title;
    private String text;

}
