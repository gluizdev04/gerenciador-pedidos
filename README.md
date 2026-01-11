#  Gerenciador de Pedidos

Projeto backend desenvolvido durante a formação Java da Alura para estudo prático de persistência de dados.
Esta aplicação simula um sistema de gestão de comércio, servindo de base para a implementação de diversos cenários de modelagem de dados e consultas complexas.

##  Objetivo do Projeto

O objetivo principal deste repositório é servir como um laboratório para dominar o **Spring Data JPA**. Ao longo do desenvolvimento, o foco foi sair do básico (CRUD) e explorar como o Java interage com bancos de dados relacionais em cenários mais robustos.

##  Estrutura do Domínio

O sistema modela um fluxo de vendas contendo as seguintes entidades e relacionamentos:

- **Produtos:** Itens com nome, preço e descrição.
- **Categorias:** Classificação dos produtos (Relacionamento 1:N).
- **Fornecedores:** Origem dos produtos (Relacionamento 1:N).
- **Pedidos:** Registro de vendas e itens comercializados (Relacionamento N:N e 1:N).

##  Conceitos e Tecnologias Praticadas

Este projeto foi utilizado para consolidar os seguintes conhecimentos:

- **Spring Data JPA:** Repositórios, injeção de dependências e configuração.
- **Mapeamento de Entidades:** Uso de anotações (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`).
- **Relacionamentos:** Configuração de chaves estrangeiras e cardinalidade (`@OneToMany`, `@ManyToOne`, `@ManyToMany`).
- **Consultas Personalizadas:**
  - **JPQL:** Criação de queries orientadas a objeto para relatórios flexíveis.
  - **Native Queries:** Execução de SQL puro para performance.
  - **Derived Queries:** Métodos de busca gerados dinamicamente pelo framework.

##  Stack Tecnológica

- **Java 17+**
- **Spring Boot 4**
- **PostgreSQL**
- **Maven**

##  Como executar

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/gluizdev04/gerenciador-pedidos.git](https://github.com/gluizdev04/gerenciador-pedidos.git)
