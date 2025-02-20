package org.poifinder.models;



import org.poifinder.models.users.RegisteredUser;

/**
 *
 * Questa classe implementa i metodi comuni e concreti dei builder dei
 * vari oggetti di classe Content.
 * @param <D>
 */
public class Builder<D> implements IBuilder<Content>{
    private RegisteredUser author;
    private RegisteredUser approver;
    private String         status;

    /**
     * Costruttore, inizializza i campi comuni a tutti
     * i builder di oggetti Content
     */
    public Builder(){
        this.status = "pending";
        this.setAuthor(null);
        this.setApprover(null);
    }


    @Override
    public Content build() {
        return null;
    }

    @Override
    public Builder<D> author(RegisteredUser author) {
        this.author = author;
        return this;
    }

    @Override
    public Builder<D> approver(RegisteredUser approver) {
        this.approver = approver;
        return this;
    }

    @Override
    public Builder<D> status(String status) {
        this.status = status;
        return this;
    }

    /** SETTERS **/

    public void setStatus(String status){ this.status = status; }
    public void setAuthor(RegisteredUser author) {this.author = author;}
    public void setApprover(RegisteredUser approver){this.approver = approver;}

    /** GETTERS **/

    public String         getStatus()   { return status; }
    public RegisteredUser getAuthor()   { return author; }
    public RegisteredUser getApprover() { return approver; }
}
