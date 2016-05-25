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

package org.wso2.test.http.netty.proxy.config;

import org.wso2.test.http.netty.proxy.config.yaml.Config;
import org.wso2.test.http.netty.proxy.config.yaml.Proxy;
import org.wso2.test.http.netty.proxy.config.yaml.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProxyConfig {

    private List<ProxyConfigEntry> proxyConfigs = new ArrayList<ProxyConfigEntry>();

    public List<ProxyConfigEntry> getProxyConfigs() {
        return proxyConfigs;
    }

    public void load(File file) {

        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            org.wso2.test.http.netty.proxy.config.yaml.Reader reader = new Reader();
            Config config = reader.read(fis);
            if (config != null && config.getProxies() != null) {
                for (Proxy proxy : config.getProxies()) {
                    if (proxy.isEnable()) {

                        ProxyConfigEntry proxyConfig = readConfigEntry(proxy);
                        if (proxyConfig != null) {
                            proxyConfigs.add(proxyConfig);
                        }
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println("Error in parsing the config file: " + file.getAbsolutePath());
            ioe.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ProxyConfigEntry readConfigEntry(Proxy p) {

        ProxyConfigEntry proxyConfig = new ProxyConfigEntry();
        proxyConfig.setInboundPort(p.getIn().getPort());
        proxyConfig.setOutboundHost(p.getOut().getHost());
        proxyConfig.setOutboundPort(p.getOut().getPort());
        proxyConfig.setMaxDelay(p.getDelay().getMax());
        proxyConfig.setAverageDelay(p.getDelay().getAvg());
        proxyConfig.setMinDelay(p.getDelay().getMin());
        proxyConfig.setProxyType(p.getType());

        return proxyConfig;
    }

    private ProxyTypes translate(String match) {
        if (ProxyTypes.SQL.name().equalsIgnoreCase(match)) {
            return ProxyTypes.SQL;
        }
        return ProxyTypes.TCP;
    }

}
