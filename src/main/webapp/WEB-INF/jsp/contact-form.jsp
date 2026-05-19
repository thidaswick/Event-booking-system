<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="${formTitle} — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>

<div class="lc-page">
    <div class="lc-container lc-form-wrap">
        <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.5rem">${formTitle}</h1>
        <p class="lc-muted" style="margin-bottom:1.75rem">
            Send us a message — we typically reply within 24 hours. Your inquiry is saved securely on our server.
        </p>

        <c:if test="${not empty error}">
            <div class="lc-alert lc-alert--danger">${error}</div>
        </c:if>

        <c:set var="isEdit" value="${not empty contact.contactId}"/>

        <div class="lc-panel">
            <form class="lc-form" method="post" action="${formAction}" novalidate>
                <c:if test="${isEdit}">
                    <input type="hidden" name="contactId" value="${contact.contactId}">
                    <input type="hidden" name="createdAt" value="${contact.createdAt}">
                    <div class="lc-field">
                        <label>Message ID</label>
                        <input class="lc-input" type="text" value="${contact.contactId}" disabled>
                        <p class="lc-hint">ID cannot be changed. Submitted: ${contact.createdAt}</p>
                    </div>
                </c:if>

                <div class="lc-field">
                    <label for="fullName">Your name</label>
                    <input class="lc-input" id="fullName" name="fullName" required maxlength="120"
                           value="${contact.fullName}">
                </div>
                <div class="lc-field">
                    <label for="email">Email</label>
                    <input class="lc-input" id="email" name="email" type="email" required maxlength="120"
                           value="${contact.email}">
                </div>
                <div class="lc-field">
                    <label for="phone">Phone <span class="lc-muted">(optional)</span></label>
                    <input class="lc-input" id="phone" name="phone" maxlength="40"
                           value="${contact.phone}">
                </div>
                <div class="lc-field">
                    <label for="subject">Subject</label>
                    <input class="lc-input" id="subject" name="subject" required maxlength="160"
                           value="${contact.subject}" placeholder="Wedding enquiry, pricing question…">
                </div>
                <div class="lc-field">
                    <label for="message">Message</label>
                    <textarea id="message" name="message" rows="5" required maxlength="2000"
                              placeholder="Tell us about your event, dates, and what you need…">${contact.message}</textarea>
                </div>

                <c:if test="${isEdit}">
                    <div class="lc-field">
                        <label for="status">Status</label>
                        <select id="status" name="status" required>
                            <c:set var="st" value="${contact.status}"/>
                            <option value="New" ${st == 'New' ? 'selected' : ''}>New</option>
                            <option value="In Progress" ${st == 'In Progress' ? 'selected' : ''}>In Progress</option>
                            <option value="Resolved" ${st == 'Resolved' ? 'selected' : ''}>Resolved</option>
                        </select>
                    </div>
                </c:if>

                <div class="lc-form-actions">
                    <button class="lc-btn lc-btn--gold" type="submit">${isEdit ? 'Save changes' : 'Send message'}</button>
                    <a class="lc-btn--link" href="${ctx}/contacts/list"
                       style="text-decoration:none;display:inline-flex;padding:0.5rem 0;font-weight:600;color:var(--muted-foreground)">
                        ${isEdit ? 'Back to messages' : 'View all messages'}
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    document.querySelector('form.lc-form').addEventListener('submit', function (e) {
        var msg = document.getElementById('message').value;
        if (msg.indexOf('\t') >= 0) {
            alert('Please remove tab characters from your message.');
            e.preventDefault();
        }
    });
</script>
<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
