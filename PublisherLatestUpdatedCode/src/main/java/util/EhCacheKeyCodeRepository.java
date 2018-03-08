package util;

/**
 * 
 */
import org.elasticsearch.plugin.nlpcn.executors.CSVResult;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.CacheManager;

import com.google.inject.Singleton;

import util.GlobalConfiguration;
import util.KeyCodeRepository;

/**
 * @author Sun Ning/SNDA
 * @since Sep 14, 2009
 * 
 */

public class EhCacheKeyCodeRepository implements KeyCodeRepository {

	private Cache cache;

    /**
     * 
     */
	public static EhCacheKeyCodeRepository ehcache;
	
	public static EhCacheKeyCodeRepository getInstance(){
		
		if(ehcache == null)
			return new EhCacheKeyCodeRepository();
		
		
		else
			return ehcache;
		
	}
	
	
	
	public EhCacheKeyCodeRepository() {
		init();
	}

    /**
     * initialize properties.
     */
	public void init() {
        CacheManager cm = CacheManager.getInstance();
		cache = cm.getCache("keyCodeRepository");
		cache.getCacheConfiguration().setTimeToLiveSeconds(60 * GlobalConfiguration
				.getInt("yan.expireminutes"));
		cache.getCacheConfiguration().setTimeToIdleSeconds(60 * GlobalConfiguration
				.getInt("yan.expireminutes"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sdo.captchaserver.repos.KeyCodeRepository#get(java.lang.String)
	 */
	@Override
	public CSVResult get(String key) {
		Element result =  cache.get(key);
		if(result != null){
			cache.remove(key);
			return (CSVResult)result.getValue();
		}else{
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sdo.captchaserver.repos.KeyCodeRepository#put(java.lang.String,
	 * com.sdo.captchaserver.repos.KeyCodeModel)
	 */
	@Override
	public void put(String key, CSVResult kcm) {
		Element element = new Element(key, kcm);
		System.out.println("Put in Cache");
		cache.put(element);
	}

    /**
     * get data from map.
     * @param key
     * @param removeOnGet remove key if set to ture
     * @return
     */
	@Override
	public CSVResult get(String key, boolean removeOnGet) {
		Element result =  cache.get(key);
		System.out.println("Get from Cache");
		
		if(result != null){
			if(removeOnGet){
				cache.remove(key);
			}
			return (CSVResult)result.getValue();
		}else{
			return null;
		}
	}

}