#!/bin/bash

echo "🚀 Iniciando o Deploy Completo..."

# Registrando o provedor do Container Registry (Necessário na primeira vez)
echo "Registrando provedor Microsoft.ContainerRegistry na sua assinatura (Isso pode levar alguns segundos)..."
az provider register --namespace Microsoft.ContainerRegistry
az provider register --namespace Microsoft.ContainerInstance

echo "1/5 - Criando Grupo de Recursos..."
az group create --name rg-money-hub --location eastus --output none

echo "2/5 - Criando Container Registry (ACR)..."
az acr create --resource-group rg-money-hub --name moneyhubrm61760 --sku Standard --location eastus --admin-enabled true --output none

echo "3/5 - Fazendo Build e Push da Imagem na Nuvem..."
az acr build --registry moneyhubrm61760 --image orbitguard-api:v1 .

echo "4/5 - Ajustando permissoes dos scripts locais..."
chmod +x ./scripts-azure/*.sh

echo "5/5 - Executando scripts de Infraestrutura..."
echo "-> Criando Storage..."
./scripts-azure/Aula_12_01_store-account.sh > /dev/null
echo "-> Criando Key Vault..."
./scripts-azure/Aula_12_02_key-vault.sh > /dev/null
echo "-> Criando MySQL ACI..."
./scripts-azure/Aula_12_03_aci-mysql.sh > /dev/null
echo "-> Criando API Java ACI..."
./scripts-azure/Aula_12_04_aci-api-java.sh > /dev/null

echo "=========================================="
echo "✅ DEPLOY FINALIZADO COM SUCESSO!"
echo "🌐 URL da sua API para testes:"
az container show --resource-group rg-money-hub --name orbitguard-api --query ipAddress.fqdn --output tsv
echo "=========================================="
