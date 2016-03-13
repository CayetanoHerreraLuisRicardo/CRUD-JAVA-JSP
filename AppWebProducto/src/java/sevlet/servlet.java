/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sevlet;

import control.ProductoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "servlet", urlPatterns = {"/servlet"})
public class servlet extends HttpServlet {
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
            try (PrintWriter out = response.getWriter()) {
            Producto datos= new Producto();
            String opcion=(String)request.getParameter("accion");
            emf= Persistence.createEntityManagerFactory("AppWebProductoPU");
            ProductoJpaController cp= new ProductoJpaController(utx, emf);
            String sid = request.getParameter("id");
               switch (opcion) {
                   case "Inicio":
                       response.sendRedirect("buscarId.jsp");
                       break;
                   case "BuscarId":
                       int id=0;
                       if(!sid.isEmpty()){
                           id=Integer.parseInt(sid);
                            datos = cp.findProducto(id);
                            //out.println(datos);
                            if(datos!=null){
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Servlet Buscar Dato</title>");
                                out.println("</head>");
                                out.println("<body>");
                                out.println("<h1> Dato Encontrado</h1>");
                                out.println("<h4> ID: "+datos.getId()+ "</h4>");
                                out.println("<h4> DESCRIPCION: "+datos.getDescripcion()+ "</h4>");
                                out.println("<h4> PRECIO: "+datos.getDescripcion()+ "</h4>");
                                out.println("<h4> CANTIDAD: "+datos.getDescripcion()+ "</h4>");
                                out.println("<a href='index.jsp'>Inicio</a>");
                                out.println("location='<a href=actualizar.jsp?id="+datos.getId()+"&descripcion="+datos.getDescripcion()+"&cantidad="+datos.getCantidad()+"&precio="+datos.getPrecio()+">Actualizar</a>';");
                                out.println("<a href='servlet?id="+datos.getId()+"accion=eliminar'>Eliminar</a>");
                                out.println("<br>");
                                out.println("</body>");
                                out.println("</html>");
                            }else{
                                out.println("<script>");
                                out.println("alert('El id "+sid+" no esta registrado');");
                                out.println("location='buscaId.jsp';");
                                out.println("</script>");
                            }
                       }
                       else{
                        out.println("<script>");
                        out.println("alert('Campo id vacio');");
                        out.println("location='buscaId.jsp';");
                        out.println("</script>");
                       }
                       break;
                       case "eliminar":
                        if(sid!=null || !sid.isEmpty()){
                            id=Integer.parseInt(sid);
                            datos = cp.findProducto(id);
                        if(datos!=null){
                            cp.destroy(id);
                            out.println("<script>");
                            out.println("alert('El id "+sid+" fue eliminado');");
                            out.println("location='buscaId.jsp';");
                            out.println("</script>");
                        }else{
                            out.println("<script>");
                            out.println("alert('El id "+sid+" No esta registrado');");
                            out.println("location='Crud?pagina=1&accion=nada';");
                            out.println("</script>");
                        }
                    }
                           break;
                       case "actualizar":
                if(opcion.equals("Actualizar")){
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
                            out.println("location='buscaId.jsp';");
                            out.println("</script>");
                        }else{
                            out.println("<script>");
                            out.println("alert('El id "+sid+" No esta registrado');");
                            out.println("location='buscaId.jsp';");
                            out.println("</script>");
                        }
                    }
                }break;
               }
        }  
catch(Exception e){
             System.out.println(e.getMessage());
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
