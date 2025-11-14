package project.telegramfinancebot.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class InitiateButtons {

    //INICIO DO BOT//
    public static InlineKeyboardMarkup createStartButton(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Iniciar");
        botao1.setCallbackData("START");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Encerrar");
        botao2.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);

        tecladoTela.setKeyboard(List.of(linha1));

        return tecladoTela;
    }

    //MENU PRINCIPAL APÓS INICIO//
    public static InlineKeyboardMarkup createMainMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Ver gastos");
        botao1.setCallbackData("VIEW_EXPENSES");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Cadastrar gasto");
        botao2.setCallbackData("REGISTER");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Editar Gastos");
        botao3.setCallbackData("EDIT");

        InlineKeyboardButton botao4 = new InlineKeyboardButton("Excluir Gastos");
        botao4.setCallbackData("DELETE");

        InlineKeyboardButton botao5 = new InlineKeyboardButton("<< Voltar");
        botao5.setCallbackData("MAIN_MENU");

        InlineKeyboardButton botao6 = new InlineKeyboardButton("Encerrar");
        botao6.setCallbackData("CLOSE");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3, botao4);
        List<InlineKeyboardButton> linha3 = List.of(botao5, botao6);

        tecladoTela.setKeyboard(List.of(linha1, linha2, linha3));

        return tecladoTela;
    }

    //SELECIONAR UM DOS DOIS TIPOS DE CONSULTAS//
    public static InlineKeyboardMarkup createQueryTypeSelectionMenu(){
        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Mês Atual");
        botao1.setCallbackData("CURRENT_MONTH");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Por Periodo");
        botao2.setCallbackData("BY_PERIOD");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("<< Voltar");
        botao3.setCallbackData("START");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }
}
