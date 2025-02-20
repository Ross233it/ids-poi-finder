package org.poifinder.models.activities;

import org.poifinder.models.Builder;

public class ActivityBuilder extends Builder<Activity> {


    public Activity build(){
            return new Activity(this);
    };
}
