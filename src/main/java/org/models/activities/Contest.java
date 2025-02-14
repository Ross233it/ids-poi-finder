package org.models.activities;

import org.models.poi.IPoi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Contest extends Activity{

    private String beginDate;

    private String endDate;

    private List<String> rules;


    public Contest(String name, String description, String type) {
        super(name, description, type);
        this.rules = new ArrayList<String>();
    }


    @Override
    public String toString() { return ""; }

    public void setBeginDate(String beginDate) { this.beginDate = beginDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }



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

    public List<String> getRules() { return rules; }

    public void addRule(String rule) { rules.add(rule); }

    public void removeRule(String rule) { rules.remove(rule); }
}
