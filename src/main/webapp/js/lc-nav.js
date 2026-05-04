(function () {
    var btn = document.querySelector('[data-lc-nav-toggle]');
    var panel = document.querySelector('[data-lc-nav-panel]');
    if (btn && panel) {
        btn.addEventListener('click', function () {
            var open = panel.classList.toggle('lc-nav-panel--open');
            btn.setAttribute('aria-expanded', open ? 'true' : 'false');
        });
    }

    var header = document.querySelector('.lc-header');
    if (!header || !document.body.classList.contains('lc-body--home')) return;

    function onScroll() {
        if (window.scrollY > 48) {
            header.classList.add('is-scrolled');
        } else {
            header.classList.remove('is-scrolled');
        }
    }

    onScroll();
    window.addEventListener('scroll', onScroll, { passive: true });
})();
