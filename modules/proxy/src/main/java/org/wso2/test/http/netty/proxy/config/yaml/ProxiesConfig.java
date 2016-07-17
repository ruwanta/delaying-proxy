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

package org.wso2.test.http.netty.proxy.config.yaml;

import java.util.List;

/**
 * Collection of Proxies
 */
public class ProxiesConfig {
    private String name;
    private RestApiConfig restApi;
    private List<Proxy> proxies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestApiConfig getRestApi() {
        return restApi;
    }

    public void setRestApi(RestApiConfig restApi) {
        this.restApi = restApi;
    }

    public List<Proxy> getProxies() {
        return proxies;
    }

    public void setProxies(List<Proxy> proxies) {
        this.proxies = proxies;
    }
}
