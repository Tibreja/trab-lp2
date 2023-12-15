import java.util.List;

public interface GerenciadorQuadra {
    void empilhar(Container container, String posicao);

    Container desempilhar(String idContainer);

    String consultarTopo(String posicao);

    int quantidadeConteineresPorTipoCarga(String tipoCarga);

    double pesoTotalCargasPorTipo(String tipoCarga);

    int quantidadeConteineresPorTipoOperacao(String tipoOperacao);

    List<String> posicoesVazias();

    int getTamanhoPilha(String posicao);
}

