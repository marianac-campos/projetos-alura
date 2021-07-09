package br.com.alura.forum.controle.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repositorio.TopicoRepository;

public class AtualizacaoTopicoForm {
	
	 @NotEmpty 
	 @Size(min = 5)
     private String titulo;

     @NotEmpty
     @Size(min = 10)
     private String mensagem;
     
     public Topico atualizar(Long id, TopicoRepository topicoRepository) {
    	 
    	 Topico topico = topicoRepository.getOne(id);

    	 topico.setTitulo(this.titulo);
    	 topico.setMensagem(this.mensagem);

    	 return topico;
     }

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}