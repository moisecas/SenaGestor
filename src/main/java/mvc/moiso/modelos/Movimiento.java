package mvc.moiso.modelos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Movimientos")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private long monto;
    private String concepto;


    @Column(name="soportePdfUrl")
    private String soportePdfUrl;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado usuario;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha;

    public Movimiento() {
    }

    public Movimiento(long monto, String concepto, Empleado empleado, Date fecha, String soportePdfUrl) {
        this.monto = monto;
        this.concepto = concepto;
        this.usuario = empleado;
        this.fecha = fecha;
        this.soportePdfUrl = soportePdfUrl; // URL del soporte PDF
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Empleado getUsuario() {
        return usuario;
    }

    public void setUsuario(Empleado empleado) {
        this.usuario = empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    public String getSoportePdfUrl() {
        return soportePdfUrl;
    }

    public void setSoportePdfUrl(String soportePdfUrl) {
        this.soportePdfUrl = soportePdfUrl;
    }


    public double getDescripcion() {
        return 0;
    }
}
