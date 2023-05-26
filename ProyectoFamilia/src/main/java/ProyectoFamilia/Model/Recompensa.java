package ProyectoFamilia.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Data
@Entity
@Table(name = "Recompensa")
public class Recompensa 
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Puntos")
    private int puntos;
    
    @ManyToOne(fetch = FetchType.EAGER)// la entida relacionada con en este caso miembro se carga de forma inmediata
    @JoinColumn(name = "Miembro_id")
    private Miembro miembro;
    
   
}
