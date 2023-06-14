package pl.zajavka.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    private Long id;
    private Customer customer;
    private Product product;
    private Integer quantity;
    private OffsetDateTime dateTime;
}