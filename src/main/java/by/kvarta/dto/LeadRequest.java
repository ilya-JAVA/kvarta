package by.kvarta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LeadRequest {
    @NotBlank(message = "Укажите ваше имя")
    @Size(max = 100, message = "Имя должно быть не длиннее 100 символов")
    private String name;

    @NotBlank(message = "Укажите номер телефона")
    @Size(max = 40, message = "Проверьте формат номера телефона")
    private String phone;

    @NotBlank(message = "Выберите, что вас интересует")
    @Pattern(regexp = "subscription|one-time|question",
             message = "Выберите один из предложенных вариантов")
    private String interest;

    @Size(max = 1000, message = "Комментарий не должен превышать 1000 символов")
    private String comment;

    @Size(max = 200, message = "Слишком длинное значение источника")
    private String source;
    @Size(max = 300, message = "Слишком длинное название кампании")
    private String campaign;
    @Size(max = 300, message = "Слишком длинное название объявления")
    private String creative;
    @Size(max = 2000, message = "Слишком длинный адрес страницы")
    private String landingPage;
    @Size(max = 2000, message = "Слишком длинный адрес источника перехода")
    private String referrer;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getInterest() { return interest; }
    public void setInterest(String interest) { this.interest = interest; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getCampaign() { return campaign; }
    public void setCampaign(String campaign) { this.campaign = campaign; }
    public String getCreative() { return creative; }
    public void setCreative(String creative) { this.creative = creative; }
    public String getLandingPage() { return landingPage; }
    public void setLandingPage(String landingPage) { this.landingPage = landingPage; }
    public String getReferrer() { return referrer; }
    public void setReferrer(String referrer) { this.referrer = referrer; }
}
