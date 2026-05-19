<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<c:set var="pageTitle" value="Registered accounts — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>
<style>
    .lc-customers-list-head { margin-bottom: 2rem; text-align: center; }
    .lc-customers-list-head h1 { font-size: clamp(1.75rem, 3vw, 2.25rem); margin: 0 0 0.5rem; }
    .lc-customers-list-head p { margin: 0 0 1.25rem; color: var(--muted-foreground); }
    .lc-customers-toolbar {
        display: flex; flex-wrap: wrap; align-items: center; justify-content: center;
        gap: 0.65rem; max-width: 48rem; margin: 0 auto;
    }
    .lc-customers-search__input { flex: 1 1 16rem; min-width: 0; max-width: 28rem; }
</style>

<div class="lc-page">
    <div class="lc-container">
        <div class="lc-customers-list-head">
            <h1>Registered accounts</h1>
            <p>Stored in <code>WEB-INF/data/customers.txt</code> (tab-separated, same pattern as bookings).</p>
            <div class="lc-customers-toolbar">
                <form method="get" action="${ctx}/customers/list" role="search"
                      style="display:flex;flex-wrap:wrap;gap:0.65rem;align-items:center;flex:1 1 auto;justify-content:center">
                    <input type="search" name="q" class="lc-input lc-customers-search__input"
                           value="${fn:escapeXml(q)}"
                           placeholder="Search by ID (CU001), name, or email…"
                           autocomplete="off">
                    <button type="submit" class="lc-btn lc-btn--gold">Search</button>
                    <c:if test="${searchActive}">
                        <a class="lc-btn-sm" href="${ctx}/customers/list">Clear</a>
                    </c:if>
                </form>
                <a class="lc-btn lc-btn--gold" href="${ctx}/register">Sign up</a>
            </div>
        </div>

        <c:if test="${param.msg == 'created'}">
            <div class="lc-alert lc-alert--success">
                Account created.
                <c:if test="${not empty param.id}"> ID: <code>${fn:escapeXml(param.id)}</code></c:if>
            </div>
        </c:if>
        <c:if test="${param.msg == 'updated'}">
            <div class="lc-alert lc-alert--success">Account updated.</div>
        </c:if>
        <c:if test="${param.msg == 'deleted'}">
            <div class="lc-alert lc-alert--success">Account deleted.</div>
        </c:if>
        <c:if test="${param.msg == 'deletefailed'}">
            <div class="lc-alert lc-alert--danger">Could not delete account.</div>
        </c:if>
        <c:if test="${param.msg == 'notfound'}">
            <div class="lc-alert lc-alert--warn">Account not found.</div>
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
                        <td><a href="mailto:${cust.email}">${cust.email}</a></td>
                        <td>${cust.phone}</td>
                        <td style="white-space:nowrap;font-size:0.85rem">${cust.createdAt}</td>
                        <td class="lc-actions">
                            <a class="lc-btn-sm" href="${ctx}/customers/edit?customerId=${cust.customerId}">Edit</a>
                            <form method="post" action="${ctx}/customers/delete"
                                  style="display:inline;margin-left:0.35rem"
                                  onsubmit="return confirm('Delete account ${cust.customerId}?');">
                                <input type="hidden" name="customerId" value="${cust.customerId}">
                                <button type="submit" class="lc-btn-sm lc-btn-sm--danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty customers}">
                    <tr>
                        <td colspan="6" style="text-align:center;padding:2.5rem;color:var(--muted-foreground)">
                            <c:choose>
                                <c:when test="${searchActive}">No accounts match your search.</c:when>
                                <c:otherwise>No accounts yet. <a href="${ctx}/register">Sign up</a> to create one.</c:otherwise>
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
