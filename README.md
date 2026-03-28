Amazon Deals Notifier (FakeStore Edition)

Sistema de monitoramento de ofertas baseado na FakeStore API
 com envio automático de notificações via Telegram.

O projeto é desenvolvido em Java 21 com Spring Boot, utilizando Spring Data JPA para persistência em banco H2, e segue boas práticas de arquitetura para aplicações RESTful.

Funcionalidades
Monitoramento de produtos da FakeStore API.
Cálculo automático de desconto comparando preço atual com preço original.
Score inteligente de ofertas, considerando:
Desconto
Preço
Avaliação do produto (rating)
Número de avaliações
Filtragem de ofertas relevantes, com base em desconto mínimo configurável.
Envio de notificações via Telegram com título, preço, desconto e link do produto.
Endpoints REST para:
Listar todas as ofertas
Listar últimas ofertas
Listar melhores ofertas com base no score
Agendamento automático de busca de ofertas (@Scheduled).
Tratamento global de erros com resposta padronizada (ApiError).
Tecnologias Utilizadas
Java 17
Spring Boot 3.x
Spring Data JPA / H2
Lombok
Spring Scheduler (@Scheduled)
REST Template
OpenAPI / Swagger (documentação da API)
Estrutura do Projeto
src/main/java/com/ghdev/amazondealsnotifier
├─ client        # Comunicação com APIs externas (FakeStore)
├─ config        # Configurações do projeto (Telegram, Scheduler, Deal)
├─ controller    # Endpoints REST
├─ dto           # Objetos de resposta para API
├─ exception     # Tratamento global de exceções
├─ mapper        # Mapeamento DTO -> Entity
├─ model         # Entidades JPA e DTOs
├─ repository    # Repositórios Spring Data
└─ service       # Lógica de negócio e integração com Telegram
Configuração do Projeto
1. Variáveis de Ambiente

Para não expor credenciais no Git, utilize um arquivo .env na raiz do projeto:

# Banco de dados H2
SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=1234

# Telegram
TELEGRAM_BOT_TOKEN=seu_token_aqui
TELEGRAM_CHAT_ID=seu_chat_id_aqui

⚠️ O arquivo .env não deve ser versionado. Já está incluído no .gitignore.

2. application.yml

O arquivo principal de configuração referencia as variáveis do .env:

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
Endpoints da API
Método	Endpoint	Descrição
GET	/deals	Lista todas as ofertas paginadas
GET	/deals/latest	Lista as últimas ofertas encontradas
GET	/deals/top	Lista as melhores ofertas por score
POST	/deals/fetch	Busca novas ofertas da API e envia notificações via Telegram
GET	/deals/error-test	Endpoint para testar tratamento de erros
Lógica de Negócio

Cálculo de desconto:

𝑑
𝑖
𝑠
𝑐
𝑜
𝑢
𝑛
𝑡
=
𝑜
𝑟
𝑖
𝑔
𝑖
𝑛
𝑎
𝑙
𝑃
𝑟
𝑖
𝑐
𝑒
−
𝑝
𝑟
𝑖
𝑐
𝑒
𝑜
𝑟
𝑖
𝑔
𝑖
𝑛
𝑎
𝑙
𝑃
𝑟
𝑖
𝑐
𝑒
∗
100
discount=
originalPrice
originalPrice−price
	​

∗100
Score de ofertas: combina múltiplos fatores para priorizar produtos mais relevantes:
score = (desconto * 0.5) + ((100 / preço) * 0.2) + (rating * 5) + log(número de reviews + 1)
Filtragem: apenas produtos com desconto acima do valor configurado (deal.min-discount) são considerados.
Notificação Telegram: mensagem com título, preço, desconto, link e imagem do produto (se disponível).
Considerações Técnicas
Spring Scheduler: permite execução automática de verificações periódicas.
Cache de ofertas: produtos já salvos não são reprocessados nem reenviados.
Tratamento global de exceções: todas exceções são convertidas em objetos ApiError com status, mensagem e timestamp.
H2 Console: disponível em /h2-console para inspeção de dados em memória.
Como Rodar
Configurar variáveis de ambiente no .env.
Executar o projeto via Maven:
./mvnw spring-boot:run
A API estará disponível em http://localhost:8081/deals
Swagger/OpenAPI: http://localhost:8081/swagger-ui.html
