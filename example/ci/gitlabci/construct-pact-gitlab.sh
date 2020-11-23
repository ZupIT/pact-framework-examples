#!/bin/bash

PERSONAL_ACCESS_TOKEN=b0e1fc55dd146460ed7d31a0c1d7ca
# Criar runner

echo -e "\n\n Criando Runner\n\n"

docker-compose exec -T runner-1 \
    gitlab-runner \
    register \
    --non-interactive \
    --url "http://gitlab" \
    --name "docker-1" \
    --registration-token "2y6_pb_VNZQvYTL3T1qH" \
    --cache-dir /cache \
    --builds-dir /builds \
    --executor docker \
    --docker-image alpine:3.8 \
    --docker-privileged \
    --docker-volumes /var/run/docker.sock:/var/run/docker.sock \
    --docker-volumes /cache:/cache \
    --docker-volumes /builds:/builds

docker-compose exec -T runner-2 \
    gitlab-runner \
    register \
    --non-interactive \
    --url "http://gitlab" \
    --name "docker-2" \
    --registration-token "2y6_pb_VNZQvYTL3T1qH" \
    --cache-dir /cache \
    --builds-dir /builds \
    --executor docker \
    --docker-image alpine:3.8 \
    --docker-privileged \
    --docker-volumes /var/run/docker.sock:/var/run/docker.sock \
    --docker-volumes /cache:/cache \
    --docker-volumes /builds:/builds

echo "verificar toml"

echo -e "\n\n Criando Personal Access Token\n\n"
#Criar personal access token
docker exec -it gitlab gitlab-rails runner "token = User.find_by_username('root').personal_access_tokens.create(scopes: [:read_user, :read_repository, :api], name: 'Automation token'); token.set_token('b0e1fc55dd146460ed7d31a0c1d7ca'); token.save!"

echo -e "\n\n Criando Projetos no Gitlab\n\n"
#Criar Projeto
curl --header "PRIVATE-TOKEN: ${PERSONAL_ACCESS_TOKEN}" -X POST "http://localhost/api/v4/projects?name=consumer-orange-stack&visibility=public"
curl --header "PRIVATE-TOKEN: ${PERSONAL_ACCESS_TOKEN}" -X POST "http://localhost/api/v4/projects?name=provider-orange-stack&visibility=public"

#Criando Pipeline Trigger para os projetos
echo -e "\n\n Criando Pipeline Trigger dos Projetos\n\n"
curl --request POST --header "PRIVATE-TOKEN: ${PERSONAL_ACCESS_TOKEN}" --form description="Pact Contract Testing Trigger" "http://localhost/api/v4/projects/2/triggers"
curl --request POST --header "PRIVATE-TOKEN: ${PERSONAL_ACCESS_TOKEN}" --form description="Pact Contract Testing Trigger" "http://localhost/api/v4/projects/3/triggers"

#Obtendo token por url
CONSUMER_CI_JOB_TOKEN=$(curl --silent --header "PRIVATE-TOKEN: ${PERSONAL_ACCESS_TOKEN}" "http://localhost/api/v4/projects/2/triggers/1" | json_pp | tr "{}" "\n" | grep "token" | awk '{ print $3 }' | sed -n 's/"//p' | sed -n 's/",//p')
PROVIDER_CI_JOB_TOKEN=$(curl --silent --header "PRIVATE-TOKEN: ${PERSONAL_ACCESS_TOKEN}" "http://localhost/api/v4/projects/3/triggers/2" | json_pp | tr "{}" "\n" | grep "token" | awk '{ print $3 }' | sed -n 's/"//p' | sed -n 's/",//p')

#Setando Variavel de ambiente nos projetos
curl --request POST --header "PRIVATE-TOKEN: ${PERSONAL_ACCESS_TOKEN}"  "http://localhost/api/v4/projects/2/variables" --form "key=PROVIDER_ORANGE_STACK_CI_TOKEN" --form "value=${PROVIDER_CI_JOB_TOKEN}"
curl --request POST --header "PRIVATE-TOKEN: ${PERSONAL_ACCESS_TOKEN}"  "http://localhost/api/v4/projects/3/variables" --form "key=CONSUMER_ORANGE_STACK_CI_TOKEN" --form "value=${CONSUMER_CI_JOB_TOKEN}"

curl --request POST --header "PRIVATE-TOKEN: ${PERSONAL_ACCESS_TOKEN}"  "http://localhost/api/v4/projects/2/variables" --form "key=GLOBAL_ACCESS_TOKEN" --form "value=${PERSONAL_ACCESS_TOKEN}"
curl --request POST --header "PRIVATE-TOKEN: ${PERSONAL_ACCESS_TOKEN}"  "http://localhost/api/v4/projects/3/variables" --form "key=GLOBAL_ACCESS_TOKEN" --form "value=${PERSONAL_ACCESS_TOKEN}"


echo -e "\n\n Enviando códigos do Consumer e Provider para o Gitlab\n\n"
cd repos

sudo rm -r consumer-orange-stack/
sudo rm -r provider-orange-stack/

git clone http://localhost/root/consumer-orange-stack.git 
git clone http://localhost/root/provider-orange-stack.git 

cp -r consumer/. consumer-orange-stack/
cp -r provider/. provider-orange-stack/

cd consumer-orange-stack
git add . && git commit -m "consumer" && git push http://root:adminPact123\$\@localhost/root/consumer-orange-stack.git
cd ..
cd provider-orange-stack
git add . && git commit -m "provider" && git push http://root:adminPact123\$\@localhost/root/provider-orange-stack.git

xdg-open http://localhost
xdg-open http://localhost:9292


echo -e "\n\n Tudo OK! Agora é só acompanhar os jobs CI!\n\n"
