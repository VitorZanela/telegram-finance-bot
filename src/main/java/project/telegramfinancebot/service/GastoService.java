package project.telegramfinancebot.service;

import org.springframework.stereotype.Service;
import project.telegramfinancebot.entity.Gasto;
import project.telegramfinancebot.repository.GastoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class GastoService {

    private final GastoRepository gastoRepository;

    public GastoService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    public Gasto registrarGasto(String tipo, BigDecimal valor, LocalDate data){

        Gasto gasto = new Gasto();
        gasto.setTipo(tipo);
        gasto.setValor(valor);
        gasto.setData(data);

        return gastoRepository.save(gasto);
    }

    public BigDecimal getGastoMesAtual(){
        return gastoRepository.getGastoSomaTotal();
    }

    public List<Gasto> listarTodosGastos(){
        return gastoRepository.findAll();
    }

    public void deletarPorId(Integer id){
        gastoRepository.deleteById(id);
    }

    public Gasto alterarGasto(Integer id, Gasto gastoAtualizado){
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto não entrado"));

        if (gastoAtualizado.getTipo() != null) {
            gasto.setTipo(gastoAtualizado.getTipo());
        }
        if (gastoAtualizado.getValor() != null) {
            gasto.setValor(gastoAtualizado.getValor());
        }
        if (gastoAtualizado.getData() != null) {
            gasto.setData(gastoAtualizado.getData());
        }

        return gastoRepository.save(gasto);
    }



}
