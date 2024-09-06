package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Surahs {
    private int id;
    private String name;
    private String transliteration;
    private String type;
    private int total_verses;
    private String link;
}
