<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="${formTitle} — LensCraft Studio" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>

<div class="lc-page">
    <div class="lc-container lc-form-wrap">
        <h1 class="lc-font-display" style="font-size:clamp(1.75rem,3vw,2.25rem);margin:0 0 0.5rem">${formTitle}</h1>
        <p class="lc-muted" style="margin-bottom:1.75rem">Fields marked in the form are stored in your booking file (TSV).</p>

        <c:if test="${not empty error}">
            <div class="lc-alert lc-alert--danger">${error}</div>
        </c:if>

        <c:set var="isEdit" value="${not empty booking.bookingId}"/>

        <div class="lc-panel">
            <form class="lc-form" method="post" action="${formAction}" novalidate>
                <c:if test="${isEdit}">
                    <input type="hidden" name="bookingId" value="${booking.bookingId}">
                    <div class="lc-field">
                        <label>Booking ID</label>
                        <input class="lc-input" type="text" value="${booking.bookingId}" disabled>
                        <p class="lc-hint">ID cannot be changed.</p>
                    </div>
                </c:if>

                <div class="lc-field">
                    <label for="registeredCustomer">Registered customer</label>
                    <select id="registeredCustomer" class="lc-input" autocomplete="off" aria-describedby="registeredCustomerHint">
                        <option value="">Select to fill name &amp; phone below, or type manually…</option>
                        <c:forEach var="cust" items="${customers}">
                            <c:set var="custMatch" value="${isEdit and cust.fullName eq booking.customerName and cust.phone eq booking.phone}"/>
                            <option value="<c:out value='${cust.customerId}'/>"
                                    data-fullname="<c:out value='${cust.fullName}'/>"
                                    data-phone="<c:out value='${cust.phone}'/>"
                                    ${custMatch ? 'selected' : ''}>
                                <c:out value="${cust.fullName}"/> — <c:out value="${cust.email}"/>
                            </option>
                        </c:forEach>
                    </select>
                    <p class="lc-hint" id="registeredCustomerHint">Optional. Not saved on the booking row; only helps copy details from <a href="${ctx}/register">registered accounts</a>.</p>
                </div>

                <div class="lc-field">
                    <label for="customerName">Customer name</label>
                    <input class="lc-input" id="customerName" name="customerName" required maxlength="120"
                           value="${booking.customerName}">
                </div>
                <div class="lc-field">
                    <label for="phone">Phone</label>
                    <input class="lc-input" id="phone" name="phone" required maxlength="40"
                           value="${booking.phone}">
                </div>
                <div class="lc-field">
                    <label for="eventType">Event type</label>
                    <input class="lc-input" id="eventType" name="eventType" required maxlength="80"
                           value="${booking.eventType}" placeholder="Wedding, Corporate, Birthday...">
                </div>
                <div class="lc-field">
                    <label for="eventDate">Event date</label>
                    <input class="lc-input" id="eventDate" name="eventDate" type="date" required
                           value="${booking.eventDate}">
                </div>
                <div class="lc-field">
                    <label for="location">Location</label>
                    <input class="lc-input" id="location" name="location" required maxlength="160"
                           value="${booking.location}">
                </div>
                <div class="lc-field">
                    <label for="serviceType">Service type</label>
                    <select id="serviceType" name="serviceType" required>
                        <c:set var="st" value="${booking.serviceType}"/>
                        <option value="" ${empty st ? 'selected' : ''}>Choose...</option>
                        <option value="Photography" ${st == 'Photography' ? 'selected' : ''}>Photography</option>
                        <option value="Videography" ${st == 'Videography' ? 'selected' : ''}>Videography</option>
                        <option value="Both" ${st == 'Both' ? 'selected' : ''}>Both</option>
                    </select>
                </div>
                <div class="lc-field">
                    <label for="packageName">Package name</label>
                    <input class="lc-input" id="packageName" name="packageName" required maxlength="120"
                           value="${booking.packageName}" list="packageOptions"
                           placeholder="Match a package title or your custom label">
                    <datalist id="packageOptions">
                        <option value="Essential Photos"></option>
                        <option value="Premium Storytelling"></option>
                        <option value="Highlight Reel"></option>
                        <option value="Cinematic Package"></option>
                    </datalist>
                </div>
                <div class="lc-field">
                    <label for="specialNotes">Special notes</label>
                    <textarea id="specialNotes" name="specialNotes" rows="3"
                              maxlength="500" placeholder="Optional details (avoid tab characters)">${booking.specialNotes}</textarea>
                </div>

                <div class="lc-form-actions">
                    <button class="lc-btn lc-btn--gold" type="submit">${isEdit ? 'Save changes' : 'Create booking'}</button>
                    <a class="lc-btn--link" href="${ctx}/bookings/list" style="text-decoration:none;display:inline-flex;padding:0.5rem 0;font-weight:600;color:var(--muted-foreground)">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    (function () {
        var sel = document.getElementById('registeredCustomer');
        if (sel) {
            sel.addEventListener('change', function () {
                var o = sel.options[sel.selectedIndex];
                if (!o || !o.value) {
                    return;
                }
                var name = o.getAttribute('data-fullname') || '';
                var phone = o.getAttribute('data-phone') || '';
                var nameEl = document.getElementById('customerName');
                var phoneEl = document.getElementById('phone');
                if (nameEl) {
                    nameEl.value = name;
                }
                if (phoneEl) {
                    phoneEl.value = phone;
                }
            });
        }
    })();
    document.querySelector('form.lc-form').addEventListener('submit', function (e) {
        var notes = document.getElementById('specialNotes').value;
        if (notes.indexOf('\t') >= 0) {
            alert('Please remove tab characters from notes.');
            e.preventDefault();
        }
    });
</script>
<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
