(() => {
    const form = document.getElementById("lead-form");
    if (!form) return;

    const message = document.getElementById("form-message");
    const button = document.getElementById("submit-button");
    let sending = false;

    const params = new URLSearchParams(window.location.search);
    const referrer = document.referrer || "";
    let source = params.get("utm_source");
    if (!source) {
        if (!referrer) source = "direct";
        else if (referrer.includes("instagram.com")) source = "instagram";
        else if (referrer.includes("yandex.")) source = "yandex";
        else source = "referral";
    }

    document.querySelectorAll("[data-interest]").forEach((link) => {
        link.addEventListener("click", () => {
            const select = document.getElementById("interest");
            if (select) select.value = link.dataset.interest;
        });
    });

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        if (sending) return;

        message.textContent = "";
        const data = new FormData(form);
        const phone = String(data.get("phone") || "").trim();
        const digits = phone.replace(/\D/g, "");
        const valid = (digits.length === 12 && digits.startsWith("375")
            && /^(25|29|33|44)/.test(digits.substring(3)))
            || (digits.length === 11 && digits.startsWith("80")
            && /^(25|29|33|44)/.test(digits.substring(2)));

        if (!valid) {
            message.textContent = "Проверьте номер: укажите белорусский мобильный номер, например +375 29 123-45-67.";
            return;
        }

        sending = true;
        button.disabled = true;
        button.textContent = "Отправляем…";

        const payload = {
            name: String(data.get("name") || "").trim(),
            phone,
            interest: String(data.get("interest") || ""),
            comment: String(data.get("comment") || "").trim(),
            source: source || "direct",
            campaign: params.get("utm_campaign") || "",
            creative: params.get("utm_content") || params.get("utm_creative") || "",
            landingPage: window.location.href,
            referrer
        };

        try {
            const response = await fetch("/api/lead", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });
            const result = await response.json().catch(() => ({}));
            if (!response.ok) {
                throw new Error(result.message || "Не удалось отправить заявку.");
            }
            message.textContent = result.message || "Заявка отправлена!";
            message.style.color = "#d9f5d0";
            form.reset();
        } catch (error) {
            message.textContent = (error.message || "Не удалось отправить заявку.")
                + " Проверьте интернет и попробуйте ещё раз.";
            message.style.color = "#ffe0c2";
        } finally {
            sending = false;
            button.disabled = false;
            button.textContent = "Отправить заявку";
        }
    });
})();
