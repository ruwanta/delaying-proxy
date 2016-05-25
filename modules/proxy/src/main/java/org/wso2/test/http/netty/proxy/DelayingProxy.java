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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.wso2.test.http.netty.proxy.config.ProxyConfig;
import org.wso2.test.http.netty.proxy.config.ProxyConfigEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class DelayingProxy {
    private ProxyConfiguratorImpl proxyConfigurator;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public static void main(String[] args) throws Exception {

        String fileName = "proxy-conf.yaml";
        if (args.length > 1) {
            fileName = args[0];
        }

        ProxyConfig proxyConfig = new ProxyConfig();
        proxyConfig.load(new File(fileName));

        new DelayingProxy().initialize(proxyConfig);
    }

    public void initialize(ProxyConfig proxyConfig) {
        proxyConfigurator = new ProxyConfiguratorImpl();
        // Configure the bootstrap.
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        List<Channel> serverChannels = new ArrayList<Channel>();

        ScheduledExecutorService  scheduledExecutorService = Executors.newScheduledThreadPool(10);

        try {
            for (ProxyConfigEntry proxyConfigEntry : proxyConfig.getProxyConfigs()) {
                ServerBootstrap b = new ServerBootstrap();
                Channel c = b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new DelayingProxyInitializer(proxyConfigEntry, scheduledExecutorService))
                        .childOption(ChannelOption.AUTO_READ, false).bind(proxyConfigEntry.getInboundPort()).sync()
                        .channel();
                serverChannels.add(c);
            }

        } catch (InterruptedException e) {
            //Ignore for now
        }
    }

    public void shutDown() {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
    }

    public ProxyConfiguratorImpl getProxyConfigurator() {
        return proxyConfigurator;
    }
}
