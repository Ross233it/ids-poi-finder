package org.poifinder.models;

import org.poifinder.models.users.RegisteredUser;

public interface IBuilder<D extends Content> {

    public D build();

    public IBuilder<D> author(RegisteredUser author);

    public IBuilder<D> approver(RegisteredUser approver);

    public IBuilder<D> status(String status);
}
