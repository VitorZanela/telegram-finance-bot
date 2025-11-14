package project.telegramfinancebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.telegramfinancebot.entity.GastoMes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface GastoMesRepository extends JpaRepository<GastoMes, Integer> {

    @Query("SELECT SUM(g.valor) FROM GastoMes g " +
            "WHERE g.data BETWEEN :inicioMes AND :fimMes")
    BigDecimal somaPorMes(
            @Param("inicioMes") LocalDate inicioMes,
            @Param("fimMes") LocalDate fimMes);

    List<GastoMes> findByDataBetween(LocalDate inicio, LocalDate fim);
}
