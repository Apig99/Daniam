<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edit Store</title>
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.min.css')}"/>
</head>
<body>
<div class="container">
    <h1>Edit Store</h1>
    <g:form action="update">
        <g:hiddenField name="id" value="${store.id}"/>
        <div class="form-group">
            <label for="name">Name</label>
            <g:textField name="name" value="${store.name}" class="form-control" required="required"/>
        </div>
        <div class="form-group">
            <label for="address">Address</label>
            <g:textField name="address" value="${store.address}" class="form-control" required="required"/>
        </div>
        <g:actionSubmit class="btn btn-primary" action="update" value="Update"/>
        <g:link class="btn btn-secondary" action="show" id="${store.id}">Cancel</g:link>
    </g:form>
</div>
</body>
</html>
