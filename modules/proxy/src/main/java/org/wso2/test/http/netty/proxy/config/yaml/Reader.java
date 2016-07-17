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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Reader {

    public ConfigFile read(InputStream inputStream) {
        ObjectMapper mapper = mapperForYAML();
        ConfigFile item = null;
        try {
            item = mapper.readValue(new InputStreamReader(inputStream), ConfigFile.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    protected ObjectMapper mapperForYAML() {
        return new ObjectMapper(new YAMLFactory());
    }

}
