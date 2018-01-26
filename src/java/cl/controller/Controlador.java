/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.controller;

import cl.beans.ServicioBeanLocal;
import cl.entities.*;
import cl.entities.Usuario;
import directorio.Hash;
import java.io.IOException;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author alumnossur
 */


@WebServlet(name = "Controlador", urlPatterns = {"/control.do"})
@MultipartConfig(location="/tmp", fileSizeThreshold = 1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class Controlador extends HttpServlet {

    @EJB
    private ServicioBeanLocal servicio;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String boton = request.getParameter("boton");
        switch(boton){
            case "login": login(request,response);
                break;
            case "nuevacategoria": nuevaCategoria(request,response);
                break;
            case "nuevoproducto": 
                log("nuevo producto");
                nuevoProducto(request,response);
                break;
            case "editardatos": modificarUsuario(request,response);
                break;
                    
                
        }
    }
    /****************** EVALUACIÓN ****************************/
    protected void modificarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo=request.getParameter("correo");
        String clave1=request.getParameter("clave1");
        String clave2=request.getParameter("clave2");
        String errores="";
        String mensajes="";
        Usuario usuario=(Usuario)request.getSession().getAttribute("admin");
        //Cambio de clave
        if(!clave1.isEmpty()){ //Si escribió algo en clave1 es porque la quiere cambiar
            if(!clave1.equals(clave2)){
                errores=errores.concat("Las claves ingresadas son distintas. Por favor inténtelo nuevamente.<BR/>");
            }
            else{ //Quiere cambiar la clave y ambas son iguales
                usuario.setClave(Hash.md5(clave1));
                mensajes=mensajes.concat("Se ha actualizado la clave del usuario.<BR/>");
            }
        }
        //Cambio de correo
        if(!correo.isEmpty()){ //Si escribió algo, entonces quiere cambiar su correo
            usuario.setEmailUser(correo);
            mensajes=mensajes.concat("Se ha actualizado el correo del usuario.<BR/>");
        }
        //Analizar los cambios
        if(!mensajes.isEmpty()){ //Si hay mensajes es porque se debe actualizar
            //Actualizo en la bse de datos
            servicio.sincronizar(usuario);
            //Actualizo en la sesión
            request.getSession().setAttribute("admin", usuario);
            //Envío el mensaje con los cambios
            request.setAttribute("msg", mensajes);
        }
        else if(errores.isEmpty()){ //No hay errores, no se actualiza nada
            request.setAttribute("msg", "No se han actualizado datos");
        }else{ //hay errores
            request.setAttribute("msg", errores);
        }
        request.getRequestDispatcher("misdatos.jsp").forward(request, response);
    }
    
    /**********************************************************/
    protected void nuevoProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre=request.getParameter("nombre");
        String precioStr=request.getParameter("precio");
        String unidadStr=request.getParameter("unidad");
        String descripcion=request.getParameter("descripcion");
        String categoriaStr=request.getParameter("idcategoria");
        String errores="";
        int precio=0, unidad=0, idCategoria=0;
        
        InputStream stream=null;
        Part foto=request.getPart("foto");
        if(foto!=null){
            stream=foto.getInputStream();
        }
        
        if(nombre.isEmpty()) errores=errores.concat("Debe ingresar el nombre");
        if(descripcion.isEmpty()) errores=errores.concat("Debe ingresar la descripción");
        if(precioStr.isEmpty()) errores=errores.concat("Debe ingresar el precio");
        try {
            precio=Integer.parseInt(precioStr);
        } catch (Exception e) {
            errores=errores.concat("El precio debe ser numérico");
        }
        if(unidadStr.isEmpty()) errores=errores.concat("Debe ingresar las unidades");
        try {
            unidad=Integer.parseInt(unidadStr);
        } catch (Exception e) {
            errores=errores.concat("La unidad debe ser numérico");
        }
        if (errores.isEmpty()) {
            //Buscar la categoria
            idCategoria=Integer.parseInt(categoriaStr);
            Categoria categoria=servicio.buscarCategoria(idCategoria);
            
            //Crear el producto, igresar los atributos y guardarlo en la BD
            Producto nuevo=new Producto();
            nuevo.setNombreProducto(nombre);
            nuevo.setPrecioProducto(precio);
            nuevo.setUnidadesProducto(unidad);
            nuevo.setDescripcionProducto(descripcion);
            nuevo.setCategoria(categoria);
            
            if(stream!=null){
                nuevo.setFotoProducto(IOUtils.toByteArray(stream));
            }
            
            servicio.guardar(nuevo);
            
            //Guardar el producto en la lista de su categoría
            categoria.getProductoList().add(nuevo);
            servicio.sincronizar(categoria);
            
            //Mensaje
            request.setAttribute("msg", "Producto creado");
            
        } else {
            request.setAttribute("msg", errores);
        }
        request.getRequestDispatcher("producto.jsp").forward(request, response);
        
    }
    
    protected void nuevaCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre=request.getParameter("nombre");
        if(nombre.isEmpty()){
            //error, no hay nombre
            request.setAttribute("msg","Debe escribir el nombre de la categoría");
        }else{
            //Agregar la categoría
            Categoria nueva=new Categoria();
            nueva.setNombreCategoria(nombre);
            servicio.guardar(nueva);
            request.setAttribute("msg","Categoría creada correctamente");
        }
        request.getRequestDispatcher("categoria.jsp").forward(request, response);
    }
    protected void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rut=request.getParameter("rut");
        String clave=request.getParameter("clave");
        String errores="";
        if(rut.isEmpty()) errores=errores.concat("Debe ingresar el rut <BR/>");
        if(rut.isEmpty()) errores=errores.concat("Debe ingresar la contraseña <BR/>");
        if(errores.isEmpty()){
            Usuario user= servicio.iniciarSesion(rut, Hash.md5(clave));
            if(user!=null){
                if(user.getPerfil().getNombrePerfil().equals("administrador")){
                    //crear sesión de administrador
                    request.getSession().setAttribute("admin",user);
                }
                else{
                    //crear la sesión de cliente
                    request.getSession().setAttribute("person",user);
                }
                //redireccionar al inicio
                response.sendRedirect("inicio.jsp");
            }
            else{
                 //redireccionar el error
                 request.setAttribute("msg","usuario no encontrado");
                 request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
        else{
            //redireccionar los errores
            request.setAttribute("msg",errores);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
