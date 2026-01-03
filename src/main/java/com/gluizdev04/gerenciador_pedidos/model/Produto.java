package com.gluizdev04.gerenciador_pedidos.model;

import jakarta.persistence.*;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nome;
    @Column(name = "valor")
    private Double preco;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    public Produto(){}

    public Produto(String nome, double preco, Fornecedor fornecedor, Categoria categoria) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }


    public Double getPreco() {
        return preco;
    }

}
