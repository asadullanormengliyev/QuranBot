package uz.pdp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import uz.pdp.model.SurahDigitState;
import uz.pdp.model.Surahs;
import uz.pdp.util.FilePath;
import uz.pdp.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class KoranService {

    private static final int TOTAL_SURAHS = 114;
    private static final int PAGE_SIZE = 10;

    public List<Surahs> getSurahs() {
        return JsonUtil.readGson(FilePath.PATH_SURAHS, new TypeReference<>() {
        });
    }

    public List<SurahDigitState> getSurahsState() {
        return JsonUtil.readGson(FilePath.PATH_SURAHSTATE, new TypeReference<>() {
        });
    }

    public List<Surahs> getStringList(List<Surahs> surahs, int startIndex) {
        List<Surahs> surahsList = new ArrayList<>();
        int endIndex = Math.min(startIndex + PAGE_SIZE, TOTAL_SURAHS);
        for (int i = startIndex; i < endIndex; i++) {
            surahsList.add(surahs.get(i));
        }
        return surahsList;
    }

    public void setSurahsState(List<SurahDigitState> surahsState) {
        JsonUtil.writeGson(FilePath.PATH_SURAHSTATE, surahsState);
    }

    public void newMenyu(long chatId, int n) {
        List<SurahDigitState> states = getSurahsState();
        SurahDigitState state = states.stream()
                .filter(s -> s.getChatId() == chatId)
                .findFirst()
                .orElse(null);

        if (state != null) {
            int newId = state.getId();
            if (n == 0) {
                newId = 0;
            } else if (n == PAGE_SIZE || n == -PAGE_SIZE) {
                newId = Math.max(0, Math.min(newId + n, TOTAL_SURAHS - PAGE_SIZE));
            }
            state.setId(newId);
        } else {
            state = new SurahDigitState(0, chatId);
            states.add(state);
        }

        setSurahsState(states);
    }
}

