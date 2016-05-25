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

public class ProxyConfigEntry {
    private int inboundPort;
    private String outboundHost;
    private int outboundPort;
    private long minDelay = 0;
    private long maxDelay = 100;
    private long averageDelay = 50;
    private ProxyTypes proxyType = ProxyTypes.TCP;

    public int getInboundPort() {
        return inboundPort;
    }

    public void setInboundPort(int inboundPort) {
        this.inboundPort = inboundPort;
    }

    public String getOutboundHost() {
        return outboundHost;
    }

    public void setOutboundHost(String outboundHost) {
        this.outboundHost = outboundHost;
    }

    public int getOutboundPort() {
        return outboundPort;
    }

    public void setOutboundPort(int outboundPort) {
        this.outboundPort = outboundPort;
    }

    public long getMinDelay() {
        return minDelay;
    }

    public void setMinDelay(long minDelay) {
        this.minDelay = minDelay;
    }

    public long getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(long maxDelay) {
        this.maxDelay = maxDelay;
    }

    public long getAverageDelay() {
        return averageDelay;
    }

    public void setAverageDelay(long averageDelay) {
        this.averageDelay = averageDelay;
    }

    public ProxyTypes getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyTypes proxyType) {
        this.proxyType = proxyType;
    }
}
