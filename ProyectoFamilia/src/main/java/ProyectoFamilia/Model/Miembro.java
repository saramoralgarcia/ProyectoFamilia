package ProyectoFamilia.Model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;


@Data
@Entity
//@Table(name = "Familia", indexes = @Index(name = "idx_codigo", columnList = "Codigo", unique = true))
@Table(name = "Miembro", indexes = @Index(name = "idx_email", columnList = "Email", unique = true))
public class Miembro 
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Email")
    private String email;
    @Column (name = "Contraseña") 
    private String contraseña;
    @Column (name = "Rol")
    private String rol;
    @Column (name = "Puntos")
    private int puntos;
    
    @ManyToOne(fetch = FetchType.EAGER)// la entida relacionada con en este caso familia se carga de forma inmediata
    @JoinColumn (name = "Familia_id")
    private Familia familia;
    
}
