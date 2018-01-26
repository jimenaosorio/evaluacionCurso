<%@include file="template/header.jsp" %>

<%@include file="template/menu.jsp" %>

<c:set var="categorias" scope="page" value="<%=servicio.getCategorias() %>"/>
<c:set var="productos" scope="page" value="<%=servicio.getProductos() %>"/>

<div class="row">
    <div class="col s6 offset-s3">
        <div class="card-panel">
        <h4 class="center-align">Productos</h4>
        <form action="control.do" method="post" enctype="multipart/form-data">
            <p>
            <label>Nombre</label>
            <input type="text" name="nombre"/>
            </p>
            <p>
            <label>Precio</label>
            <input type="text" name="precio"/>
            </p>
            <p>
            <label>Unidades</label>
            <input type="text" name="unidad"/>
            </p>
        
            <label>Descripción</label>
            <div class="input-field">
                <textarea name="descripcion" class="materialize-textarea"></textarea>
            </div>
            
            <p>
            <label>Categoria</label>
            <select name="idcategoria">
                <c:forEach items="${pageScope.categorias}" var="c">
                    <option value="${c.idCategoria}">${c.nombreCategoria}</option>
                </c:forEach>
            </select>
            </p>
            <p>
                <div class="file-field input-field">
                    <div class="btn">
                        <span>Foto</span>
                        <input type="file" name="foto">
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" >
                    </div>
                </div>
            </p>
            <p>
                <button type="submit" name="boton" value="nuevoproducto" class="btn">Crear</button>
            </p>
        </form>
        <br/>
        <p>${requestScope.msg}</p>
        <hr/>
        <table class="bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Unidades</th>
                    <th>Categoría</th>
                    <th>Foto</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pageScope.productos}" var="p">
                    <tr>
                        <td>${p.idProducto}</td>
                        <td>${p.nombreProducto}</td>
                        <td>${p.precioProducto}</td>
                        <td>${p.unidadesProducto}</td>
                        <td>${p.categoria.nombreCategoria}</td>
                        <td>
                            <xx:tagImagen array="${p.fotoProducto}" tam="50"/>
                        </td>
                        
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
    </div>
</div>


<%@include file="template/footer.jsp" %>