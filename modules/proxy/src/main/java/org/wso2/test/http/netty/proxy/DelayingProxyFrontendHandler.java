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

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.wso2.test.http.netty.proxy.config.ProxyConfigEntry;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayingProxyFrontendHandler extends ChannelInboundHandlerAdapter implements Observer {

    private ScheduledExecutorService scheduledExecutorService;
    private final String remoteHost;
    private final int remotePort;
    private long minDelay;
    private long avDelay;
    private long maxDelay;
    private long sdDelay; //Standard deviation of delay

    private volatile Channel outboundChannel;

    private ProxyConfigEntry proxyConfigEntry;
    private Random random = new Random();

    public DelayingProxyFrontendHandler(ProxyConfigEntry proxyConfigEntry,
            ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.remoteHost = proxyConfigEntry.getOutboundHost();
        this.remotePort = proxyConfigEntry.getOutboundPort();
        recalculateDelays(proxyConfigEntry);

        proxyConfigEntry.addObserver(this);
        this.proxyConfigEntry = proxyConfigEntry;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        final Channel inboundChannel = ctx.channel();

        // Start the connection attempt.
        Bootstrap b = new Bootstrap();
        b.group(inboundChannel.eventLoop())
                .channel(ctx.channel().getClass())
                .handler(new DelayingProxyBackendHandler(inboundChannel))
                .option(ChannelOption.AUTO_READ, false);
        ChannelFuture f = b.connect(remoteHost, remotePort);
        outboundChannel = f.channel();
        f.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    // connection complete start to read first data
                    inboundChannel.read();
                } else {
                    // Close the connection if the connection attempt has failed.
                    inboundChannel.close();
                }
            }
        });
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        scheduledExecutorService.schedule(new DelayingProcessor(ctx, msg), getNextDelay(), TimeUnit.MILLISECONDS);
    }

    private long getNextDelay() {
        double delay = random.nextGaussian() * sdDelay +avDelay;
        if(delay > maxDelay) {
            delay = maxDelay;
        }
        return (long)delay;
    }

    private void doProcess(final ChannelHandlerContext ctx, Object msg) {
        if (outboundChannel.isActive()) {
            outboundChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) {
                    if (future.isSuccess()) {
                        // was able to flush out data, start to read the next chunk
                        ctx.channel().read();
                    } else {
                        future.channel().close();
                    }
                }
            });
        }
    }

    private void recalculateDelays(ProxyConfigEntry proxyConfigEntry) {

        this.minDelay = proxyConfigEntry.getMinDelay();
        this.maxDelay = proxyConfigEntry.getMaxDelay();
        this.avDelay = proxyConfigEntry.getAverageDelay();

        sdDelay = (avDelay - minDelay) /3;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ProxyConfigEntry) {
            ProxyConfigEntry proxyConfigEntry = (ProxyConfigEntry)o;
            recalculateDelays(proxyConfigEntry);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (outboundChannel != null) {
            closeOnFlush(outboundChannel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        proxyConfigEntry.deleteObserver(this);
    }

    /**
     * Closes the specified channel after all queued write requests are flushed.
     */
    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    protected class DelayingProcessor implements Runnable {
        private ChannelHandlerContext ctx;
        private Object msg;

        public DelayingProcessor(ChannelHandlerContext ctx, Object msg) {
            this.ctx = ctx;
            this.msg = msg;
        }

        public void run() {
            doProcess(ctx, msg);
        }
    }
}