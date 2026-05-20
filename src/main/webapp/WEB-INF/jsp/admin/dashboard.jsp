<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/admin-navbar.jspf" %>
<style>
    .lc-admin-dash h1 { font-size: clamp(1.75rem, 3vw, 2.25rem); margin: 0 0 1.5rem; }
    .lc-admin-stats {
        display: grid; grid-template-columns: repeat(auto-fit, minmax(10rem, 1fr));
        gap: 1rem; margin-bottom: 2rem;
    }
    .lc-admin-stat {
        background: var(--card); border: 1px solid var(--border);
        border-radius: 12px; padding: 1.25rem; text-align: center;
    }
    .lc-admin-stat__num { font-size: 2rem; font-weight: 700; color: var(--gold); margin: 0; }
    .lc-admin-stat__lbl { font-size: 0.875rem; color: var(--muted-foreground); margin: 0.35rem 0 0; }
    .lc-admin-links { display: flex; flex-wrap: wrap; gap: 0.75rem; }
</style>

<div class="lc-page">
    <div class="lc-container lc-admin-dash">
        <h1>Admin dashboard</h1>
        <div class="lc-admin-stats">
            <div class="lc-admin-stat">
                <p class="lc-admin-stat__num">${customerCount}</p>
                <p class="lc-admin-stat__lbl">Registered customers</p>
            </div>
            <div class="lc-admin-stat">
                <p class="lc-admin-stat__num">${bookingCount}</p>
                <p class="lc-admin-stat__lbl">Bookings</p>
            </div>
            <div class="lc-admin-stat">
                <p class="lc-admin-stat__num">${contactCount}</p>
                <p class="lc-admin-stat__lbl">Contact messages</p>
            </div>
        </div>
        <h2 style="font-size:1.1rem;margin:0 0 1rem">Quick actions</h2>
        <div class="lc-admin-links">
            <a class="lc-btn lc-btn--gold" href="${ctx}/admin/customers">Manage customers</a>
            <a class="lc-btn" href="${ctx}/bookings/list" style="border:1px solid var(--border)">All bookings</a>
            <a class="lc-btn" href="${ctx}/contacts/list" style="border:1px solid var(--border)">Contact messages</a>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
