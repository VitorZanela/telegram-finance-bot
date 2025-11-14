# 💰 Telegram Finance Bot

Bot pessoal para registro e consulta de gastos via Telegram, substituindo o registro 
manual pelo WhatsApp por uma solução organizada e persistente.

## 🚀 Funcionalidades

- ✅ **Cadastro rápido** de gastos por categoria
- 📊 **Consultas flexíveis** por mês atual e períodos personalizados
- 💰 **Relatórios detalhados** com valores totais e listagem completa
- 🔍 **Filtros inteligentes** por tipo de gasto
- ✏️ **Edição e exclusão** de gastos cadastrados
- 💾 **Persistência completa** em PostgreSQL

## 🏗️ Estrutura do Projeto

```
src/main/java/project/telegramfinancebot/
├── bot/
│ ├── buttons/                      
│ │ ├── ConsultMonthButtons.java    # Consultas do mês
│ │ ├── ConsultPeriodButtons.java   # Consultas por período
│ │ ├── InitiateButtons.java        # Menu inicial
│ │ └── RegisterButtons.java        # Cadastro de gastos
│ ├── config/
│ │ └── BotConfig.java              # Configurações do bot
│ ├── functions/
│ │ ├── UtilFuncition.java          # Métodos Utilitarios
│ └── BotApp.java                   # Classe principal
├── controller/
│ ├── GastoController.java          # Controle gastos mês atual
│ └── GastoMesController.java       # Controle gastos outros meses
├── entity/
│ ├── Gasto.java                    # Entidade gasto
│ └── GastoMes.java                 # Entidade gasto mensal
├── repository/
│ ├── GastoRepository.java          # Repository gastos
│ └── GastoMesRepository.java       # Repository gastos mensais
├── service/
│ ├── GastoService.java             # Serviço gastos
│ ├── GastoMigrationService.java    # Serviço gastos
│ ├── GastoMesService.java          # Serviço gastos mensais
└── ChatbotgastosApplication.java   # App Spring Boot

src/main/resources/
├── application.properties          # Configurações da aplicação (Telegram e BD)

src/main
├── .gitignore                      # Arquivos ignorados no repositório  
├── pom.xml                         # Configuração de dependencias do Maven
├── README.md                       # Detalhes do funcionamento da aplicação
```

## 🛠️ Tecnologias

- **Java 21**
- **Spring Boot 3.5.6**
- **Telegram Bot API**
- **PostgreSQL**
- **JPA/Hibernate**

## 🏗️ Estrutura/arquitetura

#### O diagrama completo da arquitetura do sistema está disponível para visualização online:

Para visualizar ou editar o diagrama:
- Baixe o arquivo com o diagrama  
[🔗 **Download do arquivo**](https://drive.google.com/file/d/1i11ipMekiS5Vj9fwyx8hI7Ld1UgCorg5/view?usp=sharing)

- Acesse o draw.io
[🔗 **Acessar o Draw.io**](https://app.diagrams.net/)

- Clique em "Arquivo" → "Abrir de" → "URL"

- Cole o link do arquivo .xml do seu diagrama

## ⚙️ Configuração

### 1. Criar Bot no Telegram

#### Conversar com @BotFather no Telegram
1. Digitar o comando → /start
2. Digitar o comando → /newbot
3. Fazer as configurações do bot
   - → Nome: Nome_do_bot
   - → Username: NOME_DO_SEU_BOT_AQUI
   - → Copiar o token gerado

### 2. Banco de Dados PostgreSQL
1. 🚀 INSTALAR PostgreSQL
   - Baixar do site oficial: https://www.postgresql.org/download/
   - Durante a instalação:
     - ANOTE A SENHA DO USUÁRIO postgres (é a mais importante!)
     - Deixe a porta padrão (5432)
     - Instale o pgAdmin junto (vem na instalação)


2. Acessar o pgAdmin
   - Abra o pgAdmin no seu navegador (geralmente http://localhost:5432/pgadmin)
   - Criar credenciais de acesso ao pgAdmin (email/senha)
   - Conectar no servidor PostgreSQL usando:
     - Host: localhost
     - Usuário: SEU_USUARIO_AQUI
     - Senha: TOKEN_DO_SEU_BOT_AQUI


3. Criar Banco de Dados
   -  Clique direito em "Databases"
   - Create → Database
   - Preencha:
      - Name: SEU_BANCO_AQUI
      - Owner: postgres (ou deixe padrão)
   - Clique "Save"
   

4. Conceder Permissões
   - Clique direito no banco SEU_BANCO_AQUI
   - Properties → Security
   - Clique "Add" e adicione:
       - Grantee: SEU_USUARIO_AQUI
       - Privileges: Marque ALL
   - Clique "Save"

### 3. Configuração (application.properties)
#### COPIE AS CONFIGURAÇÕES BAIAXO PARA application.properties E CONFIGURE
```
# COPIE ESTE ARQUIVO PARA application.properties E CONFIGURE

# Database PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/SEU_BANCO_AQUI
spring.datasource.username=<SEU_USUARIO_AQUI>
spring.datasource.password=<SUA_SENHA_AQUI>
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.jdbc.time_zone=America/Sao_Paulo

#Bot Telegram (BotFather)
telegram.bot.username=<NOME_DO_SEU_BOT_AQUI>
telegram.bot.token=<TOKEN_DO_SEU_BOT_AQUI>
```

### 4. Execução
- Para executar a aplicação via IntelliJ:
    - Abra o arquivo principal: ChatbotgastosApplication.java
    - Pressione Shift + F10 para compilar e executar


- Ou pelo menu:
  - Clique com o botão direito no arquivo
  - Selecione 'Run ChatbotgastosApplication'


## 🎯 Como Usar

1. Iniciar: Envie qualquer texto para iniciar o bot


2. Cadastrar:
   - Selecione "Cadastrar gastos"
     - Escolha a categoria 
     - Informe o valor


3. Consultar:
   - "Ver gastos" → "Mês Atual" ou "Por Período"
     - Escolha entre lista detalhada ou apenas valores


4. Gerenciar:
   - Editar gastos existentes
   - Excluir registros

## 📋 Próximas Etapas
- Deploy em servidor
- Múltiplos usuários

## 📝 Licença
MIT License - Veja o arquivo LICENSE para detalhes.