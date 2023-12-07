package io.warender.skycinema.payments;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;
  private final QrCodeService qrCodeService;

  @Async
  public void sendEmail(String to, String subject, String templateName, Context context)
      throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper =
        new MimeMessageHelper(
            message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

    var orderId = 3854195;
    byte[] qrCodeImageBytes;

    try {
      qrCodeImageBytes = qrCodeService.generateQRCodeImage(String.valueOf(orderId), 500, 500);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    String html = templateEngine.process(templateName, context);

    helper.setFrom("no-reply@skycinema.com");
    helper.setTo(to);
    helper.setText(html, true);
    helper.setSubject(subject);

    var qrCodeImageResource = new ByteArrayResource(qrCodeImageBytes);
    helper.addInline("qrCodeImage", qrCodeImageResource, "image/png");

    mailSender.send(message);
  }
}
