package io.warender.skycinema.card_center;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
@Table(name = "card_center")
public final class CardCenter {

  @Id
  @UuidGenerator
  private UUID id;

  @Column(name = "bin_number")
  private String binNumber;

  @Column(name = "issuing_bank")
  private String issuingBank;

  @Column(name = "card_brand")
  private String cardBrand;

  @Column(name = "card_type")
  private String cardType;

  @Column(name = "iso_country_name")
  private String isoCountryName;
}
