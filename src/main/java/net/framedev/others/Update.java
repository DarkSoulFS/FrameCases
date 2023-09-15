package net.framedev.others;

import net.framedev.Main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Update {

	private final Main instance = Main.getInstance();
        String github = "https://api.github.com/repos/DarkSoulFS/FrameCases/releases/latest";
        private String currentVersion = instance.getDescription().getVersion();
        
        // Это в принципе блять пиздец, нахуй это дерьмо
        public void checkForUpdates() {
            try {
                URL url = new URL(github);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    StringBuilder responseBody = new StringBuilder();

                    while (scanner.hasNextLine()) {
                        responseBody.append(scanner.nextLine());
                    }

                    scanner.close();
                    inputStream.close();

                    String json = responseBody.toString();

                    String latestVersion = json.substring(json.indexOf("\"tag_name\":\"") + 12);
                    latestVersion = latestVersion.substring(0, latestVersion.indexOf("\""));

                    if (!currentVersion.equals(latestVersion)) {
                        if (instance.getServer().getVersion().contains("1.16")) {
                            instance.getLogger().warning("Доступно обновление для вашего плагина! Новая версия: " + latestVersion);
                        } else {
                            instance.getLogger().warning("Available update for your plugin! A new version: " + latestVersion);
                        }
                    } else {
                        if (instance.getServer().getVersion().contains("1.16")) {
                            instance.getLogger().info("У вас установлена последняя версия плагина.");
                        } else {
                            instance.getLogger().info("You have the latest version of the plugin installed.");
                        }
                    }
                } else {
                    if (instance.getServer().getVersion().contains("1.16")) {
                        instance.getLogger().warning("Не удалось проверить обновления. Ответ сервера: " + responseCode);
                    } else {
                        instance.getLogger().warning("Failed to check for updates. Server response: " + responseCode);
                    }
                }
            } catch (IOException e) {
                if (instance.getServer().getVersion().contains("1.16")) {
                    instance.getLogger().warning("Ошибка при проверке обновлений: " + e.getMessage());
                } else {
                    instance.getLogger().warning("Error checking for updates: " + e.getMessage());
                }
            }
    }
}
