package org.models.activities;

import org.models.Builder;

public class ActivityBuilder extends Builder<Activity> {


    public Activity build(){
            return new Activity(this);
    };
}
