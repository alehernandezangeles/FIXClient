package cencor.meif.fix.client.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by mhernandez on 5/29/15.
 */
@Entity
@Table(name = "CatEstatus", schema = "", catalog = "FixClientDB")
public class CatEstatusEntity implements Serializable {

    public static final int NUEVO = 1;
    public static final int ACK1 = 2;
    public static final int ACK2 = 3;
    public static final int ER = 4;
    public static final int ERROR = 5;

    private Integer id;
    private String descripcion;
    private Timestamp fechaInsercion;

    @Id
    @Column(name = "Id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

        CatEstatusEntity that = (CatEstatusEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (fechaInsercion != null ? !fechaInsercion.equals(that.fechaInsercion) : that.fechaInsercion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (fechaInsercion != null ? fechaInsercion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CatEstatusEntity{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInsercion=" + fechaInsercion +
                '}';
    }
}
