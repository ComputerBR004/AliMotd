package br.computer.alimotd.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Alimotdtabcomplete implements TabCompleter {

    private List<String> subCommands = new ArrayList<String>() {{
        add("ativar");
        add("desativar");
        add("reload");
        add("lista");
        add("add");
        add("remover");
    }};

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            return subCommands;
        } else if (args.length == 2 && args[0].equals("add")) {
            List<String> players = new ArrayList<>();
            for (Player player : s.getServer().getOnlinePlayers()) {
                players.add(player.getName());
            }
            return players;
        } else if (args.length == 2 && args[0].equals("remover")) {
            List<String> authorizedPlayers = new ArrayList<>();
            return authorizedPlayers;
        }
        return null;
    }
}

