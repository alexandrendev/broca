package com.lafis.infra;

import com.lafis.core.ports.BetaCalculator;

public class BetaProvider implements BetaCalculator {

    @Override
    public double calculateBeta(double beta, double maxTemperature, double temperature) {
//        return beta * (maxTemperature - temperature) / temperature;
        return beta;
    }
}
/*
* model # Representação da entidade do BD no código
*   Produto
* view
*   CadastroEEdicaoDeProduto
*   DelecaoDeProduto
*   ListagemDeProdutos
*   DetalhesDoProduto
* controller
*   ProdutoController
* */