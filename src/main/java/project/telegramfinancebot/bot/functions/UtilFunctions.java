package project.telegramfinancebot.bot.functions;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.telegramfinancebot.entity.Gasto;
import project.telegramfinancebot.entity.GastoMes;
import project.telegramfinancebot.service.GastoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class UtilFunctions {


    //INICIO MENSAGENS//
    //Edição da mensagem
    public static void editMessageWithButtons(TelegramLongPollingBot bot, String chatId, Integer messageId,
                                              String texto, InlineKeyboardMarkup teclado){

        try {
            EditMessageText mensagem = new EditMessageText();
            mensagem.setChatId(chatId);
            mensagem.setMessageId(messageId);
            mensagem.setText(texto);
            mensagem.setReplyMarkup(teclado);
            mensagem.setParseMode("Markdown");

            bot.execute(mensagem);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Erro ao editar mensagem: " + e.getMessage(), e);
        }

    }

    // nova mensagem com botão
    public static void sendNewMessageWithButtons(TelegramLongPollingBot bot, String chatId,
                                                 String texto, InlineKeyboardMarkup teclado){

        try {
            SendMessage mensagem = new SendMessage();
            mensagem.setChatId(chatId);
            mensagem.setText(texto);
            mensagem.setReplyMarkup(teclado);
            mensagem.setParseMode("Markdown");

            bot.execute(mensagem);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Erro ao editar mensagem: " + e.getMessage(), e);
        }

    }


    // Envio de mensagem para registro de messageId para substituição posterior
    public static Integer sendMessageWithButtonsAndReturnId(TelegramLongPollingBot bot, String chatId,
                                                            String mensagem, InlineKeyboardMarkup botoes) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(mensagem);

            if (botoes != null) {
                message.setReplyMarkup(botoes);
            }


            Message mensagemEnviada = bot.execute(message);
            return mensagemEnviada.getMessageId();

        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }
    //FIM MENSAGENS//

    //INICIO FORMATAÇÃO DE RELATORIOS//
    //listar gastos mes atual sem filtro
    public static String formatExpenseList(GastoService gastoService){
        List<Gasto> gastos = gastoService.listarTodosGastos();

        StringBuilder resposta = new StringBuilder();
        resposta.append("📊 **SEUS GASTOS:**\n\n");

        for (Gasto gasto : gastos) {

            resposta.append("🏷️ **Tipo:** ").append(gasto.getTipo()).append("\n");
            resposta.append("📅 **Data:** ").append(formatDateForDisplay(gasto.getData())).append("\n");
            resposta.append("💵 **Valor:** R$ ").append(String.format("%.2f", gasto.getValor())).append("\n");
            resposta.append("───────────────\n");
        }

        BigDecimal total = gastos.stream().map(Gasto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        resposta.append("\n💳 **TOTAL:** R$ ").append(String.format("%.2f", total));

        return resposta.toString();
    }

    //Listar gastos Mes atual apenas valores sem filtro
    public static String formatExpenseSummary(GastoService gastoService){
        List<Gasto> gastos = gastoService.listarTodosGastos();

        StringBuilder resposta = new StringBuilder();
        resposta.append("📊 **VALOR TOTAL DOS SEUS GASTOS:**\n\n");

        BigDecimal total = gastos.stream().map(Gasto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        resposta.append("\n💳 **TOTAL:** R$ ").append(String.format("%.2f", total));

        return resposta.toString();
    }

    //listar gastos mes atual com filtro
    public static String formatExpenseListByType(GastoService gastoService, String tipo) {
        List<Gasto> todosGastos = gastoService.listarTodosGastos();

        StringBuilder resposta = new StringBuilder();
        resposta.append("📊 **GASTOS - ").append(tipo.toUpperCase()).append("**\n\n");

        BigDecimal total = BigDecimal.ZERO;
        int count = 0;

        for (Gasto gasto : todosGastos) {
            if (gasto.getTipo() != null && gasto.getTipo().equalsIgnoreCase(tipo)) {
                resposta.append("🏷️ **Tipo:** ").append(gasto.getTipo()).append("\n");
                resposta.append("📅 **Data:** ").append(formatDateForDisplay(gasto.getData())).append("\n");
                resposta.append("💵 **Valor:** R$ ").append(String.format("%.2f", gasto.getValor())).append("\n");
                resposta.append("───────────────\n");

                total = total.add(gasto.getValor());
                count++;
            }
        }

        if (count == 0) {
            resposta.append("📭 Nenhum gasto encontrado para ").append(tipo);
        } else {
            resposta.append("\n💳 **TOTAL ").append(tipo.toUpperCase()).append(":** R$ ")
                    .append(String.format("%.2f", total));
        }

        return resposta.toString();
    }

    //apenar valor gastos mes atual com filtro
    public static String formatExpenseSummaryByType(GastoService gastoService, String tipo) {
        List<Gasto> todosGastos = gastoService.listarTodosGastos();

        BigDecimal total = BigDecimal.ZERO;
        int quantidade = 0;

        for (Gasto gasto : todosGastos) {
            if (gasto.getTipo() != null && gasto.getTipo().equalsIgnoreCase(tipo)) {
                total = total.add(gasto.getValor());
                quantidade++;
            }
        }

        StringBuilder resposta = new StringBuilder();
        resposta.append("💰 *VALOR TOTAL GASTO COM - ").append(tipo.toUpperCase()).append("*\n\n");

        if (quantidade == 0) {
            resposta.append("📭 Nenhum gasto encontrado para ").append(tipo);
        } else {
            resposta.append("💳 **Total:** R$ ").append(String.format("%.2f", total));
        }

        return resposta.toString();
    }

    //listar gastos por periodo sem FILTRO
    public static String formatPeriodExpenseList(List<GastoMes> gastoMes) {
        if (gastoMes.isEmpty()) {
            return "📭 Nenhum gasto encontrado neste período.";
        }

        StringBuilder resposta = new StringBuilder();
        resposta.append("📊 **GASTOS DO PERÍODO:**\n\n");

        BigDecimal total = BigDecimal.ZERO;

        for (GastoMes gasto : gastoMes) {
            resposta.append("🏷️ **Tipo:** ").append(gasto.getTipo()).append("\n");
            resposta.append("📅 **Data:** ").append(formatDateForDisplay(gasto.getData())).append("\n");
            resposta.append("💵 **Valor:** R$ ").append(gasto.getValor()).append("\n");
            resposta.append("───────────────\n");

            total = total.add(gasto.getValor());
        }

        resposta.append("\n💳 **TOTAL:** R$ ").append(total);

        return resposta.toString();
    }

    //apenas valor por periodo sem FILTRO
    public static String formatPeriodExpenseSummary(List<GastoMes> gastoMes) {
        if (gastoMes.isEmpty()) {
            return "📭 Nenhum gasto encontrado neste período.";
        }

        StringBuilder resposta = new StringBuilder();
        resposta.append("📊 **VALOR TOTAL DOS SEUS GASTOS:**\n\n");

        BigDecimal total = BigDecimal.ZERO;

        for (GastoMes gasto : gastoMes) {
            total = total.add(gasto.getValor());
        }

        resposta.append("\n💳 **TOTAL:** R$ ").append(total);

        return resposta.toString();
    }

    //apenas valor por periodo com filtro
    public static String formatPeriodExpenseSummaryByType(List<GastoMes> gastoMes, String tipo) {

        if (gastoMes.isEmpty()) {
            return "📭 Nenhum gasto encontrado neste período.";
        }

        BigDecimal total = BigDecimal.ZERO;
        int quantidade = 0;

        for (GastoMes gasto : gastoMes) {
            if (gasto.getTipo() != null && gasto.getTipo().equalsIgnoreCase(tipo)) {
                total = total.add(gasto.getValor());
                quantidade++;
            }
        }

        StringBuilder resposta = new StringBuilder();
        resposta.append("💰 *VALOR TOTAL GASTO COM - ").append(tipo.toUpperCase()).append("*\n\n");

        if (quantidade == 0) {
            resposta.append("📭 Nenhum gasto encontrado para ").append(tipo);
        } else {
            resposta.append("💳 **Total:** R$ ").append(String.format("%.2f", total));
        }

        return resposta.toString();
    }

    //listagem por periodo com filtro
    public static String formatPeriodExpenseListByType(List<GastoMes> gastoMes, String tipo) {
        if (gastoMes.isEmpty()) {
            return "📭 Nenhum gasto encontrado neste período.";
        }

        StringBuilder resposta = new StringBuilder();
        BigDecimal total = BigDecimal.ZERO;
        int count = 0;

        // Primeiro filtra e conta
        for (GastoMes gasto : gastoMes) {
            if (gasto.getTipo() != null && gasto.getTipo().equalsIgnoreCase(tipo)) {
                total = total.add(gasto.getValor());
                count++;
            }
        }

        if (count == 0) {
            return "📭 Nenhum gasto do tipo '" + tipo + "' encontrado neste período.";
        }

        resposta.append("📊 **GASTOS DO TIPO - ").append(tipo.toUpperCase()).append("**\n\n");

        for (GastoMes gasto : gastoMes) {
            if (gasto.getTipo() != null && gasto.getTipo().equalsIgnoreCase(tipo)) {
                resposta.append("🏷️ **Tipo:** ").append(gasto.getTipo()).append("\n");
                resposta.append("📅 **Data:** ").append(formatDateForDisplay(gasto.getData())).append("\n");
                resposta.append("💵 **Valor:** R$ ").append(String.format("%.2f", gasto.getValor())).append("\n");
                resposta.append("───────────────\n");
            }
        }

        resposta.append("💳 **TOTAL:** R$ ").append(String.format("%.2f", total));

        return resposta.toString();
    }
    //FIM FORMATAÇÃO DE RELATORIOS//



    //INICIO FORMATAÇÃO DE DADOS//
    //Formatar data para apresentação na mensagem
    private static String formatDateForDisplay(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return data.format(formatter);
    }
    //FIM FORMATAÇÃO DE DADOS//
}
