package com.apistore.apiStore.dao;

import com.apistore.apiStore.models.Clientes;

import java.util.List;

public interface ClientesDao {

    List<Clientes> getClientes();

    Clientes getCliente(Integer id );

    String crearCliente(Clientes cliente);

    Clientes loginCliente(Clientes cliente);

    String deleteCliente(Integer id);
}
