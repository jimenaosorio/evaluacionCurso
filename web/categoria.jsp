
<%@include file="template/header.jsp" %>

<%@include file="template/menu.jsp" %>

<c:set var="categorias" scope="page" value="<%=servicio.getCategorias() %>"/>

<div class="row">
    <div class="col s6 offset-s3">
        <div class="card-panel">
        <h4 class="center-align">Categorías</h4>
        <form action="control.do" method="post">
            <label>Nombre Categoría</label>
            <input type="text" name="nombre"/>
            
            <button class="btn right" name="boton" value="nuevacategoria">Crear Categoría</button>
            <br><br><br>
            
        </form>
        <br><br>
        <p class="red-text">${requestScope.msg}</p>
        <hr/>
        <table class="bordered">
            <thead>
            <th>ID</th> <th>Nombre</th>
            </thead>
            <tbody>
                <c:forEach items="${pageScope.categorias}" var="c">
                    <tr>
                        <td>${c.idCategoria}</td>
                        <td>${c.nombreCategoria}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
    </div>
</div>

<%@include file="template/footer.jsp" %>
