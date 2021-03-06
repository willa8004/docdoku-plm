/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2013 DocDoku SARL
 *
 * This file is part of DocDokuPLM.
 *
 * DocDokuPLM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DocDokuPLM is distributed in the hope that it will be useful,  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  
 * GNU Affero General Public License for more details.  
 *  
 * You should have received a copy of the GNU Affero General Public License  
 * along with DocDokuPLM.  If not, see <http://www.gnu.org/licenses/>.  
 */
package com.docdoku.server.jsf.actions;

import com.docdoku.core.common.Account;
import com.docdoku.core.common.Workspace;
import com.docdoku.core.services.AccountAlreadyExistsException;
import com.docdoku.core.services.CreationException;
import com.docdoku.core.services.IUserManagerLocal;
import java.util.HashMap;
import java.util.HashSet;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "registrationBean")
@RequestScoped
public class RegistrationBean {

    @EJB
    private IUserManagerLocal userManager;
    private String login;
    private String name;
    private String email;
    private String password;

    public RegistrationBean() {
    }

    public String register() throws AccountAlreadyExistsException, CreationException, ServletException {

        //TODO switch to a more JSF style code
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest()); 
        String language = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();

        Account account = userManager.createAccount(login, name, email, language, password);
        request.login(login, password);

        HttpSession sessionHTTP = request.getSession();
        sessionHTTP.setAttribute("account", account);
        sessionHTTP.setAttribute("administeredWorkspaces", new HashMap<String, Workspace>());
        sessionHTTP.setAttribute("regularWorkspaces", new HashSet<Workspace>());

        return "/register.xhtml";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
