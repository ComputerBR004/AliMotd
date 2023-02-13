package br.computer.alimotd.config;

import br.computer.alimotd.Alimotd;

import java.util.Objects;

public class Messege {

    public static String getmsg(String Chave) {
        String mensagemFormatada;
        switch (Chave) {
            case "Manutencao_15_Minutos":
            case "Manutencao_10_Minutos":
            case "Manutencao_7_Minutos":
            case "Manutencao_5_Minutos":
            case "Manutencao_2_Minutos":
            case "Manutencao_1_Minutos":
            case "Manutencao_30_Segundos":
            case "Manutencao_10_Segundos":
            case "Manutencao_5_Segundos":
            case "Manutencao_4_Segundos":
            case "Manutencao_3_Segundos":
            case "Manutencao_2_Segundos":
            case "Manutencao_1_Segundos":
            case "Manutencao_Kick_Ativado":
            case "Foi_Expulso_Remover":
            case "Manutencao_Erro_Ao_Entrar":
                mensagemFormatada = Objects.requireNonNull(Alimotd.mensagens.getConfig().getString(Chave))
                        .replace("&", "§")
                        .replace("{nl}", "\n");
                break;
            default:
                mensagemFormatada = "§cMensagem não encontrada, verifique os arquivos de configuração";
                break;
        }
        return mensagemFormatada;
    }
    public static String getmsg2(String Chave) {
        String mensagemFormatada;
        switch (Chave) {
            case "SemPerm":
            case "Manutencao_Aviso_Ativado":
            case "Manutencao_Ativada":
            case "Manutencao_Ja_Ativada":
            case "Manutencao_Contagem":
            case "Manutencao_Erro_Numero":
            case "Manutencao_Tempo":
            case "Manutencao_Desativada":
            case "Manutencao_Ja_Desativada":
            case "Manutencao_add":
            case "Manutencao_remover":
                mensagemFormatada = Objects.requireNonNull(Alimotd.mensagens.getConfig().getString(Chave))
                        .replace("&", "§");
                break;
            default:
                mensagemFormatada = "§cMensagem não encontrada, verifique os arquivos de configuração";
                break;
        }
        return mensagemFormatada;
    }
}
