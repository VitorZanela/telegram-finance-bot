package projeto.chatbotgastos.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.telegramfinancebot.entity.Gasto;
import project.telegramfinancebot.service.GastoService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/gastos")
public class GastoController {

    private final GastoService gastoService;

    public GastoController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    @GetMapping("/gasto_total")
    public ResponseEntity<BigDecimal> getTotalMesAtual(){
        BigDecimal gastoTotal = gastoService.getGastoMesAtual();
        
        return ResponseEntity.ok(gastoTotal);
    }

    @PostMapping
    public ResponseEntity<Gasto> registrarGasto(@RequestBody Gasto gasto){

        Gasto gastoSalvo = gastoService.registrarGasto(
                gasto.getTipo(),
                gasto.getValor(),
                gasto.getData()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(gastoSalvo);

    }

    @GetMapping
    public List<Gasto> listarTodosGastos(){
        return gastoService.listarTodosGastos();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Gasto> deletarPorId(@PathVariable Integer id){
        gastoService.deletarPorId(id);

        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Gasto> alterarGasto(@PathVariable Integer id, @RequestBody Gasto gastoAtualizado){

        Gasto gasto = gastoService.alterarGasto(id, gastoAtualizado);

        return ResponseEntity.ok(gasto);
    }
}
