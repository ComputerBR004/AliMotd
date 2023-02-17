package br.computer.alimotd.motd;

import br.computer.alimotd.Alimotd;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import org.bukkit.Bukkit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;



public class Motd {

    public static void handlePing(WrappedServerPing ping) {
        boolean estatico = Alimotd.motd.getConfig().getBoolean("Motd.ativarmotdestatico");
        boolean estaticomanu = Alimotd.motd.getConfig().getBoolean("Motd_Manutencao.ativarmotdestatico");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int diasdasemana = cal.get(Calendar.DAY_OF_WEEK);
        String motd = "";
        String motdmanutencao = "";

        ping.setVersionName(Objects.requireNonNull(Alimotd.mensagens.getConfig().getString("Versao_Errada")).replace("&", "§"));
        if (Alimotd.config.getConfig().getBoolean("FakeAumento.ativar")) {
            ping.setPlayersMaximum(Alimotd.config.getConfig().getInt("FakeAumento.quantidade") + 1);
        }
        if (Alimotd.config.getConfig().getBoolean("FakeOnline.ativar")) {
            ping.setPlayersOnline(Alimotd.config.getConfig().getInt("FakeOnline.quantidade"));
        }
        Random gerador = new Random();
        if (Bukkit.hasWhitelist()) {
            String serverIconUrl = Alimotd.motd.getConfig().getString("Motd_Manutencao.server-icon-url");
            if (serverIconUrl != null && !serverIconUrl.isEmpty()) {
                try {
                    URL url = new URL(serverIconUrl);
                    BufferedImage image = ImageIO.read(url);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", byteArrayOutputStream);
                    byte[] data = byteArrayOutputStream.toByteArray();
                    ping.setFavicon(WrappedServerPing.CompressedImage.fromPng(data));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                File serverIcon = new File("plugins/AliMOTD/Server-Icon", Objects.requireNonNull(Alimotd.motd.getConfig().getString("Motd_Manutencao.server-icon-name")));
                if (serverIcon.exists()) {
                    try {
                        BufferedImage image = ImageIO.read(serverIcon);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ImageIO.write(image, "png", byteArrayOutputStream);
                        byte[] data = byteArrayOutputStream.toByteArray();
                        ping.setFavicon(WrappedServerPing.CompressedImage.fromPng(data));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (Alimotd.config.getConfig().getBoolean("FakeMaximoJogadoresManutencao.ativar")) {
                ping.setPlayersMaximum(Alimotd.config.getConfig().getInt("FakeMaximoJogadoresManutencao.quantidade"));
            }
            if (estaticomanu) {
                ping.setMotD(Methods.replace(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.motd_estatico")
                        .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.motd_estatico").size()))));
            } else {
                switch (diasdasemana) {
                    case Calendar.MONDAY:
                        motdmanutencao = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.segunda-feira")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.segunda-feira").size())));
                        break;
                    case Calendar.TUESDAY:
                        motdmanutencao = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.terca-feira")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.terca-feira").size())));
                        break;
                    case Calendar.WEDNESDAY:
                        motdmanutencao = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.quarta-feira")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.quarta-feira").size())));
                        break;
                    case Calendar.THURSDAY:
                        motdmanutencao = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.quinta-feira")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.quinta-feira").size())));
                        break;
                    case Calendar.FRIDAY:
                        motdmanutencao = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.sexta-feira")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.sexta-feira").size())));
                        break;
                    case Calendar.SATURDAY:
                        motdmanutencao = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.sabado")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.sabado").size())));
                        break;
                    case Calendar.SUNDAY:
                        motdmanutencao = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.domingo")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd_Manutencao.domingo").size())));
                        break;
                }
                ping.setMotD(motdmanutencao);
            }
            if (Alimotd.config.getConfig().getBoolean("PlayerListPing_Manutencao.ligado_Manutencao")) {
                List<WrappedGameProfile> list = new ArrayList<>();
                for (String pName : Alimotd.config.getConfig().getStringList("PlayerListPing_Manutencao.fakePlayers_Manutencao"))
                    list.add(new WrappedGameProfile(UUID.randomUUID(), Methods.hex(Methods.replace(pName))));
                ping.setPlayers(list);
                ping.setPlayersVisible(true);
            }
        } else {
            String serverIconUrl = Alimotd.motd.getConfig().getString("Motd.server-icon-url");
            if (serverIconUrl != null && !serverIconUrl.isEmpty()) {
                try {
                    URL url = new URL(serverIconUrl);
                    BufferedImage image = ImageIO.read(url);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", byteArrayOutputStream);
                    byte[] data = byteArrayOutputStream.toByteArray();
                    ping.setFavicon(WrappedServerPing.CompressedImage.fromPng(data));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                File serverIcon = new File("plugins/AliMOTD/Server-Icon", Objects.requireNonNull(Alimotd.motd.getConfig().getString("Motd.server-icon-name")));
                if (serverIcon.exists()) {
                    try {
                        BufferedImage image = ImageIO.read(serverIcon);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ImageIO.write(image, "png", byteArrayOutputStream);
                        byte[] data = byteArrayOutputStream.toByteArray();
                        ping.setFavicon(WrappedServerPing.CompressedImage.fromPng(data));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (Alimotd.config.getConfig().getBoolean("FakeMaximoJogadores.ativar")) {
                ping.setPlayersMaximum(Alimotd.config.getConfig().getInt("FakeMaximoJogadores.quantidade"));
            }
            if (estatico) {
                ping.setMotD(Methods.replace(Alimotd.motd.getConfig().getStringList("Motd.motd_estatico")
                        .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd.motd_estatico").size()))));
            } else {
                switch (diasdasemana) {
                    case Calendar.MONDAY:
                        motd = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd.segunda-feira")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd.segunda-feira").size())));
                        break;
                    case Calendar.TUESDAY:
                        motd = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd.terca-feira")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd.terca-feira").size())));
                        break;
                    case Calendar.WEDNESDAY:
                        motd = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd.quarta-feira")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd.quarta-feira").size())));
                        break;
                    case Calendar.THURSDAY:
                        motd = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd.quinta-feira")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd.quinta-feira").size())));
                        break;
                    case Calendar.FRIDAY:
                        motd = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd.sexta-feira")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd.sexta-feira").size())));
                        break;
                    case Calendar.SATURDAY:
                        motd = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd.sabado")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd.sabado").size())));
                        break;
                    case Calendar.SUNDAY:
                        motd = Methods.replace(Alimotd.motd.getConfig().getStringList("Motd.domingo")
                                .get(gerador.nextInt(Alimotd.motd.getConfig().getStringList("Motd.domingo").size())));
                        break;
                }
                ping.setMotD(motd);
                if (Alimotd.config.getConfig().getBoolean("PlayerListPing.ligado")) {
                    List<WrappedGameProfile> list = new ArrayList<>();
                    for (String pName : Alimotd.config.getConfig().getStringList("PlayerListPing.fakePlayers"))
                        list.add(new WrappedGameProfile(UUID.randomUUID(), Methods.hex(Methods.replace(pName))));
                    ping.setPlayers(list);
                    ping.setPlayersVisible(true);
                }
            }
        }
    }
}
