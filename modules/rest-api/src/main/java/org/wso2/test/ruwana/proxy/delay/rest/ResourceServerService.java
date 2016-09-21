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

import com.google.common.io.Files;
import org.wso2.msf4j.internal.mime.MimeMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Generic resource server service serving a file content in give directory
 */
@Path("/mgt/app")
public class ResourceServerService {

    /**
     * Download file with streaming using a {@link File} object in the response. Streaming is automatically handled
     * by MSF4J.
     *
     * @param fileName Name of the file to be downloaded.
     * @return Response
     */
    @GET
    @Path("/{fileName}")
    public Response getFile(@PathParam("fileName") String fileName) {
        URL url = this.getClass().getResource("/webapp/"+fileName);
        if (url != null) {
            try {
                return Response.ok(url.openStream()).build();
            } catch (IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
