package org.poifinder.models.activities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.municipalities.Municipality;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@DiscriminatorValue("contest")
public class Contest extends Activity{

    @Column(name = "begin_date")
    @JsonView(Views.Public.class)
    private String beginDate;

    @Column(name = "end_date")
    @JsonView(Views.Public.class)
    private String endDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name= "contests_rules",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id"))
    @JsonView(Views.Internal.class)
    private List<Rule> rules;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name= "contests_prizes",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "prize_id"))
    @JsonView(Views.Internal.class)
    private List<Prize> prizes;

    public Contest(String name,
                   String description,
                   Municipality municipality,
                   List<Rule> rules,
                   List<Prize> prizes
                   ){
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


    public void addRule(Rule rule) { rules.add(rule); }

    public void addPrize(Prize prize) { prizes.add(prize); }

    public void removePrize(Prize prize) { rules.remove(prize); }

    public void removeRule(Rule rule) { rules.remove(rule); }
}
