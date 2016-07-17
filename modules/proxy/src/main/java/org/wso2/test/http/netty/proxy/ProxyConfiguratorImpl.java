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

package org.wso2.test.http.netty.proxy;

import org.wso2.test.http.netty.proxy.config.ProxyConfig;
import org.wso2.test.http.netty.proxy.config.ProxyConfigEntry;
import org.wso2.test.ruwana.proxy.delay.api.Configurator;
import org.wso2.test.ruwana.proxy.delay.api.ResourceNotFoundException;

public class ProxyConfiguratorImpl implements Configurator {

    private ProxyConfig proxyConfig;

    public ProxyConfiguratorImpl(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @Override
    public void setMinDelay(String id, String match, long value) {
        System.out.println("Setting min delay " + value);
        ProxyConfigEntry proxyConfigEntry = findProxyConfigEntry(id);
        if (proxyConfigEntry != null) {
            proxyConfigEntry.setMinDelay(value);
            proxyConfigEntry.notifyObservers();
        }
    }

    @Override
    public long getMinDelay(String id, String match) throws ResourceNotFoundException {
        ProxyConfigEntry proxyConfigEntry = findProxyConfigEntry(id);
        if (proxyConfigEntry == null) {
            throw new ResourceNotFoundException("The Proxy with Index:["+id+"] and Match Pattern:["+ match +"] not found.");
        }
        return proxyConfigEntry.getMinDelay();
    }

    @Override
    public void setMaxDelay(String id, String match, long value) {
        ProxyConfigEntry proxyConfigEntry = findProxyConfigEntry(id);
        if (proxyConfigEntry != null) {
            proxyConfigEntry.setMaxDelay(value);
            proxyConfigEntry.notifyObservers();
        }
    }

    @Override
    public void setAverageDelay(String id, String match, long value) {
        ProxyConfigEntry proxyConfigEntry = findProxyConfigEntry(id);
        if (proxyConfigEntry != null) {
            proxyConfigEntry.setAverageDelay(value);
            proxyConfigEntry.notifyObservers();
        }
    }

    private ProxyConfigEntry findProxyConfigEntry(String id) {
        if (id == null || id.length() <= 0) {
            return null;
        }
        for (ProxyConfigEntry proxyConfigEntry : proxyConfig.getProxyConfigs()) {
            if (id.equals("" + proxyConfigEntry.getId())) {
                return proxyConfigEntry;
            }
        }
        return null;
    }
}
