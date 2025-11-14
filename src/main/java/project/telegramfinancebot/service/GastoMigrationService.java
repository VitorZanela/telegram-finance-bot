package project.telegramfinancebot.service;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.telegramfinancebot.entity.Gasto;
import project.telegramfinancebot.entity.GastoMes;
import project.telegramfinancebot.repository.GastoMesRepository;
import project.telegramfinancebot.repository.GastoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GastoMigrationService {

    private final GastoRepository gastoRepository;
    private final GastoMesRepository gastoMesRepository;

    public GastoMigrationService(GastoRepository gastoRepository, GastoMesRepository gastoMesRepository) {
        this.gastoRepository = gastoRepository;
        this.gastoMesRepository = gastoMesRepository;
    }

    @Transactional
    @Scheduled(cron = "*/20 * * * * ?")
    public void migrarGastoParaGastoMes() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMesAnterior = hoje.minusMonths(1).withDayOfMonth(1);
        LocalDate fimMesAnterior = inicioMesAnterior.withDayOfMonth(inicioMesAnterior.lengthOfMonth());

        List<Gasto> gastos = gastoRepository.findByDataBetween(inicioMesAnterior, fimMesAnterior);

        List<GastoMes> listaGastosMensais = new ArrayList<>();
        for (Gasto gastoOriginal : gastos) {
            GastoMes gastoMensal = new GastoMes();
            gastoMensal.setTipo(gastoOriginal.getTipo());
            gastoMensal.setValor(gastoOriginal.getValor());
            gastoMensal.setData(gastoOriginal.getData());
            listaGastosMensais.add(gastoMensal);
        }

        gastoMesRepository.saveAll(listaGastosMensais);

        gastoRepository.deleteAll(gastos);

    }
}
