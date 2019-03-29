package com.whale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.whale.model.Work;
import com.whale.repostitory.WorkRepostitory;
@Service
public class WorkServices {
	@Autowired
	private WorkRepostitory workRepostitory;
	
	public Page<Work> queryAll(Integer page,Integer size,String order) {
		Sort sort = new Sort(Sort.Direction.DESC,order);
		PageRequest pageable = PageRequest.of(page, size,sort);
		return workRepostitory.findAll(pageable);
	}
	public boolean findByTicketNumber(Integer number) {
		return workRepostitory.findByticketNumber(number)!=null ? true:false;
	}
	
	public boolean add(Work work) {
		return workRepostitory.save(work)!=null ? true:false;
	}
}
