package com.java456.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java456.dao.ConfigDao;
import com.java456.entity.Config;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	
	@Resource
	private ConfigDao configDao;
	
	@Override
	public Config findById(Integer id) {
		return configDao.findId(id);
	}

}
