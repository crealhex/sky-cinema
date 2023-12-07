package io.warender.skycinema.payments;

import io.warender.skycinema.shared.ApiVersions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @GetMapping(value = ApiVersions.ONE + "/generate-qrcode/{orderId}", produces = "image/png")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable("orderId") Long orderId) {
        try {
            byte[] qrImage = qrCodeService.generateQRCodeImage(orderId.toString(), 250, 250);
            return ResponseEntity.status(HttpStatus.OK).body(qrImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
