package com.whale.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.whale.model.Work;
import com.whale.repostitory.WorkRepostitory;
@Service
public class WorkServices {
	@Autowired
	private WorkRepostitory workRepostitory;
	
	public Page<Work> queryAll(Integer page,Integer size,Work work) {
		PageRequest pageable = PageRequest.of(page, size);
		Page<Work> workList = workRepostitory.findAll((root,query,CriteriaBuilder) -> {
			ArrayList<Predicate> arrayList = new ArrayList<>();
			//取其他表需取两次
			if (!StringUtils.isEmpty(work.getSecurityUser().getUserName())) {
				arrayList.add(CriteriaBuilder.equal(root.get("securityUser").get("userName").as(String.class), work.getSecurityUser().getUserName()));
			}
			if (!StringUtils.isEmpty(work.getTicketNumber())) {
				arrayList.add(CriteriaBuilder.like(root.get("ticketNumber").as(String.class), "%"+ work.getTicketNumber()+"%"));
			}
			if (!StringUtils.isEmpty(work.getIsClose())) {
				arrayList.add(CriteriaBuilder.equal(root.get("isClose"), work.getIsClose()));
			}
			Predicate[] p = new Predicate[arrayList.size()];
			return CriteriaBuilder.and(arrayList.toArray(p));
		},pageable);
		
		return workList;
	}
	public boolean findByTicketNumber(String number) {
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
	public List<Work> findAll(Work work) {
		return workRepostitory.findAll();
	}
	public List<Work> quAll(Work work) {
		// TODO Auto-generated method stub
		return workRepostitory.quAll(work.getSecurityUser().getUserName());
	}
}
