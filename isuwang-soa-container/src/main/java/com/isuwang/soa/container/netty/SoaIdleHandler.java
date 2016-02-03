package com.isuwang.soa.container.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tangliu on 2016/1/14.
 */
public class SoaIdleHandler extends ChannelHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoaIdleHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;

            if (e.state() == IdleState.READER_IDLE) {
                ctx.close();
                LOGGER.info("读超时，关闭连接");

            } else if (e.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(ctx.alloc().buffer(1).writeInt(0));
                LOGGER.info("写超时，发送心跳包");

            } else if (e.state() == IdleState.ALL_IDLE) {
                LOGGER.info("读写都超时，发送心跳包");
            }
        }

    }
}
