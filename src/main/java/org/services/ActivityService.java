package org.services;

import org.dataMappers.ActivityMapper;
import org.models.activities.Activity;
import org.repositories.ActivityRepository;
import org.repositories.Repository;

import java.util.Map;

public class ActivityService  extends Service<Activity>{

    public ActivityService() {
        super(new ActivityRepository(), new ActivityMapper());
    }



    /**
     * Crea una nuova attività partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
//    @Override
//    protected Activity buildEntity(Map<String, Object> objectData)throws Exception{
//        Activity activity = new Activity(
//                (String) objectData.get("name"),
//                (String) objectData.get("description"),
//                (String) objectData.get("type")
//        );
//
//        return activity;
//    }
}
