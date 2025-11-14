package project.telegramfinancebot.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class ConsultMonthButtons {


    //INICIO CONSULTA//
    //BOTÕES COM QUAL TIPO DE CONSULTA DEPOIS DO MENU PRINCIPAL//
    public static InlineKeyboardMarkup createQueryTypeMenu(){
        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Consulta Geral"); //GERAL
        botao1.setCallbackData("GENERAL_QUERY");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Consulta com Filtro");
        botao2.setCallbackData("FILTER_BY_TYPE");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("<< Voltar");
        botao3.setCallbackData("VIEW_EXPENSES");

        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }
    //FIM CONSULTA//


    //INICIO FILTROS//
    //BOTÕES COM OS FILTROS PARA A CONSULTA POR TIPO (COM FILTRO)//
    public static InlineKeyboardMarkup createFilterMenu(){
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
        botao6.setCallbackData("CURRENT_MONTH");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3, botao4);
        List<InlineKeyboardButton> linha3 = List.of(botao5);
        List<InlineKeyboardButton> linha4 = List.of(botao6);

        tecladoTela.setKeyboard(List.of(linha1, linha2, linha3, linha4));

        return tecladoTela;
    }

    //BOTÕES COM OS FILTROS PARA A CONSULTA POR TIPO QUANDO ESTIVER NA LISTA GERAL (SEM FILTRO)//
    public static InlineKeyboardMarkup createGeneralFilterMenu(){
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
        botao6.setCallbackData("ALL_EXPENSES_LIST");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3, botao4);
        List<InlineKeyboardButton> linha3 = List.of(botao5);
        List<InlineKeyboardButton> linha4 = List.of(botao6);

        tecladoTela.setKeyboard(List.of(linha1, linha2, linha3, linha4));

        return tecladoTela;
    }

    //BOTÕES COM OS FILTROS PARA A CONSULTA POR TIPO QUANDO FINALIZAR CADASTRO (CADASTRO)//
    public static InlineKeyboardMarkup createRegistrationFilterMenu(){
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
        botao6.setCallbackData("REGISTER_TYPE");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3, botao4);
        List<InlineKeyboardButton> linha3 = List.of(botao5);
        List<InlineKeyboardButton> linha4 = List.of(botao6);

        tecladoTela.setKeyboard(List.of(linha1, linha2, linha3, linha4));

        return tecladoTela;
    }
    //FIM FILTROS//


    //INICIO RESULTADOS//
    //BOTÕES COM QUAL VAI SER O RETORNO DA CONSULTA FILTRADA (COM FILTRO)//
    public static InlineKeyboardMarkup createFilteredResultMenu(){
        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Lista Detalhada"); //GERAL
        botao1.setCallbackData("FILTERED_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Apenas Valores");
        botao2.setCallbackData("FILTERED_SUMMARY");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("<< Voltar");
        botao3.setCallbackData("FILTER_BY_TYPE");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM QUAL VAI SER O RETORNO DA CONSULTA GERAL (SEM FILTRO)//
    public static InlineKeyboardMarkup createGeneralResultMenu(){
        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Lista Detalhada");
        botao1.setCallbackData("ALL_EXPENSES_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Apenas Valores");
        botao2.setCallbackData("SUMMARY_ONLY");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("<< Voltar");
        botao3.setCallbackData("CURRENT_MONTH");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }
    //FIM RESULTADOS//

    //INICIO NAVEGAÇÃO//
    //BOTÕES COM O RETORNO DEPOIS DA LISTA FILTRADA (COM FILTRO)//
    public static InlineKeyboardMarkup createBackToFilteredListMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Outro Tipo");
        botao1.setCallbackData("FILTER_BY_TYPE");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Cancelar");
        botao2.setCallbackData("START");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM O RETORNO DEPOIS DO VALOR FILTRADO (COM FILTRO)//
    public static InlineKeyboardMarkup createBackToFilteredSummaryMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Ver Lista");
        botao1.setCallbackData("FILTERED_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Outro tipo");
        botao2.setCallbackData("FILTER_BY_TYPE");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM O RETORNO DEPOIS DA LISTA FILTRADA DEPOIS DE TER CADASTRADO (CADASTRO)//
    public static InlineKeyboardMarkup createBackToRegistrationMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Novo Cadastro");
        botao1.setCallbackData("REGISTER");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("<< Voltar");
        botao2.setCallbackData("POST_REGISTER_LIST");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1,linha2));

        return tecladoTela;
    }


    //BOTÕES COM O RETORNO DEPOIS DO VALOR GERAL (SEM FILTRO)//
    public static InlineKeyboardMarkup createBackToGeneralSummaryMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Ver Lista");
        botao1.setCallbackData("ALL_EXPENSES_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("<< Voltar");
        botao2.setCallbackData("GENERAL_QUERY");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM O RETORNO DEPOIS DA LISTA GERAL (SEM FILTRO)//
    public static InlineKeyboardMarkup createBackToGeneralListMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Filtrar Resultados");
        botao1.setCallbackData("CURRENT_MONTH_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("<< Voltar");
        botao2.setCallbackData("GENERAL_QUERY");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }



    //BOTÕES COM O RETORNO DEPOIS DA LISTA GERAL FILTRADA (SEM FILTRO)//
    public static InlineKeyboardMarkup createBackToFilteredListFromFullMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("<< Voltar");
        botao1.setCallbackData("CURRENT_MONTH_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Encerrar");
        botao2.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);

        tecladoTela.setKeyboard(List.of(linha1));

        return tecladoTela;
    }
    //FIM NAVEGAÇÃO//
}
