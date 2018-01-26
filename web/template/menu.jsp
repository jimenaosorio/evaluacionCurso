
<nav>
    <div class="nav-wrapper">
        <a href="#" class="brand-logo">
            <c:if test="${not empty sessionScope.admin}">
                Bienvenido ${sessionScope.admin.nombreUser} <!-- nombreUser, porque así está en la base de datos -->
            </c:if>
            <c:if test="${not empty sessionScope.person}">
                Bienvenido ${sessionScope.person.nombreUser}
            </c:if>    
                
        </a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">

            <c:if test="${not empty sessionScope.admin}">
                <li><a href="misdatos.jsp">Mis Datos</a></li>
                <li><a href="categoria.jsp">Módulo Categorias</a></li>
                <li><a href="producto.jsp">Módulo Productos</a></li>
                <li><a href="#">Módulo Ventas</a></li>
                <li><a href="salir.jsp">Cerrar sesion</a></li>
                </c:if>

            <c:if test="${not empty sessionScope.person}">
                <li><a href="#">Mis Datos</a></li>
                <li><a href="#">Carrito de compras</a></li>
                <li><a href="#">Mis Compras</a></li>
                <li><a href="#">Productos</a></li>
                <li><a href="salir.jsp">Cerrar sesion</a></li>
            </c:if>



        </ul>
    </div>
</nav>
