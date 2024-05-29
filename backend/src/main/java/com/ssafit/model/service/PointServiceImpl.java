package com.ssafit.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafit.model.dao.PointDao;
import com.ssafit.model.dto.Point;

@Service
public class PointServiceImpl implements PointService {

	@Autowired
	private PointDao pointDao;

	@Override
	public void trackPoint(Point point) {
		pointDao.trackPoint(point);
	}

	@Override
	public int getPointsSumByUserId(int userId) {
		return pointDao.getPointsSumByUserId(userId);
	}

	@Override
	public List<Point> getPointDescByUserId(int userId) {
		return pointDao.getPointDescByUserId(userId);
	}
}
