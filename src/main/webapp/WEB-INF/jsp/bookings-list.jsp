<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<c:set var="pageTitle" value="All bookings — Admin" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/admin-navbar.jspf" %>
<style>
    .lc-bookings-list-head { margin-bottom: 2rem; text-align: center; }
    .lc-bookings-list-head h1 {
        font-size: clamp(1.75rem, 3vw, 2.25rem);
        margin: 0 0 1.25rem;
    }
    .lc-bookings-search {
        display: flex; flex-wrap: wrap; align-items: center; justify-content: center;
        gap: 0.65rem;
        max-width: 42rem;
        margin: 0 auto;
    }
    .lc-bookings-search__input { flex: 1 1 16rem; min-width: 0; max-width: 28rem; }
    .lc-bookings-search__btn { flex-shrink: 0; padding-inline: 1.25rem; }
    .lc-bookings-search__clear { flex-shrink: 0; }
</style>

<div class="lc-page">
    <div class="lc-container">
        <div class="lc-bookings-list-head">
            <h1>All bookings (admin)</h1>
            <form class="lc-bookings-search" method="get" action="${ctx}/bookings/list" role="search" aria-label="Search bookings">
                <input type="search" name="q" class="lc-input lc-bookings-search__input"
                       value="${fn:escapeXml(q)}"
                       placeholder="Search by booking ID (e.g. BK001) or customer name…"
                       autocomplete="off">
                <button type="submit" class="lc-btn lc-btn--gold lc-bookings-search__btn">Search</button>
                <c:if test="${searchActive}">
                    <a class="lc-btn-sm lc-bookings-search__clear" href="${ctx}/bookings/list">Clear</a>
                </c:if>
            </form>
        </div>

        <c:if test="${param.msg == 'created'}">
            <div class="lc-alert lc-alert--success">Booking created.</div>
        </c:if>
        <c:if test="${param.msg == 'updated'}">
            <div class="lc-alert lc-alert--success">Booking updated.</div>
        </c:if>
        <c:if test="${param.msg == 'deleted'}">
            <div class="lc-alert lc-alert--success">Booking deleted.</div>
        </c:if>
        <c:if test="${param.msg == 'deletefailed'}">
            <div class="lc-alert lc-alert--danger">Could not delete booking.</div>
        </c:if>
        <c:if test="${param.msg == 'notfound'}">
            <div class="lc-alert lc-alert--warn">Booking not found.</div>
        </c:if>

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
                    <th>Notes</th>
                    <th class="lc-actions">Actions</th>
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
                        <td><span class="lc-text-truncate" title="${b.specialNotes}">${b.specialNotes}</span></td>
                        <td class="lc-actions">
                            <a class="lc-btn-sm" href="${ctx}/bookings/edit?bookingId=${b.bookingId}">Edit</a>
                            <form method="post" action="${ctx}/bookings/delete"
                                  style="display:inline;margin-left:0.35rem" onsubmit="return confirm('Delete booking ${b.bookingId}?');">
                                <input type="hidden" name="bookingId" value="${b.bookingId}">
                                <button type="submit" class="lc-btn-sm lc-btn-sm--danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty bookings}">
                    <tr>
                        <td colspan="10" style="text-align:center;padding:2.5rem;color:var(--muted-foreground)">
                            <c:choose>
                                <c:when test="${searchActive}">No bookings match your search.</c:when>
                                <c:otherwise>No bookings yet. <a href="${ctx}/bookings/create">Create one</a> to get started.</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
