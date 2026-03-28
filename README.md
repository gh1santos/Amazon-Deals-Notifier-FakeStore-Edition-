Amazon Deals Notifier (FakeStore Edition)

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)
![H2](https://img.shields.io/badge/H2-Database-blue?style=for-the-badge&logo=databricks)

Sistema inteligente de monitoramento de ofertas baseado na **FakeStore API** com envio automático de notificações via **Telegram**. O projeto utiliza algoritmos de score para filtrar o que é realmente uma oportunidade relevante.

---

## Funcionalidades

* **Monitoramento Ativo:** Busca periódica de novos produtos via `Spring Scheduler`.
* **Cálculo de Desconto:** Identifica automaticamente a porcentagem de desconto baseada no preço original.
* **Smart Scoring:** Sistema de pontuação que cruza dados de preço, desconto e avaliação dos usuários.
* **Filtro Configurável:** Notifica apenas ofertas que atingem um patamar mínimo de desconto.
* **Notificações Real-time:** Alertas formatados no Telegram com link e detalhes do produto.
* **API REST:** Endpoints para consulta manual, listagem de melhores ofertas e testes de erro.

---

## 🧠 Lógica de Negócio

O sistema não apenas lista produtos, ele os "rankeia" para evitar spam de ofertas ruins.

### 1. Cálculo de Desconto
$$Discount = \frac{OriginalPrice - CurrentPrice}{OriginalPrice} \times 100$$

### 2. Algoritmo de Score
Para definir a qualidade de uma oferta, utilizamos a fórmula:
$$Score = (Desc \times 0.5) + (\frac{100}{Preço} \times 0.2) + (Rating \times 5) + \log(Reviews + 1)$$

---

## 🛠️ Tecnologias Utilizadas

* **Core:** Java 21 & Spring Boot 3
* **Dados:** Spring Data JPA & H2 Database (In-memory)
* **Integração:** Rest Template & Telegram Bot API
* **Utilitários:** Lombok & MapStruct
* **Documentação:** OpenAPI / Swagger UI

---

## 📂 Estrutura do Projeto

```text
src/main/java/com/ghdev/amazondealsnotifier
├─ client      # Integração com APIs externas (FakeStore/Telegram)
├─ config      # Configurações de Beans, Telegram e Scheduler
├─ controller  # Endpoints da API REST
├─ dto         # Objetos de transferência e respostas da API
├─ exception   # Tratamento global de erros (ApiError)
├─ mapper      # Mapeamento de Entidades para DTOs
├─ model       # Entidades JPA e Regras de Domínio
├─ repository  # Interfaces de persistência
└─ service     # Lógica de negócio e regras de notificação
🚀 Como Executar1. Configurar Variáveis de AmbienteCrie um arquivo .env na raiz do projeto para proteger suas credenciais:Properties# Database
SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=1234

# Telegram API
TELEGRAM_BOT_TOKEN=seu_token_aqui
TELEGRAM_CHAT_ID=seu_chat_id_aqui
2. Rodar a AplicaçãoExecute o Maven via terminal:Bash./mvnw spring-boot:run
3. Acessar a APISwagger UI: http://localhost:8081/swagger-ui.htmlH2 Console: http://localhost:8081/h2-console (JDBC URL: jdbc:h2:mem:testdb)📡 Endpoints PrincipaisMétodoEndpointDescriçãoGET/dealsLista todas as ofertas paginadasGET/deals/topLista ofertas com maior scorePOST/deals/fetchForça a busca de novos produtos manualmente📫 Desenvolvido por GHDev
http://googleusercontent.com/interactive_content_block/0
