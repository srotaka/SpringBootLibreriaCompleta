/*

 */
package com.ejercicio02.services;

import com.ejercicio02.entities.Cliente;
import com.ejercicio02.repositories.ClienteRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Transactional
    public void crearCliente(Long dni, String nombre, String apellido, String telefono) throws Exception {

        validarNombre(nombre.toUpperCase());
        validarApellido(apellido.toUpperCase());
        validarDni(dni);

        Cliente cliente = new Cliente();
        cliente.setDni(dni);
        cliente.setNombre(nombre.toUpperCase());
        cliente.setApellido(apellido.toUpperCase());
        cliente.setTelefono(telefono);
        cliente.setAlta(true);
        clienteRepositorio.save(cliente);
    }

//    @Transactional(readOnly = true)
//    public Cliente buscarClientePorDni(Long dni) {
//        Optional<Cliente> clienteOptional = clienteRepositorio.buscarClientePorDni(dni);
//        return clienteOptional.orElse(null);
//    }
    @Transactional(readOnly = true)
    public Cliente buscarClientePorId(String id) {
        Optional<Cliente> clienteOptional = clienteRepositorio.findById(id);
        return clienteOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarClientes() {
        return clienteRepositorio.findAll();
    }

    @Transactional
    public void modificarCliente(String id, Long dni, String nombre, String apellido, String telefono) throws Exception {
        validarNombre(nombre.toUpperCase());
        validarApellido(apellido.toUpperCase());

        clienteRepositorio.modificarCliente(id, dni, nombre.toUpperCase(), apellido.toUpperCase(), telefono);
    }

    @Transactional
    public void eliminarCliente(String id) {
        clienteRepositorio.deleteById(id);
    }

    @Transactional
    public void darAltaCliente(String id) {
        clienteRepositorio.darAltaCliente(id);
    }

    // VALIDACIONES 
    public void validarNombre(String nombre) throws Exception {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("EL NOMBRE DEL CLIENTE ES OBLIGATORIO.");
        }

        Boolean validacion = false;
        for (int i = 0; i < nombre.length(); i++) {
            if (Character.isAlphabetic((nombre.charAt(i)))) {
                validacion = true;
            }
        }

        if (validacion == false) {
            throw new Exception("EL NOMBRE NO PUEDE CONTENER DÍGITOS.");
        }
    }

    public void validarApellido(String apellido) throws Exception {

        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("EL APELLIDO DEL CLIENTE ES OBLIGATORIO.");
        }

        Boolean validacion = false;
        for (int i = 0; i < apellido.length(); i++) {
            if (Character.isAlphabetic((apellido.charAt(i)))) {
                validacion = true;
            }
        }
        if (validacion == false) {
            throw new Exception("EL APELLIDO NO PUEDE CONTENER DÍGITOS.");
        }
    }

    public void validarDni(Long dni) throws Exception {

        int digitos = 0;
        while (dni != 0) {
            dni = dni / 10;
            digitos++;
        }
        if (digitos != 7 && digitos != 8 || dni == null) {
            throw new Exception("EL DNI DEBE TENER 7 U 8 DÍGITOS.");
        }

        if (clienteRepositorio.buscarClientePorDni(dni) != null) {
            throw new Exception("YA EXISTE UN CLIENTE REGISTRADO CON ESE DNI.");
        }

    }
}
