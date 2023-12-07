package io.warender.skycinema.payments;

import io.warender.skycinema.card_center.CardCenterStorage;
import io.warender.skycinema.movie_sessions.MovieSessionStorage;
import io.warender.skycinema.orders.Order;
import io.warender.skycinema.orders.OrderStatus;
import io.warender.skycinema.orders.OrderStorage;
import io.warender.skycinema.shared.ApiVersions;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentPostController {

  private final OrderStorage orderStorage;
  private final CardCenterStorage cardCenterStorage;
  private final PaymentStorage paymentStorage;
  private final MovieSessionStorage movieSessionStorage;

  @Transactional
  @PostMapping(ApiVersions.ONE + "/payments")
  public ResponseEntity<Payment> registerCustomerPayment(@RequestBody Request request) throws MessagingException {
    final var order =
        orderStorage
            .findByClientSessionId(request.clientSessionId())
            .orElseThrow(EntityNotFoundException::new);

    if (order.getTickets().isEmpty()) {
      log.info("Your order is empty");
      return ResponseEntity.badRequest().build();
    }

    var canTicketsBePurchased =
        order.getTickets().stream()
            .allMatch(ticket -> ticket.isReserved() && !ticket.isAllocated());
    if (!canTicketsBePurchased) {
      log.info("You can't purchase the tickets, some of them are not available");
      return ResponseEntity.badRequest().build();
    }

    log.info("Validate the information of the customer");
    var cardValidators = cardCenterStorage.findAll();

    var cardIsValid =
        cardValidators.stream()
            .anyMatch(
                cardValidator -> request.cardNumber().startsWith(cardValidator.getBinNumber()));
    log.info("Is your card valid?: {}", cardIsValid);

    if (!cardIsValid) {
      log.info("Your card is not valid");
      return ResponseEntity.badRequest().build();
    }
    if (!request.cardType().equals(PaymentMethod.DEBIT_CARD)) {
      log.info("Your card must be a debit card");
      return ResponseEntity.badRequest().build();
    }
    // At this point the customer's card should have already been charged

    if (!request.termsAndConditions()) {
      log.info("You must accept the terms and conditions");
      return ResponseEntity.badRequest().build();
    }
    // As soon as the customer accepts the terms and conditions, the payment should be registered

    var tickets = order.getTickets();
    tickets.forEach(ticket -> {
      ticket.setAllocated(true);
      ticket.setReserved(false);
    });
    order.setTickets(tickets);
    order.setStatus(OrderStatus.PAID);
    orderStorage.save(order);

    var movieSession =
        movieSessionStorage
            .findById(order.getMovieSessionId())
            .orElseThrow(EntityNotFoundException::new);
    movieSession.setReservedSeatsCount(movieSession.getReservedSeatsCount() - tickets.size());
    movieSession.setAllocatedSeatsCount(movieSession.getAllocatedSeatsCount() + tickets.size());

    var payment = generatePayment(request, order);
    paymentStorage.save(payment);

    log.info("Send email to the customer");
    Context context = new Context();
    context.setVariable("tickets", tickets);
    emailService.sendEmail(request.customerEmail(), "This is your order summary", "customer-order-confirmation", context);
    log.info("Email sent successfully");

    return ResponseEntity.ok(payment);
  }

  private final EmailService emailService;

  private static Payment generatePayment(Request request, Order order) {
    var payment = new Payment();
    payment.setUserDocumentType(request.userDocumentType());
    payment.setNationalId(request.nationalId());
    payment.setCustomerFullname(request.customerFullname());
    payment.setCustomerEmail(request.customerEmail());
    payment.setTermsAndConditions(true);
    payment.setOrder(order);
    payment.setAmountCents(order.getTotalAmountCents());
    payment.setStatus(PaymentStatus.SUCCESS);
    payment.setPaymentMethod(PaymentMethod.DEBIT_CARD);
    return payment;
  }

  public record Request(
      String clientSessionId,
      UserDocumentType userDocumentType,
      String nationalId,
      String customerFullname,
      String customerEmail,
      String cardNumber,
      PaymentMethod cardType,
      String cardExpMonth,
      String cardExpYear,
      String cardVerificationValue,
      boolean termsAndConditions) {}
}
