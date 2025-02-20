package org.poifinder.models.JavaFirstVersion.users;

import org.poifinder.models.JavaFirstVersion.IModel;

public interface IUser extends IModel {

    Boolean hasRole(String role);

    String getRole();

}
