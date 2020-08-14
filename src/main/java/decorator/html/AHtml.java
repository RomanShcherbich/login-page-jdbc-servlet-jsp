package decorator.html;

import lombok.*;

import javax.xml.bind.annotation.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class AHtml {

    @XmlValue
    private String a;

    @XmlAttribute
    private String href;

    public void buildEditLink() {

    }

}
