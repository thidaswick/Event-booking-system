<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Packages — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>

<div class="lc-page">
    <div class="lc-container">
        <p class="lc-section__label">What we do</p>
        <h1 class="lc-section__title">Available packages</h1>
        <p class="lc-muted lc-packages-intro">Choose a package that fits your event. When you <a href="${ctx}/bookings/create">book a session</a>, enter the package title shown below.</p>

        <c:choose>
            <c:when test="${empty packages}">
                <p class="lc-muted">Packages are being updated. Please check back soon or <a href="${ctx}/contacts/create">contact us</a>.</p>
            </c:when>
            <c:otherwise>
                <div class="lc-grid-4 lc-packages-grid">
                    <c:forEach var="pkg" items="${packages}">
                        <%@ include file="/WEB-INF/jsp/include/package-card.jspf" %>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
