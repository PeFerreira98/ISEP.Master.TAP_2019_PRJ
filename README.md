#TAP Assignment -*Airplane Scheduling Problem*- Documentation


## Objective

O objetivo principal deste trabalho é o design e desenvolvimento de uma aplicação utilizando técnicas de programação funcional.
Os estudantes terão um problema para analisar e, posteriormente, desenhar e desenvolver uma solução para o problema apresentado, utilizando técnicas de programação funcional. 


## Executive Summary

### Designation
O projeto está dividido da seguinte forma:

* Controller

    - Schedule Controller

* Domain

    - Agenda
    - Aircraft
    - Class
    - Entry
    - Runway
    - Schedule

* IO

    - XML Airport IO

Schedule Controller tem a responsabilidade de receber um *input file* no qual vai ser exportado, calculado (consoante os dados exportados) e posteriormente, extrair a agenda para um *output file*.

A agenda pode ser dividida em 3 partes fundamentais:

* **Create Schedule**
   Método responsável por obter uma lista de *aircraft* e calcular o horário para cada item recursivamente. Caso exista um problema de calculo como atingir o *maximum delay* ou não existirem *runways* que suportem a classe de *aircraft*, retorna *'None'*.

     + **Minimization & Emergency** De forma a cumprir este requisito, a utilização de recursividade foi uma necessidade. Caso exista um *Aircraft* com emergência, será retirado *Aircraft* não emergentes da lista de *Scheduled Aircrafts* até ser possivel a inserção do *Aircraft*, após a inserção do *Aircraft* com emergência, os *Aircrafts* anteriormente retirados (se existentes) serão re-calculados e inseridos novamente á lista de *Schedulled*.

* **Get Free Schedule**
   Método responsável pela criação de *Schedule* para um dado *Aircraft*. Caso não exista nenhuma *Runway* livre, será invocado o 'Get Best Runway Match' para indicar (se possível) a melhor *Runway* para aquele *Aircraft*.

* **Get Best Runway Match**
   Método responsável pelo cálculo da melhor *Runway* para um dado *Aircraft*. Caso exista penalidades, calcula o tempo e retorna a *Schedule* correspondente.
   Caso não exista *Runways* com a classe do *Aircraft*, retorna *null*.

     + **Complete Separation** De forma a cumprir este requisito, a primeira função que é chamada, obtém uma lista agrupada por *Runways* e para cada *Runway* obtém o tempo restante para o *Aircraft*.


Decidimos focalizar a logica de negócio na Agenda visto que é ela que tem a responsabilidade de efetuar os cálculos necessários bem como conter a informação necessária para o efeito. Assim complementando os princípios de **single responsability principle** e **information expert**.



###Improvements

Encontramos diversos pontos a melhorar, nomeadamente na falta de aproveitamento das partes funcionais da linguagem **Scala**. 
É um caso a averiguar e a melhorar com estudo da linguagem bem como a pratica nesta.