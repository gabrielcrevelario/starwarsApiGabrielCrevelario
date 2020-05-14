# starwarsApiGabrielCrevelario
## # O que é a API **"starwarsApiGabrielCrevelario"**:
Essa API foi criada para incorporar o projeto de um jogo da B2W sobre StarWars ela visa basicamente criar, deletar, buscar por nome, por id, listar todos e retornar os planetas existentes no universo de StarWars além também de trazer a quantidade de filmes e os filmes onde esses planetas apareceram. 6 propriedades são inseridas: nome (Obrigatória), clima(Obrigatória) ,Terreno(Obrigatória), Gravidade, Diametro. A lista de filmes correspondente a cada planeta que aparece nas franquia é trazida via API publica Swapi.

## Tecnologias 
Esse projeto é desenvolvido em java 1.8, utilizando a versão mais recente do Spring Boot, o gerenciador de dependências dele é o gradle, com as seguintes bibliotecas mockito,junit,spring data mongodb, swagger, lombok, validation.

## Arquitetura e Observações
Ele utiliza alguns princípios de design patthern conhecidos como mvc, factory, inversão de controle, builder, sobre a arquitetura o projeto está dividido em: config, controller, event, exceptionHandler, model,repository, services, o projeto segue alguns princípios de maturidade richardson, os endpoins são possíveis de visualizar pelo Swagger, todos os retornos com mais de um elemento estão paginados! Os campos obrigatórios também, o projeto está coberto de logs, api operation que descrevem o que cada endpoint também, a porta dele é a padrão 8080

## Como Visualizar o funcionamento:
Para usar basta apenas dar um git clone no projeto, o gradle baixa as suas dependências e pronto bastar "startar" o projeto, não precisa ligar o mongodb localmente pois está conectado em um servidor remoto do atlas, na minha maquina desenvolvi o projeto no intelij, recomendo que utilize.

## Endpoints  
## Todos os endpoints estão listados em: http://localhost:8080/swagger-ui.html
![](https://i2.wp.com/www.nerdsite.com.br/wp-content/uploads/2020/01/darth.jpg?fit=711%2C400&ssl=1)

