package project.telegramfinancebot.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "gastos_mes")
public class GastoMes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tipo;
    private BigDecimal valor;
    private LocalDate data;

    public GastoMes(String tipo, BigDecimal valor, LocalDate data) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
    }

    public GastoMes() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
