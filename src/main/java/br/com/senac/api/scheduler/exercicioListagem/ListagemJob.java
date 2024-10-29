package br.com.senac.api.scheduler.exercicioListagem;

import br.com.senac.api.useCases.produtos.ProdutosService;
import br.com.senac.api.useCases.produtos.domains.ProdutosResponseDom;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DisallowConcurrentExecution
public class ListagemJob implements Job {

    @Autowired
    private ProdutosService produtosService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info("Iniciando listagem...");

        List<ProdutosResponseDom> produtosResponse = produtosService.carregarProdutos();

        try {
            produtosResponse.stream().forEach(produto -> {
                System.out.println("Produto: " + produto.getNome() + " | " + "ID: " + produto.getNome());
            });
        } catch (Exception e){
            System.out.println("Fudeu tudo nesse krl!!");
        }





    }

}
