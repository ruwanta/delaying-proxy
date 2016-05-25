/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.test.ruwana.proxy.delay.rest;

import org.wso2.test.ruwana.proxy.delay.api.Configurator;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/configure/proxy")
public class ProxyConfigureService {

    private Configurator configurator;

    public ProxyConfigureService(Configurator configurator) {
        this.configurator = configurator;
    }

    @PUT
    @Path("/{id}/enabled/{isEnabled}")
    public void setEnable(@PathParam("id") String id, @PathParam("isEnabled") boolean isEnabled) {

    }

    @PUT
    @Path("/{id}/delay/{match}/min/{value}")
    public void setMinDelay(@PathParam("id") String id, @PathParam("match") String match,
            @PathParam("value") int value) {
         configurator.setMinDelay(id, match, value);
    }

    @PUT
    @Path("/{id}/delay/{match}/max/{value}")
    public void setMaxDelay(@PathParam("id") String id, @PathParam("match") String match,
            @PathParam("value") int value) {
        configurator.setMaxDelay(id, match, value);
    }

    @PUT
    @Path("/{id}/delay/{match}/average/{value}")
    public void setAverageDelay(@PathParam("id") String id, @PathParam("match") String match,
            @PathParam("value") int value) {
        configurator.setAverageDelay(id, match, value);
    }
}
