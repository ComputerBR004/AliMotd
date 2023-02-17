package br.computer.alimotd.config;

import br.computer.alimotd.Alimotd;



public class Messege {

    public static String getmsg(String Chave) {
        String mensagem = Alimotd.mensagens.getConfig().getString(Chave);
        if (mensagem == null) {
            return "§cMensagem não encontrada, verifique os arquivos de configuração";
        }
        return mensagem.replace("&", "§").replace("{nl}", "\n");
    }
    public static String getmsg2(String Chave) {
        String mensagem = Alimotd.mensagens.getConfig().getString(Chave);
        if (mensagem == null) {
            return "§cMensagem não encontrada, verifique os arquivos de configuração";
        }
        return mensagem.replace("&", "§");
    }
}
