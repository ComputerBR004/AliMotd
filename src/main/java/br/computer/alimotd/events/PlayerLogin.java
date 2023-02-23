package br.computer.alimotd.events;

import br.computer.alimotd.Alimotd;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class PlayerLogin implements Listener {

    @EventHandler
    public void login(PlayerLoginEvent e) {
        if (e.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
            Calendar maintenanceDate = Calendar.getInstance();
            int ano = Alimotd.config.getConfig().getInt("KickManutencao.year");
            int mes = Alimotd.config.getConfig().getInt("KickManutencao.month");
            int dia = Alimotd.config.getConfig().getInt("KickManutencao.day");
            int horas = Alimotd.config.getConfig().getInt("KickManutencao.hours");
            int minutos = Alimotd.config.getConfig().getInt("KickManutencao.minutes");
            int segundos = 0;
            switch (mes) {
                case 1:
                    mes = Calendar.JANUARY;
                    break;
                case 2:
                    mes = Calendar.FEBRUARY;
                    break;
                case 3:
                    mes = Calendar.MARCH;
                    break;
                case 4:
                    mes = Calendar.APRIL;
                    break;
                case 5:
                    mes = Calendar.MAY;
                    break;
                case 6:
                    mes = Calendar.JUNE;
                    break;
                case 7:
                    mes = Calendar.JULY;
                    break;
                case 8:
                    mes = Calendar.AUGUST;
                    break;
                case 9:
                    mes = Calendar.SEPTEMBER;
                    break;
                case 10:
                    mes = Calendar.OCTOBER;
                    break;
                case 11:
                    mes = Calendar.NOVEMBER;
                    break;
                case 12:
                    mes = Calendar.DECEMBER;
                    break;

            }

            maintenanceDate.set(ano, mes, dia, horas, minutos, segundos);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    boolean manutencaoativada = Alimotd.config.getConfig().getBoolean("KickManutencao.desativar_manutencao");
                    if (manutencaoativada){
                        Bukkit.setWhitelist(false);
                    }

                }
            }, maintenanceDate.getTime());


            Calendar now = Calendar.getInstance();
            long diff = maintenanceDate.getTimeInMillis() - now.getTimeInMillis();


            long hours = TimeUnit.MILLISECONDS.toHours(diff);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(hours);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours);


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            List<String> messages = Alimotd.config.getConfig().getStringList("KickManutencao.kick_mensagem_entrada");
            StringBuilder messageBuilder = new StringBuilder();
            for (String message : messages) {
                message = message.replace("{hours}", String.valueOf(hours))
                        .replace("{minutes}", String.valueOf(minutes))
                        .replace("{seconds}", String.valueOf(seconds))
                        .replace("{date}", dateFormat.format(maintenanceDate.getTime()))
                        .replace("&", "§");
                messageBuilder.append(message).append("\n");
            }
            String errorMessage = messageBuilder.toString();
            e.setKickMessage(errorMessage);
        }
    }
}
