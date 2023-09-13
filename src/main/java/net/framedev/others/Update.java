package net.framedev.others;

import net.framedev.Main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Update {

        String github = "https://api.github.com/repos/DarkSoulFS/FrameCases/releases/latest";
        private String currentVersion = Main.getInstance().getDescription().getVersion();


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
                        if (Main.getInstance().getServer().getVersion().contains("1.16")) {
                            Main.getInstance().getLogger().warning("Доступно обновление для вашего плагина! Новая версия: " + latestVersion);
                        } else {
                            Main.getInstance().getLogger().warning("Available update for your plugin! A new version:" + latestVersion);
                        }
                    } else {
                        if (Main.getInstance().getServer().getVersion().contains("1.16")) {
                            Main.getInstance().getLogger().info("У вас установлена последняя версия плагина.");
                        } else {
                            Main.getInstance().getLogger().info("You have the latest version of the plugin installed.");
                        }
                    }
                } else {
                    if (Main.getInstance().getServer().getVersion().contains("1.16")) {
                        Main.getInstance().getLogger().warning("Не удалось проверить обновления. Ответ сервера: " + responseCode);
                    } else {
                        Main.getInstance().getLogger().warning("Failed to check for updates. Server response: " + responseCode);
                    }
                }
            } catch (IOException e) {
                if (Main.getInstance().getServer().getVersion().contains("1.16")) {
                    Main.getInstance().getLogger().warning("Ошибка при проверке обновлений: " + e.getMessage());
                } else {
                    Main.getInstance().getLogger().warning("Error checking for updates: " + e.getMessage());
                }
            }
    }
}
