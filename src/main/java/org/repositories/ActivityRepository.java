package org.repositories;

import org.models.activities.Activity;

public class ActivityRepository extends Repository<Activity> {

    public ActivityRepository(String tableName) {
        super(tableName);
    }

    @Override
    public Activity create(Activity activity, String query) throws Exception {
        query = "INSERT INTO "
                + this.tableName +
                "(name," +
                "description," +
                "type, " +
                "status, author_id, approver_id) VALUES (?, ?, ? ,? ,?, ?)";
        return super.create(activity, query);
    }
}
