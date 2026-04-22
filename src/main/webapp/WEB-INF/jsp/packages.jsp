<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Available packages</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/app.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>
<div class="container pb-5">
    <h1 class="h3 mb-3">Available packages</h1>
    <p class="text-muted">These objects are created as <code>MediaPackage</code> subtypes in Java (inheritance + polymorphism).</p>

    <div class="row g-3">
        <c:forEach var="p" items="${packages}">
            <div class="col-md-6">
                <div class="card h-100">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-start mb-2">
                            <div>
                                <h2 class="h5 mb-1">${p.title}</h2>
                                <span class="badge text-bg-secondary">${p.code}</span>
                            </div>
                            <span class="badge text-bg-light border">${p.priceLabel}</span>
                        </div>
                        <p class="mb-2">${p.summary}</p>
                        <p class="small text-muted mb-0"><strong>Deliverables:</strong> ${p.deliverables}</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="alert alert-info mt-4 mb-0">
        When you <a href="${ctx}/bookings/create">create a booking</a>, pick a package name that matches your choice, or type your own label.
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
