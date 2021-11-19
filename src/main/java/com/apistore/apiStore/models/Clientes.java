package com.apistore.apiStore.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "clientes")
@ToString @EqualsAndHashCode
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id")
    private Integer id;


    @NotEmpty(message = "El campo cedula no debe ser nulo")
    @Getter @Setter @Column(name = "cedula")
    private String cedula;

    @NotEmpty(message = "El campo correo no debe ser nulo")
    @Email
    @Getter @Setter @Column(name = "correo")
    private String correo;

    @NotEmpty(message = "El campo password no debe ser nulo")
    @Getter @Setter @Column(name = "password")
    private String password;

    @NotEmpty(message = "El campo pais no debe ser nulo")
    @Getter @Setter @Column(name = "pais")
    private String pais;

    @NotEmpty(message = "El campo nombre no debe ser nulo")
    @Getter @Setter @Column(name = "nombre")
    private String nombre;

    @NotEmpty(message = "El campo apellidos no debe ser nulo")
    @Getter @Setter @Column(name = "apellidos")
    private String apellidos;

    @NotEmpty(message = "El campo direccion no debe ser nulo")
    @Getter @Setter @Column(name = "direccion")
    private String direccion;

    @NotEmpty(message = "El campo telefono no debe ser nulo")
    @Getter @Setter @Column(name = "telefono")
    private String telefono;

    @NotEmpty(message = "El campo obserbaciones no debe ser nulo")
    @Getter @Setter @Column(name = "obserbaciones")
    private String obserbaciones;

    @NotNull(message = "El status es requerido")
    @Getter @Setter @Column(name = "status")
    private Boolean status;

}
