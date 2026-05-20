<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Admin sign in — LensCraft Studio" scope="request"/>
<c:set var="adminArea" value="true" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>

<div class="lc-page">
    <div class="lc-container lc-form-wrap" style="max-width:28rem">
        <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.35rem">Admin sign in</h1>
        <p class="lc-muted" style="margin-bottom:1.75rem">Studio staff only. Separate from customer accounts.</p>

        <c:if test="${param.msg == 'loggedout'}">
            <div class="lc-alert lc-alert--success">Signed out.</div>
        </c:if>
        <c:if test="${param.msg == 'useadmin'}">
            <div class="lc-alert lc-alert--warn">Admin accounts cannot sign in on the customer page. Use this form instead.</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="lc-alert lc-alert--danger">${error}</div>
        </c:if>

        <div class="lc-panel">
            <form class="lc-form" method="post" action="${ctx}/admin/login" autocomplete="off">
                <div class="lc-field">
                    <label for="email">Admin email</label>
                    <input class="lc-input" id="email" name="email" type="email" required
                           value="${not empty email ? email : param.email}" placeholder="admin@lenscraft.studio" autocomplete="username">
                </div>
                <c:set var="pwId" value="password"/>
                <c:set var="pwName" value="password"/>
                <c:set var="pwLabel" value="Password"/>
                <c:set var="pwRequired" value="true"/>
                <c:set var="pwAutocomplete" value="current-password"/>
                <%@ include file="/WEB-INF/jsp/include/password-field.jspf" %>
                <div class="lc-form-actions">
                    <button class="lc-btn lc-btn--gold" type="submit">Sign in</button>
                    <%@ include file="/WEB-INF/jsp/include/admin-customer-site-btn.jspf" %>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
