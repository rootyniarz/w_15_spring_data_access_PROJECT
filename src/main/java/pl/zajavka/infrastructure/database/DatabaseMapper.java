package pl.zajavka.infrastructure.database;

import org.springframework.stereotype.Component;
import pl.zajavka.domain.Customer;
import pl.zajavka.domain.Opinion;
import pl.zajavka.domain.Product;
import pl.zajavka.domain.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
public class DatabaseMapper {

    private static final DateTimeFormatter DATABASE_DATE_FORMAT
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");

    public Map<String, ?> mapCustomer(Product product) {
        return Map.of(
                "product_code", product.getProductCode(),
                "product_name", product.getProductName(),
                "product_price", product.getProductPrice(),
                "adults_only", product.getAdultsOnly(),
                "description", product.getDescription(),
                "producer_id", product.getProducer().getId()
        );
    }

    public Map<String, ?> mapCustomer(Purchase purchase) {
        return Map.of(
                "customer_id", purchase.getCustomer().getId(),
                "product_id", purchase.getProduct().getId(),
                "quantity", purchase.getQuantity(),
                "date_time", DATABASE_DATE_FORMAT.format(purchase.getDateTime())
        );
    }

    public Map<String, ?> mapCustomer(Opinion opinion) {
        return Map.of(
                "customer_id", opinion.getCustomer().getId(),
                "product_id", opinion.getProduct().getId(),
                "stars", opinion.getStars(),
                "comment", opinion.getComment(),
                "date_time", DATABASE_DATE_FORMAT.format(opinion.getDateTime())
        );
    }

    @SuppressWarnings("unused")
    public Customer mapCustomer(ResultSet resultSet, int rowNum) throws SQLException {
        return Customer.builder()
                .id(resultSet.getLong("id"))
                .userName(resultSet.getString("user_name"))
                .email(resultSet.getString("email"))
                .name(resultSet.getString("name"))
                .surname(resultSet.getString("surname"))
                .dateOfBirth(LocalDate.parse(resultSet.getString("date_of_birth")))
                .telephoneNumber(resultSet.getString("telephone_number"))
                .build();
    }

    public Purchase mapPurchase(ResultSet resultSet, int rowNum) throws SQLException {
        return Purchase.builder()
                .id(resultSet.getLong("id"))
                .customer(Customer.builder().id(resultSet.getLong("customer_id")).build())
                .product(Product.builder().id(resultSet.getLong("product_id")).build())
                .quantity(resultSet.getInt("quantity"))
                .dateTime(OffsetDateTime.parse(resultSet.getString("date_time"), DATABASE_DATE_FORMAT)
                        .withOffsetSameInstant(ZoneOffset.UTC))
                .build();
    }

    public Opinion mapOpinion(ResultSet resultSet, int rowNum) throws SQLException {
        return Opinion.builder()

                .id(resultSet.getLong("id"))
                .customer(Customer.builder().id(resultSet.getLong("customer_id")).build())
                .product(Product.builder().id(resultSet.getLong("product_id")).build())
                .stars(resultSet.getByte("stars"))
                .comment(resultSet.getString("comment"))
                .dateTime(OffsetDateTime.parse(resultSet.getString("date_time"), DATABASE_DATE_FORMAT)
                        .withOffsetSameInstant(ZoneOffset.UTC))
                .build();
    }
}
