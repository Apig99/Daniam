<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Store</title>
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.min.css')}"/>
</head>
<body>
<div class="container">
    <h1>Create Store</h1>
    <g:form action="save">
        <div class="form-group">
            <label for="name">Name</label>
            <g:textField name="name" class="form-control" required="required"/>
        </div>
        <div class="form-group">
            <label for="address">Address</label>
            <g:textField name="address" class="form-control" required="required"/>
        </div>
        <g:if test="${error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </g:if>
        <g:actionSubmit class="btn btn-primary" action="save" value="Create"/>
        <g:link class="btn btn-secondary" action="index">Cancel</g:link>
    </g:form>
</div>
</body>
</html>
