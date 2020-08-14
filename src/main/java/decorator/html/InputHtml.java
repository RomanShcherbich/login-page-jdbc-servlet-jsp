package decorator.html;

import lombok.*;

import javax.xml.bind.annotation.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlType(name = "", propOrder = {
        "type",
        "placeholder",
        "value",
        "name"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class InputHtml {

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String placeholder;

    @XmlAttribute
    private String value;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String required;

}
