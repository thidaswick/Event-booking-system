<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Create account — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>

<div class="lc-page">
    <div class="lc-container lc-form-wrap">
        <c:choose>
            <c:when test="${param.created == '1'}">
                <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.35rem">You&apos;re all set</h1>
                <p class="lc-muted" style="margin-bottom:1.75rem">Thank you for joining LensCraft Studio.</p>
            </c:when>
            <c:otherwise>
                <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.35rem">Create your account</h1>
                <p class="lc-muted" style="margin-bottom:1.75rem">Join LensCraft to manage bookings. Your account is saved in <code>customers.txt</code> (same file storage as bookings).</p>
            </c:otherwise>
        </c:choose>

        <c:if test="${param.created == '1'}">
            <div class="lc-alert lc-alert--success">
                Welcome! Your account is ready<c:if test="${not empty param.id}"> (<c:out value="${param.id}"/>)</c:if>.
                Go to <a href="${ctx}/account">My account</a> to see your bookings, or <a href="${ctx}/bookings/create">book a session</a>.
            </div>
        </c:if>

        <c:if test="${param.created != '1'}">
            <c:if test="${not empty error}">
                <div class="lc-alert lc-alert--danger">${error}</div>
            </c:if>

            <div class="lc-panel">
                <form class="lc-form" method="post" action="${ctx}/register" autocomplete="off">
                    <div class="lc-field">
                        <label for="fullName">Full name</label>
                        <input class="lc-input" id="fullName" name="fullName" required maxlength="120"
                               value="${fullName}">
                    </div>
                    <div class="lc-field">
                        <label for="email">Email</label>
                        <input class="lc-input" id="email" name="email" type="email" required maxlength="120"
                               value="${email}" autocomplete="username">
                    </div>
                    <div class="lc-field">
                        <label for="phone">Phone</label>
                        <input class="lc-input" id="phone" name="phone" required maxlength="40"
                               value="${phone}">
                    </div>
                    <div class="lc-field">
                        <label for="password">Password</label>
                        <input class="lc-input" id="password" name="password" type="password" required minlength="8"
                               maxlength="128" autocomplete="new-password">
                        <p class="lc-hint">At least 8 characters.</p>
                    </div>
                    <div class="lc-field">
                        <label for="confirmPassword">Confirm password</label>
                        <input class="lc-input" id="confirmPassword" name="confirmPassword" type="password" required
                               maxlength="128" autocomplete="new-password">
                    </div>
                    <div class="lc-form-actions">
                        <button class="lc-btn lc-btn--gold" type="submit">Create account</button>
                        <a class="lc-btn--link" href="${ctx}/index.jsp" style="text-decoration:none;display:inline-flex;padding:0.5rem 0;font-weight:600;color:var(--muted-foreground)">Cancel</a>
                    </div>
                </form>
            </div>
        </c:if>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
