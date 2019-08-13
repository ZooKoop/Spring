package com.whale.services;

import com.whale.model.Work;
import com.whale.repostitory.WorkRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
@Service
public class WorkServices {
	@Autowired
	private WorkRepostitory workRepostitory;
	
	public Page<Work> queryInfo(Integer page,Integer size,Work work) {
		PageRequest pageable = PageRequest.of(page, size);
		Page<Work> workList = workRepostitory.findAll((root,query,CriteriaBuilder) -> {
			ArrayList<Predicate> arrayList = new ArrayList<>();
			//取其他表需取两次
//			if (!StringUtils.isEmpty(work.getSecurityUser().getUserName())) {
//				arrayList.add(CriteriaBuilder.equal(root.get("securityUser").get("userName").as(String.class), work.getSecurityUser().getUserName()));
//			}
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
	public List<Work> findTikcetAndIsColse(Work work) {
		List<Work> workList = workRepostitory.findAll((root,query,CriteriaBuilder) -> {
			List<Predicate> arrayList = new ArrayList<>();
			//0关1开
			String[] isClose = {"0","1"};
			if (!StringUtils.isEmpty(work.getTicketNumber())) {
				arrayList.add(CriteriaBuilder.equal(root.get("ticketNumber"), work.getTicketNumber()));
			}
			if (!StringUtils.isEmpty(work.getIsClose())) {
				In<Object> in = CriteriaBuilder.in(root.get("isClose"));
				for (String string : isClose) {
					in.value(string);
				}
				arrayList.add(in);
			}
			Predicate[] p = new Predicate[arrayList.size()];
			return CriteriaBuilder.and(arrayList.toArray(p));
		});
		
		return workList;
	}
	public Work findByTicketNumber(String number) {
		
		return workRepostitory.findByticketNumber(number);
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
	public List<Work> findAll() {
		return workRepostitory.findAll();
	}
	public List<Work> quAll(Work work) {
		// TODO Auto-generated method stub
		return workRepostitory.quAll(work.getSecurityUser().getUserName());
	}
	public void delete_All( List<String> ids) {
		for (String string : ids) {
			System.out.println(string);
		}
		List<Work> wList = workRepostitory.findAllById(ids);
		workRepostitory.deleteInBatch(wList);
	}
	public Work findByIsCloseAndTicketNumber(String close,String number) {
		return workRepostitory.findByIsCloseAndTicketNumber(close,number);
	}
}
