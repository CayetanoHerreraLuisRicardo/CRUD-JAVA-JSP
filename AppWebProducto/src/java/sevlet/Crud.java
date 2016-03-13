/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sevlet;

import control.ProductoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import modelo.Producto;

/**
 *
 * @author Richy
 */
@WebServlet(name = "Crud", urlPatterns = {"/Crud"})
public class Crud extends HttpServlet {
    @PersistenceUnit
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         PrintWriter out = response.getWriter();    
        try {
            Producto datos= new Producto();
            String accion = request.getParameter("accion");
            String sid = request.getParameter("id");
            int id=0;
            emf= Persistence.createEntityManagerFactory("AppWebProductoPU");
            ProductoJpaController cp= new ProductoJpaController(utx, emf);
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet CRUD</title>"); 
                        out.println("<script src='funciones.js'></script>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<a href='index.jsp'>Inicio</a>");
                        out.println("<a href='create.jsp'>Agregar</a>");
                        out.println("<br>");
            if(accion.equals("Inicio")){
                response.sendRedirect("index.jsp");
            }
                if(accion.equals("eliminar")){
                    if(sid!=null || !sid.isEmpty()){
                        id=Integer.parseInt(sid);
                        datos = cp.findProducto(id);
                        if(datos!=null){
                            cp.destroy(id);
                            out.println("<script>");
                            out.println("alert('El id "+sid+" fue eliminado');");
                            out.println("location='Crud?pagina=1&accion=nada';");
                            out.println("</script>");
                        }else{
                            out.println("<script>");
                            out.println("alert('El id "+sid+" No esta registrado');");
                            out.println("location='Crud?pagina=1&accion=nada';");
                            out.println("</script>");
                        }
                    }
                }
                if(accion.equals("Actualizar")){
                    if(sid!=null || !sid.isEmpty()){
                        id=Integer.parseInt(sid);
                        datos = cp.findProducto(id);
                        if(datos!=null){
                            datos.setId(id);
                            datos.setDescripcion((String)request.getParameter("descripcion"));
                            datos.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                            datos.setPrecio(Double.parseDouble(request.getParameter("precio")));
                            cp.edit(datos);
                            out.println("<script>");
                            out.println("alert('El id "+sid+" fue modificado');");
                            out.println("location='Crud?pagina=1&accion=nada';");
                            out.println("</script>");
                        }else{
                            out.println("<script>");
                            out.println("alert('El id "+sid+" No esta registrado');");
                            out.println("location='Crud?pagina=1&accion=nada';");
                            out.println("</script>");
                        }
                    }
                }
                if(accion.equals("Agregar")){
                    if(sid!=null || !sid.isEmpty()){
                        id=Integer.parseInt(sid);
                        datos = cp.findProducto(id);
                        if(datos==null){
                            datos= new Producto();
                            datos.setId(id);
                            datos.setDescripcion((String)request.getParameter("descripcion"));
                            datos.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                            datos.setPrecio(Double.parseDouble(request.getParameter("precio")));
                            cp.create(datos);
                            out.println("<script>");
                            out.println("alert('El id "+sid+" fue agregado exitosamente');");
                            out.println("location='Crud?pagina=1&accion=nada';");
                            out.println("</script>");
                        }else{
                            out.println("<script>");
                            out.println("alert('El id "+sid+" ya esta registrado');");
                            out.println("location='Crud?pagina=1&accion=nada';");
                            out.println("</script>");
                        }
                    }
                }
                else{
                        int registrosxpagina=6;
                        double registrosxpagina1=(double)registrosxpagina;
                        int inicio=1;
                        if(request.getParameter("pagina") != null){
                           inicio= Integer.parseInt(request.getParameter("pagina")); 
                        }
                        int inicio1=(inicio-1)*registrosxpagina;
                        int totalregistros=cp.getProductoCount();
                        double totalregistros1= (double)totalregistros;
                        int totalpaginas =(totalregistros/registrosxpagina);
                        int totalpaginasceil =(int)Math.ceil(totalregistros1/registrosxpagina1);
                        
                        crearTabla(out,registrosxpagina,inicio1,cp);
                        
                        //out.println("<a href=Crud?pagina=1&accion=nada> <<-- </a>");
                        int ultima=0;
                        if(inicio==1){
                            out.println("<strong>Anterior</strong>");
                        }else{
                             out.println("<a href=Crud?pagina="+((inicio-registrosxpagina)+(registrosxpagina-1))+"&accion=nada>Anterior</a>");
                        }
                            
        		for(int n=1; n<=totalpaginasceil ; n++){
                            if(inicio!=n){
                                //out.println("<a href=Crud?pagina="+n+"&accion=nada>"+n+"</a>");
                            }
                            else{
                                //out.println(n);
                            }
                            ultima++;
                        }
                        
                        if(ultima==inicio){
                            out.println("<strong>Siguiente</strong>");
                        }else{
                             out.println("<a href=Crud?pagina="+((inicio-registrosxpagina)+(registrosxpagina+1))+"&accion=nada>Siguiente</a>");
                        }
                        //out.println("<a href=Crud?pagina="+ultima+"&accion=nada> -->></a>");
                }
                        out.println("</body>");            
                        out.println("</html>");
                    }catch(Exception e){
             System.out.println(e.getMessage());
        }  
    }
    public void crearTabla(PrintWriter out, int registrosxpagina, int inicio1, ProductoJpaController cp){
                        List<Producto> list=cp.findProductoEntities(registrosxpagina, inicio1);
                        out.println("<h2>Datos</h2>");
                        out.println("<table border=\"1\">");
                        out.println("<tr>");
                        out.println("<th>Id dato</th>");
                        out.println("<th>Dato</th>");
                        out.println("<th>Eliminar</th>");
                        out.println("<th>Actualizar</th>");
                        out.println("</tr>");
                        for(Producto es: list){
                        out.println("<tr>");
                        out.println("<td>"+es.getId()+"</td>");
                        out.println("<td>"+es.getDescripcion()+"</td>");
                        out.println("<td>"+es.getCantidad()+"</td>");
                        out.println("<td>"+es.getPrecio()+"</td>");
                        //out.println("<td><a href=Crud?id="+es.getId()+"&accion=eliminar>Eliminar</a></td>");
                        out.println("<td><a href='javascript:void(0);' onclick='eliminiar("+es.getId()+")' title='Eliminar' >Eliminar</a></td>");
                        out.println("<td><a href=update.jsp?id="+es.getId()+"&descripcion="+es.getDescripcion()+"&cantidad="+es.getCantidad()+"&precio="+es.getPrecio()+">Actualizar</a></td>");
                        out.println("</tr>");
                        }
                        out.println( "</table>");
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
