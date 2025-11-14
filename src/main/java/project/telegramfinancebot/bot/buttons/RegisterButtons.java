package project.telegramfinancebot.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class RegisterButtons {

    //TIPOS PARA O CADASTRO//
    public static InlineKeyboardMarkup createRegistrationTypeMenu(){
        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Mercado");
        botao1.setCallbackData("MERCADO");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Lazer");
        botao2.setCallbackData("LAZER");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Gasolina");
        botao3.setCallbackData("GASOLINA");

        InlineKeyboardButton botao4 = new InlineKeyboardButton("Farmacia");
        botao4.setCallbackData("FARMACIA");

        InlineKeyboardButton botao5 = new InlineKeyboardButton("Outros");
        botao5.setCallbackData("OUTROS");

        InlineKeyboardButton botao6 = new InlineKeyboardButton("<< Voltar");
        botao6.setCallbackData("START");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3, botao4);
        List<InlineKeyboardButton> linha3 = List.of(botao5);
        List<InlineKeyboardButton> linha4 = List.of(botao6);

        tecladoTela.setKeyboard(List.of(linha1, linha2, linha3, linha4));

        return tecladoTela;
    }

    //BOTÕES DA LISTAGEM DEPOIS DO CADASTRO//
    public static InlineKeyboardMarkup createBackToValueInputMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Alterar Tipo");
        botao1.setCallbackData("REGISTER");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Cancelar Cadastro");
        botao2.setCallbackData("START");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);

        tecladoTela.setKeyboard(List.of(linha1));

        return tecladoTela;
    }


    //BOTÕES DEPOIS DO GASTO CADASTRADO//
    public static InlineKeyboardMarkup createPostRegistrationMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Ver Gastos");
        botao1.setCallbackData("REGISTER_TYPE");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Novo Gasto");
        botao2.setCallbackData("REGISTER");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES DA LISTAGEM DEPOIS DO CADASTRO//
    public static InlineKeyboardMarkup createPostRegistrationListMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Filtrar Gastos");
        botao1.setCallbackData("POST_REGISTER_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Editar Gastos");
        botao2.setCallbackData("EDIT");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Novo Gasto");
        botao3.setCallbackData("REGISTER");

        InlineKeyboardButton botao4 = new InlineKeyboardButton("Encerrar");
        botao4.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);
        List<InlineKeyboardButton> linha3 = List.of(botao4);

        tecladoTela.setKeyboard(List.of(linha1, linha2, linha3));

        return tecladoTela;
    }

}
