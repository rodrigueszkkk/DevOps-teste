# Variáveis
# ALTERE PARA SEU RM E SUA REGIÃO (Política)
rm=rm61760
location="eastus"
resourceGroup="rg-money-hub"
acrName="moneyhub$rm"
aciName="api-dotnet"
aciNameMysql="mysql-dimdim"
imageName="api-transacoes"
tag="v1"
keyVaultName="keyvault-$rm"
mysqlURL=$(az container show --resource-group $resourceGroup --name $aciNameMysql --query ipAddress.fqdn --output tsv)

# Registra o Serviço de ACI na Assintaura
az provider register --namespace Microsoft.ContainerInstance


# Deploy do Container Api de .NET
az container create \
  --resource-group $resourceGroup \
  --name $aciName \
  --location $location \
  --image $acrName.azurecr.io/$imageName:$tag \
  --cpu 1 \
  --memory 1 \
  --os-type Linux \
  --dns-name-label api-dotnet-$rm \
  --ports 8080 \
  --registry-login-server $acrName.azurecr.io \
  --registry-username $(az keyvault secret show --vault-name $keyVaultName --name acr-username --query value -o tsv) \
  --registry-password $(az keyvault secret show --vault-name $keyVaultName --name acr-password --query value -o tsv) \
  --environment-variables \
    ConnectionStrings__DefaultConnection=$(az keyvault secret show --name connection-strings --vault-name $keyVaultName --query value -o tsv | sed "s/mysql-dimdim/$mysqlURL/") \
  --restart-policy Always

# O comando sed troca a palavra mysql-dimdim para o IP Público do ACI em runtime (Somente Linux)
#
# Outro exemplo do comando sed:
# echo "My name is Bond, James Bond" | sed "s/Bond/Stuart/g"
#
# Em Power Shell
# 'My name is Bond, James Bond' -replace 'Bond', 'Stuart'
#

#
# Testes após a criação
#
#fqdndotnet=$(az container show --resource-group rg-money-hub --name api-dotnet --query ipAddress.fqdn --output tsv)

#curl -X GET http://$fqdndotnet:8080/api/transacoes

#curl -X POST http://$fqdndotnet:8080/api/transacoes \
#  -H "Content-Type: application/json" \
#  -d '{
#    "descricao": "Compra no supermercado",
#    "valor": 150.75
#  }'

#curl -X PUT http://$fqdndotnet:8080/api/transacoes/7 \
#  -H "Content-Type: application/json" \
#  -d '{
#    "id": 7,
#    "descricao": "Compra no supermercado - ALTERADO",
#    "valor": 150.76,
#    "dataTransacao": "2024-06-18T00:00:00"
#  }'

#curl -X DELETE http://$fqdndotnet:8080/api/transacoes/7

# Testar GET na API .NET via localhost (dentro do container)
#az container exec --resource-group $resourceGroup --name $aciName --exec-command "curl -X GET http://$fqdndotnet:5000/api/transacoes"

# Listar todos os ACIs
#az container list --resource-group $resourceGroup --output table
