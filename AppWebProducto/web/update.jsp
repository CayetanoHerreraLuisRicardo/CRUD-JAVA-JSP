<%-- 
    Document   : update
    Created on : 17/05/2015, 10:06:18 PM
    Author     : Richy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <a href='Crud?pagina=1&accion=nada'>crud</a>
        <form action="Crud" method="get">
        ID: <input type="text" name="id" readonly value="<%= request.getParameter("id") %>">
        DESCRIPCION: <input type="text" name="descripcion" value="<%= request.getParameter("descripcion")%>">
        CANTIDAD: <input type="text" name="cantidad" value="<%= request.getParameter("cantidad") %>">
        PRECIO: <input type="text" name="precio" value="<%= request.getParameter("precio") %>">
        <input type="submit" name="accion" value="Inicio" />
        <input type="submit" name="accion" value="Actualizar" />
        </form>
    </body>
</html>
