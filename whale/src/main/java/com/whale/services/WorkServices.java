package com.whale.services;

import java.util.ArrayList;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.whale.model.Work;
import com.whale.repostitory.WorkRepostitory;
@Service
public class WorkServices {
	@Autowired
	private WorkRepostitory workRepostitory;
	
	public Page<Work> queryAll(Integer page,Integer size,String order,Work work) {
		Sort sort = new Sort(Sort.Direction.DESC,order);
		PageRequest pageable = PageRequest.of(page, size,sort);
		
		Page<Work> workList = workRepostitory.findAll((root,query,CriteriaBuilder) -> {
			ArrayList<Predicate> arrayList = new ArrayList<>();
			//取其他表需取两次
			if (!StringUtils.isEmpty(work.getSecurityUser().getUserName())) {
				arrayList.add(CriteriaBuilder.equal(root.get("securityUser").get("userName"), work.getSecurityUser().getUserName()));
			}
			Predicate[] p = new Predicate[arrayList.size()];
			return CriteriaBuilder.and(arrayList.toArray(p));
		},pageable);
		
		return workList;
	}
	public boolean findByTicketNumber(Integer number) {
		return workRepostitory.findByticketNumber(number)!=null ? true:false;
	}
	
	public boolean add(Work work) {
		return workRepostitory.save(work)!=null ? true:false;
	}
	public void del(String id) {
		workRepostitory.deleteById(id);
	}
	public Work findById(String id) {
		return workRepostitory.findById(id).get();
	}
	public boolean update(Work work) {
		return workRepostitory.save(work)!=null ? true:false;
	}
}
