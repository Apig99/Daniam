<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edit Repository</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}"/>
</head>
<body>
<div class="container">
    <h1>Edit Repository</h1>
    <g:form action="update">
        <g:hiddenField name="id" value="${repository?.id}"/>
        <div class="form-group">
            <label for="name">Name</label>
            <g:textField name="name" class="form-control" value="${repository?.name}"/>
            <g:if test="${repository?.errors?.hasFieldErrors('name')}">
                <div class="alert alert-danger">
                    <g:eachError bean="${repository}" field="name">
                        <div><g:message error="${it}"/></div>
                    </g:eachError>
                </div>
            </g:if>
        </div>
        <g:actionSubmit class="btn btn-primary" value="Update"/>
    </g:form>
    <g:link class="btn btn-secondary" action="index">Back to List</g:link>
</div>
</body>
</html>
