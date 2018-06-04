package com.github.binarywang.demo.wechat.service;

import com.github.binarywang.demo.wechat.config.RedisProperies;
import com.github.binarywang.demo.wechat.config.WechatOpenProperties;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Service
@EnableConfigurationProperties({WechatOpenProperties.class, RedisProperies.class})
public class WxOpenServiceDemo extends WxOpenServiceImpl {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WechatOpenProperties wechatOpenProperties;
    @Autowired
    private RedisProperies redisProperies;
    private static JedisPool pool;
    private WxOpenMessageRouter wxOpenMessageRouter;

    @PostConstruct
    public void init() {
    	System.out.println("----------------------------------开始自动注入redis-----------------------");
        WxOpenInRedisConfigStorage inRedisConfigStorage = new WxOpenInRedisConfigStorage(getJedisPool());
        inRedisConfigStorage.setComponentAppId(wechatOpenProperties.getComponentAppId());
        inRedisConfigStorage.setComponentAppSecret(wechatOpenProperties.getComponentSecret());
        inRedisConfigStorage.setComponentToken(wechatOpenProperties.getComponentToken());
        inRedisConfigStorage.setComponentAesKey(wechatOpenProperties.getComponentAesKey());
        setWxOpenConfigStorage(inRedisConfigStorage);
        wxOpenMessageRouter = new WxOpenMessageRouter(this);
        wxOpenMessageRouter.rule().handler(new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
                logger.info("\n接收到 {} 公众号请求消息，内容：{}", wxMpService.getWxMpConfigStorage().getAppId(), wxMpXmlMessage);
                return null;
            }
        }).next();
    }
    public WxOpenMessageRouter getWxOpenMessageRouter(){
        return wxOpenMessageRouter;
    }

    private JedisPool getJedisPool() {
        if (pool == null) {
            synchronized (WxOpenServiceDemo.class) {
                if (pool == null) {
                    pool = new JedisPool(redisProperies, redisProperies.getHost(),
                            redisProperies.getPort(), redisProperies.getConnectionTimeout(),
                            redisProperies.getSoTimeout(), redisProperies.getPassword(),
                            redisProperies.getDatabase(), redisProperies.getClientName(),
                            redisProperies.isSsl(), redisProperies.getSslSocketFactory(),
                            redisProperies.getSslParameters(), redisProperies.getHostnameVerifier());
                }
            }
        }
        System.out.println("----------------------------------开始自动注入redis-----------------------");
        System.out.println(pool);
        System.out.println(pool==null);
        System.out.println("----------------------------------开始自动注入redis-----------------------");
        //System.exit(0);
        return pool;
    }
    
    public JedisPool getJedisPools() {
        if (pool == null) {
            synchronized (WxOpenServiceDemo.class) {
                if (pool == null) {
                    pool = new JedisPool(redisProperies, redisProperies.getHost(),
                            redisProperies.getPort(), redisProperies.getConnectionTimeout(),
                            redisProperies.getSoTimeout(), redisProperies.getPassword(),
                            redisProperies.getDatabase(), redisProperies.getClientName(),
                            redisProperies.isSsl(), redisProperies.getSslSocketFactory(),
                            redisProperies.getSslParameters(), redisProperies.getHostnameVerifier());
                }
            }
        }
        System.out.println("----------------------------------开始自动注入redis-----------------------");
        System.out.println(pool);
        System.out.println(pool==null);
        System.out.println("----------------------------------开始自动注入redis-----------------------");
        //System.exit(0);
        return pool;
    }
}
