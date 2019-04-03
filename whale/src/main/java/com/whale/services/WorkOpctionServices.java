package com.whale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.whale.model.WorkOpction;
import com.whale.repostitory.WorkOpctionRepostitory;
@Service
public class WorkOpctionServices {
	@Autowired
	private WorkOpctionRepostitory w;
	
	public Page<WorkOpction> queryAll(Integer page,Integer size,String order) {
		Sort sort = new Sort(Sort.Direction.DESC,order);
		PageRequest pageable = PageRequest.of(page, size,sort);
		return w.findAll(pageable);
	}
	
	public boolean add(WorkOpction work) {
		return w.save(work)!=null ? true:false;
	}
	public void del(String id) {
		w.deleteById(id);
	}
}
