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
                    9 - Ver produtos de uma categoria específica ordenados pelo preço de forma decrescente
                    10 - Ver quantos produtos uma categoria específica possui
                    11 - Ver a quantia de produtos a cima de um valor específico
                    12 - Ver produtos com preço menor que o desejado ou através de um trecho do nome do produto
                    13 - Ver pedidos realizados após uma data
                    14 - Ver pedidos realizados antes uma data
                    15 - Ver pedidos realizados entre datas
                    16 - Os três produtos mais caros
                    17 - Os cinco produtos mais baratos de um categoria
                    18 - Ver TODOS os produtos ordenador de forma crescente (valor)
                    19 - Ver TODOS os produtos ordenador de forma decrescente (valor)
                    20 - Produtos que se iniciam com uma letra especifica
                    21 - Média de preço dos produtos
                    22 - Produto mais caro de um categoria
                    23 - Quantia de produto por categoria
                    
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
                case 9:
                    buscarProdutosPorCategoriaPrecoDecrescente();
                    break;
                case 10:
                    buscarQuantiaDeProdutosPorCategoria();
                    break;
                case 11:
                    buscarQuantiaDeProdutosACimaDeUmValor();
                    break;
                case 12:
                    buscarProdutosPorPrecoOuTrecho();
                    break;
                case 13:
                    buscarPedidosFeitosAposData();
                    break;
                case 14:
                    buscarPedidosFeitosAntesData();
                    break;
                case 15:
                    buscarPedidosFeitosEntreDatas();
                    break;
                case 16:
                    buscarOsTresProdutosMaisCaros();
                    break;
                case 17:
                    buscarCincoProdutosMaisBaratosDeCategoria();
                    break;
                case 18:
                    ordenarTodosOsProdutosCrescenteValor();
                    break;
                case 19:
                    ordenarTodosOsProdutosDecrescenteValor();
                    break;
                case 20:
                    produtosQueIniciamComUmaLetraEspecifica();
                    break;
                case 21:
                    mediaPrecoProdutos();
                    break;
                case 22:
                    produtoMaisCaroCategoria();
                    break;
                case 23:
                    quantiaProdutoCategoria();
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
        List<Produto> produtosEncontrados = produtoRepository.buscarProdutoAPartirDeUmValor(valor);

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
        var categoriaDesejada = entrada.nextLine();

        List<Produto> produtosEncontrados = produtoRepository.findByCategoriaNomeIgnoreCaseOrderByPrecoAsc(categoriaDesejada);

        if (!produtosEncontrados.isEmpty()) {
            System.out.println("A categoria " + categoriaDesejada + " possui os seguintes produtos (ordenados por preço de forma crescente):");
            produtosEncontrados.forEach(p -> System.out.println(p.getNome() + " valor: " + p.getPreco()));
        } else {
            System.out.println("A categoria " + categoriaDesejada + " não possui nenhum produtos!");
        }
    }

    private void buscarProdutosPorCategoriaPrecoDecrescente() {
        List<Categoria> categorias = categoriaRepository.findAll();
        categorias.forEach(c -> System.out.println(c.getNome()));
        System.out.print("Ver produtos de qual categoria? ");
        var categoriaDesejada = entrada.nextLine();

        List<Produto> produtosEncontrados = produtoRepository.findByCategoriaNomeIgnoreCaseOrderByPrecoDesc(categoriaDesejada);

        if (!produtosEncontrados.isEmpty()) {
            System.out.println("A categoria " + categoriaDesejada + " possui os seguintes produtos (ordenados por preço de forma decrescente):");
            produtosEncontrados.forEach(p -> System.out.println(p.getNome() + " valor: " + p.getPreco()));
        } else {
            System.out.println("A categoria " + categoriaDesejada + " não possui nenhum produtos!");
        }
    }

    private void buscarQuantiaDeProdutosPorCategoria() {
        List<Categoria> categorias = categoriaRepository.findAll();
        categorias.forEach(c -> System.out.println(c.getNome()));
        System.out.print("Digite e categoria que deseja contar: ");
        var categoriaParaContagem = entrada.nextLine();

        long categoriaContada = produtoRepository.countByCategoriaNomeIgnoreCase(categoriaParaContagem);
        System.out.println("A categoria " + categoriaParaContagem + " possui " + categoriaContada + " produtos");
    }

    private void buscarQuantiaDeProdutosACimaDeUmValor() {
        System.out.print("Ver quantia de produtos a cima de qual valor?");
        var valor = entrada.nextDouble();
        long quantiaProduto = produtoRepository.countByPrecoGreaterThan(valor);

        System.out.println(quantiaProduto + " a cima de R$" + valor);
    }

    private void buscarProdutosPorPrecoOuTrecho() {
        System.out.print("Ver produtos com o preço menor que: ");
        var precoDesejado = entrada.nextDouble();
        entrada.nextLine();
        System.out.print("Trecho do nome do produto: ");
        var trechoDesejado = entrada.nextLine();
        List<Produto> produtosEncontrados = produtoRepository.findByPrecoLessThanOrNomeContainingIgnoreCase(precoDesejado, trechoDesejado);

        if (!produtosEncontrados.isEmpty()) {
            System.out.println("Produtos encontrados:");
            produtosEncontrados.forEach(p -> System.out.println(p.getNome() + " valor: " + p.getPreco()));
        } else {
            System.out.println("Nenhum produto foi encontrado!");
        }
    }

    private void buscarPedidosFeitosAposData() {
        System.out.println("Ver pedidos feitos após qual data (yyyy-MM-dd)? ");
        var dataString = entrada.nextLine();
        LocalDate data = LocalDate.parse(dataString);
        List<Pedido> pedidosEncontrados = pedidoRepository.findByDataAfter(data);

        pedidosEncontrados.forEach(p -> System.out.println("Pedido com ID " + p.getId() + " foi feito em " + p.getData()));
    }


    private void buscarPedidosFeitosAntesData() {
        System.out.println("Ver pedidos feitos antes de qual data (yyyy-MM-dd)?");
        var dataString = entrada.nextLine();

        LocalDate data = LocalDate.parse(dataString);
        List<Pedido> pedidosEncontrados = pedidoRepository.findByDataBefore(data);
        pedidosEncontrados.forEach(p -> System.out.println("Pedido com ID " + p.getId() + " foi feito em " + p.getData()));
    }


    private void buscarPedidosFeitosEntreDatas() {
        System.out.println("Data inical (yyyy-MM-dd): ");
        var dataInicioString = entrada.nextLine();
        System.out.println("Data fim (yyyy-MM-dd): ");
        var dataFimString = entrada.nextLine();
        LocalDate dataInicio = LocalDate.parse(dataInicioString);
        LocalDate dataFim = LocalDate.parse(dataFimString);
        List<Pedido> pedidosEncontrados = pedidoRepository.pedidoEntrePeriodo(dataInicio, dataFim);

        pedidosEncontrados.forEach(p -> System.out.println("Pedido com ID " + p.getId() + " foi feito em " + p.getData()));
    }

    private void buscarOsTresProdutosMaisCaros() {
        List<Produto> produtosEncontrados = produtoRepository.findTop3ByOrderByPrecoDesc();
        System.out.println("Os três produtos mais caros:");
        produtosEncontrados.forEach(p -> {
            System.out.println(p.getNome() + " | Valor: R$" + p.getPreco());
        });
    }

    private void buscarCincoProdutosMaisBaratosDeCategoria() {
        List<Categoria> categorias = categoriaRepository.findAll();
        categorias.forEach(c -> System.out.println(c.getNome()));
        System.out.print("Qual categoria? ");
        var categoria = entrada.nextLine();
        List<Produto> produtosEncontrados = produtoRepository.findTop5ByCategoriaNomeIgnoreCaseOrderByPrecoAsc(categoria);
        produtosEncontrados.forEach(p -> {
            System.out.println(p.getNome() + " | Valor: R$" + p.getPreco());
        });
    }

    private void ordenarTodosOsProdutosCrescenteValor() {
        List<Produto> produtosOrdenadosCrescente = produtoRepository.ordenarPorValorCrescente();
        produtosOrdenadosCrescente.forEach(p -> {
            System.out.println(p.getNome() + " Valor: R$" + p.getPreco());
        });
    }

    private void ordenarTodosOsProdutosDecrescenteValor() {
        List<Produto> produtosOrdenadosDecrescente = produtoRepository.ordenarPorValorDecrescente();
        produtosOrdenadosDecrescente.forEach(p -> {
            System.out.println(p.getNome() + " Valor: R$" + p.getPreco());
        });
    }

    private void produtosQueIniciamComUmaLetraEspecifica() {
        System.out.print("Deseja buscar produtos que comecem com qual letra? ");
        var letraInicial = entrada.nextLine();

        List<Produto> produtosEncontrados = produtoRepository.buscarProdutoComInicioEspecifico(letraInicial);
        produtosEncontrados.forEach(p -> {
            System.out.println(p.getNome() + " Valor: R$" + p.getPreco());
        });
    }

    private void mediaPrecoProdutos() {
        Double mediaPreco = produtoRepository.mediaPrecoProdutos();
        System.out.println("Média de preco dos produtos: " + mediaPreco);
    }

    private void produtoMaisCaroCategoria() {
        System.out.print("Ver produto mais caro de qual categoria? ");
        var categoriaDesejado = entrada.nextLine();
        Double produtoMaisCaro = produtoRepository.produtoMaisCaroCategoria(categoriaDesejado);
        System.out.println("O produto mais caro da categoria " + categoriaDesejado + " custa: R$" + produtoMaisCaro);
    }

    private void quantiaProdutoCategoria() {
        List<Object[]> resultados = produtoRepository.contarQuatiaProdutoCategoria();

        System.out.println("Conta de produtos: ");

        for (Object[] resultado : resultados) {
            String nomeProduto = (String) resultado[0];
            Long quantiaProduto = (Long) resultado[1];

            System.out.println("Categoria: " + nomeProduto + " | Quantidade: " + quantiaProduto);
        }
    }

    public void criarFornecedores() {
        if (fornecedorRepository.count() > 0) return;
        Fornecedor fornSamsung = new Fornecedor("Samsung Logística");
        Fornecedor fornFastShop = new Fornecedor("Fast Shop Distribuidora");
        Fornecedor fornKabum = new Fornecedor("Kabum Log");
        Fornecedor fornArno = new Fornecedor("Arno Factory");

        fornecedorRepository.saveAll(List.of(fornSamsung, fornFastShop, fornKabum, fornArno));
    }

    public void criarCategoria() {
        if (categoriaRepository.count() > 0) return;
        Categoria catEletronicos = new Categoria("Eletrônicos");
        Categoria catEletrodomesticos = new Categoria("Eletrodomésticos");
        Categoria catGamer = new Categoria("Gamer");

        categoriaRepository.saveAll(List.of(catEletronicos, catEletrodomesticos, catGamer));
    }

    public void criarProdutos() {
        if (produtoRepository.count() > 0) return;

        var samsung = fornecedorRepository.findByNome("Samsung Logística").get();
        var fastShop = fornecedorRepository.findByNome("Fast Shop Distribuidora").get();
        var kabum = fornecedorRepository.findByNome("Kabum Log").get();
        var arno = fornecedorRepository.findByNome("Arno Factory").get();

        var eletronicos = categoriaRepository.findByNome("Eletrônicos").get();
        var eletrodomesticos = categoriaRepository.findByNome("Eletrodomésticos").get();
        var gamer = categoriaRepository.findByNome("Gamer").get();

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Smartphone Galaxy S23", 4500.00, samsung, eletronicos));
        produtos.add(new Produto("Notebook Dell XPS", 8900.00, fastShop, eletronicos));
        produtos.add(new Produto("Monitor LG Ultrawide 29", 1200.00, kabum, eletronicos));
        produtos.add(new Produto("Tablet Tab S9", 3200.00, samsung, eletronicos));
        produtos.add(new Produto("Smartwatch Watch 5", 1500.00, samsung, eletronicos));

        produtos.add(new Produto("Geladeira Frost Free", 3100.00, fastShop, eletrodomesticos));
        produtos.add(new Produto("Microondas Inox", 800.00, fastShop, eletrodomesticos));
        produtos.add(new Produto("Air Fryer Family", 450.00, arno, eletrodomesticos));
        produtos.add(new Produto("Cafeteira Expresso", 600.00, arno, eletrodomesticos));
        produtos.add(new Produto("Aspirador Robô", 1800.00, kabum, eletrodomesticos));

        produtos.add(new Produto("Mouse Logitech G Pro", 650.00, kabum, gamer));
        produtos.add(new Produto("Teclado Mecânico HyperX", 750.00, kabum, gamer));
        produtos.add(new Produto("Headset Cloud Stinger", 350.00, kabum, gamer));
        produtos.add(new Produto("Cadeira Gamer DX", 1200.00, fastShop, gamer));
        produtos.add(new Produto("Placa de Vídeo RTX 4060", 2500.00, kabum, gamer));

        produtoRepository.saveAll(produtos);
    }

    public void criarPedidos() {
        if (pedidoRepository.count() > 0) return;

        var mouse = produtoRepository.findByNomeIgnoreCase("Mouse Logitech G Pro").get();
        var teclado = produtoRepository.findByNomeIgnoreCase("Teclado Mecânico HyperX").get();
        var headset = produtoRepository.findByNomeIgnoreCase("Headset Cloud Stinger").get();
        var placaVideo = produtoRepository.findByNomeIgnoreCase("Placa de Vídeo RTX 4060").get();

        var geladeira = produtoRepository.findByNomeIgnoreCase("Geladeira Frost Free").get();
        var microondas = produtoRepository.findByNomeIgnoreCase("Microondas Inox").get();

        var notebook = produtoRepository.findByNomeIgnoreCase("Notebook Dell XPS").get();
        var monitor = produtoRepository.findByNomeIgnoreCase("Monitor LG Ultrawide 29").get();

        var cafeteira = produtoRepository.findByNomeIgnoreCase("Cafeteira Expresso").get();

        var tablet = produtoRepository.findByNomeIgnoreCase("Tablet Tab S9").get();
        var airfryer = produtoRepository.findByNomeIgnoreCase("Air Fryer Family").get();

        var galaxy = produtoRepository.findByNomeIgnoreCase("Smartphone Galaxy S23").get();
        var watch = produtoRepository.findByNomeIgnoreCase("Smartwatch Watch 5").get();

        List<Pedido> pedidos = new ArrayList<>();

        Pedido pedidoGamer = new Pedido(
                LocalDate.now().minusMonths(1),
                LocalDate.now().minusMonths(1).plusDays(7),
                Arrays.asList(mouse, teclado, headset, placaVideo)
        );
        pedidos.add(pedidoGamer);

        Pedido pedidoCozinha = new Pedido(
                LocalDate.now().minusWeeks(2),
                LocalDate.now().minusWeeks(2).plusDays(3),
                Arrays.asList(geladeira, microondas)
        );
        pedidos.add(pedidoCozinha);

        Pedido pedidoHomeOffice = new Pedido(
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                Arrays.asList(notebook, monitor)
        );
        pedidos.add(pedidoHomeOffice);

        Pedido pedidoCafe = new Pedido(
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1),
                Arrays.asList(cafeteira)
        );
        pedidos.add(pedidoCafe);

        Pedido pedidoMix = new Pedido(
                LocalDate.now().minusMonths(2),
                LocalDate.now().minusMonths(2).plusDays(10),
                Arrays.asList(tablet, airfryer)
        );
        pedidos.add(pedidoMix);

        Pedido pedidoPendente = new Pedido(
                LocalDate.now(),
                null,
                Arrays.asList(galaxy, watch)
        );
        pedidos.add(pedidoPendente);

        pedidoRepository.saveAll(pedidos);

        System.out.println("5 Pedidos criados com sucesso!");
    }
}