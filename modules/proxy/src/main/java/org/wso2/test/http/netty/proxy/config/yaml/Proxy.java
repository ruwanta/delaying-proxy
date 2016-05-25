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

import org.wso2.test.http.netty.proxy.config.ProxyTypes;

public class Proxy {
    private String name;
    private boolean enable;
    private ProxyTypes type;
    private Addr in;
    private Addr out;
    private Delay delay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public ProxyTypes getType() {
        return type;
    }

    public void setType(ProxyTypes type) {
        this.type = type;
    }

    public Addr getIn() {
        return in;
    }

    public void setIn(Addr in) {
        this.in = in;
    }

    public Addr getOut() {
        return out;
    }

    public void setOut(Addr out) {
        this.out = out;
    }

    public Delay getDelay() {
        return delay;
    }

    public void setDelay(Delay delay) {
        this.delay = delay;
    }
}
