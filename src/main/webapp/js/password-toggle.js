(function () {
    document.querySelectorAll("[data-password-toggle]").forEach(function (btn) {
        var selector = btn.getAttribute("data-password-toggle");
        var input = selector ? document.querySelector(selector) : null;
        if (!input) {
            return;
        }
        var showLabel = btn.querySelector(".lc-password-field__label--show");
        var hideLabel = btn.querySelector(".lc-password-field__label--hide");

        btn.addEventListener("click", function () {
            var revealing = input.type === "password";
            input.type = revealing ? "text" : "password";
            btn.setAttribute("aria-pressed", revealing ? "true" : "false");
            btn.setAttribute("aria-label", revealing ? "Hide password" : "Show password");
            if (showLabel) {
                showLabel.hidden = revealing;
            }
            if (hideLabel) {
                hideLabel.hidden = !revealing;
            }
        });
    });
})();
