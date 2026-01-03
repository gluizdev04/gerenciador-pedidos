package com.gluizdev04.gerenciador_pedidos.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private LocalDate dataEntrega;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )

    private List<Produto> produtos;

    public Pedido() {}

    public Pedido(LocalDate now, LocalDate dataEntrega, List<Produto> produtos) {
        this.data = now;
        this.dataEntrega = dataEntrega;
        this.produtos = produtos;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
