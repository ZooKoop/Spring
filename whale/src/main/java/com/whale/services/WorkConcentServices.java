package com.whale.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whale.model.Work;
import com.whale.model.WorkConcent;
import com.whale.repostitory.WorkConcentRepostitory;
@Service
public class WorkConcentServices {
	@Autowired
	private WorkConcentRepostitory workConcentRepostitory;

	public WorkConcent save(WorkConcent workConcent) {
		return workConcentRepostitory.save(workConcent);
	}

	public WorkConcent findById(String id) {
		return workConcentRepostitory.findById(id).get();
	}
	

}
