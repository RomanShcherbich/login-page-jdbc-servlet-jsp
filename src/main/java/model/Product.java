package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String code;
    private String name;
    private Float price;

    @Override
    public String toString() {
        return "Product{" +
               "code='" + code + '\'' +
               ", name='" + name + '\'' +
               ", price=" + price +
               '}';
    }

}
