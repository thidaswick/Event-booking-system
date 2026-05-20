<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="${formTitle} — Admin" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/admin-navbar.jspf" %>

<div class="lc-page">
    <div class="lc-container lc-form-wrap">
        <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.5rem">${formTitle}</h1>
        <p class="lc-muted" style="margin-bottom:1.75rem">Fields match the card shown to customers on the <a href="${ctx}/packages">Packages</a> page.</p>

        <c:if test="${not empty error}">
            <div class="lc-alert lc-alert--danger">${error}</div>
        </c:if>

        <c:set var="isEdit" value="${not empty pkg.packageId}"/>

        <div class="lc-panel">
            <form class="lc-form" method="post" action="${formAction}" novalidate>
                <c:if test="${isEdit}">
                    <input type="hidden" name="packageId" value="${pkg.packageId}">
                    <div class="lc-field">
                        <label>Package ID</label>
                        <input class="lc-input" type="text" value="${pkg.packageId}" disabled>
                    </div>
                </c:if>

                <div class="lc-field">
                    <label for="code">Code</label>
                    <input class="lc-input" id="code" name="code" required maxlength="40"
                           value="${pkg.code}" placeholder="PH-BASIC">
                    <p class="lc-hint">Short code shown as a badge on the card.</p>
                </div>
                <div class="lc-field">
                    <label for="title">Title</label>
                    <input class="lc-input" id="title" name="title" required maxlength="120"
                           value="${pkg.title}" placeholder="Essential Photos">
                </div>
                <div class="lc-field">
                    <label for="priceLabel">Price label</label>
                    <input class="lc-input" id="priceLabel" name="priceLabel" required maxlength="80"
                           value="${pkg.priceLabel}" placeholder="From LKR 45,000 (sample)">
                </div>
                <div class="lc-field">
                    <label for="summary">Summary</label>
                    <textarea id="summary" name="summary" rows="2" required maxlength="300"
                              placeholder="On-site coverage: 4h, edited stills: 150">${pkg.summary}</textarea>
                </div>
                <div class="lc-field">
                    <label for="deliverables">Deliverables</label>
                    <textarea id="deliverables" name="deliverables" rows="2" required maxlength="300"
                              placeholder="Online gallery + print-ready exports">${pkg.deliverables}</textarea>
                </div>

                <div class="lc-form-actions">
                    <button class="lc-btn lc-btn--gold" type="submit">${isEdit ? 'Save changes' : 'Create package'}</button>
                    <a class="lc-btn--link" href="${ctx}/admin/packages" style="text-decoration:none;display:inline-flex;padding:0.5rem 0;font-weight:600;color:var(--muted-foreground)">Cancel</a>
                </div>
            </form>
        </div>

        <c:if test="${not empty pkg.title}">
            <h2 style="font-size:1.1rem;margin:2rem 0 1rem">Preview</h2>
            <%@ include file="/WEB-INF/jsp/include/package-card.jspf" %>
        </c:if>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
