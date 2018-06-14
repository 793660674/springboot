package com.util;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
    private static final Charset UTF_8 = Charset.forName("utf-8");  
	private static RedisUtils single=null;
    
	//Redis服务器IP
//    private static String ADDR = "180.153.69.69";
//    private static String ADDR = "192.168.0.164";
    private String ADDR = "192.168.1.55";
    
    //Redis的端口号
    private int PORT = 6388;
    
    //访问密码,若你的redis服务器没有设置密码，就不需要用密码去连接
    private String AUTH = "-xueduoduo-5*6^7!8=";
    
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_TOTAL = 600;
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 50;
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
    
    // 最大延迟时间
    private static int TIMEOUT = 10000;
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    
    private static JedisPool jedisPool = null;
    
    /**
     * 初始化Redis连接池
     */
//    static {
//        try {
//            JedisPoolConfig config = new JedisPoolConfig();
//            config.setMaxTotal(MAX_TOTAL);
//            config.setMaxIdle(MAX_IDLE);
//            config.setMaxWaitMillis(MAX_WAIT);
//            config.setTestOnBorrow(TEST_ON_BORROW);
//            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    /**
     * 
     * @param addr
     * @param port
     * @param authPass
     */
    public RedisUtils(String addr,int port,String authPass){
    	this.ADDR = addr;
    	this.PORT = port;
    	this.AUTH = authPass;
        
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //静态工厂方法   
    public static RedisUtils getInstance(String addr,int port,String authPass) {  
         if (single == null) {    
             single = new RedisUtils(addr, port, authPass);  
         }   
        return single;  
    }  
    
    /**
     * 获取Jedis实例
     * @return
     */
    private synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis jedis = jedisPool.getResource();
                return jedis;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 释放jedis资源
     * @param jedis
     */
    @SuppressWarnings("deprecation")
    private static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
    
    /** 
     * 向redis新增字符串键值对 
     * @param key 
     * @param value 
     */  
    public boolean setString(String key, String value) {  
        if(null == key || value == null ) {  
            return false;  
        }  
          
        return setBytes(key.getBytes(UTF_8), value.getBytes(UTF_8));  
    }  
      
    /** 
     * 向Redis中储存键值对的byte数组，最长不能超过1GB的字节 
     * @param key 键 
     * @param value 值 
     * @return 
     */  
    private boolean setBytes(byte[] key, byte[] value) {  
        if(null == key || null == value) {  
            return false;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        String statusCode = jedis.set(key, value);  
        System.out.println("状态码:(" + statusCode + ")");  
        RedisUtils.returnResource(jedis);  
        return true;  
    }  
      
    /** 
     * 获取String类型的值 
     * @param key 键的值 
     * @return 
     */  
    public String getString(String key) {  
        if(null == key) {  
            return null;  
        }  
          
        byte[] val = getBytes(key.getBytes(UTF_8));  
        if(val == null) {  
            return null;  
        }  
        return new String(val, UTF_8);  
    }  
      
    /** 
     * 获取Redis中的缓存值 
     * @param key 
     * @return 
     */  
    private byte[] getBytes(byte[] key) {  
        if(null == key) {  
            return null;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        byte[] val = jedis.get(key);  
        RedisUtils.returnResource(jedis);  
        return val;  
    }  
      
    /** 
     * 删除某个键，如果键被删除，再次请求相同键时，返回null 
     * @param key 
     */  
    private boolean del(byte[] key) {  
        if(null == key) {  
            return true;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        jedis.del(key);  
        return true;  
    }  
      
    /** 
     * 操作字符串类型(String),删除键 
     * @param key 
     * @return 
     */  
    public boolean delString(String key) {  
        if(null == key) {  
            return true;  
        }  
          
        byte[] k = key.getBytes(UTF_8);  
        return del(k);  
    }  
      
    /** 
     * 批量插入缓存:<br> 
     * key,value,key,value<br> 
     * 例如<br> 
     * name，johnny,age,12<br> 
     * 则会新增name=johnny,age=12的缓存，如果在缓存中已经存在相同的缓存，则会立即更新。 
     * @param keyValues 
     * @return 
     */  
    public boolean fetchSet(String ... keyValues) {  
        if(keyValues == null) {  
            return false;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        jedis.mset(keyValues);  
        RedisUtils.returnResource(jedis);  
        return true;  
    }  
      
    /** 
     * 插入一个简单类型的Map 
     * @param key 
     * @param map 
     */  
    public void addMap(String key, Map<String, String> map) {  
        if(null == key || null == map) {  
            return;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        jedis.hmset(key, map);  
        RedisUtils.returnResource(jedis);  
    }  
      
    public void addMapVal(String key, String field, String value) {  
        if(null == key || field == null || null == value) {  
            return;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        jedis.hsetnx(key, field, value);  
        RedisUtils.returnResource(jedis);  
    }  
      
    public void addMapVal(byte[] key, byte[] field, byte[] value) {  
        if(null == key || field == null || null == value) {  
            return;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        jedis.hsetnx(key, field, value);  
        RedisUtils.returnResource(jedis);  
    }  
      
    /** 
     * 向Redis中插入一个Map的值 
     * @param key 
     * @param mapByte 
     */  
    public void addMap(byte[] key, Map<byte[], byte[]> mapByte) {  
        if(null == key || null == mapByte) {  
            return;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        //总是会返回OK,并不会执行失败  
        String status = jedis.hmset(key, mapByte);  
        System.out.println("执行状态:" + status);  
        RedisUtils.returnResource(jedis);  
    }  
      
    /** 
     * 获取Map中的值，只能够 
     * @param key 
     * @return 
     */  
    public List<String> getMapVal(String key, String ... fields) {  
        if(null == key) {  
            return null;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
          
        List<String> rtnList = null;  
        if(null == fields || fields.length == 0) {  
            rtnList = jedis.hvals(key);  
        } else {  
            rtnList = jedis.hmget(key, fields);  
        }  
          
        RedisUtils.returnResource(jedis);  
        return rtnList;  
    }  
      
    /** 
     * 获取Map中的值 
     * @param key 
     * @param fields 
     * @return 
     */  
    public List<byte[]> getMapVal(byte[] key, byte[] ... fields) {  
        if(null == key) {  
            return null;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
          
        if(!jedis.exists(key)) {  
            return null;  
        }  
        List<byte[]> rtnList = null;  
        if(null == fields || fields.length == 0) {  
            rtnList = jedis.hvals(key);  
        } else {  
            rtnList = jedis.hmget(key, fields);  
        }  
          
        return rtnList;  
    }  
      
    /** 
     * 向Redis中添加set集合 
     * @param key 
     * @param values 
     */  
    public void addSet(String key, String ... values) {  
        if(null == key || values == null) {  
            return;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        jedis.sadd(key, values);  
    }  
      
    public void delSetVal(String key, String ... fields) {  
        if(null == key) {  
            return;  
        }  
          
        if(fields == null || fields.length == 0) {  
            del(key.getBytes(UTF_8));  
            return;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        jedis.srem(key, fields);  
        RedisUtils.returnResource(jedis);  
    }  
      
    public void addSetBytes(byte[] key, byte[]...values) {  
        if(null == key || values == null) {  
            return;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        jedis.sadd(key, values);  
        RedisUtils.returnResource(jedis);  
    }  
      
    public void delSetVal(byte[] key, byte[]...values) {  
        if(null == key) {  
            return;  
        }  
        if(values == null || values.length == 0) {  
            del(key);  
            return;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        jedis.srem(key, values);  
        RedisUtils.returnResource(jedis);  
    }  
      
    /** 
     * 获取所有的值 
     * @param key 
     */  
    public Set<byte[]> getSetVals(byte[] key) {  
        if(null == key) {  
            return null;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        Set<byte[]> rtnList = jedis.smembers(key);  
        return rtnList;  
    }  
      
    public Set<String> getSetVals(String key) {  
        if(null == key) {  
            return null;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        Set<String> rtnSet = jedis.smembers(key);  
        RedisUtils.returnResource(jedis);  
        return rtnSet;  
    }  
      
    /** 
     * 判断是否Set集合中包含元素 
     * @param key 
     * @param field 
     * @return 
     */  
    public boolean isSetContain(String key, String field) {  
        if(null == key || field == null) {  
            return false;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        boolean isContain = jedis.sismember(key, field);  
        RedisUtils.returnResource(jedis);  
        return isContain;  
    }  
      
    public boolean isSetContain(byte[] key, byte[] field) {  
        if(null == key || field == null) {  
            return false;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        boolean isSuccess = jedis.sismember(key, field);  
        RedisUtils.returnResource(jedis);  
        return isSuccess;  
    }  
      
    /** 
     * 返回Set集合中的元素个数 
     * @param key 
     * @return 
     */  
    public Long getSetLength(String key) {  
        if(null == key) {  
            return 0L;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        Long length = jedis.scard(key);  
        return length;  
    }  
      
    public Long getSetLength(byte[] key) {  
        if(null == key) {  
            return 0L;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        Long length = jedis.scard(key);  
        RedisUtils.returnResource(jedis);  
        return length;  
    }  
      
    /** 
     * 向list集合中添加元素 
     * @param key 
     * @param values 
     */  
    public void addList(String key, String ...values) {  
        if(null == key || values == null) {  
            return;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        jedis.rpush(key, values);  
        RedisUtils.returnResource(jedis);  
    }  
      
    /** 
     * 向list集合中添加元素 
     * @param key 
     * @param values 
     */  
    public void addList(byte[] key, byte[] ...values) {  
        if(null == key || values == null) {  
            return;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        jedis.rpush(key, values);  
        RedisUtils.returnResource(jedis);  
    }  
      
    /** 
     * 获取start到end范围的值，超出list的范围，不会抛出异常 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public List<String> getListVals(String key, int start, int end) {  
        if(null == key) {  
            return null;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        List<String> rtnList = jedis.lrange(key, start, end);  
        RedisUtils.returnResource(jedis);  
        return rtnList;  
    }  
      
    /** 
     * 获取start到end范围的值，超出list的范围，不会抛出异常 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public List<byte[]> getListVals(byte[] key, int start, int end) {  
        if(null == key) {  
            return null;  
        }  
          
        Jedis jedis = RedisUtils.getJedis();  
        List<byte[]> rtnList = jedis.lrange(key, start, end);  
        RedisUtils.returnResource(jedis);  
        return rtnList;  
    }  
      
    public List<String> getListAll(String key) {  
        if(null == key) {  
            return null;  
        }  
        return getListVals(key, 0, -1);  
    }  
      
    public List<byte[]> getListAll(byte[] key) {  
        if(null == key) {  
            return null;  
        }  
        return getListVals(key, 0, -1);  
    }  
      
    public String popList(String key) {  
        if(null == key) {  
            return null;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        return jedis.lpop(key);  
    }  
    public byte[] popList(byte[] key) {  
        if(null == key) {  
            return null;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        return jedis.lpop(key);  
    }  
}
