package org.poifinder.services;

import org.poifinder.dataMappers.ActivityMapper;
import org.poifinder.models.activities.Activity;
import org.poifinder.repositories.ActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivityService extends BaseService<Activity> {

    @Autowired
    public ActivityService(ActivityRepository repository, ActivityMapper mapper) {
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
    public Activity create(Map<String, Object> objectData) throws Exception{
        Activity newActivity = super.create(objectData);
        if(newActivity != null)
            eventManager.notify("Nuovo Punto di interesse in attesa di validazione", null);
        return newActivity;
    }

    @Override
    public Activity delete(long id) throws Exception {
        return null;
    }
}
