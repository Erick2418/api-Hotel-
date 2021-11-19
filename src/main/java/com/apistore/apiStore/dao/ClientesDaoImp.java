package com.apistore.apiStore.dao;

import com.apistore.apiStore.models.Clientes;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class ClientesDaoImp implements ClientesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Clientes> getClientes() {

        String query ="FROM Clientes WHERE status = 1"; //NOMBRE DE LA CLASE NO DE LA TABLA
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Clientes getCliente(Integer id ) {
       Clientes cliente = entityManager.find(Clientes.class,id);
       if (!cliente.getStatus() || cliente ==null){
           return null;
       }
       return cliente;
    }

    @Override
    public String crearCliente(Clientes cliente){

        String query= "FROM Clientes WHERE correo = :correo";
        List<Clientes> clienteGet = entityManager.createQuery(query)
                .setParameter("correo",cliente.getCorreo() )
                .getResultList();

        if(!clienteGet.isEmpty()){
           return "EMAIL EXIST";
        }
        if(entityManager.merge(cliente) == null){
            return "FAIL";
        }
        return "OK";
    }

    @Override
    public Clientes loginCliente(Clientes cliente) {

        String query= "FROM Clientes WHERE correo = :correo";
        List<Clientes> clienteGet = entityManager.createQuery(query)
                .setParameter("correo",cliente.getCorreo() )
                .getResultList();

        if(clienteGet.isEmpty()){
            return null;
        }

        String passwordHashed = clienteGet.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(passwordHashed,cliente.getPassword())){
            return clienteGet.get(0);
        }
        return null;
    }

    @Override
    public String deleteCliente(Integer id) { //eliminacion logica

        Clientes cliente = entityManager.find(Clientes.class,id);


        if(cliente == null){
            return "NOT_FOUNT";
        }
        cliente.setStatus(false);
        entityManager.merge(cliente);
        return "Eliminado con Exito";
    }
}
