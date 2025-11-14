package project.telegramfinancebot.service;

import org.springframework.stereotype.Service;
import project.telegramfinancebot.entity.GastoMes;
import project.telegramfinancebot.repository.GastoMesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GastoMesService {

    private final GastoMesRepository gastoMesRepository;

    public GastoMesService(GastoMesRepository gastoMesRepository) {
        this.gastoMesRepository = gastoMesRepository;
    }

    public BigDecimal getGastoMes(int mes, int ano){

        LocalDate inicioMes = LocalDate.of(ano,mes,  1);
        LocalDate fimMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

        return gastoMesRepository.somaPorMes(inicioMes, fimMes);
    }

    public BigDecimal getGastoPeriodo(String inicioPeriodo, String fimPeriodo){
        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate inicio = LocalDate.parse(inicioPeriodo, formatoEntrada);
        LocalDate fim = LocalDate.parse(fimPeriodo, formatoEntrada);


        return gastoMesRepository.somaPorMes(inicio, fim);
    }

    public List<GastoMes> listarGastosPorPeriodo(String inicioPeriodo, String fimPeriodo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate inicio = LocalDate.parse(inicioPeriodo, formatter);
        LocalDate fim = LocalDate.parse(fimPeriodo, formatter);

        return gastoMesRepository.findByDataBetween(inicio, fim);
    }
}
