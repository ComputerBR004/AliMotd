package br.computer.alimotd.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerJoin implements Listener {

    public static String newVersion;

    public PlayerJoin(String newVersion) {
        PlayerJoin.newVersion = newVersion;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            TextComponent mensagem = new TextComponent("Uma nova versão do ALIMOTD está disponível: ");
            mensagem.setColor(ChatColor.RED.asBungee());

            TextComponent link = new TextComponent("Clique aqui");
            link.setColor(ChatColor.AQUA.asBungee());
            link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/alimotd-1-8-1-19.107981/"));
            link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Clique para ir para o link").create()));
            mensagem.addExtra(link);

            player.spigot().sendMessage(mensagem);
        }
    }
}
