<%-- 
    Document   : create
    Created on : 17/05/2015, 10:06:07 PM
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
        <form action="Crud" method="get">
        ID: <input type="text" name="id" >
        DESCRIPCION <input type="text" name="descripcion" >
        CANTIDAD: <input type="text" name="cantidad" >
        PRECIO: <input type="text" name="precio" >
        <input type="submit" name="accion" value="Inicio" />
        <input type="submit" name="accion" value="Agregar" />
        </form>
    </body>
</html>

