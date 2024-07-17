<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Repository List</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}"/>
</head>
<body>
<div class="container">
    <h1>Repository List</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${repositoryList}" var="repository">
            <tr>
                <td>${repository.id}</td>
                <td>${repository.name}</td>
                <td>
                    <g:link class="btn btn-primary" action="show" id="${repository.id}">View</g:link>
                    <g:link class="btn btn-primary" action="edit" id="${repository.id}">Edit</g:link>
                    <g:link class="btn btn-danger" action="delete" id="${repository.id}" onclick="return confirm('Are you sure you want to delete this repository?')">Delete</g:link>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <g:link class="btn btn-success" action="create">Create New Repository</g:link>
    <g:link class="btn btn-primary" controller="product" action="index">Go to Products</g:link>
    <g:link class="btn btn-danger" controller="store" action="index">Go to Stores</g:link>
</div>
</body>
</html>
