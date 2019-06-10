package com.whale.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.whale.model.WorkOpction;
import com.whale.repostitory.WorkOpctionRepostitory;
@Service
public class WorkOpctionServices {
	@Autowired
	private WorkOpctionRepostitory w;
	
	public Page<WorkOpction> queryAll(Integer page,Integer size) {
//		Sort sort = new Sort(Sort.Direction.DESC,order);
		PageRequest pageable = PageRequest.of(page, size);
		return w.findAll(pageable);
	}
	
	public boolean add(WorkOpction work) {
		return w.save(work)!=null ? true:false;
	}
	public void del(String id) {
		w.deleteById(id);
	}

	public boolean findByOpctionCode(String opctionCode) {
		return w.findByOpctionCode(opctionCode)!=null ? true:false;
	}

	public List<WorkOpction> getAll() {
		return w.findAll();
	}
}
