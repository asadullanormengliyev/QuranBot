package uz.pdp.service;

import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.pdp.utill.ObjectUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NearestPrayerTimeBotService {
    public SendMessage getNextPrayer(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(getNearestPrayerTime());
        return sendMessage;
    }

    private String getNearestPrayerTime() {
        String apiUrl = "https://islomapi.uz/api/present/day?region=Toshkent";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();

                JSONObject jsonObject = new JSONObject(inline.toString());
                JSONObject times = jsonObject.getJSONObject("times");

                LocalDateTime currentDateTime = LocalDateTime.now();
                return findNearestPrayer(currentDateTime, times);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Error: Unable to get prayer times.";
    }

    private String findNearestPrayer(LocalDateTime currentDateTime, JSONObject times) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Map<String, LocalDateTime> prayerTimes = new HashMap<>();
        prayerTimes.put("Bomdod", LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.parse(times.getString("tong_saharlik"), timeFormatter)));
        prayerTimes.put("Quyosh", LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.parse(times.getString("quyosh"), timeFormatter)));
        prayerTimes.put("Peshin", LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.parse(times.getString("peshin"), timeFormatter)));
        prayerTimes.put("Asr", LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.parse(times.getString("asr"), timeFormatter)));
        prayerTimes.put("Shom", LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.parse(times.getString("shom_iftor"), timeFormatter)));
        prayerTimes.put("Hufton", LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.parse(times.getString("hufton"), timeFormatter)));

        String nextPrayerName = "";
        Duration shortestDuration = Duration.ofDays(1);
        for (Map.Entry<String, LocalDateTime> entry : prayerTimes.entrySet()) {
            String prayerName = entry.getKey();
            LocalDateTime prayerDateTime = entry.getValue();

            if (currentDateTime.isAfter(prayerDateTime)) {
                prayerDateTime = prayerDateTime.plusDays(1);
            }
            Duration duration = Duration.between(currentDateTime, prayerDateTime);
            if (duration.compareTo(shortestDuration) < 0) {
                shortestDuration = duration;
                nextPrayerName = prayerName;
            }
        }
        long hours = shortestDuration.toHours();
        long minutes = shortestDuration.toMinutes() % 60;

        return String.format("Keyingi namoz vaqti bu %s: %d soat va %d daqiqa qoldi \uD83D\uDE0A", nextPrayerName, hours, minutes);
    }
}
