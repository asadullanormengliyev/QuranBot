package uz.pdp.utill;

import uz.pdp.service.*;
import uz.pdp.servicex.MonthlyPrayerTimesUtil;

public class ObjectUtil {
    public static KoranBotService koranBotService = new KoranBotService();
    public static KoranService koranService = new KoranService();
    public static TasbexBotService tasbexBotService = new TasbexBotService();
    public static SurahLanguageBotService surahLanguageBotService = new SurahLanguageBotService();
    public static PrayerTimeInlineBotService prayerTimeInlineBotService = new PrayerTimeInlineBotService();
    public static MosqueBotService mosqueBotService = new MosqueBotService();
    public static HijriDateBotService hijriDateBotService = new HijriDateBotService();
    public static MonthlyPrayerTimesUtil monthlyPrayerTimesUtil = new MonthlyPrayerTimesUtil();
    public static NearestPrayerTimeBotService nearestPrayerTimeBotService = new NearestPrayerTimeBotService();
    public static PrayerTimeBotService prayerTimeBotService = new PrayerTimeBotService();
    public static NamesAllahBotService namesAllahBotService = new NamesAllahBotService();
}
