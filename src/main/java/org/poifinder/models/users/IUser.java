package org.poifinder.models.users;

import org.poifinder.models.IModel;

public interface IUser extends IModel {

    Boolean hasRole(String role);

    String getRole();

}
