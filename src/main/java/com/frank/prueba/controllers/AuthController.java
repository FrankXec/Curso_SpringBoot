
package com.frank.prueba.controllers;

import com.frank.prueba.dao.UsuarioDAO;
import com.frank.prueba.models.Usuario;
import com.frank.prueba.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    
    @Autowired
    private UsuarioDAO dao;
    
    @Autowired
    private JWTUtil jwtUtils;
    
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){
        Usuario usuarioLog = dao.getByEmailPassword(usuario);
        if( usuarioLog != null){
            //Creamos el token a devolver, aca le podemos enviar la informacion que querramos
            return jwtUtils.create(usuarioLog.getId().toString(), usuarioLog.getEmail());
        }else{
            return "FAIL";
        }
    }
}
