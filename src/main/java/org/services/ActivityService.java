package org.services;

import org.dataMappers.ActivityMapper;
import org.models.activities.Activity;
import org.models.users.RegisteredUser;
import org.repositories.ActivityRepository;

import java.util.Map;

public class ActivityService  extends Service<Activity>{

    public ActivityService() {
        super(new ActivityRepository(), new ActivityMapper());
    }

    @Override
    public Activity create(Map<String, Object> objectData) throws Exception{
        Activity newActivity = super.create(objectData);
        if(newActivity != null)
            eventManager.notify("Nuovo Punto di interesse in attesa di validazione");
        return newActivity;
    }


}
