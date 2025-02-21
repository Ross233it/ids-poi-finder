package org.poifinder.dataMappers.activities;

import org.poifinder.models.activities.Prize;
import org.poifinder.models.activities.Rule;

import java.util.List;

public class ContestListMapper extends ActivityListMapper{


    private String beginDate;

    private String endDate;

    private List<Rule> rules;

    private List<Prize> prizes;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }
}
