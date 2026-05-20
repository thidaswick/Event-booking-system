<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/admin-navbar.jspf" %>

<div class="lc-page">
    <div class="lc-container">
        <h1 style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 1.25rem">Manage customers</h1>
        <p class="lc-muted" style="margin-bottom:1.5rem">Data from <code>customers.txt</code></p>

        <form method="get" action="${ctx}/admin/customers" role="search"
              style="display:flex;flex-wrap:wrap;gap:0.65rem;margin-bottom:1.5rem;max-width:36rem">
            <input type="search" name="q" class="lc-input" style="flex:1;min-width:12rem"
                   value="${fn:escapeXml(q)}" placeholder="Search ID, name, or email…">
            <button type="submit" class="lc-btn lc-btn--gold">Search</button>
            <c:if test="${searchActive}">
                <a class="lc-btn-sm" href="${ctx}/admin/customers">Clear</a>
            </c:if>
        </form>

        <c:if test="${param.msg == 'updated'}">
            <div class="lc-alert lc-alert--success">Customer updated.</div>
        </c:if>
        <c:if test="${param.msg == 'deleted'}">
            <div class="lc-alert lc-alert--success">Customer deleted.</div>
        </c:if>
        <c:if test="${param.msg == 'deletefailed'}">
            <div class="lc-alert lc-alert--danger">Could not delete customer.</div>
        </c:if>
        <c:if test="${param.msg == 'notfound'}">
            <div class="lc-alert lc-alert--warn">Customer not found.</div>
        </c:if>

        <div class="lc-panel lc-table-wrap">
            <table class="lc-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Registered</th>
                    <th class="lc-actions">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cust" items="${customers}">
                    <tr>
                        <td><code>${cust.customerId}</code></td>
                        <td>${cust.fullName}</td>
                        <td>${cust.email}</td>
                        <td>${cust.phone}</td>
                        <td style="font-size:0.85rem;white-space:nowrap">${cust.createdAt}</td>
                        <td class="lc-actions">
                            <a class="lc-btn-sm" href="${ctx}/customers/edit?customerId=${cust.customerId}&from=admin">Edit</a>
                            <form method="post" action="${ctx}/customers/delete" style="display:inline;margin-left:0.35rem"
                                  onsubmit="return confirm('Delete ${cust.customerId}?');">
                                <input type="hidden" name="customerId" value="${cust.customerId}">
                                <input type="hidden" name="from" value="admin">
                                <button type="submit" class="lc-btn-sm lc-btn-sm--danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty customers}">
                    <tr>
                        <td colspan="6" style="text-align:center;padding:2rem;color:var(--muted-foreground)">
                            <c:choose>
                                <c:when test="${searchActive}">No customers match.</c:when>
                                <c:otherwise>No registered customers yet.</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
