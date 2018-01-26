<%@include file="template/header.jsp" %>

<%@include file="template/menu.jsp" %>


<div class="row">
    <div class="col s6 offset-s3">
        <div class="card-panel">
            <h4 class="center-align">Mis Datos</h4>
            <form action="control.do" method="post">
                <p>
                    <label>RUT</label>
                    <input disabled type="text" name="rut" value="${sessionScope.admin.rutUser}"/>
                </p>
                <p>
                    <label>Nombre</label>
                    <input disabled type="text" name="nombre" value="${sessionScope.admin.nombreUser}"/>
                </p>
                <p>
                    <label>Apellido</label>
                    <input disabled type="text" name="apellido" value="${sessionScope.admin.apellidoUser}"/>
                </p>
                <p>
                    <label>Correo</label>
                    <input type="text" name="correo" placeholder="${sessionScope.admin.emailUser}"/>
                </p>
                <p>
                    <label>Clave</label>
                    <input type="password" name="clave1"/>
                </p>
                <p>
                    <label>Confirmar clave</label>
                    <input type="password" name="clave2"/>
                </p>
                <button class="btn left" name="boton" value="editardatos">Actualizar</button>
                <br/><br/><br/>
                <p>${requestScope.msg}</p>
            </form>
        </div>
    </div>
</div>




<%@include file="template/footer.jsp" %>
