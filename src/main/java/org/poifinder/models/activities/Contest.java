package org.poifinder.models.activities;

import jakarta.persistence.*;
import org.poifinder.models.municipalities.Municipality;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("contest")
public class Contest extends Activity{

    @Column(name = "begin_date")
    private String beginDate;

    @Column(name = "end_date")
    private String endDate;

    @ManyToMany
    @JoinTable(name= "contests_rules",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id"))
    private List<Rule> rules;

    @ManyToMany
    @JoinTable(name= "contests_prizes",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "prize_id"))
    private List<Prize> prizes;

    public Contest(String name, String description, Municipality municipality) {
        super(name, description, "contest", municipality);
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
    public String getBeginDate() { return beginDate; }

    public String getEndDate() { return endDate; }

    public List<Rule> getRules() { return rules; }

    public List<Prize> getPrizes() { return prizes; }

    public void addRule(Rule rule) { rules.add(rule); }

    public void addPrize(Prize prize) { prizes.add(prize); }

    public void removePrize(Prize prize) { rules.remove(prize); }

    public void removeRule(Rule rule) { rules.remove(rule); }
}
