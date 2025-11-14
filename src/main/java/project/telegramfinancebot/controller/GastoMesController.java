package project.telegramfinancebot.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.telegramfinancebot.service.GastoMesService;

import java.math.BigDecimal;


@RestController
@RequestMapping("/gastos")
public class GastoMesController {

    private GastoMesService gastoMesService;

    public GastoMesController(GastoMesService gastoMesService) {
        this.gastoMesService = gastoMesService;
    }

    @GetMapping("/gasto_mes")
    public ResponseEntity<BigDecimal> getGastoMes (@RequestParam int mes, @RequestParam int ano){
        BigDecimal gastoMes = gastoMesService.getGastoMes(mes, ano);

        return ResponseEntity.ok(gastoMes);
    }

    @GetMapping("/gasto_periodo")
    public ResponseEntity<BigDecimal> listarGastoPorMes(@RequestParam String inicioPeriodo, @RequestParam String fimPeriodo){
        BigDecimal gastoPeriodo = gastoMesService.getGastoPeriodo(inicioPeriodo, fimPeriodo);

        return ResponseEntity.ok(gastoPeriodo);
    }
}
