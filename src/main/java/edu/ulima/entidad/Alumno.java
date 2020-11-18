
package edu.ulima.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;


@Data
@Entity(name="EALUMNO")
@Table(name="TALUMNO")
public class Alumno implements Serializable {//Interface marker
    @Id
    @Column(name="CODIGO", length=20, nullable=false)
    private String codigo;
    
    @Column(name="NOMBRE", length=255, nullable=false)
    private String nombre;
    
    @Column(name="EDAD",  nullable=false)
    private double edad;
    
    @Lob
    @Column(name="IMAGEN", length=Integer.MAX_VALUE, nullable=true)    
    private byte[] imagen;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FECHA_VIG",  nullable=false)    
    private Date fecha_vigencia;

    public Alumno() {
    }
    
    

    public Alumno(String codigo, String nombre, double edad, Date fecha_vigencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.edad = edad;
        this.fecha_vigencia = fecha_vigencia;
    }
    
    
}
