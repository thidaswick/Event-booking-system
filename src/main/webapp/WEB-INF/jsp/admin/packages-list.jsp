<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<c:set var="pageTitle" value="Manage packages — Admin" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/admin-navbar.jspf" %>

<div class="lc-page">
    <div class="lc-container">
        <div style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:flex-start;gap:1rem;margin-bottom:1.5rem">
            <div>
                <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.35rem">Packages</h1>
                <p class="lc-muted" style="margin:0">Add and edit packages shown on the public <a href="${ctx}/packages">Packages</a> page.</p>
            </div>
            <a class="lc-btn lc-btn--gold" href="${ctx}/admin/packages/create">Add package</a>
        </div>

        <c:if test="${param.msg == 'created'}">
            <div class="lc-alert lc-alert--success">Package created.</div>
        </c:if>
        <c:if test="${param.msg == 'updated'}">
            <div class="lc-alert lc-alert--success">Package updated.</div>
        </c:if>
        <c:if test="${param.msg == 'deleted'}">
            <div class="lc-alert lc-alert--success">Package deleted.</div>
        </c:if>
        <c:if test="${param.msg == 'deletefailed'}">
            <div class="lc-alert lc-alert--danger">Could not delete package.</div>
        </c:if>
        <c:if test="${param.msg == 'notfound'}">
            <div class="lc-alert lc-alert--warn">Package not found.</div>
        </c:if>

        <div class="lc-panel lc-table-wrap" style="margin-bottom:2rem">
            <table class="lc-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Code</th>
                    <th>Title</th>
                    <th>Price</th>
                    <th>Summary</th>
                    <th class="lc-actions">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${packages}">
                    <tr>
                        <td><code>${fn:escapeXml(p.packageId)}</code></td>
                        <td>${fn:escapeXml(p.code)}</td>
                        <td>${fn:escapeXml(p.title)}</td>
                        <td>${fn:escapeXml(p.priceLabel)}</td>
                        <td style="max-width:18rem">${fn:escapeXml(p.summary)}</td>
                        <td class="lc-actions">
                            <a class="lc-btn-sm" href="${ctx}/admin/packages/edit?packageId=${fn:escapeXml(p.packageId)}">Edit</a>
                            <form method="post" action="${ctx}/admin/packages/delete" style="display:inline"
                                  onsubmit="return confirm('Delete this package?');">
                                <input type="hidden" name="packageId" value="${fn:escapeXml(p.packageId)}">
                                <button type="submit" class="lc-btn-sm lc-btn-sm--danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty packages}">
                    <tr><td colspan="6" class="lc-muted" style="text-align:center;padding:2rem">No packages yet. <a href="${ctx}/admin/packages/create">Add one</a>.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <h2 style="font-size:1.1rem;margin:0 0 1rem">Customer preview</h2>
        <div class="lc-grid-4 lc-packages-grid">
            <c:forEach var="pkg" items="${packages}">
                <%@ include file="/WEB-INF/jsp/include/package-card.jspf" %>
            </c:forEach>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
