package br.computer.alimotd.commands;

import br.computer.alimotd.Alimotd;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import br.computer.alimotd.config.Messege;

import java.util.Objects;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.*;


public class MaintenanceCommand implements CommandExecutor {

    long countDownTime = 0;

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!s.hasPermission(Objects.requireNonNull(Alimotd.config.getConfig().getString("Permissoes.alimotd")))) {
            s.sendMessage((Messege.getmsg2("SemPerm")));
            return false;
        }
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("ativar")) {
                if (args.length > 1) {
                    try {
                        int time = 0;
                        if (args[1].endsWith("m")) {
                            time = Integer.parseInt(args[1].substring(0, args[1].length() - 1)) * 60;
                        } else if (args[1].endsWith("s")) {
                            time = Integer.parseInt(args[1].substring(0, args[1].length() - 1));
                        } else {
                            time = Integer.parseInt(args[1]);
                        }
                        countDownTime = time;
                        Bukkit.getScheduler().runTaskTimerAsynchronously(Alimotd.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                if (countDownTime == 900) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_15_Minutos"));
                                }
                                if (countDownTime == 600) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_10_Minutos"));
                                }
                                if (countDownTime == 420) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_7_Minutos"));
                                }
                                if (countDownTime == 300) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_5_Minutos"));
                                }
                                if (countDownTime == 120) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_2_Minutos"));
                                }
                                if (countDownTime == 60) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_1_Minutos"));
                                }
                                if (countDownTime == 30) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_30_Segundos"));
                                }
                                if (countDownTime == 10) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_10_Segundos"));
                                }
                                if (countDownTime == 5) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_5_Segundos"));
                                }
                                if (countDownTime == 4) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_4_Segundos"));
                                }
                                if (countDownTime == 3) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_3_Segundos"));
                                }
                                if (countDownTime == 2) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_2_Segundos"));
                                }
                                if (countDownTime == 1) {
                                    Bukkit.broadcastMessage(Messege.getmsg("Manutencao_1_Segundos"));
                                }
                                if (countDownTime == 0) {
                                    if (!Bukkit.hasWhitelist()) {
                                        for (Player pp : Bukkit.getOnlinePlayers()) {
                                            if (getWhitelistedPlayers().contains(pp) || pp.hasPermission((Objects.requireNonNull(Alimotd.config.getConfig().getString("Permissoes.perme_continuarnoservidor")))) || pp.isOp()) {
                                                pp.sendMessage("");
                                                pp.sendMessage((Messege.getmsg2("Manutencao_Aviso_Ativado")));
                                                pp.sendMessage("");
                                            } else {
                                                if (Alimotd.config.getConfig().getBoolean("Whitelist.kickplayerativar")) {
                                                    Bukkit.getScheduler().runTask(Alimotd.getInstance(), () -> {
                                                        pp.kickPlayer(Objects.requireNonNull(Messege.getmsg("Manutencao_Kick_Ativado")));
                                                    });
                                                }
                                            }
                                        }
                                        Bukkit.setWhitelist(true);
                                        s.sendMessage((Messege.getmsg2("Manutencao_Ativada")));
                                    } else {
                                        s.sendMessage((Messege.getmsg2("Manutencao_Ja_Ativada")));
                                    }
                                }
                                countDownTime--;
                            }
                        }, 0, 20);
                        s.sendMessage((Messege.getmsg2("Manutencao_Contagem")));
                        return true;
                    } catch (NumberFormatException e) {
                        s.sendMessage((Messege.getmsg2("Manutencao_Erro_Numero")));
                        return false;
                    }
                } else {
                    s.sendMessage((Messege.getmsg2("Manutencao_Tempo")));
                    return false;
                }
            }
            if (args[0].equalsIgnoreCase("desativar")) {
                if (Bukkit.hasWhitelist()) {
                    Bukkit.setWhitelist(false);
                    s.sendMessage((Messege.getmsg2("Manutencao_Desativada")));
                } else {
                    s.sendMessage((Messege.getmsg2("Manutencao_Ja_Desativada")));
                }
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length < 2) {
                    s.sendMessage((Messege.getmsg2("Manutencao_add")));
                    return true;
                }
                OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
                if (target.isWhitelisted()) {
                    s.sendMessage(Objects.requireNonNull(Alimotd.mensagens.getConfig().getString("Ja_Esta_Adicionado"))
                            .replace("&", "§")
                            .replace("{player}", Objects.requireNonNull(target.getName())));
                } else {
                    target.setWhitelisted(true);
                    s.sendMessage(Objects.requireNonNull(Alimotd.mensagens.getConfig().getString("Adicionado_Na_Lista"))
                            .replace("&", "§")
                            .replace("{player}", Objects.requireNonNull(target.getName())));
                }
            }
            if (args[0].equalsIgnoreCase("remover")) {
                if (args.length < 2) {
                    s.sendMessage((Messege.getmsg2("Manutencao_remover")));
                    return true;
                }
                OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
                if (target.isWhitelisted()) {
                    if (Alimotd.config.getConfig().getStringList("Whitelist.naoremover").contains(args[1])) {
                        s.sendMessage(Objects.requireNonNull(Alimotd.mensagens.getConfig().getString("Nao_Pode_Remover"))
                                .replace("&", "§")
                                .replace("{player}", Objects.requireNonNull(target.getName())));
                        return false;
                    }
                    if (Bukkit.hasWhitelist() && Alimotd.config.getConfig().getBoolean("Whitelist.removerplayerkick")) {
                        if (target.isOnline() && target.isWhitelisted()) {
                            Objects.requireNonNull(target.getPlayer()).kickPlayer((Messege.getmsg("Foi_Expulso_Remover")));
                        }
                    }
                    target.setWhitelisted(false);
                    s.sendMessage(Objects.requireNonNull(Alimotd.mensagens.getConfig().getString("Remivido_Da_lista"))
                            .replace("&", "§")
                            .replace("{player}", Objects.requireNonNull(target.getName())));
                } else {
                    s.sendMessage(Objects.requireNonNull(Alimotd.mensagens.getConfig().getString("Ja_Foi_Removido"))
                            .replace("&", "§")
                            .replace("{player}", Objects.requireNonNull(target.getName())));
                    return false;
                }
            }
            if (args[0].equalsIgnoreCase("lista")) {
                s.sendMessage(Objects.requireNonNull(Alimotd.mensagens.getConfig().getString("Lista_De_Manutencao")).replace("&", "§") +
                        StringUtils.join(getWhitelistedPlayers().stream().map(OfflinePlayer::getName).collect(Collectors.toList()), ", "));
                return false;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                Alimotd.config.reloadConfig();
                Alimotd.mensagens.reloadConfig();
                Alimotd.motd.reloadConfig();
                s.sendMessage("§6[ALIMOTD] §7Configurações recarregada com sucesso!");
            }
        }
        if (args.length == 0) {
            for (String texto : Alimotd.mensagens.getConfig().getStringList("Manutencao_Comando_incorreto")) {
                s.sendMessage(texto.replace("&", "§").replace("{command}", label));
            }
        }
        return false;
    }
}
