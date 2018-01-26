/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.beans;

import cl.entities.*;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alumnossur
 */
@Local
public interface ServicioBeanLocal {
    //Inicio de sesión
    Usuario iniciarSesion(String rut, String clave);
    
    //Para guardar cualquier entidad
    void guardar(Object o);
    
    //Categorías
    List<Categoria> getCategorias();
    
    //Productos
    List<Producto> getProductos();
    void sincronizar(Object o);
    Categoria buscarCategoria(int id); //Para reconocer la categoría que viene del combo box
    
    /********* EVALUACION **********/
    //Buscar usuario
    Usuario buscarUsuario(String rut);
    
    
}
