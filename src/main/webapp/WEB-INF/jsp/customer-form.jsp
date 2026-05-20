<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="${formTitle} — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<c:choose>
    <c:when test="${fromAdmin}"><%@ include file="/WEB-INF/jsp/include/admin-navbar.jspf" %></c:when>
    <c:otherwise><%@ include file="/WEB-INF/jsp/include/navbar.jspf" %></c:otherwise>
</c:choose>

<div class="lc-page">
    <div class="lc-container lc-form-wrap">
        <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.5rem">${formTitle}</h1>
        <p class="lc-muted" style="margin-bottom:1.75rem">Changes are saved to <code>customers.txt</code>. Leave password blank to keep the current one.</p>

        <c:if test="${not empty error}">
            <div class="lc-alert lc-alert--danger">${error}</div>
        </c:if>

        <div class="lc-panel">
            <form class="lc-form" method="post" action="${formAction}" autocomplete="off">
                <input type="hidden" name="customerId" value="${customer.customerId}">
                <c:if test="${fromAdmin}"><input type="hidden" name="from" value="admin"></c:if>
                <div class="lc-field">
                    <label>Account ID</label>
                    <input class="lc-input" type="text" value="${customer.customerId}" disabled>
                </div>
                <div class="lc-field">
                    <label for="fullName">Full name</label>
                    <input class="lc-input" id="fullName" name="fullName" required maxlength="120"
                           value="${customer.fullName}">
                </div>
                <div class="lc-field">
                    <label for="email">Email</label>
                    <input class="lc-input" id="email" name="email" type="email" required maxlength="120"
                           value="${customer.email}">
                </div>
                <div class="lc-field">
                    <label for="phone">Phone</label>
                    <input class="lc-input" id="phone" name="phone" required maxlength="40"
                           value="${customer.phone}">
                </div>
                <div class="lc-field">
                    <label for="password">New password <span class="lc-muted">(optional)</span></label>
                    <input class="lc-input" id="password" name="password" type="password" minlength="8" maxlength="128"
                           autocomplete="new-password">
                </div>
                <div class="lc-field">
                    <label for="confirmPassword">Confirm new password</label>
                    <input class="lc-input" id="confirmPassword" name="confirmPassword" type="password"
                           maxlength="128" autocomplete="new-password">
                </div>
                <div class="lc-form-actions">
                    <button class="lc-btn lc-btn--gold" type="submit">Save changes</button>
                    <a class="lc-btn--link" href="${fromAdmin ? ctx.concat('/admin/customers') : ctx.concat('/register')}"
                       style="text-decoration:none;display:inline-flex;padding:0.5rem 0;font-weight:600;color:var(--muted-foreground)">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
