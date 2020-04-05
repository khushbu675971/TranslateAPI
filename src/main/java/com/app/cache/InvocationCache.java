package com.app.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class InvocationCache {
	static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
	      .withCache("preConfigured", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
	                                          ResourcePoolsBuilder.heap(100)).build())
	      .build(true);
	
	static Cache<String, String> myCache
	      = cacheManager.getCache("preConfigured", String.class, String.class);
	
	public static String getCache(String key) {
		return myCache.get(key);
	}
  
	public static void setCache(String key, String value) {
		myCache.put(key, value);
	}
}