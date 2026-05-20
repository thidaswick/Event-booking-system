<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<c:set var="pageTitle" value="Contact messages — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<c:choose>
    <c:when test="${adminArea}"><%@ include file="/WEB-INF/jsp/include/admin-navbar.jspf" %></c:when>
    <c:otherwise><%@ include file="/WEB-INF/jsp/include/navbar.jspf" %></c:otherwise>
</c:choose>
<style>
    .lc-contacts-list-head { margin-bottom: 2rem; text-align: center; }
    .lc-contacts-list-head h1 {
        font-size: clamp(1.75rem, 3vw, 2.25rem);
        margin: 0 0 0.5rem;
    }
    .lc-contacts-list-head p { margin: 0 0 1.25rem; color: var(--muted-foreground); }
    .lc-contacts-toolbar {
        display: flex; flex-wrap: wrap; align-items: center; justify-content: center;
        gap: 0.65rem; max-width: 48rem; margin: 0 auto;
    }
    .lc-contacts-search__input { flex: 1 1 16rem; min-width: 0; max-width: 28rem; }
    .lc-status { font-size: 0.8rem; font-weight: 600; padding: 0.2rem 0.5rem; border-radius: 4px; }
    .lc-status--new { background: rgba(212, 175, 55, 0.2); color: var(--gold); }
    .lc-status--progress { background: rgba(100, 149, 237, 0.15); color: #6eb5ff; }
    .lc-status--resolved { background: rgba(80, 200, 120, 0.15); color: #6fd68a; }
</style>

<div class="lc-page">
    <div class="lc-container">
        <div class="lc-contacts-list-head">
            <h1>Contact messages</h1>
            <p>Manage inquiries submitted through the Contact Us form.</p>
            <div class="lc-contacts-toolbar">
                <form class="lc-contacts-search" method="get" action="${ctx}/contacts/list" role="search"
                      style="display:flex;flex-wrap:wrap;gap:0.65rem;align-items:center;flex:1 1 auto;justify-content:center">
                    <input type="search" name="q" class="lc-input lc-contacts-search__input"
                           value="${fn:escapeXml(q)}"
                           placeholder="Search by ID (CT001), name, email, or subject…"
                           autocomplete="off">
                    <button type="submit" class="lc-btn lc-btn--gold">Search</button>
                    <c:if test="${searchActive}">
                        <a class="lc-btn-sm" href="${ctx}/contacts/list">Clear</a>
                    </c:if>
                </form>
                <a class="lc-btn lc-btn--gold" href="${ctx}/contacts/create">New message</a>
            </div>
        </div>

        <c:if test="${param.msg == 'created'}">
            <div class="lc-alert lc-alert--success">
                Message sent successfully.
                <c:if test="${not empty param.id}"> Reference: <code>${fn:escapeXml(param.id)}</code></c:if>
            </div>
        </c:if>
        <c:if test="${param.msg == 'updated'}">
            <div class="lc-alert lc-alert--success">Message updated.</div>
        </c:if>
        <c:if test="${param.msg == 'deleted'}">
            <div class="lc-alert lc-alert--success">Message deleted.</div>
        </c:if>
        <c:if test="${param.msg == 'deletefailed'}">
            <div class="lc-alert lc-alert--danger">Could not delete message.</div>
        </c:if>
        <c:if test="${param.msg == 'notfound'}">
            <div class="lc-alert lc-alert--warn">Message not found.</div>
        </c:if>

        <div class="lc-panel lc-table-wrap">
            <table class="lc-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Subject</th>
                    <th>Message</th>
                    <th>Status</th>
                    <th>Submitted</th>
                    <th class="lc-actions">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="c" items="${contacts}">
                    <tr>
                        <td><code>${c.contactId}</code></td>
                        <td>${c.fullName}</td>
                        <td><a href="mailto:${c.email}">${c.email}</a></td>
                        <td>${c.phone}</td>
                        <td>${c.subject}</td>
                        <td><span class="lc-text-truncate" title="${c.message}">${c.message}</span></td>
                        <td>
                            <c:choose>
                                <c:when test="${c.status == 'Resolved'}">
                                    <span class="lc-status lc-status--resolved">${c.status}</span>
                                </c:when>
                                <c:when test="${c.status == 'In Progress'}">
                                    <span class="lc-status lc-status--progress">${c.status}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="lc-status lc-status--new">${empty c.status ? 'New' : c.status}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="white-space:nowrap;font-size:0.85rem">${c.createdAt}</td>
                        <td class="lc-actions">
                            <a class="lc-btn-sm" href="${ctx}/contacts/edit?contactId=${c.contactId}">Edit</a>
                            <form method="post" action="${ctx}/contacts/delete"
                                  style="display:inline;margin-left:0.35rem"
                                  onsubmit="return confirm('Delete message ${c.contactId}?');">
                                <input type="hidden" name="contactId" value="${c.contactId}">
                                <button type="submit" class="lc-btn-sm lc-btn-sm--danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty contacts}">
                    <tr>
                        <td colspan="9" style="text-align:center;padding:2.5rem;color:var(--muted-foreground)">
                            <c:choose>
                                <c:when test="${searchActive}">No messages match your search.</c:when>
                                <c:otherwise>No messages yet. <a href="${ctx}/contacts/create">Send the first one</a>.</c:otherwise>
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
