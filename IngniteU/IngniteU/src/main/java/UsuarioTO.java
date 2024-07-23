
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UsuarioTO {
    
@Id   
@GeneratedValue(strategy = GenerationType.IDENTITY)
public int Id;

public String NombreUsuario;
public String CorreoUsuario;

}
