package by.kvarta.service;

import by.kvarta.dto.LeadRequest;
import by.kvarta.entity.Lead;
import by.kvarta.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class LeadService {
    private final LeadRepository repository;

    public LeadService(LeadRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Lead create(LeadRequest request) {
        String phone = normalizePhone(request.getPhone());
        if (!phone.matches("^\\+375(25|29|33|44)\\d{7}$")) {
            throw new IllegalArgumentException(
                "Введите корректный белорусский мобильный номер, например +375291234567."
            );
        }

        // synchronized защищает от двойного клика/параллельных запросов в одном экземпляре приложения.
        synchronized (this) {
            if (repository.existsByPhoneAndCreatedAtAfter(phone, LocalDateTime.now().minusMinutes(10))) {
                throw new DuplicateLeadException(
                    "Заявка с этим номером уже отправлена. Повторить можно через 10 минут."
                );
            }

            String source = clean(request.getSource());
            if (source == null || source.isBlank()) source = "direct";

            return repository.save(new Lead(
                request.getName().trim(),
                phone,
                request.getInterest(),
                clean(request.getComment()),
                source,
                clean(request.getCampaign()),
                clean(request.getCreative()),
                clean(request.getLandingPage()),
                clean(request.getReferrer())
            ));
        }
    }

    private String normalizePhone(String value) {
        String digits = value == null ? "" : value.replaceAll("\\D", "");
        if (digits.startsWith("80") && digits.length() == 11) {
            digits = "375" + digits.substring(2);
        } else if (digits.length() == 9) {
            digits = "375" + digits;
        }
        return digits.startsWith("375") ? "+" + digits : digits;
    }

    private String clean(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public static class DuplicateLeadException extends RuntimeException {
        public DuplicateLeadException(String message) { super(message); }
    }
}
