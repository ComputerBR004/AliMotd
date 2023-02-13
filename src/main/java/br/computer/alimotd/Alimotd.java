package br.computer.alimotd;


import br.computer.alimotd.commands.MaintenanceCommand;
import br.computer.alimotd.events.PlayerLogin;
import br.computer.alimotd.motd.Motd;
import br.computer.alimotd.tabcomplete.Alimotdtabcomplete;
import br.computer.alimotd.utils.Config;
import br.computer.alimotd.version.UpdateChecker;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.*;

public final class Alimotd extends JavaPlugin {

    private static Alimotd plugin;
    public static Alimotd instance;
    public static Config config;
    public static Config mensagens;
    public static Config motd;

    public static Alimotd getInstance() {
        return plugin;
    }


    @Override
    public void onEnable() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(this, ListenerPriority.NORMAL,
                        Collections.singletonList(PacketType.Status.Server.SERVER_INFO)) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        Motd.handlePing(event.getPacket().getServerPings().read(0));
                    }
                }
        );
        Objects.requireNonNull(Bukkit.getPluginCommand("manutencao")).setTabCompleter(new Alimotdtabcomplete());
        registrareventos();
        files();
        plugin = this;
        registrarcomandos();
        SendMenssage("§bPlugin iniciado com sucesso!");
        SendMenssage("§bVesão 1.5.0");
        SendMenssage("§bAuthor: Computer_BR (Alisson)");
        new UpdateChecker(this, 107981).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                SendMenssage("§aVocê está na ultima versão: " + version);

            } else {
                SendMenssage("§aHá uma nova atualização disponível.");
                SendMenssage("§6Há uma nova versão do AliMotd disponível: " + version + " Faça o download da nova versão em: https://www.spigotmc.org/resources/alimotd-1-8-1-19.107981/");
            }
        });
    }

    @Override
    public void onDisable() {
        SendMenssage("§cPlugin desligado com sucesso!");
    }

    public Alimotd() {
        instance = this;
    }

    public void registrareventos() {
        PluginManager ev = Bukkit.getPluginManager();
        ev.registerEvents(new PlayerLogin(), this);
    }

    public void registrarcomandos() {
        Objects.requireNonNull(getCommand("manutencao")).setExecutor((CommandExecutor) new MaintenanceCommand());
    }

    public void files() {
        config = new Config(this, "Config.yml");
        mensagens = new Config(this, "Mensagens.yml");
        motd = new Config(this, "Motd.yml");
        config.saveDefaultConfig();
        mensagens.saveDefaultConfig();
        motd.saveDefaultConfig();
    }

    private void SendMenssage(String msg) {
        Bukkit.getConsoleSender().sendMessage("§b[AliMotd] " + msg);
    }

    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf(46) + 1) + ".";
    }

    public static Class<?> getOBClass(String className) {
        String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (Exception var5) {
            var5.printStackTrace();
        }
        return clazz;
    }

}
