package decorator.html;

import lombok.*;

import javax.xml.bind.annotation.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "div",
        "a"
})
public class TdHtml {

    private String div;

    private AHtml a;

}
