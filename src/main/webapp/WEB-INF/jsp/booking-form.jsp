<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${formTitle}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/app.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>
<div class="container pb-5" style="max-width: 720px;">
    <h1 class="h3 mb-3">${formTitle}</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <c:set var="isEdit" value="${not empty booking.bookingId}"/>

    <div class="card">
        <div class="card-body">
            <form method="post" action="${formAction}" novalidate>
                <c:if test="${isEdit}">
                    <input type="hidden" name="bookingId" value="${booking.bookingId}">
                    <div class="mb-3">
                        <label class="form-label">Booking ID</label>
                        <input class="form-control" type="text" value="${booking.bookingId}" disabled>
                        <div class="form-text">ID cannot be changed.</div>
                    </div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label" for="customerName">Customer name</label>
                    <input class="form-control" id="customerName" name="customerName" required maxlength="120"
                           value="${booking.customerName}">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="phone">Phone</label>
                    <input class="form-control" id="phone" name="phone" required maxlength="40"
                           value="${booking.phone}">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="eventType">Event type</label>
                    <input class="form-control" id="eventType" name="eventType" required maxlength="80"
                           value="${booking.eventType}" placeholder="Wedding, Corporate, Birthday...">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="eventDate">Event date</label>
                    <input class="form-control" id="eventDate" name="eventDate" type="date" required
                           value="${booking.eventDate}">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="location">Location</label>
                    <input class="form-control" id="location" name="location" required maxlength="160"
                           value="${booking.location}">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="serviceType">Service type</label>
                    <select class="form-select" id="serviceType" name="serviceType" required>
                        <c:set var="st" value="${booking.serviceType}"/>
                        <option value="" ${empty st ? 'selected' : ''}>Choose...</option>
                        <option value="Photography" ${st == 'Photography' ? 'selected' : ''}>Photography</option>
                        <option value="Videography" ${st == 'Videography' ? 'selected' : ''}>Videography</option>
                        <option value="Both" ${st == 'Both' ? 'selected' : ''}>Both</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="packageName">Package name</label>
                    <input class="form-control" id="packageName" name="packageName" required maxlength="120"
                           value="${booking.packageName}" list="packageOptions"
                           placeholder="Match a package title or your custom label">
                    <datalist id="packageOptions">
                        <option value="Essential Photos"></option>
                        <option value="Premium Storytelling"></option>
                        <option value="Highlight Reel"></option>
                        <option value="Cinematic Package"></option>
                    </datalist>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="specialNotes">Special notes</label>
                    <textarea class="form-control" id="specialNotes" name="specialNotes" rows="3"
                              maxlength="500" placeholder="Optional details (avoid tab characters)">${booking.specialNotes}</textarea>
                </div>

                <button class="btn btn-primary" type="submit">${isEdit ? 'Save changes' : 'Create booking'}</button>
                <a class="btn btn-link" href="${ctx}/bookings/list">Cancel</a>
            </form>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Tiny UX helper: warn if notes contain a tab (would break TSV storage).
    document.querySelector('form').addEventListener('submit', function (e) {
        var notes = document.getElementById('specialNotes').value;
        if (notes.indexOf('\t') >= 0) {
            alert('Please remove tab characters from notes.');
            e.preventDefault();
        }
    });
</script>
</body>
</html>
