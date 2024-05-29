package com.ssafit.model.dao;

import java.util.List;

import com.ssafit.model.dto.Point;

public interface PointDao {

	void trackPoint(Point point);

	int getPointsSumByUserId(int userId);

	List<Point> getPointDescByUserId(int userId);
}