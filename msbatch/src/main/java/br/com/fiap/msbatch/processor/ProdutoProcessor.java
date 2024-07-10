package br.com.fiap.msbatch.processor;

import br.com.fiap.msbatch.model.Produto;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

public class ProdutoProcessor implements ItemProcessor<Produto,Produto> {

    @Override
    public Produto process(Produto item) throws Exception {
        item.setDataUpdate(LocalDateTime.now());
        System.out.println(item.toString());
        return item;
    }
}
