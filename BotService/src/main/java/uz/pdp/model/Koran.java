package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Koran {
    private int id;
    private String name;
    private String transliteration;
    private String type;
    private int total_verses;
    private ArrayList<Verse> verses;
    @NoArgsConstructor
    public static class Verse{
        public int id;
        public String text;
        public String transliteration;
    }
}
