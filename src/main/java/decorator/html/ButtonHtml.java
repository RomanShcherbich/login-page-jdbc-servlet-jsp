package decorator.html;

import lombok.*;

import javax.xml.bind.annotation.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ButtonHtml {

    public static final String BUTTON_ON_CLICK_ACTION = "window.location.href='%s';";

    @XmlValue
    private String button;

    @XmlAttribute
    private String onclick;

    @XmlAttribute
    private String type;


}
