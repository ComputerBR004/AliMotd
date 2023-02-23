package br.computer.alimotd.motd;

import br.computer.alimotd.Alimotd;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {
    public static String hex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            try {
                Method chatcoloroff = net.md_5.bungee.api.ChatColor.class.getDeclaredMethod("of", String.class);
                net.md_5.bungee.api.ChatColor chatcolor = net.md_5.bungee.api.ChatColor.WHITE;
                net.md_5.bungee.api.ChatColor finalcolor = (net.md_5.bungee.api.ChatColor) chatcoloroff.invoke(chatcolor, color);
                message = message.replace(color, finalcolor + "");
                matcher = pattern.matcher(message);
            } catch (Exception e) {
                return message.replace(color, "");
            }
        }
        return message.replace("&", "§");
    }
    public static String replace(String string) {
        int maxplayers = Bukkit.getMaxPlayers();
        int online = Bukkit.getOnlinePlayers().size();

        if (Alimotd.config.getConfig().getBoolean("FakeMaximoJogadores.ativar")) {
            maxplayers = Alimotd.config.getConfig().getInt("FakeMaximoJogadores.quantidade");
        }
        if (Alimotd.config.getConfig().getBoolean("FakeAumento.ativar")) {
            maxplayers = Alimotd.config.getConfig().getInt("FakeAumento.quantidade") + 1;
        }
        if (Alimotd.config.getConfig().getBoolean("FakeOnline.ativar")) {
            online = Alimotd.config.getConfig().getInt("FakeOnline.quantidade");
        }

        return hex(ChatColor.translateAlternateColorCodes('&',
                string.replaceAll("\\{nl\\}", "\n")
                        .replaceAll("\\{max_players\\}", String.valueOf(maxplayers))
                        .replaceAll("\\{date_time\\}", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime()))
                        .replaceAll("\\{online\\}", String.valueOf(online))
        ));
    }
}
