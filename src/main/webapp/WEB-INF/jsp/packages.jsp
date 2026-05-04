<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Packages — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>

<div class="lc-page">
    <div class="lc-container">
        <p class="lc-section__label">What we do</p>
        <h1 class="lc-section__title">Available packages</h1>
        <p class="lc-muted" style="max-width:40rem;margin-bottom:2.5rem">These offerings are represented as <code>MediaPackage</code> subtypes in Java (inheritance + polymorphism). When you <a href="${ctx}/bookings/create">create a booking</a>, pick a package name that matches your choice, or type your own label.</p>

        <div class="lc-grid-4">
            <c:forEach var="p" items="${packages}">
                <article class="lc-card-soft lc-package-card">
                    <div style="display:flex;justify-content:space-between;align-items:flex-start;gap:0.75rem;flex-wrap:wrap">
                        <div>
                            <h2 class="lc-font-display" style="font-size:1.2rem;margin:0 0 0.35rem">${p.title}</h2>
                            <span class="lc-badge">${p.code}</span>
                        </div>
                        <span class="lc-badge lc-badge--gold">${p.priceLabel}</span>
                    </div>
                    <p style="margin:0;font-size:0.95rem;color:var(--muted-foreground)">${p.summary}</p>
                    <p style="margin:0;font-size:0.85rem;color:var(--muted-foreground)"><strong style="color:var(--foreground)">Deliverables:</strong> ${p.deliverables}</p>
                </article>
            </c:forEach>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
