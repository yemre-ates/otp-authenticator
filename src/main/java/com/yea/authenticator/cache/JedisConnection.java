package com.yea.authenticator.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yea.authenticator.config.dto.JedisConfigProviderDto;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

@EnableScheduling
@Service
public class JedisConnection {

	private Jedis jedis;

	private static final Logger logger = LogManager.getLogger(JedisConnection.class);
	
	@Autowired
	private JedisConfigProviderDto jedisProviderDto;

	@PostConstruct
	private void connectRedis() {
		
		try {
			jedis = new Jedis(jedisProviderDto.getUrl(), jedisProviderDto.getPort());
			if(!jedisProviderDto.getPass().isEmpty()) {
				jedis.auth(jedisProviderDto.getPass());
			}
			logger.info("REDIS connection is completed succesfully.");
		} catch (JedisException | ClassCastException e) {
			logger.warn("REDIS connection has been FAILED ! ",e);
		}
	
	}
	public void pushJedis(String key, String[]... param) {
		synchronized (this) {
			Map<String, String> value = new HashMap<String, String>();

			for (String[] ac : param) {
				value.put(ac[0], ac[1]);
			}
			jedis.hmset(key, value);
		}
	}

	public Map<String, String> getFromJedis(String key) {
		
		try {
			synchronized (this) {
				return jedis.hgetAll(key);
			}
		} catch (JedisException | ClassCastException e) {
			logger.warn("ERROR getting from redis. Removing current redis instance and creating new redis instance: ",e);
			safeClose();
			connectRedis();
			return null;
		}
	}
	
	public void removeFromJedis(String key) {
		synchronized (this) {
			jedis.del(key);
		}
	}

	public void setExpire(String key, Integer seconds) {
		synchronized (this) {
			jedis.expire(key, seconds);
		}
	}

	@Scheduled(fixedRate = 60000)
	public void ping() {
		try {
			synchronized (this) {
				logger.info("redis heartbeat gum gum -> PING:" + jedis.ping());
			}
		} catch (JedisException  | ClassCastException e) {
			logger.warn("ERROR ON PING REDIS, Removing current redis instance and creating new redis instance: ",e);
			safeClose();
			connectRedis();
		}

	}
	
	private void safeClose() {
		if(jedis != null) {
			try {
				jedis.close();
			} catch (Exception e) {
				logger.warn("SafeClose REDIS!", e);
			}
		}
	}

}
