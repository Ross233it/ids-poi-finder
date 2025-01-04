package org.models.users;

import org.models.IModel;

public interface IUser extends IModel {

    Boolean hasRole(String role);

    String getRole();

}
