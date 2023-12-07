package io.warender.skycinema.payments;

import io.warender.skycinema.movie_tickets.Ticket;
import io.warender.skycinema.shared.ApiVersions;
import jakarta.mail.MessagingException;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RestController
@RequiredArgsConstructor
public final class EmailController {

  private final EmailService emailService;

  @PutMapping(ApiVersions.ONE + "/send-email")
  public void sendEmail() throws MessagingException {
    Context context = new Context();
    context.setVariable("message", "This is a test message");
    var tickets = new ArrayList<Ticket>();
    context.setVariable("tickets", tickets);
    emailService.sendEmail("crealhex@gmail.com", "Test", "customer-order-confirmation", context);
  }
}
