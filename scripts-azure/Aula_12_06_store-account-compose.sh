# Variáveis
# ALTERE PARA SEU RM E SUA REGIÃO (Política)
rm="rm61760"
location="eastus"
RESOURCE_GROUP="rg-money-hub"
STORAGE_ACCOUNT="volumecompose$rm"
FILE_SHARE=mysql-dimdim-volume-compose

# Registra o Serviço de Storage na Assintaura
az provider register --namespace Microsoft.Storage

# Cria a conta de armazenamento
az storage account create \
  --resource-group $RESOURCE_GROUP \
  --location $LOCATION \
  --name $STORAGE_ACCOUNT \
  --sku Standard_LRS

# Cria o compartilhamento de arquivos (Será nosso Volume do Banco de Dados)
az storage share-rm create \
  --resource-group $RESOURCE_GROUP \
  --storage-account $STORAGE_ACCOUNT \
  --name $FILE_SHARE

STORAGE_KEY=$(az storage account keys list \
  --resource-group $RESOURCE_GROUP \
  --account-name $STORAGE_ACCOUNT \
  --query "[0].value" -o tsv)

echo $STORAGE_KEY
