package cencor.meif.fix.client.jpa.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by mhernandez on 5/29/15.
 */
@Entity
@Table(name = "OtrosMsjFix", schema = "", catalog = "FixClientDB")
public class OtrosMsjFixEntity {
    private Long id;
    private String clOrdId;
    private String fixMsg;
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
    @Column(name = "FixMsg")
    public String getFixMsg() {
        return fixMsg;
    }

    public void setFixMsg(String fixMsg) {
        this.fixMsg = fixMsg;
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

        OtrosMsjFixEntity that = (OtrosMsjFixEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (clOrdId != null ? !clOrdId.equals(that.clOrdId) : that.clOrdId != null) return false;
        if (fixMsg != null ? !fixMsg.equals(that.fixMsg) : that.fixMsg != null) return false;
        if (fechaInsercion != null ? !fechaInsercion.equals(that.fechaInsercion) : that.fechaInsercion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (clOrdId != null ? clOrdId.hashCode() : 0);
        result = 31 * result + (fixMsg != null ? fixMsg.hashCode() : 0);
        result = 31 * result + (fechaInsercion != null ? fechaInsercion.hashCode() : 0);
        return result;
    }
}
