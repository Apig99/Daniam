<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Store List</title>
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.min.css')}"/>
</head>
<body>
<div class="container">
    <h1>Stores</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Address</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${storeList}" var="store">
            <tr>
                <td>${store.id}</td>
                <td>${store.name}</td>
                <td>${store.address}</td>
                <td>
                    <g:link class="btn btn-primary btn-sm" action="show" id="${store.id}">View</g:link>
                    <g:link class="btn btn-secondary btn-sm" action="edit" id="${store.id}">Edit</g:link>
                    <g:form action="delete" method="post" style="display:inline;">
                        <g:hiddenField name="id" value="${store.id}"/>
                        <g:actionSubmit class="btn btn-danger btn-sm" value="Delete" onclick="return confirm('Are you sure you want to delete this store?');"/>
                    </g:form>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <g:link class="btn btn-success" action="create">Add New Store</g:link>
    <g:link class="btn btn-primary" controller="product" action="index">Go to Products</g:link>
    <g:link class="btn btn-danger" controller="repository" action="index">Go to Repositories</g:link>
</div>
</body>
</html>
