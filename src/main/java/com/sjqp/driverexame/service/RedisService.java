package com.sjqp.driverexame.service;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.ArrayList;
import java.util.List;


public class RedisService {

	@Autowired
	private JedisPool jedisPool;

	public boolean set(String key, String value, int db) {
		boolean flag = false;
		Jedis jedis = this.jedisPool.getResource();
		try {
			jedis.select(db);
			String rs = jedis.set(key, value);
			if (rs.equals("ok")) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return flag;

	}

	public boolean setEx(String key, Integer second, String value, int db) {
		boolean flag = false;
		Jedis jedis = this.jedisPool.getResource();
		try {
			jedis.select(db);
			String rs = jedis.setex(key, second, value);
			if (rs.equals("ok")) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return flag;

	}

	public String get(String key, int db) {
		Jedis jedis = this.jedisPool.getResource();
		try {
			jedis.select(db);
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}
	
	public Long del(String key, int db) {

		Jedis jedis = this.jedisPool.getResource();
		try {
			jedis.select(db);
			return jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return new Long(0);

	}

	public Long batchDel(List<String> listKey, int db) {
		Long flag = new Long(0);
		Jedis jedis = this.jedisPool.getResource();
		try {
			jedis.select(db);
			for (String key : listKey) {
				flag = flag + jedis.del(key);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return flag;
	}

	public Boolean exists(String key, int db) {
		boolean flag = false;
		Jedis jedis = this.jedisPool.getResource();
		try {
			jedis.select(db);
			flag = jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return flag;
	}

	public Long lpush(String key, int db, String... values) {
		Jedis jedis = this.jedisPool.getResource();
		try {
			jedis.select(db);
			return jedis.lpush(key, values);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return new Long(0);
	}

	public List<String> lrange(String key, Long start, Long end, int db) {
		List<String> list = new ArrayList<String>();
		Jedis jedis = this.jedisPool.getResource();
		try {
			jedis.select(db);
			list = jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return list;
	}

	public Long inc(String key, int db) {
		Jedis jedis = this.jedisPool.getResource();
		Long res = new Long(0);
		try {
			jedis.select(db);
			res = jedis.incr(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}

		return res;
	}

	public Long dec(String key, int db) {
		Jedis jedis = this.jedisPool.getResource();
		Long res = new Long(0);
		try {
			jedis.select(db);
			res = jedis.decr(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}

		return res;
	}

	public Long ttl(String key, int db) {
		Jedis jedis = this.jedisPool.getResource();
		Long res = new Long(-1);
		try {
			jedis.select(db);
			res = jedis.ttl(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}

		return res;
	}

	public String lpop(int db,String key){
		Jedis jedis = this.jedisPool.getResource();
		try {
			jedis.select(db);
			return jedis.lpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	public boolean pipelined(String key, String value) {
		boolean flag = false;
		Jedis jedis = this.jedisPool.getResource();
		try {
			Pipeline pipeline = jedis.pipelined();
			Response<String> s= pipeline.set(key, value);
			/*if (rs.equals(Const.REDIS_SET_RETURN_OK)) {
				flag = true;
			}*/
			flag=true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return flag;
	}

}