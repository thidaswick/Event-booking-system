<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="LensCraft Studio — Cinematic Event Photography & Videography" scope="request"/>
<c:set var="bodyExtraClass" value="lc-body--home" scope="request"/>
<%@ include file="/WEB-INF/jsp/include/head.jspf" %>
<%@ include file="/WEB-INF/jsp/include/navbar.jspf" %>

<section class="lc-hero">
    <div class="lc-hero__bg" aria-hidden="true"></div>
    <img class="lc-hero__img" src="${ctx}/images/hero.jpg" alt="LensCraft photographer at a luxurious wedding">
    <div class="lc-hero__shade" aria-hidden="true"></div>
    <div class="lc-container">
        <p class="lc-hero__eyebrow">Est. 2026 · Colombo</p>
        <h1 class="lc-hero__title">Capture your <em>special</em> moments, beautifully.</h1>
        <p class="lc-hero__lead">Premium photography and videography for weddings, engagements, birthdays and corporate events — crafted with a cinematic touch.</p>
        <div class="lc-hero__actions">
            <a class="lc-btn lc-btn--gold" href="${ctx}/bookings/create">Book Now</a>
            <a class="lc-btn lc-btn--ghost" href="${ctx}/packages">Explore Packages</a>
        </div>
        <p class="lc-hero__stat">500+ stories captured</p>
    </div>
</section>

<section class="lc-section">
    <div class="lc-container">
        <p class="lc-section__label">What we do</p>
        <h2 class="lc-section__title">A signature look for <br>every story.</h2>
        <p class="lc-section__intro">View our tailored offerings for every occasion — from intimate portraits to full-day wedding cinema.</p>
        <p style="margin-bottom:2.5rem"><a class="lc-link-arrow" href="${ctx}/packages">View all packages</a></p>
        <div class="lc-grid-4">
            <article class="lc-card-soft">
                <h3>Weddings</h3>
                <p>Editorial, emotive, full-day coverage of your once-in-a-lifetime day.</p>
            </article>
            <article class="lc-card-soft">
                <h3>Portraits</h3>
                <p>Studio and on-location portraits styled with a cinematic eye.</p>
            </article>
            <article class="lc-card-soft">
                <h3>Cinematic Films</h3>
                <p>Story-driven films with drone, gimbal &amp; custom soundtracks.</p>
            </article>
            <article class="lc-card-soft">
                <h3>Events &amp; Brands</h3>
                <p>Birthdays, launches, conferences — captured beautifully.</p>
            </article>
        </div>
    </div>
</section>

<section class="lc-section lc-section--tight" id="about">
    <div class="lc-container lc-split">
        <div>
            <p class="lc-section__label">Why LensCraft</p>
            <h2 class="lc-section__title">Storytellers first.<br>Photographers always.</h2>
            <p class="lc-quote">We blend editorial fashion sensibility with documentary instincts — so your gallery feels like a magazine and your film feels like a movie.</p>
            <div class="lc-stats">
                <div class="lc-stat">
                    <div class="lc-stat__num">500+</div>
                    <div class="lc-stat__lbl">Events</div>
                </div>
                <div class="lc-stat">
                    <div class="lc-stat__num">12</div>
                    <div class="lc-stat__lbl">Awards</div>
                </div>
                <div class="lc-stat">
                    <div class="lc-stat__num">98%</div>
                    <div class="lc-stat__lbl">5-star</div>
                </div>
            </div>
        </div>
        <div class="lc-gallery" style="columns:2">
            <figure>
                <img src="https://images.unsplash.com/photo-1519741497674-611481863552?w=600&q=80" alt="Wedding rings" width="600" height="750" loading="lazy">
            </figure>
            <figure>
                <img src="https://images.unsplash.com/photo-1522673607200-164d1b6ce486?w=600&q=80" alt="Engagement at sunset" width="600" height="400" loading="lazy">
            </figure>
        </div>
    </div>
</section>

<section class="lc-section" id="gallery">
    <div class="lc-container">
        <p class="lc-section__label">Recent work</p>
        <h2 class="lc-section__title">A glimpse from the archive.</h2>
        <p style="margin-bottom:2rem"><a class="lc-link-arrow" href="${ctx}/index.jsp#gallery">See full gallery</a></p>
        <div class="lc-gallery">
            <figure><img src="https://images.unsplash.com/photo-1606800052052-a08af7148866?w=800&q=80" alt="Wedding celebration" width="800" height="1000" loading="lazy"></figure>
            <figure><img src="https://images.unsplash.com/photo-1511285560929-80b456fea0bc?w=800&q=80" alt="Couple portrait" width="800" height="533" loading="lazy"></figure>
            <figure><img src="https://images.unsplash.com/photo-1492691527719-9d1e07a534b4?w=800&q=80" alt="Photography session" width="800" height="1200" loading="lazy"></figure>
            <figure><img src="https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=800&q=80" alt="Corporate event" width="800" height="533" loading="lazy"></figure>
            <figure><img src="https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?w=800&q=80" alt="Event details" width="800" height="533" loading="lazy"></figure>
        </div>
    </div>
</section>

<section class="lc-section lc-section--tight">
    <div class="lc-container">
        <p class="lc-section__label">Testimonials</p>
        <h2 class="lc-section__title">Loved by storytellers.</h2>
        <div class="lc-testimonials" style="margin-top:2.5rem">
            <article class="lc-quote-card">
                <p>Every photo feels like a film still. LensCraft turned our wedding into a story we'll relive forever.</p>
                <strong>Aanya &amp; Rohan</strong>
                <span>Wedding · Galle</span>
            </article>
            <article class="lc-quote-card">
                <p>The team delivered a same-day reel that took our launch viral. Effortlessly professional.</p>
                <strong>Marcus Lee</strong>
                <span>Brand Launch</span>
            </article>
            <article class="lc-quote-card">
                <p>I cried looking at the gallery. The light, the emotion — they captured us perfectly.</p>
                <strong>Priya M.</strong>
                <span>Engagement</span>
            </article>
        </div>
    </div>
</section>

<section class="lc-cta-band" id="contact">
    <div class="lc-container">
        <p class="lc-section__label">Let's create</p>
        <h2 class="lc-section__title">Your story deserves the spotlight.</h2>
        <p class="lc-section__intro">Tell us about your event — we'll craft a bespoke proposal within 24 hours.</p>
        <div style="display:flex;flex-wrap:wrap;gap:0.75rem;justify-content:center">
            <a class="lc-btn lc-btn--gold" href="${ctx}/contacts/create">Contact us</a>
            <a class="lc-btn" href="${ctx}/bookings/create" style="border:1px solid var(--border)">Start your booking</a>
        </div>
    </div>
</section>

<%@ include file="/WEB-INF/jsp/include/footer.jspf" %>
