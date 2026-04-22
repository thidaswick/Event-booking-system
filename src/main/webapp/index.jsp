<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Event Photography &amp; Videography Booking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/app.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>
<div class="container pb-5">
    <div class="hero mb-4">
        <h1 class="h3 mb-2">Event Photography and Videography Booking System</h1>
        <p class="mb-0 opacity-75">Create and manage bookings, browse packages, and keep everything in simple text files
            for your OOP assignment demo.</p>
    </div>
    <div class="row g-3">
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body">
                    <h2 class="h5 card-title">Packages</h2>
                    <p class="card-text">See example photo and video packages (polymorphism in Java).</p>
                    <a class="btn btn-primary" href="${ctx}/packages">View packages</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body">
                    <h2 class="h5 card-title">Bookings</h2>
                    <p class="card-text">Full CRUD stored in <code>WEB-INF/data/bookings.txt</code>.</p>
                    <a class="btn btn-outline-primary me-2" href="${ctx}/bookings/list">List</a>
                    <a class="btn btn-primary" href="${ctx}/bookings/create">New</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body">
                    <h2 class="h5 card-title">Search</h2>
                    <p class="card-text">Find a booking by id or customer name.</p>
                    <a class="btn btn-outline-secondary" href="${ctx}/bookings/search">Open search</a>
                </div>
            </div>
        </div>
    </div>
    <p class="footer-note mt-4 mb-0">Java 17 · JSP/Servlets · Maven · Bootstrap · File handling (TSV)</p>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
