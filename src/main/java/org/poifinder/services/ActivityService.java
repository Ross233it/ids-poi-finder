package org.poifinder.services;

import org.poifinder.dataMappers.activities.ActivityListMapper;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.models.activities.Activity;
import org.poifinder.repositories.ActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivityService extends BaseService<Activity> {


    @Autowired
    public ActivityService(ActivityRepository repository,
                           ActivityListMapper mapper) {
        super(repository, mapper);
    }


    @Override
    public List<Activity> filter(Map<String, String> queryParams) throws Exception {
        return List.of();
    }

    @Override
    public Activity setStatus(Map<String, Object> data) throws Exception {
        return null;
    }

    @Override
    public DataMapper<Activity> create(DataMapper<Activity> objectData) throws Exception{
        return null;
        //        Activity newActivity = super.create(objectData);
//        if(newActivity != null)
//            eventManager.notify("Nuovo Punto di interesse in attesa di validazione", null);
//        return newActivity;
    }

    @Override
    public Activity delete(long id) throws Exception {
        return null;
    }
}
