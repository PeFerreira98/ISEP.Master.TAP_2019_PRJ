#TAP Assignment -*Airplane Scheduling Problem*- Documentation


## Objective

O objectivo principal deste trabalho é o design e desenvolvimento de uma aplicação utilizando técnicas de programação funcional.
Os estudantes terão um problema para analisar e, posteriormente, desenhar e desenvolver uma solução para o problema apresentado, utilizando tecnicas de programação funcional. 


## Executive Summary

### Milestone 1
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

* Get Best Runway Match


