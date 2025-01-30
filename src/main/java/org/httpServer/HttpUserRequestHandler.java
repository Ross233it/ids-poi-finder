package org.httpServer;

import org.controllers.RegisteredUserController;

import java.io.IOException;

public class HttpUserRequestHandler extends  HttpRequestHandler<RegisteredUserController>{


    public HttpUserRequestHandler(RegisteredUserController controller) {
        super(controller);
    }

    /**
     * Gestisce i comportamenti attivati dalle chiamate su specifiche rotte http
     * @throws IOException
     */
    @Override
    protected void handleGetCalls() throws IOException {
        int id = HttpUtilities.getQueryId(this.requestPath);
        if (this.requestPath.equals("/api/user/set-role")
                && this.currentUser.hasRole("platformAdmin")) {
            try {
                this.controller.setRole();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (id > 0)
            this.controller.show(id);
        else
            this.controller.index();
    }

    @Override
    protected void handlePostCalls()throws IOException{
        if(this.requestPath.equals("/api/user/login")) {
            try {
               this.controller.login();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if(this.requestPath.equals("/api/user/logout")) {
            try {
               this.controller.logout();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else
            this.controller.create();
    }

    @Override
    protected void handlePatchCalls() throws IOException{
        if(this.requestPath.equals("/api/user/set-role")) {
            try {
                this.controller.setRole();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else
            this.controller.update();
    }

}
