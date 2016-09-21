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

package org.wso2.test.ruwana.proxy.delay.app;

import org.wso2.msf4j.MicroservicesRunner;
import org.wso2.test.http.netty.proxy.DelayingProxy;
import org.wso2.test.http.netty.proxy.config.ProxyConfig;
import org.wso2.test.http.netty.proxy.config.yaml.RestApiConfig;
import org.wso2.test.ruwana.proxy.delay.rest.ProxyConfigureService;
import org.wso2.test.ruwana.proxy.delay.rest.ResourceServerService;

import java.io.File;

public class Application {

    public static void main(String[] args) {

        String fileName = "proxy-conf.yaml";
        if (args.length > 1) {
            fileName = args[0];
        }

        ProxyConfig proxyConfig = new ProxyConfig();
        proxyConfig.load(new File(fileName));

        DelayingProxy delayingProxy = new DelayingProxy();
        delayingProxy.initialize(proxyConfig);

        ProxyConfigureService proxyConfigureService = new ProxyConfigureService(delayingProxy.getProxyConfigurator());
        ResourceServerService resourceServerService = new ResourceServerService();

        RestApiConfig restApiConfig = proxyConfig.getRestApiConfig();
        int port = restApiConfig.getListenPort();
        if (port == 0) {
            port = 8080;
        }
        MicroservicesRunner microservicesRunner = new MicroservicesRunner(port);
        microservicesRunner.deploy(proxyConfigureService, resourceServerService).start();
    }
}
