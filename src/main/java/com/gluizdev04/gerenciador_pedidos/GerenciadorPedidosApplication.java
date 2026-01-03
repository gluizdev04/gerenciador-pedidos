package com.gluizdev04.gerenciador_pedidos;

import com.gluizdev04.gerenciador_pedidos.principal.Principal;
import com.gluizdev04.gerenciador_pedidos.repository.CategoriaRepository;
import com.gluizdev04.gerenciador_pedidos.repository.PedidoRepository;
import com.gluizdev04.gerenciador_pedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GerenciadorPedidosApplication implements CommandLineRunner  {
    @Autowired
    private Principal principal;
    public static void main(String[] args) {
        SpringApplication.run(GerenciadorPedidosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        principal.exibirMenu();
    }
}
