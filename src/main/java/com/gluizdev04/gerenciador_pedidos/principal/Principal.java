package com.gluizdev04.gerenciador_pedidos.principal;

import com.gluizdev04.gerenciador_pedidos.model.Categoria;
import com.gluizdev04.gerenciador_pedidos.model.Fornecedor;
import com.gluizdev04.gerenciador_pedidos.model.Pedido;
import com.gluizdev04.gerenciador_pedidos.model.Produto;
import com.gluizdev04.gerenciador_pedidos.repository.CategoriaRepository;
import com.gluizdev04.gerenciador_pedidos.repository.FornecedorRepository;
import com.gluizdev04.gerenciador_pedidos.repository.PedidoRepository;
import com.gluizdev04.gerenciador_pedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    private Scanner entrada = new Scanner(System.in);

    public void exibirMenu() {
        var menuExibindo = true;
        while (menuExibindo) {
            var menu = """
                    1 - Buscar produto
                    2 - Buscar produto por categoria
                    3 - Buscar produtos a partir de um valor
                    4 - Buscar produtos abaixo de um valor
                    5 - Buscar produto por trecho de nome
                    6 - Produtos que não possuem data de entrega
                    7 - Produtos com data de entrega
                    8 - Ver produtos de uma categoria específica ordenados pelo preço de forma crescente
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            var opcao = entrada.nextInt();
            entrada.nextLine();
            switch (opcao) {
                case 1:
                    buscarProdutoPorNome();
                    break;
                case 2:
                    buscarProdutoPorCategoria();
                    break;
                case 3:
                    buscarProdutosAPartirDeValorFornecido();
                    break;
                case 4:
                    buscarProdutoAbaixoDeValorFornecido();
                    break;
                case 5:
                    buscarProdutoPorTrechoDoNome();
                    break;
                case 6:
                    buscarPedidosSemDataDeEntregar();
                    break;
                case 7:
                    buscarProdutosComDataDeEntrega();
                    break;
                case 8:
                    buscarProdutosPorCategoriaPrecoCrescente();
                    break;
                case 0:
                    menuExibindo = false;
                    break;
            }
        }
    }

    private void buscarProdutoPorCategoria() {
        System.out.print("Deseja ver produtos de qual categoria? ");
        var categoria = entrada.nextLine();
        List<Produto> produtosEncontrados = produtoRepository.findByCategoriaNomeIgnoreCase(categoria);

        if (!produtosEncontrados.isEmpty()) {
            System.out.println("A categoria " + categoria + " possui os seguintes produtos:");
            produtosEncontrados.forEach(p -> System.out.println(p.getNome() + " valor: " + p.getPreco()));
        } else {
            System.out.println("A categoria " + categoria + " não possui nenhum produtos!");
        }
    }

    private void buscarProdutoPorNome() {
        System.out.print("Digite o nome do produto que deseja buscar: ");
        var nome = entrada.nextLine();
        Optional<Produto> produtoEncontrado = produtoRepository.findByNomeIgnoreCase(nome);

        if (produtoEncontrado.isPresent()) {
            System.out.println(produtoEncontrado.get().getNome() +
                    " \nValor: " + produtoEncontrado.get().getPreco());
        } else {
            System.out.println("O produto digitado não foi encontrado!");
        }
    }

    private void buscarProdutosAPartirDeValorFornecido() {
        System.out.print("Deseja ver produtos com preço a partir de: ");
        var valor = entrada.nextDouble();
        List<Produto> produtosEncontrados = produtoRepository.findByPrecoGreaterThan(valor);

        if (!produtosEncontrados.isEmpty()) {
            System.out.println("Produtos a cima de: " + valor);
            produtosEncontrados.forEach(p -> System.out.println(p.getNome() + " | preço: " + p.getPreco()));
        } else {
            System.out.println("Não existe produtos com valores a cima do desejado");
        }
    }

    private void buscarProdutoAbaixoDeValorFornecido() {
        System.out.print("Deseja ver produtos com valor abaixo de: ");
        var valor = entrada.nextDouble();
        List<Produto> produtosEncontrados = produtoRepository.findByPrecoLessThan(valor);

        if (!produtosEncontrados.isEmpty()) {
            System.out.println("Produtos abaixo de: " + valor);
            produtosEncontrados.forEach(p -> System.out.println(p.getNome() + " | preço: " + p.getPreco()));
        } else {
            System.out.println("Não existe produtos com valores abaixo do desejado");
        }
    }

    private void buscarProdutoPorTrechoDoNome() {
        System.out.print("Digite o um trecho do produto desejado: ");
        var trechoNome = entrada.nextLine();
        Optional<Produto> produtoEncontrado = produtoRepository.findByNomeContainingIgnoreCase(trechoNome);

        if (produtoEncontrado.isPresent()) {
            System.out.println(produtoEncontrado.get().getNome() +
                    " \nValor: " + produtoEncontrado.get().getPreco());
        } else {
            System.out.println("Nenhum produto contém o trecho digitado!");
        }
    }

    private void buscarPedidosSemDataDeEntregar() {
        List<Pedido> pedidosEncontrados = pedidoRepository.findByDataEntregaIsNull();

        System.out.println("Pedidos sem data de entrega: ");
        pedidosEncontrados.forEach(p -> {
            String nomesProdutos = p.getProdutos().stream()
                    .map(Produto::getNome)
                    .collect(Collectors.joining(", "));

            System.out.println("ID do pedido: " + p.getId() + " | Produtos: " + nomesProdutos);
        });
    }

    private void buscarProdutosComDataDeEntrega() {
        List<Pedido> pedidosEncontrados = pedidoRepository.findByDataEntregaIsNotNull();

        System.out.println("Pedidos com data de entrega: ");
        pedidosEncontrados.forEach(p -> {
            String nomesProdutos = p.getProdutos().stream()
                    .map(Produto::getNome)
                    .collect(Collectors.joining(", "));

            System.out.println("ID do pedido: " + p.getId() + " | Produtos: " + nomesProdutos);
        });
    }

    private void buscarProdutosPorCategoriaPrecoCrescente() {
        List<Categoria> categorias = categoriaRepository.findAll();
        categorias.forEach(c -> System.out.println(c.getNome()));
        System.out.print("Ver produtos de qual categoria? ");
        var categoriaDesejada =entrada.nextLine();

        List<Produto> produtosEncontrados = produtoRepository.findByCategoriaNomeIgnoreCaseOrderByPrecoAsc(categoriaDesejada);

        if (!produtosEncontrados.isEmpty()) {
            System.out.println("A categoria " + categoriaDesejada + " possui os seguintes produtos (ordenados por preço de forma crescente):");
            produtosEncontrados.forEach(p -> System.out.println(p.getNome() + " valor: " + p.getPreco()));
        } else {
            System.out.println("A categoria " + categoriaDesejada + " não possui nenhum produtos!");
        }
    }

//    public void criarFornecedores() {
//        Fornecedor fornSamsung = new Fornecedor("Samsung Logística");
//        Fornecedor fornFastShop = new Fornecedor("Fast Shop Distribuidora");
//        Fornecedor fornKabum = new Fornecedor("Kabum Log");
//        Fornecedor fornArno = new Fornecedor("Arno Factory");
//
//        fornecedorRepository.saveAll(List.of(fornSamsung, fornFastShop, fornKabum, fornArno));
//
//    }
//
//    public void criarCategoria() {
//        Categoria catEletronicos = new Categoria("Eletrônicos");
//        Categoria catEletrodomesticos = new Categoria("Eletrodomésticos");
//        Categoria catGamer = new Categoria("Gamer");
//
//        categoriaRepository.saveAll(List.of(catEletronicos, catEletrodomesticos, catGamer));
//
//    }
//
//    public void criarProdutos() {
//        List<Produto> produtos = new ArrayList<>();
//        produtos.add(new Produto("Smartphone Galaxy S23", 4500.00, fornecedorRepository.getReferenceById(7L), categoriaRepository.getReferenceById(25L)));
//        produtos.add(new Produto("Notebook Dell XPS", 8900.00, fornecedorRepository.getReferenceById(8L), categoriaRepository.getReferenceById(25L)));
//        produtos.add(new Produto("Monitor LG Ultrawide 29", 1200.00, fornecedorRepository.getReferenceById(9L), categoriaRepository.getReferenceById(25L)));
//        produtos.add(new Produto("Tablet Tab S9", 3200.00, fornecedorRepository.getReferenceById(7L), categoriaRepository.getReferenceById(25L)));
//        produtos.add(new Produto("Smartwatch Watch 5", 1500.00, fornecedorRepository.getReferenceById(7L), categoriaRepository.getReferenceById(25L)));
//
//        produtos.add(new Produto("Geladeira Frost Free", 3100.00, fornecedorRepository.getReferenceById(8L), categoriaRepository.getReferenceById(26L)));
//        produtos.add(new Produto("Microondas Inox", 800.00, fornecedorRepository.getReferenceById(8L), categoriaRepository.getReferenceById(26L)));
//        produtos.add(new Produto("Air Fryer Family", 450.00, fornecedorRepository.getReferenceById(10L), categoriaRepository.getReferenceById(26L)));
//        produtos.add(new Produto("Cafeteira Expresso", 600.00, fornecedorRepository.getReferenceById(10L), categoriaRepository.getReferenceById(26L)));
//        produtos.add(new Produto("Aspirador Robô", 1800.00, fornecedorRepository.getReferenceById(9L), categoriaRepository.getReferenceById(26L)));
//
//        produtos.add(new Produto("Mouse Logitech G Pro", 650.00, fornecedorRepository.getReferenceById(9L), categoriaRepository.getReferenceById(27L)));
//        produtos.add(new Produto("Teclado Mecânico HyperX", 750.00, fornecedorRepository.getReferenceById(9L), categoriaRepository.getReferenceById(27L)));
//        produtos.add(new Produto("Headset Cloud Stinger", 350.00, fornecedorRepository.getReferenceById(9L), categoriaRepository.getReferenceById(27L)));
//        produtos.add(new Produto("Cadeira Gamer DX", 1200.00, fornecedorRepository.getReferenceById(8L), categoriaRepository.getReferenceById(27L)));
//        produtos.add(new Produto("Placa de Vídeo RTX 4060", 2500.00, fornecedorRepository.getReferenceById(9L), categoriaRepository.getReferenceById(27L)));
//
//        produtoRepository.saveAll(produtos);
//    }
//
//    public void criarPedidos() {
//        List<Pedido> pedidos = new ArrayList<>();
//
//        Pedido pedidoGamer = new Pedido(
//                LocalDate.now().minusMonths(1),
//                LocalDate.now().minusMonths(1).plusDays(7),
//                Arrays.asList(produtoRepository.getReferenceById(51L),
//                        produtoRepository.getReferenceById(52L),
//                        produtoRepository.getReferenceById(53L),
//                        produtoRepository.getReferenceById(55L))
//        );
//        pedidos.add(pedidoGamer);
//
//        Pedido pedidoCozinha = new Pedido(
//                LocalDate.now().minusWeeks(2),
//                LocalDate.now().minusWeeks(2).plusDays(3),
//                Arrays.asList(produtoRepository.getReferenceById(46L),
//                        produtoRepository.getReferenceById(47L))
//        );
//        pedidos.add(pedidoCozinha);
//
//        Pedido pedidoHomeOffice = new Pedido(
//                LocalDate.now(), // Data de hoje
//                LocalDate.now().plusDays(5),
//                Arrays.asList(produtoRepository.getReferenceById(42L),
//                        produtoRepository.getReferenceById(43L))
//        );
//        pedidos.add(pedidoHomeOffice);
//
//        Pedido pedidoCafe = new Pedido(
//                LocalDate.now().minusDays(1),
//                LocalDate.now().plusDays(1),
//                Arrays.asList(produtoRepository.getReferenceById(49L))
//        );
//        pedidos.add(pedidoCafe);
//
//        Pedido pedidoMix = new Pedido(
//                LocalDate.now().minusMonths(2),
//                LocalDate.now().minusMonths(2).plusDays(10),
//                Arrays.asList(produtoRepository.getReferenceById(44L),
//                        produtoRepository.getReferenceById(48L))
//        );
//        pedidos.add(pedidoMix);
//
//        Pedido pedidoPendente = new Pedido(
//                LocalDate.now(),
//                null,
//                Arrays.asList(produtoRepository.getReferenceById(41L),
//                        produtoRepository.getReferenceById(45L)) // Ex: Galaxy S23 + Smartwatch
//        );
//        pedidos.add(pedidoPendente);
//
//        pedidoRepository.saveAll(pedidos);
//
//        System.out.println("5 Pedidos criados com sucesso! 📦");
//    }
//
//    public void principal() {
//        Categoria categoriaEletronicos = new Categoria("Eletrônicos");
//        Categoria categoriaLivros = new Categoria("Livros");
//
//        Produto produto1 = new Produto("Entendendo Java", 89.90, categoriaLivros);
//        Produto produto2 = new Produto("Entendendo Spring framework", 99.99, categoriaLivros);
//        Produto produto3 = new Produto("Iphone 12", 1980.99, categoriaEletronicos);
//        Produto produto4 = new Produto("Moto G7 Play", 290.90, categoriaEletronicos);
//
//        categoriaEletronicos.setProdutos(List.of(produto3, produto4));
//        categoriaLivros.setProdutos(List.of(produto1, produto2));
//
//        categoriaRepository.saveAll(List.of(categoriaEletronicos, categoriaLivros));
//    }
//
//    public void fazendoPedido(){
//        Produto produto1 = produtoRepository.findById(37L).get();
//        Produto produto2 = produtoRepository.findById(38L).get();
//        Produto produto3 = produtoRepository.findById(39L).get();
//        Produto produto4 = produtoRepository.findById(40L).get();
//
//        System.out.println(produto1);
//        System.out.println(produto2);
//        System.out.println(produto3);
//        System.out.println(produto4);
//
//        Pedido pedidoLuiz = new Pedido(LocalDate.now());
//        Pedido pedidoNamorada = new Pedido(LocalDate.now());
//
//        pedidoLuiz.setProdutos(List.of(produto3, produto4));
//        pedidoNamorada.setProdutos(List.of(produto1));
//
//        pedidoRepository.saveAll(List.of(pedidoLuiz, pedidoNamorada));
//        System.out.println("Pedido feito com sucesso!");
//    }
//
//    public void testandoFornecedor(){
//        Fornecedor fornecedorEletronicos = new Fornecedor("Luiz Eletrônicos");
//        Fornecedor fornecedorLivros = new Fornecedor("Livraria Mariani");
//        fornecedorRepository.saveAll(List.of(fornecedorLivros, fornecedorEletronicos));
//
//        Produto produto1 = produtoRepository.findById(37L).get();
//        Produto produto2 = produtoRepository.findById(38L).get();
//        Produto produto3 = produtoRepository.findById(39L).get();
//        Produto produto4 = produtoRepository.findById(40L).get();
//
//        produto1.setFornecedor(fornecedorEletronicos);
//        produto2.setFornecedor(fornecedorEletronicos);
//        produto3.setFornecedor(fornecedorLivros);
//        produto4.setFornecedor(fornecedorLivros);
//
//        produtoRepository.saveAll(List.of(produto1, produto2, produto3, produto4));
//        System.out.println("Fornecedores estabelecidos!");
//    }
//
//    public void testandoRelacionamentos(){
//        System.out.println("Produtos na categoria Eletrônicos:");
//        categoriaRepository.findById(1L).ifPresent(categoria ->
//                categoria.getProdutos().forEach(produto ->
//                        System.out.println(" - " + produto.getNome())
//                )
//        );
//
//        System.out.println("\nPedidos e seus produtos:");
//        pedidoRepository.findAll().forEach(pedido -> {
//            System.out.println("Pedido " + pedido.getId() + ":");
//            pedido.getProdutos().forEach(produto ->
//                    System.out.println(" - " + produto.getNome())
//            );
//        });
//
//        System.out.println("\nProdutos e seus fornecedores:");
//        produtoRepository.findAll().forEach(produto ->
//                System.out.println("Produto: " + produto.getNome() +
//                        ", Fornecedor: " + produto.getFornecedor().getNome())
//        );
//    }

}
