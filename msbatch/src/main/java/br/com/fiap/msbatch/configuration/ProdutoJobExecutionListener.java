package br.com.fiap.msbatch.configuration;

import org.springframework.batch.core.*;

import java.io.File;

public class ProdutoJobExecutionListener implements JobExecutionListener {

    private String arquivo;

    public ProdutoJobExecutionListener(String arquivo){
        this.arquivo = arquivo;
        System.out.println(arquivo);
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        try {
            File arquivoOriginal = new File(arquivo);
            String novoNome = arquivo.substring(0, arquivo.length() - 4) + ".ant";
            File novoArquivo = new File(novoNome);
            arquivoOriginal.renameTo(novoArquivo);
            arquivoOriginal.delete();
        } catch (Exception err) {
            System.out.println("Erro ao deletar arquivo : " + err.getMessage());
        }
    }
}
