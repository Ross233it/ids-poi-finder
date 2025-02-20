package org.poifinder.models.JavaFirstVersion.activities;

import org.poifinder.models.JavaFirstVersion.Builder;

public class ActivityBuilder extends Builder<Activity> {


    public Activity build(){
            return new Activity(this);
    };
}
