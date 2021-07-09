package br.com.alura.forum.controle;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controle.dto.DetalhesTopicoDto;
import br.com.alura.forum.controle.dto.TopicoDto;
import br.com.alura.forum.controle.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controle.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repositorio.CursoRepository;
import br.com.alura.forum.repositorio.TopicoRepository;


@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso) {
		
		if (nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDto.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);
		}
		
	}
	
	 @GetMapping("/{id}")
     public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {
		 
		 Optional<Topico> topico = topicoRepository.findById(id);
		 if(topico.isPresent()) {
			 return ResponseEntity.ok(new DetalhesTopicoDto(topico.get()));
		 }
		 	return ResponseEntity.notFound().build();
		 
     }
	
	 @PostMapping
	 @Transactional //prompts Spring to commit the transaction at the end of the method
	 public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		
		 Topico topico = form.converter(cursoRepository);
		 topicoRepository.save(topico);
		
		 URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		 return ResponseEntity.created(uri).body(new TopicoDto(topico));
		
	 }
	 
	 @PutMapping("/{id}")
	 @Transactional
	 public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		 
		 Optional<Topico> atualizar = topicoRepository.findById(id);
		 if(atualizar.isPresent()) {
			 
			 Topico topico = form.atualizar(id, topicoRepository);
			 return ResponseEntity.ok(new TopicoDto(topico));
		 }
		 	return ResponseEntity.notFound().build();
		 	
	 }
	 
	 @DeleteMapping("/{id}")
	 @Transactional
	 public ResponseEntity<?> deletar(@PathVariable long id) {
		 
		 Optional<Topico> atualizar = topicoRepository.findById(id);
		 
		 if(atualizar.isPresent()) {
			 
			 topicoRepository.deleteById(id);
			 return ResponseEntity.ok().build();
		 }
		 	return ResponseEntity.notFound().build();
		 
	 }
}
