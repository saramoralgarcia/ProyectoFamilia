package ProyectoFamilia.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.*;

@Data
@Entity
@Table(name = "Tarea")

public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Fecha")
    private Date fecha;
    @Column(name = "Puntos")
    private int puntos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Familia_id", referencedColumnName = "id")
    private Familia familia;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Miembro_id", referencedColumnName = "id")
    private Miembro miembro;

}
