<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>All bookings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/app.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>
<div class="container pb-5">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0">All bookings</h1>
        <a class="btn btn-primary" href="${ctx}/bookings/create">New booking</a>
    </div>

    <c:if test="${param.msg == 'created'}">
        <div class="alert alert-success">Booking created.</div>
    </c:if>
    <c:if test="${param.msg == 'updated'}">
        <div class="alert alert-success">Booking updated.</div>
    </c:if>
    <c:if test="${param.msg == 'deleted'}">
        <div class="alert alert-success">Booking deleted.</div>
    </c:if>
    <c:if test="${param.msg == 'deletefailed'}">
        <div class="alert alert-danger">Could not delete booking.</div>
    </c:if>
    <c:if test="${param.msg == 'notfound'}">
        <div class="alert alert-warning">Booking not found.</div>
    </c:if>

    <div class="card">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-striped table-hover mb-0 align-middle">
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
                        <th>Notes</th>
                        <th class="text-end">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="b" items="${bookings}">
                        <tr>
                            <td><code>${b.bookingId}</code></td>
                            <td>${b.customerName}</td>
                            <td>${b.phone}</td>
                            <td>${b.eventType}</td>
                            <td>${b.eventDate}</td>
                            <td>${b.location}</td>
                            <td>${b.serviceType}</td>
                            <td>${b.packageName}</td>
                            <td class="text-truncate" style="max-width: 160px;" title="${b.specialNotes}">${b.specialNotes}</td>
                            <td class="text-end text-nowrap">
                                <a class="btn btn-sm btn-outline-primary"
                                   href="${ctx}/bookings/edit?bookingId=${b.bookingId}">Edit</a>
                                <form class="d-inline" method="post" action="${ctx}/bookings/delete"
                                      onsubmit="return confirm('Delete booking ${b.bookingId}?');">
                                    <input type="hidden" name="bookingId" value="${b.bookingId}">
                                    <button type="submit" class="btn btn-sm btn-outline-danger">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty bookings}">
                        <tr>
                            <td colspan="10" class="text-center text-muted py-4">No bookings yet. Create one to get started.</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
