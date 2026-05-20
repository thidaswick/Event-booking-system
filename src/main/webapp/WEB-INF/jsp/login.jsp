<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Sign in — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>

<div class="lc-page">
    <div class="lc-container lc-form-wrap">
        <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.35rem">Sign in</h1>
        <p class="lc-muted" style="margin-bottom:1.75rem">Access your account to view bookings and track event progress.
            Studio staff: use <a href="${ctx}/admin/login">admin sign in</a> instead.</p>

        <c:if test="${param.msg == 'loggedout'}">
            <div class="lc-alert lc-alert--success">You have been signed out.</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="lc-alert lc-alert--danger">${error}</div>
        </c:if>

        <div class="lc-panel">
            <form class="lc-form" method="post" action="${ctx}/login" autocomplete="off">
                <div class="lc-field">
                    <label for="email">Email</label>
                    <input class="lc-input" id="email" name="email" type="email" required maxlength="120"
                           value="${email}" autocomplete="username">
                </div>
                <c:set var="pwId" value="password"/>
                <c:set var="pwName" value="password"/>
                <c:set var="pwLabel" value="Password"/>
                <c:set var="pwRequired" value="true"/>
                <c:set var="pwMaxlength" value="128"/>
                <c:set var="pwAutocomplete" value="current-password"/>
                <%@ include file="/WEB-INF/jsp/include/password-field.jspf" %>
                <div class="lc-form-actions">
                    <button class="lc-btn lc-btn--gold" type="submit">Sign in</button>
                    <a class="lc-btn--link" href="${ctx}/register"
                       style="text-decoration:none;display:inline-flex;padding:0.5rem 0;font-weight:600;color:var(--muted-foreground)">Create account</a>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
