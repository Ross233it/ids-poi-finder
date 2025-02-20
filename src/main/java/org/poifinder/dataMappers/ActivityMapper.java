package org.poifinder.dataMappers;

import org.poifinder.models.activities.Activity;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActivityMapper extends DataMapper{

    @Override
    public Object updateEntityFromMap(Object item, Map result) {
        return null;
    }

    @Override
    public Object mapDataToObject(Map result) {

        Activity activity = new Activity(
            (String) result.getOrDefault("name", null),
            (String) result.getOrDefault("description", null),
            (String) result.getOrDefault("type", null));

        if(result.containsKey("author") && result.get("author") != null){
            activity.setAuthor((RegisteredUser) result.get("author"));
        }
        return activity;
    }
}
