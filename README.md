# OrbitGuard API - SafeShelter (Global Solution 2026/1)

API REST em Java/Spring Boot conteinerizada para a Global Solution 2026/1 da FIAP.

## 🌍 Proposta da Solução (SafeShelter & OrbitGuard)
O **SafeShelter** é um "Beacon" de emergência focado em comunidades de risco. Durante desastres naturais (como inundações e deslizamentos), a infraestrutura terrestre de telecomunicações frequentemente falha. A nossa solução conecta dados orbitais e infraestrutura de satélites de baixa órbita para enviar pacotes de dados vitais (SOS e Localização via ESP32) diretamente para o painel de controle da Defesa Civil (OrbitGuard API).

O projeto atende ao tema de **Economia Espacial** pois utiliza a conectividade e telemetria de satélites para mitigar riscos climáticos na Terra, salvando vidas quando a rede móvel convencional colapsa.

---

## 🏗️ Desenho Macro da Arquitetura (Cloud & IoT)

<img width="773" height="601" alt="Diagrama-Macro drawio" src="https://github.com/user-attachments/assets/a3d8b482-77bd-445c-bd89-67679bae0241" />

A arquitetura engloba:
1. **IoT (ESP32):** Protótipo simulado capturando acionamentos de SOS e biometria/clima.
2. **Cloud (VM/EC2):** Hospedagem da solução conteinerizada.
3. **App Container:** API Java isolada recebendo os alertas via HTTP.
4. **DB Container:** Banco PostgreSQL persistindo os logs de emergência.

---

## 🚀 How To - Tutorial de Execução em Nuvem (Docker)

Siga este passo a passo para executar a aplicação em um ambiente Cloud (AWS EC2, Azure VM, etc.) desde a clonagem até as evidências de persistência.

### 1. Clonar o Repositório
Acesse o terminal da sua Máquina Virtual e clone este projeto:
```bash
git clone https://github.com/rodrigueszkkk/GS-Docker
cd GS-Docker
```

### 2. Executar os containers
A aplicação foi construída utilizando o recurso de Multi-stage Build, portanto não é necessário ter o Java ou Maven instalados na máquina hospedeira. Suba a infraestrutura executando:
```bash
docker compose up -d --build
```
O comando subirá dois containers (app-rm61760 e db-rm61760) operando na mesma rede bridge.

### 3. Evidência 1: Exibição dos Logs
Para validar se a API e o Banco iniciaram corretamente na porta 8080 e 5432:

```bash
docker logs app-rm61760
docker logs db-rm61760
```

### 4. Evidência 2: Segurança e Estrutura (Aplicação)
O container da aplicação atende aos requisitos de segurança rodando com usuário não-root e diretório de trabalho parametrizado. Acesse o terminal do container para validar:

```bash
docker container exec -it app-rm61760 sh
```

Dentro do container, execute:

1. pwd (Deve retornar /app)
2. ls -l (Exibe as permissões e o arquivo app.jar)
3. whoami (Deve retornar orbituser)
4. Digite 'exit' para sair do container.

### 5. Evidência 3: Persistência de Dados (Banco de Dados)
Após realizar chamadas HTTP (POST) para salvar os dados na API, valide a persistência acessando diretamente o container do PostgreSQL:
```bash
docker container exec -it db-rm61760 psql -U orbit_admin -d orbitguard_db
```

Dentro do banco, liste as tabelas criadas automaticamente:

```SQL
\dt
```

Execute o comando SELECT na tabela correspondente para provar a gravação:

```SQL
SELECT * FROM tb_area_monitorada;
```
(Digite \q para sair do banco).

### ⚙️ Tecnologias Utilizadas
1. Linguagem: Java 17
2. Framework: Spring Boot 3.5.14 (Web, Data JPA, Security/JWT, Validation, HATEOAS)
3. Banco de Dados: PostgreSQL (Produção/Docker) e H2 (Desenvolvimento local)
4. DevOps: Docker e Docker Compose (Multi-stage build)

### 👤 Usuário de Demonstração (API)
Para testar as rotas protegidas no Swagger (http://IP:8080/swagger-ui.html), obtenha o JWT na rota POST /auth/login:

```JSON
{
  "email": "admin@orbitguard.com",
  "senha": "orbitguard123"
}
```

### 🎓 Demonstração
Link do Vídeo Demonstrativo: [INSERIR O LINK DO YOUTUBE AQUI]

#### Integrantes:

1. Kaiky Pereira Rodrigues Da Silva - RM 564578 - Turma 2TDSPV
2. Leandro Guarido de Oliveira - RM 561760 - Turma 2TDSPV
3. Gabriel Costa Solano - RM 562325 - Turma 2TDSPV


## Como executar o deploy (Tutorial - Checkpoint 1)

### 1. Preparar o Build e Push da Imagem
Faça o login no Azure e crie o Resource Group e ACR:

```bash
az login

# Crie o grupo de recursos
az group create --name rg-money-hub --location eastus

# Crie o Azure Container Registry
az acr create \
    --resource-group rg-money-hub \
    --name moneyhubrm61760 \
    --sku Standard \
    --location eastus \
    --public-network-enabled true \
    --admin-enabled true

# Faça login no ACR
az acr login --name moneyhubrm61760
```

Faça o Build da imagem e o Push para o Azure Container Registry:

```bash
# Build da imagem da API
docker build -t orbitguard-api .

# Tag e Push para o ACR
docker tag orbitguard-api moneyhubrm61760.azurecr.io/orbitguard-api:v1
docker push moneyhubrm61760.azurecr.io/orbitguard-api:v1
```

### 2. Executar os Scripts de Deploy na Nuvem (ACI)
Execute os scripts bash na seguinte ordem (utilizando bash / git bash):

```bash
# 1. Cria Storage Account e Volume
./scripts-azure/Aula_12_01_store-account.sh

# 2. Cria Key Vault e Segredos
./scripts-azure/Aula_12_02_key-vault.sh

# 3. Cria ACI do MySQL (Banco de dados na nuvem)
./scripts-azure/Aula_12_03_aci-mysql.sh

# 4. Cria ACI da API Java conectada ao MySQL
./scripts-azure/Aula_12_04_aci-api-java.sh
```
