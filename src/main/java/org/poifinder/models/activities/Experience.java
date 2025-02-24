package org.poifinder.models.activities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.municipalities.Municipality;


/**
 * Classe Experience che estende Activity
 * Un'esperienza è una raccolta di punti di interesse senza un ordine specifico.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("experience")
public class Experience extends Activity {

    @Column(name = "begin_date")
    @JsonView(Views.Public.class)
    private String beginDate;

    @Column(name = "end_date")
    @JsonView(Views.Public.class)
    private String endDate;

    public Experience(String name,
                      String description,
                      String beginDate,
                      String endDate,
                      Municipality municipality){
        super(name, description, "experience", municipality);
        this.beginDate = beginDate;
        this.endDate   = endDate;
    }

    public Experience() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("{\"begin_date\": \"").append(getBeginDate()).append("\"} ");
        sb.append("{\"end_date\": \"").append(getEndDate()).append("\"} ");
        return sb.toString();
    }
}
