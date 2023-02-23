package br.computer.alimotd;


import br.computer.alimotd.commands.MaintenanceCommand;
import br.computer.alimotd.events.PlayerJoin;
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
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.util.*;

import static br.computer.alimotd.events.PlayerJoin.newVersion;

public final class Alimotd extends JavaPlugin implements Listener {
    private static Alimotd plugin;
    public static Alimotd instance;
    public static Config config;
    public static Config mensagens;
    public static Config motd;
    public static Config configpt;
    public static Config mensagenspt;
    public static Config motdpt;
    public static Config configes;
    public static Config mensagenses;
    public static Config motdes;

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
        SendMenssage("§bPlugin launched successfully!");
        SendMenssage("§bVersion 1.9.0");
        SendMenssage("§bAuthor: Computer_BR (Alisson)");
        new UpdateChecker(this, 107981).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                SendMenssage("§aYou are on the latest version: " + version);

            } else {
                SendMenssage("§aThere is a new update available.");
                SendMenssage("§6There is a new version of AliMotd available: " + version + " Download the new version at: https://www.spigotmc.org/resources/alimotd-1-8-1-19.107981/");
                getServer().getPluginManager().registerEvents(new PlayerJoin(newVersion), this);
            }
        });
    }

    @Override
    public void onDisable() {
        SendMenssage("§cPlugin shut down successfully!");
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
        mensagens = new Config(this, "Messages.yml");
        motd = new Config(this, "Motd.yml");

        configpt = new Config(this, "Lang/PT-BR/Config.yml");
        mensagenspt = new Config(this, "Lang/PT-BR/Messages.yml");
        motdpt = new Config(this, "Lang/PT-BR/Motd.yml");

        configes = new Config(this, "Lang/ES-SPANISH/Config.yml");
        mensagenses = new Config(this, "Lang/ES-SPANISH/Messages.yml");
        motdes = new Config(this, "Lang/ES-SPANISH/Motd.yml");

        config.saveDefaultConfig();
        mensagens.saveDefaultConfig();
        motd.saveDefaultConfig();

        configpt.saveDefaultConfig();
        mensagenspt.saveDefaultConfig();
        motdpt.saveDefaultConfig();

        configes.saveDefaultConfig();
        mensagenses.saveDefaultConfig();
        motdes.saveDefaultConfig();

        File folder = new File(getDataFolder(), "Server-Icon");
        File folder2 = new File(getDataFolder(), "Lang");
        if (!folder2.exists()) {
            folder2.mkdirs();
        }
        if (!folder.exists()) {
            folder.mkdirs();
        }
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
