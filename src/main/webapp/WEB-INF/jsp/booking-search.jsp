<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Search bookings — Admin" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/admin-navbar.jspf" %>

<div class="lc-page">
    <div class="lc-container">
        <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.5rem">Search bookings</h1>
        <p class="lc-muted" style="margin-bottom:1.75rem">Match by <strong>booking ID</strong> (exact) <em>or</em> <strong>customer name</strong> (partial, case-insensitive).</p>

        <div class="lc-panel" style="margin-bottom:2rem">
            <form class="lc-form" method="get" action="${ctx}/bookings/search">
                <input type="hidden" name="search" value="1">
                <div class="lc-search-row">
                    <div class="lc-field" style="margin-bottom:0">
                        <label for="bookingId">Booking ID</label>
                        <input class="lc-input" id="bookingId" name="bookingId" value="${bookingIdQuery}" placeholder="BK001">
                    </div>
                    <div class="lc-field" style="margin-bottom:0">
                        <label for="customerName">Customer name</label>
                        <input class="lc-input" id="customerName" name="customerName" value="${customerNameQuery}" placeholder="Part of a name">
                    </div>
                    <div>
                        <button class="lc-btn lc-btn--gold" style="width:100%" type="submit">Search</button>
                    </div>
                </div>
            </form>
        </div>

        <c:if test="${searched}">
            <h2 class="lc-font-display" style="font-size:1.35rem;margin:0 0 1rem">Results</h2>
            <div class="lc-panel lc-table-wrap">
                <table class="lc-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer</th>
                        <th>Phone</th>
                        <th>Event</th>
                        <th>Date</th>
                        <th>Location</th>
                        <th>Service</th>
                        <th>Package</th>
                        <th class="lc-actions"></th>
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
                            <td class="lc-actions">
                                <a class="lc-btn-sm" href="${ctx}/bookings/edit?bookingId=${b.bookingId}">Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty results}">
                        <tr>
                            <td colspan="9" style="text-align:center;padding:2rem;color:var(--muted-foreground)">No matches.</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
