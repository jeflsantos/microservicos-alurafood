package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDto> listar(@PageableDefault(size = 10)Pageable paginacao){
        return pagamentoService.obterTodosPagamentos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> detalhar(@PathVariable @NotNull Long id){
        PagamentoDto pagamentoDto = pagamentoService.obterPagamentoPorId(id);
        return ResponseEntity.ok(pagamentoDto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto pagamentoDto, UriComponentsBuilder uri){
        PagamentoDto pagamento = pagamentoService.criarPagamento(pagamentoDto);
        URI endereco = uri.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(endereco).body(pagamento);
    }

    @PutMapping
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDto pagamentoDto){
        PagamentoDto atualizado = pagamentoService.atualizarPagamento(id, pagamentoDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping
    public ResponseEntity<PagamentoDto> remover(@PathVariable @NotNull Long id){
        pagamentoService.excluirPagamento(id);

        return ResponseEntity.noContent().build();
    }
}
