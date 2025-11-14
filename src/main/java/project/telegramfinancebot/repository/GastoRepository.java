package project.telegramfinancebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.telegramfinancebot.entity.Gasto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Integer> {

    @Query("SELECT SUM(g.valor) FROM Gasto g")
    BigDecimal getGastoSomaTotal();
    List<Gasto> findByDataBetween(LocalDate inicio, LocalDate fim);
}
