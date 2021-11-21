package com.apistore.apiStore.controllers;

import com.apistore.apiStore.dao.ClientesDao;
import com.apistore.apiStore.models.Clientes;
import com.apistore.apiStore.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClientesController {

    @Autowired
    private ClientesDao clientesDao;

    @Autowired
    private JWTUtil jwtUtil;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "api/clientes")
    public ResponseEntity<List<Clientes>> getClientes(){
        List<Clientes> clientesList= clientesDao.getClientes();
        return new ResponseEntity<> (clientesList,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "api/cliente/{id}", method = RequestMethod.GET)
    public ResponseEntity<Clientes> getCliente(@PathVariable Integer id){ //List<Usuario>
        Clientes cliente1= clientesDao.getCliente(id);
        if(cliente1 == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente1,HttpStatus.OK) ;
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "api/cliente", method = RequestMethod.POST)
    public ResponseEntity<Map<String,String>> CrearCliente(@Valid  @RequestBody Clientes cliente,BindingResult result){ //List<Usuario>

        HashMap<String, String> map = new HashMap<>();

        if(result.hasErrors()){ //@valid..
           String msjError = result.getFieldError().getDefaultMessage();
            map.put("data", msjError);
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,cliente.getPassword());
        cliente.setPassword(hash);

        String resultado = clientesDao.crearCliente(cliente);


        map.put("data", resultado);
        if(resultado.equals("FAIL")){
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }else if(resultado.equals("EMAIL EXIST")){
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "api/cliente/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,String>> DeleteCliente(@PathVariable Integer id){ //List<Usuario>
        HashMap<String, String> map = new HashMap<>();
        String response = clientesDao.deleteCliente(id);
        if(response.equals("NOT_FOUNT")){
            map.put("data", response);
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }
        map.put("data", response);
        return new ResponseEntity<>(map,HttpStatus.OK);

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "api/cliente/update", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,String>> UpdateCliente(@Valid  @RequestBody Clientes cliente,BindingResult result){ //List<Usuario>

        HashMap<String, String> map = new HashMap<>();

        if(result.hasErrors()){ //@valid..
            String msjError = result.getFieldError().getDefaultMessage();
            map.put("data", msjError);
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }

        String resultado = clientesDao.updateCliente(cliente);


        map.put("data", resultado);
        if(resultado.equals("FAIL")){
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }else if(resultado.equals("USER NOT FOUND")){
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(map,HttpStatus.OK);
    }



}