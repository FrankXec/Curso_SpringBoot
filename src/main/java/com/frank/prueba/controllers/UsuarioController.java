package com.frank.prueba.controllers;

import com.frank.prueba.interfaces.ICRUD;
import com.frank.prueba.models.Usuario;
import com.frank.prueba.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController //Indica que es un controlador
public class UsuarioController{
    
    @Autowired //automaticamente hace que se cree un objeto del dao para poder reutilizarlo
    private ICRUD dao;
    
    @Autowired
    private JWTUtil jwtUtils;
    
    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET) //llamado solo por la url. www.x.n/usuarios
    public List<Usuario> getAll(@RequestHeader(value = "Authorization") String token){
        if(!validarToke(token)){ return null;}
        
        return dao.getAll();
    }
    
    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.GET)
    public Usuario getOneByID(@PathVariable Long id){
        var usuario = new Usuario(id,"Francisco","Xec","francisco@gmail.com","36451234","admin123");
        return usuario;
    }

    @RequestMapping(value = "api/usuario", method = RequestMethod.POST)
    public boolean create(@RequestBody Usuario entity) {
        //hashh contraseña
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, entity.getPassword());
        entity.setPassword(hash);
        
        return dao.create(entity);
    }

    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.DELETE)
    public boolean delete(@RequestHeader(value = "Authorization") String token, @PathVariable Long id ) {
        if(!validarToke(token)){ return false; }
        return dao.delete(id);
    }

    public boolean update(Usuario entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    //others
    private boolean validarToke(String token){
        //verificamos el token
        String usuarioId = jwtUtils.getKey(token);//obtenemos por id del usuario
        //luego de esto se puede verificar si el usuario existe en la base de datos.
        //pero de momento solo verificamos que no sea null
        return usuarioId != null;
    }
}
