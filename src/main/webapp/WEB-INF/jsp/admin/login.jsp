<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Admin sign in — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>

<div class="lc-page">
    <div class="lc-container lc-form-wrap" style="max-width:28rem">
        <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.35rem">Admin sign in</h1>
        <p class="lc-muted" style="margin-bottom:1.75rem">Studio staff only. Separate from customer accounts.</p>

        <c:if test="${param.msg == 'loggedout'}">
            <div class="lc-alert lc-alert--success">Signed out.</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="lc-alert lc-alert--danger">${error}</div>
        </c:if>

        <div class="lc-panel">
            <form class="lc-form" method="post" action="${ctx}/admin/login" autocomplete="off">
                <div class="lc-field">
                    <label for="email">Admin email</label>
                    <input class="lc-input" id="email" name="email" type="email" required
                           value="${email}" placeholder="admin@lenscraft.studio" autocomplete="username">
                </div>
                <div class="lc-field">
                    <label for="password">Password</label>
                    <input class="lc-input" id="password" name="password" type="password" required
                           autocomplete="current-password">
                </div>
                <div class="lc-form-actions">
                    <button class="lc-btn lc-btn--gold" type="submit">Sign in</button>
                    <a class="lc-btn--link" href="${ctx}/index.jsp"
                       style="text-decoration:none;display:inline-flex;padding:0.5rem 0;font-weight:600;color:var(--muted-foreground)">Back to site</a>
                </div>
            </form>
        </div>
        <p class="lc-hint" style="margin-top:1.25rem;text-align:center">
            Default: <code>admin@lenscraft.studio</code> / <code>Admin@LensCraft2026</code>
        </p>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
