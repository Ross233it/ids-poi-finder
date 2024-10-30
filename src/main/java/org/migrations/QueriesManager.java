package org.migrations;

import java.util.ArrayList;
import java.util.List;

public class QueriesManager {

    protected final List<String> queries;

    public QueriesManager() {
        this.queries = new ArrayList<String>();
        this.buildQueriesList();
    }

    public List<String> getQueries() {
        return this.queries;
    }

    protected void buildQueriesList(){
        this.queries.add("");
    }
}
