package project.telegramfinancebot.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class ConsultPeriodButtons {

    //INICIO CONSULTAS//
    //BOTÕES COM QUAL TIPO DE CONSULTA//
    public static InlineKeyboardMarkup createPeriodQueryStyleSelectionMenu(){
        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Consulta Geral"); //GERAL
        botao1.setCallbackData("PERIOD_GENERAL_QUERY");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Consulta por Categoria");
        botao2.setCallbackData("PERIOD_BY_TYPE_QUERY");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("<< Voltar");
        botao3.setCallbackData("VIEW_EXPENSES");

        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM QUAL VAI SER O RETORNO DA CONSULTA (SEM FILTRO)//
    public static InlineKeyboardMarkup createPeriodResultTypeMenu(){
        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Lista Detalhada");
        botao1.setCallbackData("PERIOD_ALL_EXPENSES_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Apenas Valor");
        botao2.setCallbackData("PERIOD_SUMMARY_ONLY");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Alterar Período");
        botao3.setCallbackData("PERIOD_GENERAL_QUERY");

        InlineKeyboardButton botao4 = new InlineKeyboardButton("Encerrar");
        botao4.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3, botao4);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM QUAL VAI SER O RETORNO DA CONSULTA (COM FILTRO)//
    public static InlineKeyboardMarkup createPeriodByTypeResultTypeMenu(){
        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Lista Detalhada");
        botao1.setCallbackData("PERIOD_BY_TYPE_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Apenas Valor");
        botao2.setCallbackData("PERIOD_BY_TYPE_SUMMARY");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Alterar Período");
        botao3.setCallbackData("PERIOD_BY_TYPE_QUERY");

        InlineKeyboardButton botao4 = new InlineKeyboardButton("Encerrar");
        botao4.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3, botao4);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }
    //FIM CONSULTAS//

    //INICIO FILTROS//
    //BOTÕES COM O RETORNO QUANDO VAI COLOCAR O PERIODO DE INCIO PARA FILTRAR
    public static InlineKeyboardMarkup createPeriodStartDateMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("<< Voltar");
        botao1.setCallbackData("BY_PERIOD");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Cancelar");
        botao2.setCallbackData("START");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> line1 = List.of(botao1);
        List<InlineKeyboardButton> line2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(line1, line2));

        return tecladoTela;
    }

    //BOTÕES COM O RETORNO QUANDO VAI COLOCAR O PERIODO FINAL PARA FILTRAR GERAL
    public static InlineKeyboardMarkup createPeriodEndDateMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Alterar Data Inicial");
        botao1.setCallbackData("PERIOD_GENERAL_QUERY");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Cancelar");
        botao2.setCallbackData("START");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM O RETORNO QUANDO VAI COLOCAR O PERIODO FINAL PARA FILTRAR POR TIPO
    public static InlineKeyboardMarkup createPeriodByTypeEndDateMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Alterar Data Inicial");
        botao1.setCallbackData("PERIOD_BY_TYPE_QUERY");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Cancelar");
        botao2.setCallbackData("START");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }
    //FIM FILTROS//

    //INICIO RESULTADOS//
    //BOTÕES COM OS FILTROS PARA A CONSULTA POR TIPO E PERIODO//
    public static InlineKeyboardMarkup createPeriodByTypeResultMenu(){
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

        InlineKeyboardButton botao6 = new InlineKeyboardButton("Alterar Período");
        botao6.setCallbackData("PERIOD_BY_TYPE_QUERY");

        InlineKeyboardButton botao7 = new InlineKeyboardButton("Encerrar");
        botao7.setCallbackData("CLOSE");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3, botao4);
        List<InlineKeyboardButton> linha3 = List.of(botao5);
        List<InlineKeyboardButton> linha4 = List.of(botao6, botao7);

        tecladoTela.setKeyboard(List.of(linha1, linha2, linha3, linha4));

        return tecladoTela;
    }

    //BOTÕRD COM OS FILTROS PARA A CONSULTA POR TIPO E PERIODO//
    public static InlineKeyboardMarkup createPeriodByTypeResultFromListMenu(){
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
        botao6.setCallbackData("PERIOD_ALL_EXPENSES_LIST");


        List<InlineKeyboardButton> linha1 = List.of(botao1, botao2);
        List<InlineKeyboardButton> linha2 = List.of(botao3, botao4);
        List<InlineKeyboardButton> linha3 = List.of(botao5);
        List<InlineKeyboardButton> linha4 = List.of(botao6);

        tecladoTela.setKeyboard(List.of(linha1, linha2, linha3, linha4));

        return tecladoTela;
    }
    //FIM RESULTADOS//

    //INICIO NAVEGAÇÃO//
    //BOTÕES COM O RETORNO DEPOIS DO RESULTADO GERAL APENAS VALOR (CONSULTA PERIODO)//
    public static InlineKeyboardMarkup createBackToPeriodSummaryMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Ver Lista Completa");
        botao1.setCallbackData("PERIOD_ALL_EXPENSES_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Alterar Período");
        botao2.setCallbackData("PERIOD_GENERAL_QUERY");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM O RETORNO DEPOIS DO RESULTADO GERAL LSITAGEM (CONSULTA PERIODO)//
    public static InlineKeyboardMarkup createBackToPeriodListMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Filtrar por Categoria");
        botao1.setCallbackData("PERIOD_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Alterar Período");
        botao2.setCallbackData("PERIOD_GENERAL_QUERY");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM O RETORNO DEPOIS DO RESULTADO FILTRADO APÓS SOLICITAR FILTRA DA PARTE DO RESULTADO GERAL LISTAGEM (CONSULTA PERIODO)//
    public static InlineKeyboardMarkup createBackToFilteredPeriodListMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Alterar Período");
        botao1.setCallbackData("PERIOD_GENERAL_QUERY");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("<< Voltar");
        botao2.setCallbackData("PERIOD_LIST");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM O RETORNO DEPOIS DO RESULTADO POR TIPO APENAS VALOR POR PERIODO (CONSULTA PERIODO POR TIPO)//
    public static InlineKeyboardMarkup createBackToPeriodByTypeSummaryMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Ver Lista Completa");
        botao1.setCallbackData("PERIOD_BY_TYPE_LIST");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("<< Voltar");
        botao2.setCallbackData("PERIOD_BY_TYPE_QUERY");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }

    //BOTÕES COM O RETORNO DEPOIS DO RESULTADO POR TIPO LISTAGEM POR PERIODO (CONSULTA PERIODO POR TIPO)//
    public static InlineKeyboardMarkup createBackToPeriodByTypeListMenu(){

        InlineKeyboardMarkup tecladoTela = new InlineKeyboardMarkup();

        InlineKeyboardButton botao1 = new InlineKeyboardButton("Alterar Período");
        botao1.setCallbackData("PERIOD_BY_TYPE_QUERY");

        InlineKeyboardButton botao2 = new InlineKeyboardButton("Voltar do Inicio");
        botao2.setCallbackData("START");

        InlineKeyboardButton botao3 = new InlineKeyboardButton("Encerrar");
        botao3.setCallbackData("CLOSE");

        List<InlineKeyboardButton> linha1 = List.of(botao1);
        List<InlineKeyboardButton> linha2 = List.of(botao2, botao3);

        tecladoTela.setKeyboard(List.of(linha1, linha2));

        return tecladoTela;
    }







}
