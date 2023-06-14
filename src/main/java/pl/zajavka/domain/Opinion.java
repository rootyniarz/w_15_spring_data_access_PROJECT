package pl.zajavka.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Opinion {
    private Long id;
    private Customer customer;
    private Product product;
    private Byte stars;
    private String comment;
    private OffsetDateTime dateTime;
}
