<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edit Product</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}"/>
</head>
<body>
<div class="container">
    <h1>Edit Product</h1>
    <g:form action="update">
        <g:hiddenField name="id" value="${product?.id}"/>
        <div class="form-group">
            <label for="name">Name</label>
            <g:textField name="name" class="form-control" value="${product?.name}"/>
            <g:if test="${product?.errors?.hasFieldErrors('name')}">
                <div class="alert alert-danger">
                    <g:eachError bean="${product}" field="name">
                        <div><g:message error="${it}"/></div>
                    </g:eachError>
                </div>
            </g:if>
        </div>
        <div class="form-group">
            <label for="price">Price</label>
            <g:textField name="price" class="form-control" value="${product?.price}"/>
            <g:if test="${product?.errors?.hasFieldErrors('price')}">
                <div class="alert alert-danger">
                    <g:eachError bean="${product}" field="price">
                        <div><g:message error="${it}"/></div>
                    </g:eachError>
                </div>
            </g:if>
        </div>
        <div class="form-group">
            <label for="productionDate">Production Date</label>
            <g:datePicker name="productionDate" precision="day" class="form-control" value="${product?.productionDate}"/>
            <g:if test="${product?.errors?.hasFieldErrors('productionDate')}">
                <div class="alert alert-danger">
                    <g:eachError bean="${product}" field="productionDate">
                        <div><g:message error="${it}"/></div>
                    </g:eachError>
                </div>
            </g:if>
        </div>
        <div class="form-group">
            <label for="expirationDate">Expiration Date</label>
            <g:datePicker name="expirationDate" precision="day" class="form-control" value="${product?.expirationDate}"/>
            <g:if test="${product?.errors?.hasFieldErrors('expirationDate')}">
                <div class="alert alert-danger">
                    <g:eachError bean="${product}" field="expirationDate">
                        <div><g:message error="${it}"/></div>
                    </g:eachError>
                </div>
            </g:if>
        </div>
        <g:actionSubmit class="btn btn-primary" value="Update"/>
    </g:form>
</div>
</body>
</html>
