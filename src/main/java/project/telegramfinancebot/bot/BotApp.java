package project.telegramfinancebot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import project.telegramfinancebot.bot.buttons.ConsultMonthButtons;
import project.telegramfinancebot.bot.buttons.ConsultPeriodButtons;
import project.telegramfinancebot.bot.buttons.InitiateButtons;
import project.telegramfinancebot.bot.buttons.RegisterButtons;
import project.telegramfinancebot.bot.functions.UtilFunctions;
import project.telegramfinancebot.entity.Gasto;
import project.telegramfinancebot.entity.GastoMes;
import project.telegramfinancebot.service.GastoMesService;
import project.telegramfinancebot.service.GastoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class BotApp extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUserName;

    @Autowired
    private GastoService gastoService;

    @Autowired
    private GastoMesService gastoMesService;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    // Mapa para controlar o estado de cada chat
    private Map<String, String> chatStates = new HashMap<>();
    
    //Mapas para guardar o inicio do periodo de busca ao fazer as consultas
    private Map<String, String> temporaryStartDates = new HashMap<>();

    //Mapas para guardar o fim do periodo de busca ao fazer as consultas
    private Map<String, String> temporaryEndDates = new HashMap<>();
    Gasto expense = new Gasto();

    //lista para guardar a lista com o periodo da nossa busca por tipo
    private List<GastoMes> periodExpensesByType;

    //String para guardar qual o tipo que ele quis para usar em um segundo momento
    private String selectionQueryType;

    //Mapa para guardar os messageId para substituição
    private Map<String, Integer> periodButtonMessageIds = new HashMap<>();



    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String usuario = update.getMessage().getFrom().getFirstName();

            if (chatStates.containsKey(chatId)) {
            } else {

                UtilFunctions.sendNewMessageWithButtons(this, chatId,
                        "👋 Olá " + usuario + "!\nClique em *Iniciar* para começar!", InitiateButtons.createStartButton());
            }
        }

        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            String usuario = update.getCallbackQuery().getFrom().getFirstName();
            String estado = chatStates.get(chatId);

            switch (callbackData) {
                //->INICIO SEÇÃO OPÇÕES NO MENU PRINCIPAL DO BOT<-//
                case "START": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "✅ Bot iniciado!\nEscolha uma opção abaixo:", InitiateButtons.createMainMenu());
                    break;

                //ESTE CALLBACK APENAS PARA TER O RETORNO DAS OPÇÕES DEPOIS DE JA TER INICIADO
                case "MAIN_MENU": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "👋 Olá " + usuario + "!\nClique em *Iniciar* para começar!", InitiateButtons.createStartButton());
                    break;

                case "VIEW_EXPENSES": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📊 Como você quer consultar seus gastos?", InitiateButtons.createQueryTypeSelectionMenu());
                    break;

                case "REGISTER": //OK

                    //TIRAR TECLADO MENSAGEM ANTERIOR
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "✅ Bot iniciado!\nEscolha uma opção abaixo:", null);


                    UtilFunctions.sendNewMessageWithButtons(this, chatId,
                            "📝 Iniciando cadastro de gasto...", null);

                    Integer novaMessageId = UtilFunctions.sendMessageWithButtonsAndReturnId(this, chatId,
                            "📝 Selecione a categoria do gasto:", RegisterButtons.createRegistrationTypeMenu());

                    periodButtonMessageIds.put(chatId, novaMessageId);
                    chatStates.put(chatId, "AWAITING_TYPE");
                    break;

//                case "EDIT":
//                    break;

//                case "DELETE":
//                    break;

                //CALLBACK DE ENCERRAMENTO PARA QUEM QUISER CHAMAR
                case "CLOSE": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "👋 Bot encerrado!\nEnvie qualquer mensagem para iniciar novamente.", null);
                    chatStates.remove(chatId);
                    break;
                //->FIM SEÇÃO OPÇÕES NO MENU PRINCIPAL DO BOT<-//


                //->INICIO OPÇÕES DENTRO DE "VER_GASTOS"<-//
                case "CURRENT_MONTH": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📈 Escolha o tipo de consulta:", ConsultMonthButtons.createQueryTypeMenu());
                    break;

                case "BY_PERIOD": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📈 Escolha o tipo de consulta:", ConsultPeriodButtons.createPeriodQueryStyleSelectionMenu());
                    break;

                    //-->INICIO CONSULTA DENTRO DE "MES_ATUAL"<--//
                case "GENERAL_QUERY": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Consulta do mês atual\nSelecione o formato:", ConsultMonthButtons.createGeneralResultMenu());
                    break;

                case "ALL_EXPENSES_LIST": //OK
                    UtilFunctions.formatExpenseList(gastoService);
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseList(gastoService), ConsultMonthButtons.createBackToGeneralListMenu());
                    break;

                case "SUMMARY_ONLY": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseSummary(gastoService), ConsultMonthButtons.createBackToGeneralSummaryMenu());
                    break;

                case "CURRENT_MONTH_LIST": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "🔍 Selecione o tipo para filtrar:", ConsultMonthButtons.createGeneralFilterMenu());

                    chatStates.put(chatId, "BACK_TO_CURRENT_MONTH_LIST");
                    break;
                        //--->INICIO SEÇÃO CONSULTA FILTRADA DENTRO DE "MES_ATUAL"<---//
                case "FILTER_BY_TYPE": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "🔍 Selecione o tipo para filtrar:", ConsultMonthButtons.createFilterMenu());

                    chatStates.put(chatId, "BACK_TO_FILTER");

                case "FILTERED_LIST": //OK

                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, selectionQueryType),
                            ConsultMonthButtons.createBackToFilteredListMenu());

                    selectionQueryType = null;
                    break;

                case "FILTERED_SUMMARY": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseSummaryByType(gastoService, selectionQueryType),
                            ConsultMonthButtons.createBackToFilteredSummaryMenu());
                    break;
                        //--->FIM SEÇÃO CONSULTA FILTRADA DENTRO DE "MES_ATUAL"<---//
                    //-->FIM CONSULTA DENTRO DE "MES_ATUAL"<--//

                    //-->INICIO CONSULTA DENTRO DE "POR PERIODO"<--//
                case "PERIOD_GENERAL_QUERY": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Digite a data de início (dd-mm-aaaa):\nOu selecione uma opção abaixo ⬇️",
                            ConsultPeriodButtons.createPeriodStartDateMenu());

                    periodButtonMessageIds.put(chatId, messageId);
                    chatStates.put(chatId, "AWAITING_START_DATE");
                    break;

                case "PERIOD_ALL_EXPENSES_LIST": //OK

                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatPeriodExpenseList(periodExpensesByType), ConsultPeriodButtons.createBackToPeriodListMenu());

                    break;

                case "PERIOD_SUMMARY_ONLY": //OK

                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatPeriodExpenseSummary(periodExpensesByType), ConsultPeriodButtons.createBackToPeriodSummaryMenu());

                    break;

                case "PERIOD_LIST": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "🔍 Selecione o tipo para filtrar:", ConsultPeriodButtons.createPeriodByTypeResultFromListMenu());

                    chatStates.put(chatId, "BACK_TO_FILTERED_PERIOD_LIST");
                    break;


                            //--->INICIO SEÇÃO CONSULTA FILTRADA DENTRO DE "POR_PERIODO"<---//
                case "PERIOD_BY_TYPE_QUERY": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Digite a data de início (dd-mm-aaaa):\nOu selecione uma opção abaixo ⬇️",
                            ConsultPeriodButtons.createPeriodStartDateMenu());

                    periodButtonMessageIds.put(chatId, messageId);
                    chatStates.put(chatId, "AWAITING_START_DATE_BY_TYPE");
                    break;


                case "PERIOD_BY_TYPE_LIST": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatPeriodExpenseListByType(periodExpensesByType, selectionQueryType),
                            ConsultPeriodButtons.createBackToPeriodByTypeListMenu());
                    break;

                case "PERIOD_BY_TYPE_SUMMARY": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatPeriodExpenseSummaryByType(periodExpensesByType, selectionQueryType),
                            ConsultPeriodButtons.createBackToPeriodByTypeSummaryMenu());
                    break;
                            //--->FIM SEÇÃO CONSULTA FILTRADA DENTRO DE "POR_PERIODO"<---//
                    //-->FIM CONSULTA DENTRO DE "POR PERIODO"<--//
                //->FIM OPÇÕES DENTRO DE "VER_GASTOS"<-//


                //->INICIO OPÇÕES DENTRO DE "CADASTRAR"<-//
                case "REGISTER_TYPE": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseList(gastoService), RegisterButtons.createPostRegistrationListMenu());
                    break;


                case "POST_REGISTER_LIST": //OK
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "🔍 Selecione o tipo para filtrar:", ConsultMonthButtons.createRegistrationFilterMenu());

                    chatStates.put(chatId, "BACK_TO_REGISTER_FILTER");
                    break;
            }
            //->FIM OPÇÕES DENTRO DE "CADASTRAR"<-//


            //->INICIO SEÇÃO CALLBACK DENTRO DE "MES_ATUAL"<-//
            if (Objects.equals(estado, "BACK_TO_CURRENT_MONTH_LIST")) { //OK
                if (callbackData.equals("MERCADO")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, "Mercado"),
                            ConsultMonthButtons.createBackToFilteredListFromFullMenu());

                } else if (callbackData.equals("LAZER")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, "Lazer"),
                            ConsultMonthButtons.createBackToFilteredListFromFullMenu());

                } else if (callbackData.equals("GASOLINA")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, "Gasolina"),
                            ConsultMonthButtons.createBackToFilteredListFromFullMenu());

                } else if (callbackData.equals("FARMACIA")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, "Farmacia"),
                            ConsultMonthButtons.createBackToFilteredListFromFullMenu());

                } else if (callbackData.equals("OUTROS")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, "Outros"),
                            ConsultMonthButtons.createBackToFilteredListFromFullMenu());
                }


            } else if (Objects.equals(estado, "BACK_TO_FILTER")) { //OK
                if (callbackData.equals("MERCADO")) {
                    selectionQueryType = "Mercado";
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Tipo selecionado: " + selectionQueryType + "\nEscolha o formato do resultado:",
                            ConsultMonthButtons.createFilteredResultMenu());

                } else if (callbackData.equals("LAZER")) {
                    selectionQueryType = "Lazer";
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Tipo selecionado: " + selectionQueryType + "\nEscolha o formato do resultado:",
                            ConsultMonthButtons.createFilteredResultMenu());

                } else if (callbackData.equals("GASOLINA")) {
                    selectionQueryType = "Gasolina";
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Tipo selecionado: " + selectionQueryType + "\nEscolha o formato do resultado:",
                            ConsultMonthButtons.createFilteredResultMenu());

                } else if (callbackData.equals("FARMACIA")) {
                    selectionQueryType = "Farmacia";
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Tipo selecionado: " + selectionQueryType + "\nEscolha o formato do resultado:",
                            ConsultMonthButtons.createFilteredResultMenu());

                } else if (callbackData.equals("OUTROS")) {
                    selectionQueryType = "Outros";
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Tipo selecionado: " + selectionQueryType + "\nEscolha o formato do resultado:",
                            ConsultMonthButtons.createFilteredResultMenu());
                }
            //->FIM SEÇÃO CALLBACK DENTRO DE "MES_ATUAL"<-//


            //->INICIO SEÇÃO CALLBACK DENTRO DE "POR_PERIODO"<-//
            } else if (Objects.equals(estado, "BACK_TO_FILTERED_PERIOD_LIST")) { //OK
                if (callbackData.equals("MERCADO")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatPeriodExpenseListByType(periodExpensesByType, "Mercado"),
                            ConsultPeriodButtons.createBackToFilteredPeriodListMenu());

                } else if (callbackData.equals("LAZER")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatPeriodExpenseListByType(periodExpensesByType, "Lazer"),
                            ConsultPeriodButtons.createBackToFilteredPeriodListMenu());

                } else if (callbackData.equals("GASOLINA")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatPeriodExpenseListByType(periodExpensesByType, "Gasolina"),
                            ConsultPeriodButtons.createBackToFilteredPeriodListMenu());

                } else if (callbackData.equals("FARMACIA")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatPeriodExpenseListByType(periodExpensesByType, "Farmacia"),
                            ConsultPeriodButtons.createBackToFilteredPeriodListMenu());

                } else if (callbackData.equals("OUTROS")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatPeriodExpenseListByType(periodExpensesByType, "Outros"),
                            ConsultPeriodButtons.createBackToFilteredPeriodListMenu());
                }

            } else if (Objects.equals(estado, "BACK_TO_PERIOD_LIST")) { //OK
                if (callbackData.equals("MERCADO")) {
                    selectionQueryType = "Mercado";
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Tipo selecionado: " + selectionQueryType + "\nEscolha o formato do resultado:",
                            ConsultPeriodButtons.createPeriodByTypeResultTypeMenu());

                } else if (callbackData.equals("LAZER")) {
                    selectionQueryType = "Lazer";
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Tipo selecionado: " + selectionQueryType + "\nEscolha o formato do resultado:",
                            ConsultPeriodButtons.createPeriodByTypeResultTypeMenu());

                } else if (callbackData.equals("GASOLINA")) {
                    selectionQueryType = "Gasolina";
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Tipo selecionado: " + selectionQueryType + "\nEscolha o formato do resultado:",
                            ConsultPeriodButtons.createPeriodByTypeResultTypeMenu());

                } else if (callbackData.equals("FARMACIA")) {
                    selectionQueryType = "Farmacia";
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Tipo selecionado: " + selectionQueryType + "\nEscolha o formato do resultado:",
                            ConsultPeriodButtons.createPeriodByTypeResultTypeMenu());

                } else if (callbackData.equals("OUTROS")) {
                    selectionQueryType = "Outros";
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            "📅 Tipo selecionado: " + selectionQueryType + "\nEscolha o formato do resultado:",
                            ConsultPeriodButtons.createPeriodByTypeResultTypeMenu());
                }
            //->FIM SEÇÃO CALLBACK DENTRO DE "POR_PERIODO"<-//


            //->INICIO SEÇÃO CALLBACK DENTRO DE "CADASTRAR"<-//
            } else if (Objects.equals(estado, "AWAITING_TYPE")) {
                if (callbackData.equals("MERCADO")) {
                    expense.setTipo("Mercado");
                } else if (callbackData.equals("LAZER")) {
                    expense.setTipo("Lazer");
                } else if (callbackData.equals("GASOLINA")) {
                    expense.setTipo("Gasolina");
                } else if (callbackData.equals("FARMACIA")) {
                    expense.setTipo("Farmacia");
                } else if (callbackData.equals("OUTROS")) {
                    expense.setTipo("Outros");
                    //IMPLEMENTAR FUNCIONALIDADE PARA DIGITAR QUAL FOI O GASTO
                    //PODE CRIAR UMA FUNÇÃO DE DESCRIÇÃO QUE SÓ APARECE EM OUTROS
                } else {
                    System.out.println("Tipo Incorreto");
                }

                expense.setData(LocalDate.now());
                chatStates.put(chatId, "AWAITING_VALUE");

                Integer messageIdBotoes = periodButtonMessageIds.get(chatId);
                if (messageIdBotoes != null) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageIdBotoes,
                            "✅ Categoria selecionada: " + expense.getTipo(),
                            null);
                }

                Integer novaMessageId = UtilFunctions.sendMessageWithButtonsAndReturnId(this, chatId,
                        "📝 Digite o valor do gasto:\nOu selecione uma opção abaixo ⬇️", RegisterButtons.createBackToValueInputMenu());

                periodButtonMessageIds.put(chatId, novaMessageId);

            } else if (Objects.equals(estado, "BACK_TO_REGISTER_FILTER")) { //OK
                if (callbackData.equals("MERCADO")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, "Mercado"),
                            ConsultMonthButtons.createBackToRegistrationMenu());

                } else if (callbackData.equals("LAZER")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, "Lazer"),
                            ConsultMonthButtons.createBackToRegistrationMenu());

                } else if (callbackData.equals("GASOLINA")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, "Gasolina"),
                            ConsultMonthButtons.createBackToRegistrationMenu());

                } else if (callbackData.equals("FARMACIA")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, "Farmacia"),
                            ConsultMonthButtons.createBackToRegistrationMenu());

                } else if (callbackData.equals("OUTROS")) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageId,
                            UtilFunctions.formatExpenseListByType(gastoService, "Outros"),
                            ConsultMonthButtons.createBackToRegistrationMenu());
                }
            }
            //->FIM SEÇÃO CALLBACK DENTRO DE "CADASTRAR"<-//


        } else if (update.hasMessage() && update.getMessage().hasText()) {
            String textoUsuario = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            String estado = chatStates.get(chatId);

            //->INICIO SEÇÃO ENTRADA DE DADOS DENTRO DE "CADASTRAR"<-//
            if (Objects.equals(estado, "AWAITING_VALUE")) {

                String valorFormatado = textoUsuario.replace(",", ".");
                BigDecimal valor = new BigDecimal(valorFormatado);

                expense.setValor(valor);

                gastoService.registrarGasto(expense.getTipo(), expense.getValor(), expense.getData());

                chatStates.remove(chatId);

                Integer messageIdBotoes = periodButtonMessageIds.get(chatId);
                if (messageIdBotoes != null) {
                    UtilFunctions.editMessageWithButtons(this, chatId, messageIdBotoes,
                            "📝 Digite o valor do gasto:\nOu selecione uma opção abaixo ⬇️",
                            null);
                }


                UtilFunctions.sendNewMessageWithButtons(this, chatId,
                        "✅ Gasto cadastrado com sucesso!\n\nO que você gostaria de fazer agora?", RegisterButtons.createPostRegistrationMenu());
            //->FIM SEÇÃO ENTRADA DE DADOS DENTRO DE "CADASTRAR"<-//


            //->INICIO SEÇÃO ENTRADA DE DADOS DENTRO DE "POR_PERIODO"<-//
            } else if (Objects.equals(estado, "AWAITING_START_DATE")) { //OK
                try {
                    // Valida o formato dd-MM-yyyy ANTES de guardar
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate.parse(textoUsuario, formatter); // 👈 VALIDA AQUI

                    // Se passou, formato está correto - guarda como String
                    temporaryStartDates.put(chatId, textoUsuario);
                    chatStates.put(chatId, "AWAITING_END_DATE");

                    //
                    Integer messageIdBotoes = periodButtonMessageIds.get(chatId);
                    if (messageIdBotoes != null) {
                        UtilFunctions.editMessageWithButtons(this, chatId, messageIdBotoes,
                                "📅 Digite a data de início (dd-mm-aaaa):\nOu selecione uma opção abaixo ⬇️",
                                null);
                    }

                    UtilFunctions.sendNewMessageWithButtons(this, chatId,
                            "✅ Data inicial definida: " + textoUsuario,
                            null);


                    //ENVIANDO UMA NOVA MENSAGEM PARA SUBSTITUIR AS ANTERIORES (CRIAR UMA FUNÇÃO NO UTIL PARA ELA
                    //POIS VAMOS USAR OUTRAS VEZES
                    Integer messageIdEnviado = UtilFunctions.sendMessageWithButtonsAndReturnId(this, chatId,
                            "Digite a data final (dd-mm-aaaa):\nOu selecione um dos botões abaixo:",
                            ConsultPeriodButtons.createPeriodEndDateMenu());

                    if (messageIdEnviado != null) {
                        periodButtonMessageIds.put(chatId.toString(), messageIdEnviado);
                    }



                } catch (DateTimeParseException e) {
                    UtilFunctions.sendNewMessageWithButtons(this, chatId,
                            "❌ Data inválida!\nUse o formato dd-mm-aaaa\nExemplo: 30-09-2024", null);
                }

            } else if (Objects.equals(estado, "AWAITING_END_DATE")) { //OK
                try {
                    // Valida a data final também
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate.parse(textoUsuario, formatter); // 👈 VALIDA A DATA FINAL

                    String dataInicioStr = temporaryStartDates.get(chatId);
                    String dataFimStr = textoUsuario;

                    temporaryStartDates.put(chatId, dataInicioStr); // Mantém a inicial
                    temporaryEndDates.put(chatId, dataFimStr);


                    Integer messageIdBotoes = periodButtonMessageIds.get(chatId);
                    if (messageIdBotoes != null) {
                        UtilFunctions.editMessageWithButtons(this, chatId, messageIdBotoes,
                                "📅 Digite a data final (dd-mm-aaaa):\nOu selecione uma opção rápida abaixo ⬇️",
                                null);

                        periodButtonMessageIds.remove(chatId.toString());
                    }
                    UtilFunctions.sendNewMessageWithButtons(this, chatId,
                            "✅ Período definido!\n\n📅 Início: "
                                    + dataInicioStr + "\n📅 Fim: " + dataFimStr +
                                    "\n\nEscolha o formato:",
                            null);

                    periodExpensesByType = gastoMesService.listarGastosPorPeriodo(dataInicioStr, dataFimStr);

                    UtilFunctions.sendNewMessageWithButtons(this, chatId,
                            "📅 Consulta por período geral\nSelecione o formato:", ConsultPeriodButtons.createPeriodResultTypeMenu());

                } catch (DateTimeParseException e) {
                    UtilFunctions.sendNewMessageWithButtons(this, chatId,
                            "❌ Data inválida!\nUse o formato dd-mm-aaaa\nExemplo: 30-09-2024", null);
                }

            } else if (Objects.equals(estado, "AWAITING_START_DATE_BY_TYPE")) { //OK
                try {
                    // Valida o formato dd-MM-yyyy ANTES de guardar
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate.parse(textoUsuario, formatter); // 👈 VALIDA AQUI

                    // Se passou, formato está correto - guarda como String
                    temporaryStartDates.put(chatId, textoUsuario);
                    chatStates.put(chatId, "AWAITING_END_DATE_BY_TYPE");

                    //TIRANDO O BOTÃO DA MENSAGEM ANTERIOR A DIGITAÇÃO DA DATA
                    Integer messageIdBotoes = periodButtonMessageIds.get(chatId);
                    if (messageIdBotoes != null) {
                        UtilFunctions.editMessageWithButtons(this, chatId, messageIdBotoes,
                                "📅 Digite a data de início (dd-mm-aaaa):\nOu selecione uma opção rápida abaixo ⬇️",
                                null);
                    }

                    UtilFunctions.sendNewMessageWithButtons(this, chatId,
                            "✅ Data inicial definida: " + textoUsuario,
                            null);



                    Integer messageIdEnviado = UtilFunctions.sendMessageWithButtonsAndReturnId(this, chatId,
                            "📅 Digite a data final (dd-mm-aaaa):\nOu selecione uma opção rápida abaixo ⬇️",
                            ConsultPeriodButtons.createPeriodByTypeEndDateMenu());

                    if (messageIdEnviado != null) {
                        periodButtonMessageIds.put(chatId, messageIdEnviado);
                    }
                } catch (DateTimeParseException e) {
                    UtilFunctions.sendNewMessageWithButtons(this, chatId,
                            "❌ Data inválida!\nUse o formato dd-mm-aaaa\nExemplo: 30-09-2024", null);
                }

            } else if (Objects.equals(estado, "AWAITING_END_DATE_BY_TYPE")) { //OK
                try {
                    // Valida a data final também
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate.parse(textoUsuario, formatter); // 👈 VALIDA A DATA FINAL

                    String dataInicioStr = temporaryStartDates.get(chatId);
                    String dataFimStr = textoUsuario;

                    temporaryStartDates.put(chatId, dataInicioStr); // Mantém a inicial
                    temporaryEndDates.put(chatId, dataFimStr);

                    //TIRANDO O BOTÃO DA MENSAGEM ANTERIOR A DIGITAÇÃO DA DATA
                    Integer messageIdBotoes = periodButtonMessageIds.get(chatId);
                    if (messageIdBotoes != null) {
                        UtilFunctions.editMessageWithButtons(this, chatId, messageIdBotoes,
                                "📅 Digite a data final (dd-mm-aaaa):\nOu selecione uma opção rápida abaixo ⬇️",
                                null);

                        periodButtonMessageIds.remove(chatId);
                    }
                    UtilFunctions.sendNewMessageWithButtons(this, chatId,
                            "✅ Período definido!\n\n📅 Início: " + dataInicioStr + "\n📅 Fim: " + dataFimStr + "\n\nEscolha o formato:",
                            null);

                    periodExpensesByType = gastoMesService.listarGastosPorPeriodo(dataInicioStr, dataFimStr);

                    Integer messageIdEnviado = UtilFunctions.sendMessageWithButtonsAndReturnId(this, chatId,
                            "🔍 Selecione o tipo para filtrar:", ConsultPeriodButtons.createPeriodByTypeResultMenu());

                    if (messageIdEnviado != null) {
                        periodButtonMessageIds.put(chatId, messageIdEnviado);
                    }


                    chatStates.put(chatId, "BACK_TO_PERIOD_LIST");


                } catch (DateTimeParseException e) {
                    UtilFunctions.sendNewMessageWithButtons(this, chatId,
                            "❌ Data inválida!\nUse o formato dd-mm-aaaa\nExemplo: 30-09-2024", null);
                }
            }
            //->FIM SEÇÃO ENTRADA DE DADOS DENTRO DE "POR_PERIODO"<-//
        }
    }
}






