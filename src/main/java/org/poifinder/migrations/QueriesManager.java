package org.poifinder.migrations;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
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
