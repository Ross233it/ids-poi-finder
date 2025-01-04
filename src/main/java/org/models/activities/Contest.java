package org.models.activities;

import org.models.poi.IPoi;
import org.models.users.RegisteredUser;

import java.util.List;

public class Contest extends Activity{

    private String beginDate;

    private String endDate;

    private List<String> rules;

    private List<IPoi> poiList;

    public Contest(String name, String description, RegisteredUser author) {
        super(name, description, author);
    }

    @Override
    public String toString() { return ""; }

    public void setBeginDate(String beginDate) { this.beginDate = beginDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public void setPoiList(List<IPoi> poiList) { this.poiList = poiList; }

    /** Getters **/
    @Override
    public Object[] getData() {
        return new Object[] {
                getName(),
                getDescription(),
                getStatus(),
                getAuthor(),
                getValidator(),
                getBeginDate(),
                getEndDate(),
                getRules(),
                getPoiList()
        };
    }

    public String getBeginDate() { return beginDate; }

    public String getEndDate() { return endDate; }

    public List<String> getRules() { return rules; }

    public List<IPoi> getPoiList() { return poiList; }


    @Override
    public void addPoi(IPoi poi) { poiList.add(poi); }

    @Override
    public void removePoi(IPoi poi) { poiList.add(poi); }

    public void addRule(String rule) { rules.add(rule); }

    public void removeRule(String rule) { rules.remove(rule); }
}
