package org.poifinder.models.activities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Entity
public class Contest extends Activity{

    private String beginDate;

    private String endDate;

    @ManyToMany
    private List<Rule> rules;

    @ManyToMany
    private List<Prize> prizes;

    public Contest(String name,
                   String description,
                   String type) {
        super(name, description, type);
        this.listInit();
    }

    public Contest() {
        super();
        this.listInit();
    }

    private void listInit(){
        this.rules = new ArrayList<Rule>();
        this.prizes = new ArrayList<Prize>();
    }

    @Override
    public String toString() { return ""; }

    public void setBeginDate(String beginDate) { this.beginDate = beginDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public void setPrices(List<Prize> prizes) { this.prizes = prizes;}

    public void setRules(List<Rule> rules) { this.rules = rules;}

    /** Getters **/
    @Override
    public Object[] getData() {
        Object[] data = super.getData();

        Object[] newData = new Object[] {
                getBeginDate(),
                getEndDate(),
                getRules(),
        };
        return Stream.concat(Arrays.stream(data), Arrays.stream( newData )).toArray();
    }

    public String getBeginDate() { return beginDate; }

    public String getEndDate() { return endDate; }

    public List<Rule> getRules() { return rules; }

    public List<Prize> getPrizes() { return prizes; }

    public void addRule(Rule rule) { rules.add(rule); }

    public void removeRule(Rule rule) { rules.remove(rule); }
}
