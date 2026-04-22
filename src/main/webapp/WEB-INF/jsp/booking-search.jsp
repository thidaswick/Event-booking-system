<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Search bookings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/app.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>
<div class="container pb-5">
    <h1 class="h3 mb-3">Search bookings</h1>
    <p class="text-muted">Match by <strong>booking ID</strong> (exact) <em>or</em> <strong>customer name</strong> (partial, case-insensitive).</p>

    <div class="card mb-4">
        <div class="card-body">
            <form class="row g-3 align-items-end" method="get" action="${ctx}/bookings/search">
                <input type="hidden" name="search" value="1">
                <div class="col-md-4">
                    <label class="form-label" for="bookingId">Booking ID</label>
                    <input class="form-control" id="bookingId" name="bookingId" value="${bookingIdQuery}" placeholder="BK001">
                </div>
                <div class="col-md-5">
                    <label class="form-label" for="customerName">Customer name</label>
                    <input class="form-control" id="customerName" name="customerName" value="${customerNameQuery}" placeholder="Part of a name">
                </div>
                <div class="col-md-3">
                    <button class="btn btn-primary w-100" type="submit">Search</button>
                </div>
            </form>
        </div>
    </div>

    <c:if test="${searched}">
        <h2 class="h5">Results</h2>
        <div class="table-responsive card">
            <table class="table table-striped mb-0 align-middle">
                <thead class="table-light">
                <tr>
                    <th>ID</th>
                    <th>Customer</th>
                    <th>Phone</th>
                    <th>Event</th>
                    <th>Date</th>
                    <th>Location</th>
                    <th>Service</th>
                    <th>Package</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="b" items="${results}">
                    <tr>
                        <td><code>${b.bookingId}</code></td>
                        <td>${b.customerName}</td>
                        <td>${b.phone}</td>
                        <td>${b.eventType}</td>
                        <td>${b.eventDate}</td>
                        <td>${b.location}</td>
                        <td>${b.serviceType}</td>
                        <td>${b.packageName}</td>
                        <td class="text-end">
                            <a class="btn btn-sm btn-outline-primary" href="${ctx}/bookings/edit?bookingId=${b.bookingId}">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty results}">
                    <tr>
                        <td colspan="9" class="text-center text-muted py-3">No matches.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
