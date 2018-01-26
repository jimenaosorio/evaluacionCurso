<%-- 
    Document   : header
    Created on : 23-ene-2018, 11:24:04
    Author     : roman
--%>
<%--
Se agrega el contexto aquí para no tener que hacerlo en cada jsp
--%>

<%@page import="javax.naming.InitialContext"%>
<%@page import="cl.beans.ServicioBeanLocal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xx" uri="/WEB-INF/tlds/tagimg.tld" %>
<%! private ServicioBeanLocal servicio;%>
<%
    InitialContext ctx = new InitialContext();
    servicio = (ServicioBeanLocal)ctx.lookup("java:global/TiendaMascotas2018/ServicioBean!cl.beans.ServicioBeanLocal");
%>
<!DOCTYPE html>
<html>
    <head>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>App Martes</title>
    </head>
    <body>
