Amazon Deals Notifier (FakeStore Edition)

Sistema de monitoramento de ofertas baseado em FakeStore API
 com envio automático de notificações pelo Telegram. O projeto é feito com Spring Boot, H2, e conta com um score inteligente de ofertas.

⚡ Funcionalidades
Busca produtos da API FakeStore e calcula descontos reais.
Score de ofertas inteligente considerando:
Desconto
Preço
Avaliação do produto
Número de avaliações
Armazena produtos e ofertas em H2 (in-memory).
Envia notificações para Telegram, incluindo título, preço, desconto e link.
Endpoints REST para:
Listar todas as ofertas
Listar últimas ofertas
Listar melhores ofertas com base no score
Suporte a Swagger (OpenAPI) para documentação da API.
Configuração de agendamento automático de verificações de ofertas via @Scheduled.
📦 Tecnologias
Java 17
Spring Boot 3.x
Spring Data JPA / H2
Lombok
Playwright (opcional, caso queira scraping futuro)
Telegram API
Swagger / OpenAPI
⚙️ Configuração
1. Variáveis de ambiente

Crie um arquivo .env na raiz do projeto (não versionar, já está no .gitignore) com:

# Banco de dados H2
SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=1234

# Telegram
TELEGRAM_BOT_TOKEN=your_bot_token
TELEGRAM_CHAT_ID=your_chat_id

⚠️ Nunca coloque esse arquivo no Git para não expor suas chaves.

2. application.yml

Seu application.yml usa essas variáveis do .env:

server:
  port: 8081

deal:
  min-discount: 20

scheduler:
  interval: 600000 # 10 minutos

spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
    driver-class-name: org.h2.Driver
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: update

telegram:
  bot-token: ${TELEGRAM_BOT_TOKEN}
  chat-id: ${TELEGRAM_CHAT_ID}
🚀 Como rodar
1. Local
./mvnw spring-boot:run

Acesse a API em http://localhost:8081/deals
Swagger em http://localhost:8081/swagger-ui.html

2. Docker (opcional)

Caso queira rodar em container, crie um Dockerfile:

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/amazondealsnotifier.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]

Build:

docker build -t amazondealsnotifier .
docker run -d -p 8081:8081 --env-file .env amazondealsnotifier
📡 Endpoints da API
Método	Endpoint	Descrição
GET	/deals	Lista todas ofertas paginadas
GET	/deals/latest	Lista últimas ofertas encontradas
GET	/deals/top	Lista melhores ofertas por score
POST	/deals/fetch	Busca novas ofertas da API e envia Telegram
GET	/deals/error-test	Testa o tratamento de erros (dev)
🧠 Lógica de ofertas
Calcula discount a partir de originalPrice e price.
Score de ofertas:
score = (desconto * 0.5) + ((100 / preço) * 0.2) + (rating * 5) + log(número de reviews + 1)
Apenas produtos com desconto maior ou igual ao min-discount configurado são enviados.
📂 Estrutura do projeto
src/main/java/com/ghdev/amazondealsnotifier
├─ client        # Comunicação com FakeStore
├─ config        # Configurações (Telegram, Scheduler, Deal)
├─ controller    # Endpoints REST
├─ dto           # Objetos de resposta
├─ exception     # Tratamento de erros
├─ mapper        # Mapeamento DTO -> Entity
├─ model         # Entidades JPA e DTOs
├─ repository    # Repositórios Spring Data
└─ service       # Lógica de negócio e Telegram
