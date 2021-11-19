package com.apistore.apiStore.controllers;

import com.apistore.apiStore.dao.ClientesDao;
import com.apistore.apiStore.models.Clientes;
import com.apistore.apiStore.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthclienteController {

    @Autowired
    private ClientesDao clientesDao;

    @Autowired
    private JWTUtil jwtUtil;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "api/login",method =  RequestMethod.POST)
//    @ResponseStatus(HttpStatus.OK)  TAMBIEN PUEDES CONTROLARLO ASI
    public ResponseEntity<Map<String,String>> login(@RequestBody Clientes cliente){
        HashMap<String, String> map = new HashMap<>();

        Clientes clienteLogueado = clientesDao.loginCliente(cliente);

        if(clienteLogueado!=null){

            String tokenJWT = jwtUtil.create(String.valueOf(clienteLogueado.getId()),clienteLogueado.getCorreo());
            map.put("data", tokenJWT);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("data", "FAIL");
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);

    }

}
