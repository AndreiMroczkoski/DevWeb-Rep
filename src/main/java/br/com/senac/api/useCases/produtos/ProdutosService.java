package br.com.senac.api.useCases.produtos;

import br.com.senac.api.entitys.Produtos;
import br.com.senac.api.useCases.produtos.domains.ProdutosResponseDom;
import br.com.senac.api.useCases.produtos.implement.ProdutosRepository;
import br.com.senac.api.utils.paginacao.PaginacaoRequest;
import br.com.senac.api.utils.paginacao.PaginacaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutosService {


    @Autowired
    private ProdutosRepository produtosRepository;


    public PaginacaoResponse<List<ProdutosResponseDom>>
            carregarProdutos(PaginacaoRequest request) {

        PageRequest paginacao = PageRequest.of(request.getPagina() - 1 < 0 ? 0 : request.getPagina() - 1,
                request.getTamanho(),
                Sort.Direction.valueOf("ASC"),
                request.getOrderBy());

        Page<Produtos> produtosResult = produtosRepository.findAll(paginacao);

        if (!produtosResult.isEmpty()){
            List<ProdutosResponseDom> produtosResponse =
                    produtosResult
                            .stream()
                            .map(row -> new ProdutosResponseDom(
                                    row.getId(),
                                    row.getNome(),
                                    row.getDescricao()))
                            .toList();

            PaginacaoResponse<List<ProdutosResponseDom>> paginaResponse = new PaginacaoResponse<>();
            paginaResponse.setPagina(produtosResult.getNumber() + 1);
            paginaResponse.setTamanho(produtosResult.getSize());
            paginaResponse.setTotal(produtosResult.getTotalPages());
            paginaResponse.setData(produtosResponse);

            return paginaResponse;
        }

        return null;

    }


    public List<ProdutosResponseDom> carregarProdutos() {
        List<Produtos> produtosResult = produtosRepository.findAll();

        if (!produtosResult.isEmpty()){
            List<ProdutosResponseDom> produtosResponse =
                    produtosResult
                            .stream()
                            .map(row -> new ProdutosResponseDom(
                                    row.getId(),
                                    row.getNome(),
                                    row.getDescricao()))
                            .toList();
            return produtosResponse;
        }

        return null;

    }


}
