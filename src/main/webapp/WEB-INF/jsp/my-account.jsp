<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<c:set var="pageTitle" value="My account — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>
<style>
    .lc-account-hero { margin-bottom: 2rem; }
    .lc-account-hero h1 { font-size: clamp(1.75rem, 3vw, 2.25rem); margin: 0 0 0.35rem; }
    .lc-account-hero p { color: var(--muted-foreground); margin: 0; }
    .lc-progress { margin: 0.75rem 0 0; }
    .lc-progress__label {
        display: flex; justify-content: space-between; font-size: 0.8rem;
        color: var(--muted-foreground); margin-bottom: 0.35rem;
    }
    .lc-progress__track {
        height: 8px; border-radius: 999px;
        background: rgba(255,255,255,0.08); overflow: hidden;
    }
    .lc-progress__bar {
        height: 100%; border-radius: 999px;
        background: linear-gradient(90deg, var(--gold, #d4af37), #e8c96a);
    }
    .lc-booking-card {
        border: 1px solid var(--border, rgba(255,255,255,0.08));
        border-radius: 12px; padding: 1.25rem 1.35rem; margin-bottom: 1rem;
        background: var(--card, #1a1a1f);
    }
    .lc-booking-card h3 { margin: 0 0 0.35rem; font-size: 1.05rem; }
    .lc-booking-card__meta { font-size: 0.85rem; color: var(--muted-foreground); margin: 0 0 0.75rem; }
    .lc-account-actions { margin-top: 1.25rem; }
</style>

<div class="lc-page">
    <div class="lc-container">
        <div class="lc-account-hero">
            <h1>Hello, ${customer.fullName}</h1>
            <p>Account <code>${customer.customerId}</code> · ${customer.email} · ${customer.phone}</p>
        </div>

        <c:if test="${param.welcome == '1'}">
            <div class="lc-alert lc-alert--success">Welcome! Your account is ready. Book an event below.</div>
        </c:if>
        <c:if test="${param.msg == 'created'}">
            <div class="lc-alert lc-alert--success">Your booking was saved and linked to this account.</div>
        </c:if>

        <div class="lc-panel" style="margin-bottom:1.5rem;padding:1.25rem 1.5rem">
            <div class="lc-progress" aria-label="Overall progress">
                <div class="lc-progress__label">
                    <span>Overall event progress</span>
                    <span>
                        <c:choose>
                            <c:when test="${bookingCount == 0}">No bookings yet</c:when>
                            <c:otherwise>${overallPercent}% · ${bookingCount} booking(s)</c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div class="lc-progress__track">
                    <div class="lc-progress__bar" style="width:${overallPercent}%"></div>
                </div>
            </div>
            <div class="lc-account-actions">
                <a class="lc-btn lc-btn--gold" href="${ctx}/bookings/create">Book an event</a>
            </div>
        </div>

        <h2 style="font-size:1.15rem;margin:0 0 1rem">Your bookings</h2>

        <c:choose>
            <c:when test="${bookingCount == 0}">
                <div class="lc-panel" style="text-align:center;padding:2rem;color:var(--muted-foreground)">
                    You have no bookings yet.
                    <a href="${ctx}/bookings/create">Create your first booking</a> — it will appear here with progress tracking.
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="row" items="${bookingRows}">
                    <c:set var="b" value="${row.booking}"/>
                    <article class="lc-booking-card">
                        <h3><code>${b.bookingId}</code> — ${b.eventType}</h3>
                        <p class="lc-booking-card__meta">
                            ${b.eventDate} · ${b.location} · ${b.serviceType} · ${b.packageName}
                            <c:if test="${not empty b.specialNotes}"> · ${b.specialNotes}</c:if>
                        </p>
                        <div class="lc-progress">
                            <div class="lc-progress__label">
                                <span>${row.stageLabel}</span>
                                <span>${row.percent}%</span>
                            </div>
                            <div class="lc-progress__track">
                                <div class="lc-progress__bar" style="width:${row.percent}%"></div>
                            </div>
                        </div>
                    </article>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
