
package com.frank.prueba.dao;

import com.frank.prueba.interfaces.ICRUD;
import com.frank.prueba.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository //accede al repositorio de la base de datos
@Transactional //consultas sql
public class UsuarioDAO implements ICRUD<Usuario>{
    
    @PersistenceContext //contexto en el lugar de la base de datos
    private EntityManager manager;

    @Override
    public List getAll() {
        String query = "From Usuario";
        try{
            return manager.createQuery(query).getResultList();
        }catch(Exception ex){
            System.out.println("Error usuario getAll:"+ex);
            return null;
        }
    }

    @Override
    public Usuario getOneByID(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean create(Usuario entity) {
        try{
            manager.merge(entity);
            return true;
        }catch(Exception ex){
            System.out.println("Error usuario create:"+ex);
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try{
            Usuario usuario = manager.find(Usuario.class, id);
            manager.remove(usuario);
            return true;
        }catch(Exception ex){
            System.out.println("Error usuario delete:"+ex);
            return false;
        }
    }

    @Override
    public boolean update(Usuario entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    //others
    public Usuario getByEmailPassword(Usuario usuario){
        String query = "FROM Usuario WHERE email = :email";
        List<Usuario> usuarios =  manager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();
        if(usuarios.isEmpty()){return null;}
        //verificamos por medio de hash
        String passwordHashed = usuarios.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(!argon2.verify(passwordHashed, usuario.getPassword())){
            return null;
        }
        return usuarios.get(0);
    }
}
