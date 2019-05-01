#TAP Assignment -*Airplane Scheduling Problem*- Documentation


## Objective

O objectivo principal deste trabalho é o design e desenvolvimento de uma aplicação utilizando técnicas de programação funcional.
Os estudantes terão um problema para analisar e, posteriormente, desenhar e desenvolver uma solução para o problema apresentado, utilizando tecnicas de programação funcional. 


## Executive Summary

### Designation
O projeto está dividido da seguinte forma:
* Controller
  * Schedule Controller
* Domain
  * Agenda
  * Aircreft
  * Class
  * Entry
  * Runway
  * Schedule
* IO
  * XML Airport IO

Schedule Controller tem a responsabilidade de receber um *input file* no qual vai ser exportado, calculado (consoante os dados exportados) e posteriormente, extrair a agenda para um *output file*.

A agenda pode ser dividida em 3 partes fundamentais:
* Create Schedule
   Metodo responsavel por obter uma lista de *aircraft* e calcular o horário para cada item recursivamente. Caso exista um problema de calculo como atingir o *maximum delay* ou não existirem *runways* que suportem a classe de *aircraft*, retona *'None'*.

* Get Free Schedule
   Metodo responsavel pela criação de *Schedule* para um dado *Aircraft*. Caso não exista nenhuma *Runway* livre, será invocado o 'Get Best Runway Match' para indicar (se possivel) a melhor *Runway* para aquele *Aircraft*.

* Get Best Runway Match
   Metodo responsavel pelo calculo da melhor *Runway* para um dado *Aircraft*. Caso exista penalidades, calcula o tempo e retorna a *Schedule* correspondente.
   Caso não exista *Runways* com a classe do *Aircraft*, retorna *null*.

Decidimos focalizar a logica de negocio na Agenda visto que é ela que tem a responsabilidade de efectuar os calculos necessários bem como conter a informação necessária para o efeito. Assim complementando os principios de single responsability principle e information expert.

###Improvements

Encontramos diversos pontos a melhorar, nomeadamente na falta de aproveitamento das partes funcionais da linguagem Scala. 
É um caso a averiguar e a amelhorar com estudo da linguagem bem como a pratica nesta.




