package org.poifinder.models.activities;

import jakarta.persistence.*;


/**
 * Classe Experience che estende Activity
 * Un'esperienza è una raccolta di punti di interesse senza un ordine specifico.
 */
@Entity
@DiscriminatorValue("experience")
public class Experience extends Activity {

    @Column(name = "begin_date")
    private String beginDate;

    @Column(name = "end_date")
    private String endDate;

    public Experience(String name,
                      String description,
                      String beginDate,
                      String endDate){
        super(name, description, "experience");
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Experience() {
        super();
    }

    /** Getters **/

    public String getBeginDate() { return beginDate; }

    public String getEndDate() { return beginDate; }

    /** Setters **/

    public void setBeginDate(String date) { this.beginDate = date; }

    public void setEndDate(String date) { this.endDate = date; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("{\"begin_date\": \"").append(getBeginDate()).append("\"} ");
        sb.append("{\"end_date\": \"").append(getEndDate()).append("\"} ");
        return sb.toString();
    }
}
