package cencor.meif.fix.client.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by mhernandez on 5/29/15.
 */
@Entity
@Table(name = "Errores", schema = "", catalog = "FixClientDB")
public class ErroresEntity implements Serializable {
    private Long id;
    private String clOrdId;
    private String mensajeError;
    private Timestamp fechaInsercion;

    @Id
    @Column(name = "Id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ClOrdID")
    public String getClOrdId() {
        return clOrdId;
    }

    public void setClOrdId(String clOrdId) {
        this.clOrdId = clOrdId;
    }

    @Basic
    @Column(name = "MensajeError")
    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    @Basic
    @Column(name = "FechaInsercion")
    public Timestamp getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Timestamp fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErroresEntity that = (ErroresEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (clOrdId != null ? !clOrdId.equals(that.clOrdId) : that.clOrdId != null) return false;
        if (mensajeError != null ? !mensajeError.equals(that.mensajeError) : that.mensajeError != null) return false;
        if (fechaInsercion != null ? !fechaInsercion.equals(that.fechaInsercion) : that.fechaInsercion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (clOrdId != null ? clOrdId.hashCode() : 0);
        result = 31 * result + (mensajeError != null ? mensajeError.hashCode() : 0);
        result = 31 * result + (fechaInsercion != null ? fechaInsercion.hashCode() : 0);
        return result;
    }
}
