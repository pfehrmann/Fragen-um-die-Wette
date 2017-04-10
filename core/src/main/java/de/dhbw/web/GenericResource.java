package de.dhbw.web;

import org.restlet.resource.ServerResource;

/**
 * Created by phili on 10.04.2017.
 */
public class GenericResource extends ServerResource {
    protected long getIdFromParameter(String parameterName) {
        Object idObject = getRequest().getAttributes().get(parameterName);
        if(null == idObject) {
            throw new RuntimeException("Parameter " + parameterName + " is not supplied or null.");
        }
        return Long.parseLong(idObject.toString());
    }
}
