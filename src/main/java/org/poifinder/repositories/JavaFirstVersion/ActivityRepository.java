package org.poifinder.repositories.JavaFirstVersion;

import org.poifinder.models.activities.Activity;
import org.poifinder.repositories.JavaFirstVersion.BaseRepository;

public class ActivityRepository extends BaseRepository<Activity> {

    public ActivityRepository() {
        super("activities");
    }

    @Override
    public Activity create(Activity activity, String query) throws Exception {
        query = "INSERT INTO "
                + this.tableName +
                "(name," +
                "description," +
                "type, " +
                "status, " +
                "author_id, " +
                "approver_id) VALUES (?, ?, ? ,? ,?, ?)";
        return super.create(activity, query);
    }
}
