package org.services;

import org.models.activities.Activity;
import org.repositories.Repository;

import java.util.Map;

public class ActivityService  extends Service<Activity>{

    public ActivityService(Repository repository) {
        super(repository);
    }

}
