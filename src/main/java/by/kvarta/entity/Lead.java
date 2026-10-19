package by.kvarta.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leads", indexes = {
    @Index(name = "idx_leads_phone_created", columnList = "phone, created_at")
})
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 30)
    private String interest;

    @Column(length = 1000)
    private String comment;

    @Column(length = 200)
    private String source;

    @Column(length = 300)
    private String campaign;

    @Column(length = 300)
    private String creative;

    @Column(name = "landing_page", length = 2000)
    private String landingPage;

    @Column(length = 2000)
    private String referrer;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Lead() {}

    public Lead(String name, String phone, String interest, String comment,
                String source, String campaign, String creative,
                String landingPage, String referrer) {
        this.name = name;
        this.phone = phone;
        this.interest = interest;
        this.comment = comment;
        this.source = source;
        this.campaign = campaign;
        this.creative = creative;
        this.landingPage = landingPage;
        this.referrer = referrer;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getInterest() { return interest; }
    public String getComment() { return comment; }
    public String getSource() { return source; }
    public String getCampaign() { return campaign; }
    public String getCreative() { return creative; }
    public String getLandingPage() { return landingPage; }
    public String getReferrer() { return referrer; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
