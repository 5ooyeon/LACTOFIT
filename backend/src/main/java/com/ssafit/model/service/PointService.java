package com.ssafit.model.service;

import java.util.List;

import com.ssafit.model.dto.Point;

public interface PointService {
	void trackPoint(Point point);

	int getPointsSumByUserId(int userId);

	List<Point> getPointDescByUserId(int userId);
}
