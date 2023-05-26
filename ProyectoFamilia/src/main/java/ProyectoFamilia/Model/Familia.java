package ProyectoFamilia.Model;

import jakarta.persistence.*;
import java.util.Random;
import lombok.*;
 

@Data
@Entity
@Table(name = "Familia", indexes = @Index(name = "idx_codigo", columnList = "Codigo", unique = true))
public class Familia 
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Codigo")
    private int codigo;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Descripcion")
    private String descripcion;
    
    public static int generarCodigo()
    {
        Random codigo = new Random();
        int min = 0;
        int max = 9999;
        int num = codigo.nextInt((max - min) + 1) + min;
        return num;
    }
}

