/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.beans;

import cl.entities.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alumnossur
 */
@Stateless
public class ServicioBean implements ServicioBeanLocal {
    @PersistenceContext(unitName="TiendaMascotas2018PU")
    private EntityManager em;

    @Override
    public Usuario iniciarSesion(String rut, String clave){
        try{
            return (Usuario) em.createNamedQuery("Usuario.iniciarSesion", Usuario.class).setParameter("rutUser",rut).setParameter("clave", clave).getSingleResult(); //Los dos parámetros de entrada
        }catch(Exception e){
            return null;
        }
    }
    @Override
    public void guardar(Object o){
        em.persist(o);
    }

    @Override
    public List<Categoria> getCategorias() {
        return em.createNamedQuery("Categoria.findAll").getResultList();
    }

    @Override
    public List<Producto> getProductos() {
        return em.createNamedQuery("Producto.findAll").getResultList();
    }

    @Override
    public void sincronizar(Object o) {
        em.merge(o); //Guarda el producto en la lista de su categoría
        em.flush();
    }

    @Override
    public Categoria buscarCategoria(int id) {
        return em.find(Categoria.class,id);
    }

    /****************** EVALUACIÓN *******************************/
    //Implementación del método buscarUsuario
    @Override
    public Usuario buscarUsuario(String rut) {
        return em.find(Usuario.class,rut);
    }
    
    
}
